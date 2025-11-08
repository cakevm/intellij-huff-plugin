package com.github.com.cakevm.intellij_huff_plugin

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class HuffParserTest : BasePlatformTestCase() {

  fun testEmbedTableBuiltin() {
    val file =
      myFixture.configureByText(
        HuffFileType.INSTANCE,
        """
      #define table MY_TABLE {
          0x1234
          0x5678
      }

      #define macro MAIN() = takes(0) returns(0) {
          __EMBED_TABLE(MY_TABLE)
          __tablestart(MY_TABLE)
          stop
      }
      """
          .trimIndent(),
      )

    // Verify no parse errors
    val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
    assertTrue("File should parse without errors, but found: ${errors.map { it.errorDescription }}", errors.isEmpty())
  }

  fun testKeccak256Opcode() {
    val file =
      myFixture.configureByText(
        HuffFileType.INSTANCE,
        """
      #define macro MAIN() = takes(0) returns(0) {
          0x00 calldataload
          keccak256
          sha3
          stop
      }
      """
          .trimIndent(),
      )

    // Verify no parse errors
    val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
    assertTrue("File should parse without errors, but found: ${errors.map { it.errorDescription }}", errors.isEmpty())
  }

  fun testBuiltinsInIfStatement() {
    val file =
      myFixture.configureByText(
        HuffFileType.INSTANCE,
        """
      #define constant VALUE = 0x10

      #define macro TEST() = takes(0) returns(0) {
          if (true) {
              __RIGHTPAD(0x1234)
              [VALUE]
          }
          stop
      }
      """
          .trimIndent(),
      )

    // Verify no parse errors
    val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
    assertTrue("File should parse without errors, but found: ${errors.map { it.errorDescription }}", errors.isEmpty())
  }
}

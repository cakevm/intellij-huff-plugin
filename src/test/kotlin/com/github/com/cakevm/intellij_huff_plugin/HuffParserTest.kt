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

  fun testStringLiteralInConstantDefinition() {
    val file =
      myFixture.configureByText(
        HuffFileType.INSTANCE,
        """
      #define constant GREETING = "hello"
      #define constant MSG = "world"

      #define macro MAIN() = takes(0) returns(0) {
          __BYTES([GREETING])
          __BYTES([MSG])
          stop
      }
      """
          .trimIndent(),
      )

    // Verify no parse errors
    val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
    assertTrue("File should parse without errors, but found: ${errors.map { it.errorDescription }}", errors.isEmpty())
  }

  fun testConstantWithBuiltinInCodeTable() {
    val file =
      myFixture.configureByText(
        HuffFileType.INSTANCE,
        """
      #define constant C = __RIGHTPAD(0x1234)

      #define table MY_TABLE {
          [C]
          __LEFTPAD(0x5678)
      }

      #define macro MAIN() = takes(0) returns(0) {
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

  fun testConstantReferenceInVerbatim() {
    val file =
      myFixture.configureByText(
        HuffFileType.INSTANCE,
        """
      #define constant SIG = __FUNC_SIG("transfer(address,uint256)")
      #define constant MY_CONST = 0x1234

      #define macro MAIN() = takes(0) returns(0) {
          __VERBATIM([SIG])
          __VERBATIM([MY_CONST])
          __VERBATIM(0x6001600101)
          stop
      }
      """
          .trimIndent(),
      )

    // Verify no parse errors
    val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
    assertTrue("File should parse without errors, but found: ${errors.map { it.errorDescription }}", errors.isEmpty())
  }

  fun testUnreleasedFeaturesCombined() {
    val file =
      myFixture.configureByText(
        HuffFileType.INSTANCE,
        """
      #define constant GREETING = "hello"
      #define constant C = __RIGHTPAD(0x1234)
      #define constant SIG = __FUNC_SIG("balanceOf(address)")

      #define table DATA_TABLE {
          [C]
          __LEFTPAD(0xffff)
      }

      #define macro MAIN() = takes(0) returns(0) {
          __BYTES([GREETING])
          __VERBATIM([SIG])
          __tablestart(DATA_TABLE)
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

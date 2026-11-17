package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroArgumentReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabel
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/** A bare identifier passed to a macro must name a label, a constant, a macro or an opcode. */
class HuffMacroArgumentInspectionTest : BasePlatformTestCase() {

  override fun setUp() {
    super.setUp()
    myFixture.enableInspections(HuffResolveNameInspection::class.java, HuffUnusedElementInspection::class.java)
  }

  private fun problems(source: String): List<String> {
    myFixture.configureByText(HuffFileType.INSTANCE, source.trimIndent())
    return myFixture.doHighlighting().filter { it.severity >= HighlightSeverity.WARNING }.mapNotNull { it.description }
  }

  fun testUndefinedLabelIsReported() {
    val problems =
      problems(
        """
        #define macro MACRO(err) = takes(0) returns (0) {
            <err> jump
        }

        #define macro MAIN() = takes(0) returns (0) {
            MACRO(err)
        }
        """
      )
    assertEquals(listOf("'err' is neither a label, a constant nor a macro"), problems)
  }

  fun testMacroParameterIsNoValidBareArgument() {
    val problems =
      problems(
        """
        #define macro INNER(dest) = takes(0) returns (0) { <dest> jump }

        #define macro OUTER(dest) = takes(0) returns (0) {
            INNER(dest)
        }

        #define macro MAIN() = takes(0) returns (0) {
            OUTER(done)
            done:
        }
        """
      )
    assertEquals(listOf("'dest' is neither a label, a constant nor a macro"), problems)
  }

  fun testLabelsConstantsAndMacrosResolve() {
    val problems =
      problems(
        """
        #define constant SIZE = 0x20

        #define macro HELPER() = takes(0) returns (0) { stop }

        #define macro CALL(a, b, c) = takes(0) returns (0) {
            <a> jump <b> pop <c>()
        }

        #define macro MAIN() = takes(0) returns (0) {
            CALL(done, SIZE, HELPER)
            done:
        }
        """
      )
    assertEmpty(problems)

    val targets = PsiTreeUtil.findChildrenOfType(myFixture.file, HuffMacroArgumentReference::class.java).map { it.reference?.resolve() }
    assertInstanceOf(targets[0], HuffMacroLabel::class.java)
    assertInstanceOf(targets[1], HuffConstantDefinition::class.java)
    assertInstanceOf(targets[2], HuffMacroDefinition::class.java)
  }

  fun testOpcodesAndCalldataNeedNoDefinition() {
    val opcodes =
      (Json.parseToJsonElement(javaClass.getResource("/opcodes/instructions.json")!!.readText()) as JsonObject).keys + "calldata"
    val problems =
      problems(
        """
        #define macro TAKE(op) = takes(0) returns (0) { <op> }

        #define macro MAIN() = takes(0) returns (0) {
        ${opcodes.joinToString("\n") { "    TAKE($it)" }}
        }
        """
      )
    assertEmpty(problems)
  }
}

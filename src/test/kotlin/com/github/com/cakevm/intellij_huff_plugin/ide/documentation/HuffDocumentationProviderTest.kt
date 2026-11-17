package com.github.com.cakevm.intellij_huff_plugin.ide.documentation

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/** Opcode documentation is shown for instructions only, never for names that happen to be spelled like an opcode. */
class HuffDocumentationProviderTest : BasePlatformTestCase() {

  private val provider = HuffDocumentationProvider()

  private val opcodes: List<String> =
    Json.parseToJsonElement(javaClass.getResource("/opcodes/instructions.json")!!.readText()).let { (it as JsonObject).keys.toList() }

  /** Returns, for each occurrence of an opcode in [source], whether documentation is shown for it. */
  private fun documented(source: String): Map<String, Boolean> {
    myFixture.configureByText(HuffFileType.INSTANCE, source)
    val text = myFixture.file.text
    return opcodes.associateWith { opcode ->
      val offset = Regex("\\b${opcode}\\b").find(text)!!.range.first
      val element = provider.getCustomDocumentationElement(myFixture.editor, myFixture.file, myFixture.file.findElementAt(offset), offset)
      element?.let { provider.generateDoc(it, it) } != null
    }
  }

  private fun assertAll(expected: Boolean, documented: Map<String, Boolean>) {
    val wrong = documented.filterValues { it != expected }.keys
    assertTrue("Expected documentation to be ${if (expected) "shown" else "hidden"} for $wrong", wrong.isEmpty())
  }

  fun testInstructionsInMacroBodyAreDocumented() =
    assertAll(true, documented("#define macro MAIN() = takes(0) returns(0) {\n${opcodes.joinToString("\n")}\n}"))

  fun testDecoratorFlagsAreNotDocumented() =
    assertAll(false, documented(opcodes.joinToString("\n") { "#[$it(0x01)]" } + "\n#define test T() = {}"))

  fun testDecoratorValuesAreNotDocumented() =
    assertAll(false, documented("#[${opcodes.joinToString(", ") { "deal($it, 0)" }}]\n#define test T() = {}"))

  fun testMacroNamesAreNotDocumented() =
    assertAll(false, documented(opcodes.joinToString("\n") { "#define macro $it() = takes(0) returns(0) {}" }))
}

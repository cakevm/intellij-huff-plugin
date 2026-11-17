package com.github.com.cakevm.intellij_huff_plugin.ide.documentation

import com.github.com.cakevm.intellij_huff_plugin.language.psi.*
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import kotlinx.serialization.json.Json

class HuffDocumentationProvider : AbstractDocumentationProvider() {
  private val instructions: Map<String, InstructionDocumentation>
  private val builtins: Map<String, BuiltinFunctionDocumentation>

  init {
    val instructionsJson =
      javaClass.getResource("/opcodes/instructions.json")?.readText() ?: throw IllegalArgumentException("instructions.json not found")
    instructions = loadInstructionsFromJson(instructionsJson)

    val builtinsJson =
      javaClass.getResource("/builtins/functions.json")?.readText() ?: throw IllegalArgumentException("builtins/functions.json not found")
    builtins = loadBuiltinsFromJson(builtinsJson)
  }

  private fun loadInstructionsFromJson(jsonString: String): Map<String, InstructionDocumentation> {
    return Json.decodeFromString(jsonString)
  }

  private fun loadBuiltinsFromJson(jsonString: String): Map<String, BuiltinFunctionDocumentation> {
    return Json.decodeFromString(jsonString)
  }

  override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
    // Check for opcodes
    if (isOpcode(element)) {
      val instruction = instructions[element.text.lowercase()]
      if (instruction != null) {
        return instruction.toHtml()
      }
    }

    // Check for builtin functions
    val builtinName = getBuiltinFunctionName(element)
    if (builtinName != null) {
      val builtin = builtins[builtinName]
      if (builtin != null) {
        return builtin.toHtml()
      }
    }

    return null
  }

  /**
   * Whether the element is an instruction. Opcode names also serve as names, e.g. the `timestamp` flag in `#[timestamp(1)]` or a macro
   * called `pc`, and those are not instructions.
   */
  private fun isOpcode(element: PsiElement): Boolean {
    val opcode = PsiTreeUtil.getParentOfType(element, HuffOpcodeName::class.java, false) ?: return false
    return PsiTreeUtil.getParentOfType(opcode, HuffIdentifier::class.java) == null
  }

  private fun getBuiltinFunctionName(element: PsiElement): String? {
    // Check if element is within any builtin function PSI type
    val funcSigCall = PsiTreeUtil.getParentOfType(element, HuffBuiltinFnFuncSigCall::class.java)
    if (funcSigCall != null) return "__FUNC_SIG"

    val eventHashCall = PsiTreeUtil.getParentOfType(element, HuffBuiltinFnEventHashCall::class.java)
    if (eventHashCall != null) return "__EVENT_HASH"

    val errorCall = PsiTreeUtil.getParentOfType(element, HuffBuiltinFnErrorCall::class.java)
    if (errorCall != null) return "__ERROR"

    val assertPcCall = PsiTreeUtil.getParentOfType(element, HuffBuiltinFnAssertPcCall::class.java)
    if (assertPcCall != null) return "__ASSERT_PC"

    val argsStartCall = PsiTreeUtil.getParentOfType(element, HuffBuiltinFnArgsStartCall::class.java)
    if (argsStartCall != null) return "__argsstart"

    val tableCall = PsiTreeUtil.getParentOfType(element, HuffBuiltinFnTableCall::class.java)
    if (tableCall != null) {
      // Could be __tablesize, __tablestart, __EMBED_TABLE, or __codesize - check the actual text
      val text = element.text
      return when {
        text.contains("tablesize", ignoreCase = true) -> "__tablesize"
        text.contains("tablestart", ignoreCase = true) -> "__tablestart"
        text.contains("EMBED_TABLE", ignoreCase = false) -> "__EMBED_TABLE"
        text.contains("codesize", ignoreCase = true) -> "__codesize"
        else -> null
      }
    }

    val builtinFnName = PsiTreeUtil.getParentOfType(element, HuffBuiltinFnName::class.java)
    if (builtinFnName != null) {
      // Could be __codesize, __LEFTPAD, __RIGHTPAD, __VERBATIM, __BYTES
      val text = element.text
      return when {
        text.contains("codesize", ignoreCase = true) -> "__codesize"
        text.contains("LEFTPAD", ignoreCase = false) -> "__LEFTPAD"
        text.contains("RIGHTPAD", ignoreCase = false) -> "__RIGHTPAD"
        text.contains("VERBATIM", ignoreCase = false) -> "__VERBATIM"
        text.contains("BYTES", ignoreCase = false) -> "__BYTES"
        else -> null
      }
    }

    return null
  }

  override fun getCustomDocumentationElement(editor: Editor, file: PsiFile, contextElement: PsiElement?, targetOffset: Int): PsiElement? {
    // Check for opcodes
    if (contextElement != null && isOpcode(contextElement)) {
      return contextElement
    }

    // Check for builtin functions
    if (contextElement != null && getBuiltinFunctionName(contextElement) != null) {
      return contextElement
    }

    return null
  }
}

package com.github.com.cakevm.intellij_huff_plugin.ide.documentation

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffOpcodeName
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import kotlinx.serialization.json.Json

class HuffDocumentationProvider : AbstractDocumentationProvider() {
  private val instructions: Map<String, InstructionDocumentation>

  init {
    val jsonString = javaClass.getResource("/opcodes/instructions.json")?.readText() ?: throw IllegalArgumentException("File not found")
    instructions = loadInstructionsFromJson(jsonString)
  }

  private fun loadInstructionsFromJson(jsonString: String): Map<String, InstructionDocumentation> {
    return Json.decodeFromString(jsonString)
  }

  override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
    val parent = PsiTreeUtil.getParentOfType(element, HuffOpcodeName::class.java)
    if (parent is HuffOpcodeName) {
      val instruction = instructions[element.text.lowercase()]
      if (instruction != null) {
        return instruction.toHtml()
      }
    }
    return null
  }

  override fun getCustomDocumentationElement(editor: Editor, file: PsiFile, contextElement: PsiElement?, targetOffset: Int): PsiElement? {
    val parent = PsiTreeUtil.getParentOfType(contextElement, HuffOpcodeName::class.java)
    if (parent is HuffOpcodeName) {
      return contextElement
    }
    return null
  }
}

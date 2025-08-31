package com.github.com.cakevm.intellij_huff_plugin.ide.quickfix

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class HuffRemoveIncludeQuickFix(import: HuffIncludeDirective, val type: Type) : LocalQuickFixOnPsiElement(import) {

  enum class Type {
    Invalid,
    Unused,
  }

  override fun getText(): String {
    return when (type) {
      Type.Invalid -> "Remove invalid include"
      Type.Unused -> "Remove unused include"
    }
  }

  override fun getFamilyName(): String = "Remove include"

  override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
    val element = startElement as? HuffIncludeDirective ?: return

    // Check if there's a newline after the include directive and remove it too
    val nextSibling = element.nextSibling
    if (nextSibling?.text?.startsWith("\n") == true) {
      nextSibling.delete()
    }

    element.delete()
  }
}

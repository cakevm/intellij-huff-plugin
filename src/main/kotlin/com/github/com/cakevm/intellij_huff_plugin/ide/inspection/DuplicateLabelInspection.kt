package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroBody
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabelIdentifier
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil

class DuplicateLabelInspection : LocalInspectionTool() {
  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : PsiElementVisitor() {
      override fun visitElement(element: PsiElement) {
        if (element is HuffMacroBody) {
          val labels = PsiTreeUtil.findChildrenOfType(element, HuffMacroLabelIdentifier::class.java)
          val duplicateLabels = labels.groupBy { it.identifier.stringIdentifier?.text }.filter { it.value.size > 1 }
          duplicateLabels.forEach { (name, elements) ->
            elements.forEach { duplicateElement -> holder.registerProblem(duplicateElement, "Duplicate label: $name") }
          }
        }
      }
    }
  }
}

package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroBody
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabel
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabelGoTo
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil

class MissingLabelInspection : LocalInspectionTool() {
  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : PsiElementVisitor() {
      override fun visitElement(element: PsiElement) {
        if (element is HuffMacroBody) {
          val labels =
            PsiTreeUtil.findChildrenOfType(element, HuffMacroLabel::class.java).associate { it.identifier.stringIdentifier?.text to true }
          val labelReferences = PsiTreeUtil.findChildrenOfType(element, HuffMacroLabelGoTo::class.java)
          for (label in labelReferences) {
            if (!labels.containsKey(label.identifier.text)) {
              holder.registerProblem(label, "Label not found")
            }
          }
        }
      }
    }
  }
}

package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffVisitor
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffResolver
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.search.searches.ReferencesSearch

class HuffUnusedElementInspection : LocalInspectionTool() {
  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : HuffVisitor() {
      override fun visitIncludeDirective(o: HuffIncludeDirective) {
        val used = HuffResolver.collectUsedElements(o)
        if (used.isEmpty()) {
          holder.registerProblem(o, "Unused import directive", ProblemHighlightType.LIKE_UNUSED_SYMBOL)
        }
      }

      override fun visitConstantDefinition(o: HuffConstantDefinition) {
        o.constantDefinitionIdentifier.identifier.checkForUsage(o, holder, "Constant '${o.name}' is never used")
      }
    }
  }
}

private fun PsiElement.checkForUsage(o: PsiElement, holder: ProblemsHolder, msg: String) {
  if (ReferencesSearch.search(o).findFirst() == null) {
    holder.registerProblem(this, msg, ProblemHighlightType.LIKE_UNUSED_SYMBOL)
  }
}

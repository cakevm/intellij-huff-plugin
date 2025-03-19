package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.*
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffResolver
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.search.searches.ReferencesSearch

class HuffUnusedElementInspection : LocalInspectionTool() {
  private val defaultMacros = arrayOf("MAIN", "CONSTRUCTOR", "FALLBACK").associateWith { true }

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

      override fun visitMacroDefinition(o: HuffMacroDefinition) {
        if (defaultMacros[o.name] == null && o.macroType.text == "macro") {
          o.macroIdentifier.checkForUsage(o, holder, "Macro '${o.name}' is never used")
        }
      }

      override fun visitFunctionAbiDefinition(o: HuffFunctionAbiDefinition) {
        o.identifier.checkForUsage(o, holder, "Function ABI '${o.name}' is never used")
      }

      override fun visitEventAbiDefinition(o: HuffEventAbiDefinition) {
        o.identifier.checkForUsage(o, holder, "Event ABI '${o.name}' is never used")
      }

      override fun visitErrorDefinition(o: HuffErrorDefinition) {
        o.identifier.checkForUsage(o, holder, "Error '${o.name}' is never used")
      }

      override fun visitTableDefinition(o: HuffTableDefinition) {
        o.identifier.checkForUsage(o, holder, "Table '${o.name}' is never used")
      }
    }
  }
}

private fun PsiElement.checkForUsage(o: PsiElement, holder: ProblemsHolder, msg: String) {
  if (ReferencesSearch.search(o).findFirst() == null) {
    holder.registerProblem(this, msg, ProblemHighlightType.LIKE_UNUSED_SYMBOL)
  }
}

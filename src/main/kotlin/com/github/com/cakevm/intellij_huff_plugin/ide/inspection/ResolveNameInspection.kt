package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroConstantReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabelGoTo
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffVisitor
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffReferenceElement
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor

class ResolveHuffNameInspection : LocalInspectionTool() {
  override fun getDisplayName(): String = ""

  private val buildInFns =
    arrayOf(
        "calldata",
        "__tablesize",
        "__codesize",
        "__tablestart",
        "__FUNC_SIG",
        "__EVENT_HASH",
        "__ERROR",
        "__RIGHTPAD",
        "__CODECOPY_DYN_ARG",
        "__VERBATIM",
      )
      .associateWith { true }

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : HuffVisitor() {
      override fun visitIncludeDirective(o: HuffIncludeDirective) {
        o.includePath.let { path ->
          if (path.reference?.resolve() == null) {
            holder.registerProblem(path, "'${path.text}' cannot be resolved", ProblemHighlightType.WARNING)
          }
        }
      }

      override fun visitMacroLabelGoTo(element: HuffMacroLabelGoTo) {
        checkReference(element) {
          holder.registerProblem(element, "'${element.identifier.text}' label is undefined", ProblemHighlightType.WARNING)
        }
      }

      override fun visitMacroConstantReference(element: HuffMacroConstantReference) {
        checkReference(element) {
          holder.registerProblem(element, "'${element.identifier.text}' constant is undefined", ProblemHighlightType.WARNING)
        }
      }

      override fun visitMacroCall(element: HuffMacroCall) {
        checkReference(element) {
          if (buildInFns[element.macroCallIdentifier.text] == null) {
            holder.registerProblem(element, "'${element.macroCallIdentifier.text}' macro is undefined", ProblemHighlightType.WARNING)
          }
        }
      }
    }
  }
}

private fun checkReference(element: HuffReferenceElement, report: () -> Unit) {
  if (element.reference != null) {
    // resolve return either 1 reference or null, and because our resolve is not perfect we can return a number
    // of references, so instead of showing false positives we can use multiResolve
    val results = element.reference?.multiResolve(false)
    if (results.isNullOrEmpty()) {
      report()
    }
  }
}

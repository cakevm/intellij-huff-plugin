package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.ide.quickfix.HuffRemoveIncludeQuickFix
import com.github.com.cakevm.intellij_huff_plugin.language.psi.*
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffReferenceElement
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor

class HuffResolveNameInspection : LocalInspectionTool() {
  override fun getDisplayName(): String = ""

  private val builtinFns =
    arrayOf(
        "calldata",
        "__tablesize",
        "__codesize",
        "__tablestart",
        "__FUNC_SIG",
        "__EVENT_HASH",
        "__ERROR",
        "__LEFTPAD",
        "__RIGHTPAD",
        "__CODECOPY_DYN_ARG",
        "__VERBATIM",
        "__BYTES",
      )
      .associateWith { true }

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : HuffVisitor() {
      override fun visitIncludeDirective(o: HuffIncludeDirective) {
        o.includePath.let { path ->
          if (path.reference?.resolve() == null) {
            holder.registerProblem(
              path,
              "'${path.text}' cannot be resolved",
              ProblemHighlightType.WARNING,
              HuffRemoveIncludeQuickFix(o, HuffRemoveIncludeQuickFix.Type.Invalid),
            )
          }
        }
      }

      override fun visitMacroLabelGoTo(element: HuffMacroLabelGoTo) {
        checkReference(element) {
          holder.registerProblem(element, "'${element.identifier.text}' label is undefined", ProblemHighlightType.WARNING)
        }
      }

      override fun visitConstantReference(element: HuffConstantReference) {
        checkReference(element) {
          holder.registerProblem(element, "'${element.identifier.text}' constant is undefined", ProblemHighlightType.WARNING)
        }
      }

      override fun visitForLoopVariableReference(element: HuffForLoopVariableReference) {
        checkReference(element) {
          holder.registerProblem(element, "'${element.identifier.text}' for-loop variable is undefined", ProblemHighlightType.WARNING)
        }
      }

      override fun visitMacroCall(element: HuffMacroCall) {
        checkReference(element) {
          if (builtinFns[element.macroCallIdentifier.text] == null) {
            holder.registerProblem(element, "'${element.macroCallIdentifier.text}' macro is undefined", ProblemHighlightType.WARNING)
          }
        }
      }

      override fun visitBuiltinFnFuncSigCall(o: HuffBuiltinFnFuncSigCall) {
        checkReference(o) {
          if (o.quotedString == null) {
            holder.registerProblem(o, "'${o.identifier?.text}' function ABI is undefined", ProblemHighlightType.WARNING)
          }
        }
      }

      override fun visitBuiltinFnEventHashCall(o: HuffBuiltinFnEventHashCall) {
        checkReference(o) {
          if (o.quotedString == null) {
            holder.registerProblem(o, "'${o.identifier?.text}' event ABI is undefined", ProblemHighlightType.WARNING)
          }
        }
      }

      override fun visitBuiltinFnErrorCall(o: HuffBuiltinFnErrorCall) {
        checkReference(o) { holder.registerProblem(o, "'${o.identifier.text}' error is undefined", ProblemHighlightType.WARNING) }
      }

      override fun visitBuiltinFnTableCall(o: HuffBuiltinFnTableCall) {
        checkReference(o) { holder.registerProblem(o, "'${o.identifier.text}' table is undefined", ProblemHighlightType.WARNING) }
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

package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffVisitor
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor

class ResolveNameInspection : LocalInspectionTool() {
  override fun getDisplayName(): String = ""

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : HuffVisitor() {
      override fun visitIncludeDirective(o: HuffIncludeDirective) {
        o.includePath.let { path ->
          if (path.reference?.resolve() == null) {
            holder.registerProblem(path, "'${path.text}' cannot be resolved", ProblemHighlightType.WARNING)
          }
        }
      }
    }
  }
}

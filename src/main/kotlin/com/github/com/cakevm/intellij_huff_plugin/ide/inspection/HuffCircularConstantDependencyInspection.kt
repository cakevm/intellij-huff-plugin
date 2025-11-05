package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffVisitor
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil

class HuffCircularConstantDependencyInspection : LocalInspectionTool() {

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : HuffVisitor() {
      override fun visitConstantDefinition(o: HuffConstantDefinition) {
        val visited = mutableSetOf<String>()
        val currentPath = mutableSetOf<String>()
        val constantName = o.name ?: return

        if (hasCircularDependency(o, visited, currentPath)) {
          holder.registerProblem(
            o.constantDefinitionIdentifier,
            "Circular constant dependency detected for '${constantName}'",
            ProblemHighlightType.ERROR,
          )
        }
      }
    }
  }

  private fun hasCircularDependency(
    constant: HuffConstantDefinition,
    visited: MutableSet<String>,
    currentPath: MutableSet<String>,
  ): Boolean {
    val constantName = constant.name ?: return false

    // If we've seen this constant in the current path, we have a cycle
    if (constantName in currentPath) {
      return true
    }

    // If we've already fully explored this constant, no need to revisit
    if (constantName in visited) {
      return false
    }

    // Add to current path and visited set
    currentPath.add(constantName)
    visited.add(constantName)

    // Find all constant references in this constant's expression
    val references = PsiTreeUtil.findChildrenOfType(constant.constantExpression, HuffConstantReference::class.java)

    for (ref in references) {
      val referencedName = ref.identifier?.text ?: continue
      val referencedConstant = ref.reference?.resolve() as? HuffConstantDefinition ?: continue

      // Recursively check the referenced constant
      if (hasCircularDependency(referencedConstant, visited, currentPath)) {
        return true
      }
    }

    // Remove from current path (backtrack)
    currentPath.remove(constantName)

    return false
  }
}

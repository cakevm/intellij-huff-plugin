package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffVisitor
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil

/**
 * Detects string constants used directly without __BYTES builtin.
 *
 * In huff-neo, string constants must be used with __BYTES to convert them to UTF-8 bytes. Direct usage like [STRING_CONST] in macro bodies
 * causes compilation errors.
 *
 * Valid: __BYTES([GREETING]) where GREETING = "hello" Invalid: [GREETING] directly in macro body
 */
class HuffStringConstantDirectPushInspection : LocalInspectionTool() {

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : HuffVisitor() {
      override fun visitConstantReference(element: HuffConstantReference) {
        val resolved = element.reference?.resolve() as? HuffConstantDefinition ?: return

        // Check if the resolved constant contains a string literal
        if (isStringConstant(resolved)) {
          // Check if this reference is NOT inside a __BYTES builtin call
          if (!isInsideBytesCall(element)) {
            holder.registerProblem(
              element,
              "String constant '${element.identifier.text}' must be used with __BYTES([${element.identifier.text}])",
              ProblemHighlightType.ERROR,
            )
          }
        }
      }
    }
  }

  /** Checks if a constant definition contains a string literal. */
  private fun isStringConstant(constant: HuffConstantDefinition): Boolean {
    // Look for QUOTED_STRING token in the constant expression
    val quotedString = constant.constantExpression.node.findChildByType(HuffElementTypes.QUOTED_STRING)
    return quotedString != null
  }

  /** Checks if a constant reference is inside a __BYTES builtin function call. */
  private fun isInsideBytesCall(element: HuffConstantReference): Boolean {
    // Find the parent macro call
    val macroCall = PsiTreeUtil.getParentOfType(element, HuffMacroCall::class.java) ?: return false

    // Check if it's a __BYTES call
    return macroCall.macroCallIdentifier.text == "__BYTES"
  }
}

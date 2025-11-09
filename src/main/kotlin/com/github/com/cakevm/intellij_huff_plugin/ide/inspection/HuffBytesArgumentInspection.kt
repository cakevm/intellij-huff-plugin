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
 * Validates arguments passed to the __BYTES builtin function.
 *
 * According to huff-neo grammar, __BYTES accepts:
 * - String literals: __BYTES("hello")
 * - String constant references: __BYTES([GREETING]) where GREETING = "hello"
 *
 * This inspection detects:
 * 1. Non-string constants used with __BYTES
 * 2. Empty string literals in __BYTES
 */
class HuffBytesArgumentInspection : LocalInspectionTool() {

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : HuffVisitor() {
      override fun visitMacroCall(element: HuffMacroCall) {
        // Only check __BYTES calls
        if (element.macroCallIdentifier.text != "__BYTES") {
          return
        }

        val params = element.macroCallParameterList

        for (param in params) {
          // Check for constant references
          val constRef = PsiTreeUtil.findChildOfType(param, HuffConstantReference::class.java)
          if (constRef != null) {
            val resolved = constRef.reference?.resolve() as? HuffConstantDefinition
            if (resolved != null && !isStringConstant(resolved)) {
              holder.registerProblem(
                constRef,
                "__BYTES requires a string constant, but '${constRef.identifier.text}' is a hex/numeric constant",
                ProblemHighlightType.ERROR,
              )
            }
            continue
          }

          // Check for direct string literals
          val quotedString = param.node.findChildByType(HuffElementTypes.QUOTED_STRING)
          if (quotedString != null) {
            val text = quotedString.text
            // Remove surrounding quotes
            val content =
              if (text.length >= 2) {
                text.substring(1, text.length - 1)
              } else {
                ""
              }

            if (content.isEmpty()) {
              holder.registerProblem(param, "Empty string passed to __BYTES", ProblemHighlightType.ERROR)
            }
          }
        }
      }
    }
  }

  /** Checks if a constant definition contains a string literal. */
  private fun isStringConstant(constant: HuffConstantDefinition): Boolean {
    val quotedString = constant.constantExpression.node.findChildByType(HuffElementTypes.QUOTED_STRING)
    return quotedString != null
  }
}

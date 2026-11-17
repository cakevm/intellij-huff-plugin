package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffForLoopStatement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroParameterReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReferenceBase
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

/**
 * Resolves `<name>` to the loop variable of the innermost enclosing for loop with that name, or otherwise to the parameter of the
 * containing macro.
 */
class HuffMacroParameterReferenceImpl(element: HuffMacroParameterReference) : HuffReferenceBase<HuffMacroParameterReference>(element) {

  override fun calculateDefaultRangeInElement(): TextRange {
    // The reference is the name between < and >
    return TextRange.from(element.parameterName.startOffsetInParent, element.parameterName.textLength)
  }

  override fun singleResolve(): PsiElement? {
    val name = element.parameterName.text
    return enclosingLoopVariables().find { it.name == name } ?: macroParameters().find { it.name == name }
  }

  override fun getVariants(): Array<Any> = (enclosingLoopVariables() + macroParameters()).toTypedArray()

  /** Loop variables of all enclosing for loops, innermost first, so an inner loop variable shadows an outer one. */
  private fun enclosingLoopVariables(): List<HuffNamedElement> =
    generateSequence(PsiTreeUtil.getParentOfType(element, HuffForLoopStatement::class.java)) {
        PsiTreeUtil.getParentOfType(it, HuffForLoopStatement::class.java)
      }
      .mapNotNull { it.forLoopVariable }
      .toList()

  private fun macroParameters(): List<HuffNamedElement> =
    PsiTreeUtil.getParentOfType(element, HuffMacroDefinition::class.java)?.macroParameters?.macroParameterList ?: emptyList()
}

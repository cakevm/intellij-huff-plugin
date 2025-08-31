package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroParameterReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReferenceBase
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class HuffMacroParameterReferenceImpl(element: HuffMacroParameterReference) : HuffReferenceBase<HuffMacroParameterReference>(element) {

  override fun calculateDefaultRangeInElement(): TextRange {
    // The reference is the identifier between < and >
    return TextRange(1, element.textLength - 1)
  }

  override fun singleResolve(): PsiElement? {
    // Find the containing macro definition
    val macroDefinition = PsiTreeUtil.getParentOfType(element, HuffMacroDefinition::class.java) ?: return null

    // Get the parameter name we're looking for
    val parameterName = element.identifier.text

    // Look through the macro's parameters
    val parameters = macroDefinition.macroParameters?.macroParameterList ?: return null

    return parameters.find { it.name == parameterName }
  }

  override fun getVariants(): Array<Any> {
    // Find the containing macro definition
    val macroDefinition = PsiTreeUtil.getParentOfType(element, HuffMacroDefinition::class.java) ?: return emptyArray()

    // Return all parameters from the containing macro
    val parameters = macroDefinition.macroParameters?.macroParameterList ?: return emptyArray()

    return parameters.toTypedArray()
  }
}

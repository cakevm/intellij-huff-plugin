package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroParameterInvocation
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReferenceBase
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class HuffMacroParameterInvocationReference(element: HuffMacroParameterInvocation) :
  HuffReferenceBase<HuffMacroParameterInvocation>(element) {

  override fun calculateDefaultRangeInElement(): TextRange {
    // The reference is the identifier between < and > (before the parentheses)
    val identifierStart = element.text.indexOf('<') + 1
    val identifierEnd = element.text.indexOf('>')
    return if (identifierStart > 0 && identifierEnd > identifierStart) {
      TextRange(identifierStart, identifierEnd)
    } else {
      TextRange(1, element.identifier.textLength + 1)
    }
  }

  override fun singleResolve(): PsiElement? {
    // Find the containing macro definition
    val macroDefinition = PsiTreeUtil.getParentOfType(element, HuffMacroDefinition::class.java) ?: return null

    // Get the parameter name we're looking for
    val parameterName = element.identifier.text

    // Look through the macro's parameters for a matching parameter
    // that should be a macro (being invoked with parentheses)
    val parameters = macroDefinition.macroParameters?.macroParameterList ?: return null

    return parameters.find { it.name == parameterName }
  }

  override fun getVariants(): Array<Any> {
    // Find the containing macro definition
    val macroDefinition = PsiTreeUtil.getParentOfType(element, HuffMacroDefinition::class.java) ?: return emptyArray()

    // Return all parameters from the containing macro that could be invoked
    val parameters = macroDefinition.macroParameters?.macroParameterList ?: return emptyArray()

    return parameters.toTypedArray()
  }
}

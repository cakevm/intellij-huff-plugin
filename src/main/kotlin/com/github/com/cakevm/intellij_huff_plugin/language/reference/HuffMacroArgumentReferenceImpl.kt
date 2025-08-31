package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroArgumentReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.HuffNamedElementIndex
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReferenceBase
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil

class HuffMacroArgumentReferenceImpl(element: HuffMacroArgumentReference) : HuffReferenceBase<HuffMacroArgumentReference>(element) {

  override fun calculateDefaultRangeInElement(): TextRange {
    return TextRange(0, element.textLength)
  }

  override fun singleResolve(): PsiElement? {
    val argumentName = element.identifier.text

    // First, check if this is a parameter in the current macro
    val containingMacro = PsiTreeUtil.getParentOfType(element, HuffMacroDefinition::class.java)
    if (containingMacro != null) {
      val parameters = containingMacro.macroParameters?.macroParameterList ?: emptyList()
      val parameter = parameters.find { it.name == argumentName }
      if (parameter != null) {
        return parameter
      }
    }

    // Otherwise, resolve as a macro name (for first-class macro passing)
    // Use the stub index to find macro definitions with this name
    val elements = StubIndex.getElements(HuffNamedElementIndex.KEY, argumentName, element.project, null, HuffNamedElement::class.java)

    // Filter for macro definitions
    return elements.filterIsInstance<HuffMacroDefinition>().firstOrNull()
  }

  override fun getVariants(): Array<Any> {
    val variants = mutableListOf<Any>()

    // Add parameters from containing macro
    val containingMacro = PsiTreeUtil.getParentOfType(element, HuffMacroDefinition::class.java)
    if (containingMacro != null) {
      val parameters = containingMacro.macroParameters?.macroParameterList ?: emptyList()
      variants.addAll(parameters)
    }

    // Add all available macros from the project
    val allKeys = StubIndex.getInstance().getAllKeys(HuffNamedElementIndex.KEY, element.project)
    for (key in allKeys) {
      val elements = StubIndex.getElements(HuffNamedElementIndex.KEY, key, element.project, null, HuffNamedElement::class.java)
      variants.addAll(elements.filterIsInstance<HuffMacroDefinition>())
    }

    return variants.toTypedArray()
  }
}

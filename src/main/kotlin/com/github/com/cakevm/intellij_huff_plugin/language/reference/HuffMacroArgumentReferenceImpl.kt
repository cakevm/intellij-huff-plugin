package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroArgumentReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.HuffNamedElementIndex
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReferenceBase
import com.intellij.openapi.util.TextRange
import com.intellij.psi.stubs.StubIndex

/** Resolves a bare identifier passed to a macro, e.g. `MACRO(err)`, to the label, constant or macro of that name. */
class HuffMacroArgumentReferenceImpl(element: HuffMacroArgumentReference) : HuffReferenceBase<HuffMacroArgumentReference>(element) {

  override fun calculateDefaultRangeInElement(): TextRange {
    return TextRange(0, element.textLength)
  }

  override fun multiResolve(): Collection<HuffNamedElement> = HuffResolver.resolveTypeNameUsingImports(element)

  override fun getVariants(): Array<Any> {
    val variants = mutableListOf<Any>()

    // Add all available macros from the project
    val allKeys = StubIndex.getInstance().getAllKeys(HuffNamedElementIndex.KEY, element.project)
    for (key in allKeys) {
      val elements = StubIndex.getElements(HuffNamedElementIndex.KEY, key, element.project, null, HuffNamedElement::class.java)
      variants.addAll(elements.filterIsInstance<HuffMacroDefinition>())
    }

    return variants.toTypedArray()
  }
}

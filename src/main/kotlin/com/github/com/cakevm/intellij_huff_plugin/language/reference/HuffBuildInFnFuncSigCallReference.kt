package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffBuildInFnFuncSigCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.parentRelativeRange
import com.intellij.openapi.util.TextRange

class HuffBuildInFnFuncSigCallReference(element: HuffBuildInFnFuncSigCall) :
  HuffReferenceBase<HuffBuildInFnFuncSigCall>(element), HuffReference {

  override fun calculateDefaultRangeInElement(): TextRange {
    return element.referenceNameElement.parentRelativeRange
  }

  override fun multiResolve(): Collection<HuffNamedElement> {
    return HuffResolver.resolveTypeNameUsingImports(this.element)
  }

  override fun resolve(): HuffNamedElement? {
    return this.multiResolve().firstOrNull()
  }
}

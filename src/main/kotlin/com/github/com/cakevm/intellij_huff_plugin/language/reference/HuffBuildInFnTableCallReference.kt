package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffBuildInFnTableCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.parentRelativeRange
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReferenceBase
import com.intellij.openapi.util.TextRange

class HuffBuildInFnTableCallReference(element: HuffBuildInFnTableCall) : HuffReferenceBase<HuffBuildInFnTableCall>(element), HuffReference {

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
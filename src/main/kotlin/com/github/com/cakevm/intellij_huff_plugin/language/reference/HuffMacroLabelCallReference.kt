package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabelCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.parentRelativeRange
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

class HuffMacroLabelCallReference(element: HuffMacroLabelCall) : HuffReferenceBase<HuffMacroLabelCall>(element), HuffReference {

  override fun calculateDefaultRangeInElement(): TextRange {
    return element.referenceNameElement.parentRelativeRange
  }

  override fun multiResolve(): Collection<PsiElement> {
    return emptyList()
  }
}

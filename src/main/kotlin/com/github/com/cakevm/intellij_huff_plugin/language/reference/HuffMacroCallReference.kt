package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.parentRelativeRange
import com.intellij.openapi.util.TextRange

class HuffMacroCallReference(element: HuffMacroCall) : HuffReferenceBase<HuffMacroCall>(element), HuffReference {

  override fun calculateDefaultRangeInElement(): TextRange {
    return element.referenceNameElement.parentRelativeRange
  }
}

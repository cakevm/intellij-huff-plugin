package com.github.com.cakevm.intellij_huff_plugin.language.psi.element

import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffCallable
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffReferenceElement

interface HuffMacroCallElement : HuffReferenceElement {

  fun resolveDefinitions(): List<HuffCallable>?
}

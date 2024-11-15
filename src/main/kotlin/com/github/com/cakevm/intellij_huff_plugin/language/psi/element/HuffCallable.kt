package com.github.com.cakevm.intellij_huff_plugin.language.psi.element

interface HuffCallable {
  fun getName(): String?

  fun resolveElement(): HuffNamedElement?
}

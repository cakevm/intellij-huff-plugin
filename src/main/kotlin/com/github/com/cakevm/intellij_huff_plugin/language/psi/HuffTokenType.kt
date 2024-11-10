package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class HuffTokenType(@NonNls debugName: String) : IElementType(debugName, HuffLanguage.INSTANCE) {
  override fun toString(): String {
    return "HuffTokenType." + super.toString()
  }
}

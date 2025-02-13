package com.github.com.cakevm.intellij_huff_plugin.language

import com.intellij.lang.Language

class HuffLanguage : Language(NAME) {
  override fun getDisplayName() = NAME

  override fun isCaseSensitive() = true

  companion object {
    const val NAME = "Huff"
    @JvmStatic val INSTANCE = HuffLanguage()
  }
}

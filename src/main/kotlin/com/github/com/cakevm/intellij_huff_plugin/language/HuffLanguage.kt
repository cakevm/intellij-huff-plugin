package com.github.com.cakevm.intellij_huff_plugin.language

import com.intellij.lang.Language

object HuffLanguage : Language("Huff") {
  private fun readResolve(): Any = HuffLanguage

  override fun getDisplayName() = "Huff"
}

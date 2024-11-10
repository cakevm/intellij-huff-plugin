package com.github.com.cakevm.intellij_huff_plugin.ide.completion

import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.MACRO_BODY
import com.intellij.codeInsight.completion.*
import com.intellij.patterns.PlatformPatterns.psiElement

class HuffOpcodeNameCompletionContributor : CompletionContributor() {
  init {
    extend(CompletionType.BASIC, OPCODE_NAME_PATTERN, HuffOpcodeCompletionProvider())
  }

  companion object {
    private val OPCODE_NAME_PATTERN = psiElement().withLanguage(HuffLanguage.INSTANCE).inside(psiElement(MACRO_BODY))
  }
}

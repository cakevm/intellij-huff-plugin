package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroType
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase

class HuffMacroReference(method: HuffMacroType, private var textRange: TextRange) : PsiReferenceBase<HuffMacroType>(method, textRange) {
  override fun resolve(): PsiElement? {
    TODO("Not yet implemented")
  }
}

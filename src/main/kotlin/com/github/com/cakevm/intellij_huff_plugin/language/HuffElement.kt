package com.github.com.cakevm.intellij_huff_plugin.language

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference

interface HuffElement : PsiElement {
  override fun getReference(): PsiReference?
}

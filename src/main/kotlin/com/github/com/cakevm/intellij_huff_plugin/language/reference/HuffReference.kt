package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.HuffElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReference

interface HuffReference : PsiPolyVariantReference {
  override fun getElement(): HuffElement

  override fun resolve(): HuffElement?

  fun multiResolve(): Collection<PsiElement>
}

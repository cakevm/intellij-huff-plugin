package com.github.com.cakevm.intellij_huff_plugin.language.psi.element

import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReference
import com.intellij.psi.PsiElement

interface HuffReferenceElement : HuffNamedElement {
  val referenceNameElement: PsiElement
  val referenceName: String

  override fun getReference(): HuffReference?
}

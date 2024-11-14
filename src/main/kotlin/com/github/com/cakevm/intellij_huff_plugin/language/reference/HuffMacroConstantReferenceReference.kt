package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroConstantReference
import com.intellij.psi.PsiElement

class HuffMacroConstantReferenceReference(element: HuffMacroConstantReference) :
  HuffReferenceBase<HuffMacroConstantReference>(element), HuffReference {

  override fun multiResolve(): Collection<PsiElement> {
    return emptyList()
  }
}

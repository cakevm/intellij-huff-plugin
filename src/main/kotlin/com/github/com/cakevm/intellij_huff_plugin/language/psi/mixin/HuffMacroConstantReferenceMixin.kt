package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIdentifier
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroConstantReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffMacroLabelGoToElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffCallable
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffMacroConstantReferenceReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffMacroConstantReferenceMixin(node: ASTNode) :
  HuffNamedElementImpl(node), HuffMacroLabelGoToElement, HuffMacroConstantReference {

  private fun getReferenceNameElement(expr: HuffIdentifier): PsiElement {
    return expr
  }

  override val referenceNameElement: PsiElement
    get() = getReferenceNameElement(this.identifier)

  override fun getReference(): HuffReference = HuffMacroConstantReferenceReference(this as HuffMacroConstantReference)

  override fun resolveDefinitions(): List<HuffCallable>? {
    return emptyList()
  }

  override val referenceName: String
    get() = referenceNameElement.text
}

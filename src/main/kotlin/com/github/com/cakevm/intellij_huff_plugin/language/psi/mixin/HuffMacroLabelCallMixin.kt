package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIdentifier
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabelCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffMacroLabelCallElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffCallable
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffMacroLabelCallReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffMacroLabelCallMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffMacroLabelCallElement, HuffMacroLabelCall {

  private fun getReferenceNameElement(expr: HuffIdentifier): PsiElement {
    return expr
  }

  override val referenceNameElement: PsiElement
    get() = getReferenceNameElement(this.identifier)

  override fun getReference(): HuffReference = HuffMacroLabelCallReference(this as HuffMacroLabelCall)

  override fun resolveDefinitions(): List<HuffCallable>? {
    return emptyList<HuffCallable>()
  }

  override val referenceName: String
    get() = referenceNameElement.text
}

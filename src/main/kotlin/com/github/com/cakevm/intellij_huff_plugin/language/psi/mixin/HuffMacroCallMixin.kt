package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroCallIdentifier
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffMacroCallElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffCallable
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffMacroCallReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffMacroCallMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffMacroCallElement, HuffMacroCall {

  private fun getReferenceNameElement(expr: HuffMacroCallIdentifier): PsiElement {
    return expr
  }

  override val referenceNameElement: PsiElement
    get() = getReferenceNameElement(this.macroCallIdentifier)

  override fun getReference(): HuffReference = HuffMacroCallReference(this as HuffMacroCall)

  override fun resolveDefinitions(): List<HuffCallable>? {
    return emptyList<HuffCallable>()
  }

  override val referenceName: String
    get() = referenceNameElement.text
}

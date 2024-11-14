package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffMacroCallElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffMacroCallReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffMacroCallMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffMacroCallElement, HuffMacroCall {

  override val referenceNameElement: PsiElement
    get() = this.macroCallIdentifier

  override val referenceName: String
    get() = referenceNameElement.text

  override fun getName(): String? = referenceName

  override fun getReference(): HuffReference = HuffMacroCallReference(this as HuffMacroCall)
}

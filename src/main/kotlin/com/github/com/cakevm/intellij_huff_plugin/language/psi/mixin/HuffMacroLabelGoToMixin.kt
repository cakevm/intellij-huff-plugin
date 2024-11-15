package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabelGoTo
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffMacroLabelGoToReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffMacroLabelGoToMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffMacroLabelGoTo {

  override val referenceNameElement: PsiElement
    get() = this.identifier

  override val referenceName: String
    get() = referenceNameElement.text

  override fun getName(): String? = referenceName

  override fun getReference(): HuffReference = HuffMacroLabelGoToReference(this as HuffMacroLabelGoTo)
}

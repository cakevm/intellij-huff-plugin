package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantReferenceExpression
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffConstantReferenceExpressionReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffConstantReferenceExpressionMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffConstantReferenceExpression {

  override val referenceNameElement: PsiElement
    get() = this.identifier

  override val referenceName: String
    get() = referenceNameElement.text

  override fun getName(): String? = referenceName

  override fun getReference(): HuffReference = HuffConstantReferenceExpressionReference(this as HuffConstantReferenceExpression)
}

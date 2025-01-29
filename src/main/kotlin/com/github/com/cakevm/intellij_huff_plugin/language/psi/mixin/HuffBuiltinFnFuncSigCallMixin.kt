package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffBuiltinFnFuncSigCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffBuiltinFnFuncSigCallReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffBuiltinFnFuncSigCallMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffBuiltinFnFuncSigCall {

  override val referenceNameElement: PsiElement
    get() = this.identifier ?: this

  override val referenceName: String
    get() = referenceNameElement.text

  override fun getName(): String? = referenceName

  override fun getReference(): HuffReference = HuffBuiltinFnFuncSigCallReference(this as HuffBuiltinFnFuncSigCall)
}

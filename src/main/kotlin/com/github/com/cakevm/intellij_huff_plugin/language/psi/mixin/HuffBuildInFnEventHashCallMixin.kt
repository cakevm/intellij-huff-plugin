package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffBuildInFnEventHashCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffBuildInFnEventHashCallReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffBuildInFnEventHashCallMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffBuildInFnEventHashCall {

  override val referenceNameElement: PsiElement
    get() = this.identifier ?: this

  override val referenceName: String
    get() = referenceNameElement.text

  override fun getName(): String? = referenceName

  override fun getReference(): HuffReference = HuffBuildInFnEventHashCallReference(this as HuffBuildInFnEventHashCall)
}

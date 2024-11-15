package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffBuildInFnErrorCall
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffBuildInFnErrorCallElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffBuildInFnErrorCallReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffBuildInFnErrorCallMixin(node: ASTNode) :
  HuffNamedElementImpl(node), HuffBuildInFnErrorCall, HuffBuildInFnErrorCallElement {

  override val referenceNameElement: PsiElement
    get() = this.identifier ?: this

  override val referenceName: String
    get() = referenceNameElement.text

  override fun getName(): String? = referenceName

  override fun getReference(): HuffReference = HuffBuildInFnErrorCallReference(this as HuffBuildInFnErrorCall)
}

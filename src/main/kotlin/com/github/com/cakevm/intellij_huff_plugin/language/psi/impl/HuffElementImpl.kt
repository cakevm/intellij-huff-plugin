package com.github.com.cakevm.intellij_huff_plugin.language.psi.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffElement
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffReference
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class HuffElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), HuffElement {
  override fun getReference(): HuffReference? = null
}

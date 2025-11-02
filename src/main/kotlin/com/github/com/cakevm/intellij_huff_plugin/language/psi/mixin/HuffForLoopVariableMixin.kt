package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffForLoopVariable
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffForLoopVariableMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffForLoopVariable {
  override fun getName(): String? = identifier?.text

  override fun setName(name: String): PsiElement = this

  override fun getNameIdentifier(): PsiElement? = identifier
}

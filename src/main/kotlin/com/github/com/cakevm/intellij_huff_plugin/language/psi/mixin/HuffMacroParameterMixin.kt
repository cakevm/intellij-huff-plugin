package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroParameter
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffNamedElementImpl
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class HuffMacroParameterMixin(node: ASTNode) : HuffNamedElementImpl(node), HuffMacroParameter {
  override fun getName(): String? = identifier?.text

  override fun setName(name: String): PsiElement = this

  override fun getNameIdentifier(): PsiElement? = identifier
}

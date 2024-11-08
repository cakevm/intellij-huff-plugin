package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

class Expression(node: ASTNode) : HuffPsiNode(node) {
  companion object {
    fun fromAst(node: ASTNode): PsiElement {
      return Expression(node)
    }
  }
}

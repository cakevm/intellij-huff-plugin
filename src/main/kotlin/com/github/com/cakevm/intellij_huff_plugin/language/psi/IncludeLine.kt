package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.intellij.lang.ASTNode

class IncludeLine(node: ASTNode) : HuffPsiNode(node) {

  companion object {
    fun fromAst(node: ASTNode): IncludeLine {
      return IncludeLine(node)
    }
  }
}

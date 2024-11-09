package com.github.com.cakevm.intellij_huff_plugin.language.psi.definition

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffPsiNode
import com.intellij.lang.ASTNode

class MacroDefinition(node: ASTNode) : HuffPsiNode(node) {

  companion object {
    fun fromAst(node: ASTNode): MacroDefinition {
      return MacroDefinition(node)
    }
  }
}

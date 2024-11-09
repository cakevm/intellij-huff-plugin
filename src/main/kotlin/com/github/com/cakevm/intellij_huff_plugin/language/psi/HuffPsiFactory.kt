package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.github.com.cakevm.intellij_huff_plugin.language.psi.definition.IncludeDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.definition.MacroDefinition
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class HuffPsiFactory {
  companion object {
    fun createNode(node: ASTNode): PsiElement {
      val elType = node.elementType
      if (elType is TokenIElementType) {
        return ANTLRPsiNode(node)
      }
      if (elType !is RuleIElementType) {
        return ANTLRPsiNode(node)
      }
      return fromRule(node, elType)
    }

    private fun fromRule(node: ASTNode, elType: RuleIElementType): PsiElement {
      // println("Constructing PSI element for node: $node")
      return when (elType.ruleIndex) {
        HuffParser.RULE_includeDefinition -> IncludeDefinition.fromAst(node)
        HuffParser.RULE_opcodeName -> OpcodeNamePsiNode(node)
        HuffParser.RULE_macroDefinition -> MacroDefinition.fromAst(node)

        else -> HuffPsiNode(node)
      }
    }
  }
}

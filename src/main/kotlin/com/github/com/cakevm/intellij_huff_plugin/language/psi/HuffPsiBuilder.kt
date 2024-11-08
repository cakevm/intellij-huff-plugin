package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiErrorElementImpl
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

class HuffPsiErrorElement(s: String) : PsiErrorElementImpl(s)

open class HuffPsiNode(node: ASTNode) : ANTLRPsiNode(node) {
  val antlrRule: Int = HuffPsiBuilder.getAntlrRule(node)
}

/**
 * Takes AST created by the ANTLR generated parser, and turns it into something we can work with.
 */
class HuffPsiBuilder {
  companion object {
    /** Creates a new PsiElement based on the given ASTNode. */
    fun from(node: ASTNode): PsiElement {
      return when (val elType = node.elementType) {
        is TokenIElementType -> {
          // Raw tokens should not be present in the final PSI tree and create an PSI error
          HuffPsiErrorElement("Unexpected TOKEN type: $elType")
        }
        is RuleIElementType -> {
          fromRule(node, elType)
        }

        else -> HuffPsiErrorElement("Unexpected AST element: $elType")
      }
    }

    private fun fromRule(node: ASTNode, elType: RuleIElementType): PsiElement {
      // println("Constructing PSI element for node: $node")
      return when (elType.ruleIndex) {
        HuffParser.RULE_includeDefinition -> IncludeLine.fromAst(node)
        // HuffParser.RULE_defineFunctionDefinition -> Func(node)
        // HuffParser.RULE_funcAttrExternal -> FuncAttr(node)

        else -> HuffPsiNode(node)
      }
    }

    fun getAntlrRule(node: ASTNode): Int {
      return when (val elementType = node.elementType) {
        is RuleIElementType -> elementType.ruleIndex
        else -> -1
      }
    }
  }
}

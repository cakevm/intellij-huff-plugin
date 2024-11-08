package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.intellij.lang.ASTNode
import org.antlr.intellij.adaptor.lexer.RuleIElementType
import org.antlr.intellij.adaptor.lexer.TokenIElementType

/** Predicate helpers for filtering AST stuff. For PSI filtering use [HuffPsiPredicate] */
interface HuffASTPredicate {
  companion object {
    /** Predicate for filtering node.getChildren(null) for expressions */
    fun isExpression(child: ASTNode): Boolean =
      when (val elType = child.elementType) {
        is RuleIElementType -> elType.ruleIndex == HuffParser.RULE_expression
        else -> false
      }

    /**
     * Predicate for filtering node.getChildren(null) for patterns (expressions suitable for
     * function args and matching)
     */
    fun isPattern(child: ASTNode): Boolean =
      when (val elType = child.elementType) {
        is RuleIElementType -> elType.ruleIndex == HuffParser.RULE_parameterList
        else -> false
      }

    /** Predicate for filtering node.getChildren(null) for identifier names */
    fun isIdentifier(child: ASTNode): Boolean =
      when (val elType = child.elementType) {
        is RuleIElementType -> elType.ruleIndex == HuffParser.RULE_identifier
        else -> false
      }

    /** Predicate for filtering node.getChildren(null) for types */
    fun isType(child: ASTNode): Boolean =
      when (val elType = child.elementType) {
        is RuleIElementType -> elType.ruleIndex == HuffParser.RULE_typeName
        else -> false
      }

    /** Predicate for filtering node.getChildren(null) for function attrs */
    fun isFunctionAttr(child: ASTNode): Boolean =
      when (val elType = child.elementType) {
        is RuleIElementType -> MacroParameter.isFunctionAttrRuleType(elType.ruleIndex)
        else -> false
      }

    /** Predicate for ASTNode to be a specific token */
    fun isToken(node: ASTNode?, tok: Int): Boolean =
      when (val elType = node?.elementType) {
        is TokenIElementType -> elType.antlrTokenType == tok
        else -> false
      }
  }
}

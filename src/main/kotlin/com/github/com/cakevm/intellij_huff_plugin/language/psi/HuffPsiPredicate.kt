package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.intellij.psi.PsiElement

/** Predicate helpers for filtering PSI stuff. For AST filtering use [HuffASTPredicate] */
interface HuffPsiPredicate {
  companion object {
    // Predicate for filtering expression nodes
    fun isExpression(node: PsiElement): Boolean =
      (node as HuffPsiNode).antlrRule == HuffParser.RULE_expression

    // Predicate for filtering type literals
    fun isType(node: PsiElement): Boolean =
      (node as HuffPsiNode).antlrRule == HuffParser.RULE_typeName

    // Predicate for filtering identifiers
    fun isIdentifier(node: PsiElement): Boolean =
      (node as HuffPsiNode).antlrRule == HuffParser.RULE_identifier

    // Predicate for filtering function parameter group (contains funcParameter)
    fun isFuncParameters(node: PsiElement): Boolean =
      (node as HuffPsiNode).antlrRule == HuffParser.RULE_parameterList

    // Predicate for filtering individual function parameters
    fun isFuncParameter(node: PsiElement): Boolean =
      (node as HuffPsiNode).antlrRule == HuffParser.RULE_parameterDeclaration
  }
}

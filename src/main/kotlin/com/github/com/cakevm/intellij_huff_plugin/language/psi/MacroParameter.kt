package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.intellij.lang.ASTNode
import com.intellij.util.containers.stream
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode

/**
 * A Function Attribute PSI, e.g. @external(language, "module", "func"). Related stuff is reachable
 * dynamically through queries.
 */
open class MacroParameter(node: ASTNode) : ANTLRPsiNode(node) {
  val attrName: ASTNode
    get() = node.firstChildNode

  val attrArgs: List<Expression>
    get() =
      children
        .stream()
        .filter(HuffPsiPredicate::isExpression)
        .map { el -> el as Expression }
        .toList()

  class FuncAttrObsolete(node: ASTNode) : MacroParameter(node)

  class FuncAttrExternal(node: ASTNode) : MacroParameter(node)

  companion object {
    /** Construct a function attr from AST. Detect known types and pre-build them. */

    // A rule id check whether a given id is one of supported function attribute types
    fun isFunctionAttrRuleType(ruleIndex: Int): Boolean =
      ruleIndex == HuffParser.RULE_parameterDeclaration
  }
}

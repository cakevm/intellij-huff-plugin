package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffForLoopStatement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffForLoopVariableReference
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReferenceBase
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class HuffForLoopVariableReferenceImpl(element: HuffForLoopVariableReference) : HuffReferenceBase<HuffForLoopVariableReference>(element) {

  override fun calculateDefaultRangeInElement(): TextRange {
    // The reference is the identifier between < and >
    return TextRange(1, element.textLength - 1)
  }

  override fun singleResolve(): PsiElement? {
    // Get the variable name we're looking for
    val variableName = element.identifier.text

    // Walk up the PSI tree to find all containing for-loops
    // Support shadowing: innermost loop variable wins
    var context: PsiElement? = element
    while (context != null) {
      val forLoopStatement = PsiTreeUtil.getParentOfType(context, HuffForLoopStatement::class.java, true)
      if (forLoopStatement != null) {
        val loopVariable = forLoopStatement.forLoopVariable
        if (loopVariable?.name == variableName) {
          return loopVariable // Found! Return the innermost matching variable (shadowing)
        }
        // Continue searching outer loops
        context = forLoopStatement.parent
      } else {
        break
      }
    }

    return null // No matching variable found in any enclosing loop
  }

  override fun getVariants(): Array<Any> {
    // Collect all loop variables from all enclosing for-loops
    val variants = mutableListOf<PsiElement>()
    var context: PsiElement? = element
    while (context != null) {
      val forLoopStatement = PsiTreeUtil.getParentOfType(context, HuffForLoopStatement::class.java, true)
      if (forLoopStatement != null) {
        forLoopStatement.forLoopVariable?.let { variants.add(it) }
        context = forLoopStatement.parent
      } else {
        break
      }
    }

    return variants.toTypedArray()
  }
}

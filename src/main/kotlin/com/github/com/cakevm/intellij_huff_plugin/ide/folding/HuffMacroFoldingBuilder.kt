package com.github.com.cakevm.intellij_huff_plugin.ide.folding

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.*
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

class HuffMacroFoldingBuilder : FoldingBuilderEx() {
  override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
    return root.children
      .filter { it.elementType == BODY_STATEMENT && it.children.size > 1 }
      .map {
        val newRange = TextRange(it.firstChild.textOffset, it.lastChild.textOffset + it.lastChild.textLength)
        FoldingDescriptor(it.node, newRange)
      }
      .toTypedArray()
  }

  override fun getPlaceholderText(node: ASTNode): String {
    return node.firstChildNode.text + "..." + node.lastChildNode.text
  }

  override fun isCollapsedByDefault(node: ASTNode): Boolean {
    return false
  }
}

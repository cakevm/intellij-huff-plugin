package com.github.com.cakevm.intellij_huff_plugin.ide.folding

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.COMMENT
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

class HuffBlockCommentFoldingBuilder : FoldingBuilderEx() {
  override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
    return root.children.filter { it.elementType == COMMENT }.map { FoldingDescriptor(it.node, it.textRange) }.toTypedArray()
  }

  override fun getPlaceholderText(node: ASTNode): String {
    return "/* ... */"
  }

  override fun isCollapsedByDefault(node: ASTNode): Boolean {
    return false
  }
}

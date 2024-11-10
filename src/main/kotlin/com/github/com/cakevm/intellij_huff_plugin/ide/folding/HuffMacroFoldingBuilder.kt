package com.github.com.cakevm.intellij_huff_plugin.ide.folding

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroBody
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class HuffMacroFoldingBuilder : FoldingBuilderEx() {
  override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
    return PsiTreeUtil.findChildrenOfType(root, HuffMacroBody::class.java).map { FoldingDescriptor(it.node, it.textRange) }.toTypedArray()
  }

  override fun getPlaceholderText(node: ASTNode): String {
    return node.firstChildNode.text + "..." + node.lastChildNode.text
  }

  override fun isCollapsedByDefault(node: ASTNode): Boolean {
    return false
  }
}

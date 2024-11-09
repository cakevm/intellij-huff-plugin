package com.github.com.cakevm.intellij_huff_plugin.ide.folding

import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.psi.PsiElement
import org.antlr.intellij.adaptor.xpath.XPath

class HuffBlockCommentFoldingBuilder : FoldingBuilderEx() {
  override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
    return XPath.findAll(HuffLanguage.INSTANCE, root, "/huffFileRoot/blockComment")
      .filter { it.textLength > 0 }
      .map {
        FoldingDescriptor(
          it.node,
          it.textRange,
          FoldingGroup.newGroup("huff-block-comment-" + it.textRange.startOffset + "-" + it.textRange.endOffset),
        )
      }
      .toTypedArray()
  }

  override fun getPlaceholderText(node: ASTNode): String {
    return "/* ... */"
  }

  override fun isCollapsedByDefault(node: ASTNode): Boolean {
    return false
  }
}

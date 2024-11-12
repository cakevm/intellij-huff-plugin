package com.github.com.cakevm.intellij_huff_plugin.ide.folding

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroBody
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabelIdentifier
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class HuffMacroAndLabelFoldingBuilder : FoldingBuilderEx() {
  override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
    return PsiTreeUtil.findChildrenOfType(root, HuffMacroBody::class.java)
      .flatMap { macro ->
        val descriptors = mutableListOf<FoldingDescriptor>()
        descriptors.add(FoldingDescriptor(macro.node, macro.textRange))

        var labels = PsiTreeUtil.findChildrenOfType(macro, HuffMacroLabelIdentifier::class.java)
        // labels = labels.sortedBy { it.textRange.startOffset }

        var previousLabel = labels.firstOrNull()
        // label1: -> label2: -> label3: -> ...
        for (label in labels.drop(1)) {
          if (previousLabel != null && label.node.treePrev != null) {
            if (previousLabel.textRange.startOffset + previousLabel.text.length < label.node.treePrev.textRange.startOffset) {
              descriptors.add(
                FoldingDescriptor(
                  previousLabel.node,
                  TextRange(previousLabel.textRange.startOffset + previousLabel.text.length, label.node.treePrev.textRange.startOffset),
                )
              )
            }
          }
          previousLabel = label
        }
        // label until end of body
        if (previousLabel != null && previousLabel.textRange.startOffset + previousLabel.text.length < (macro.textRange.endOffset - 1)) {
          descriptors.add(
            FoldingDescriptor(
              previousLabel.node,
              TextRange(previousLabel.textRange.startOffset + previousLabel.text.length, macro.textRange.endOffset - 1),
            )
          )
        }
        descriptors
      }
      .toTypedArray()
  }

  override fun getPlaceholderText(node: ASTNode): String {
    when (node.elementType) {
      HuffElementTypes.MACRO_BODY -> return "{ ... }"
      HuffElementTypes.MACRO_LABEL_IDENTIFIER -> return " ..."
      else -> return "a..."
    }
  }

  override fun isCollapsedByDefault(node: ASTNode): Boolean {
    return false
  }
}

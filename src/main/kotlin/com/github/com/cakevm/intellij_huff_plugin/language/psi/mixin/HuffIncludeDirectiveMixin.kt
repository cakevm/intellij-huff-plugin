package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementBase
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.github.com.cakevm.intellij_huff_plugin.language.psi.getTextRangeWithoutQuote
import com.github.com.cakevm.intellij_huff_plugin.language.psi.importedPsiFile
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffIncludeReference
import com.github.com.cakevm.intellij_huff_plugin.language.util.projectFilePath
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiReference
import javax.swing.Icon

abstract class HuffIncludeDirectiveMixin(node: ASTNode) : HuffElementBase(node), HuffIncludeDirective, ItemPresentation {
  override fun getReference(): PsiReference? {
    return this.quotedString.getTextRangeWithoutQuote().let { HuffIncludeReference(this, it) }
  }

  override fun getPresentableText(): String? {
    return importedPsiFile()?.projectFilePath() ?: "Invalid import"
  }

  override fun getIcon(b: Boolean): Icon? {
    return null
  }

  override fun getPresentation(): ItemPresentation? {
    return this
  }
}

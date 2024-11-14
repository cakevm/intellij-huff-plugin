package com.github.com.cakevm.intellij_huff_plugin.language.psi.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.IDENTIFIER
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffPsiFactory
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

abstract class HuffNamedElementImpl(node: ASTNode) : HuffElementImpl(node), HuffNamedElement, PsiNameIdentifierOwner {
  override fun getNameIdentifier(): PsiElement? = findChildByType(IDENTIFIER)

  override fun getName(): String? {
    return nameIdentifier?.text
  }

  override fun setName(name: String): PsiElement? {
    nameIdentifier?.replace(HuffPsiFactory(project).createIdentifier(name))
    return this
  }

  override fun getNavigationElement(): PsiElement = nameIdentifier ?: this

  override fun getTextOffset(): Int = nameIdentifier?.textOffset ?: super.getTextOffset()
}

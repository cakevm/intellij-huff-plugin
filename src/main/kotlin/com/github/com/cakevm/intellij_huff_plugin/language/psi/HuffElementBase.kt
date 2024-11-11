package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation

abstract class HuffElementBase(node: ASTNode) : ASTWrapperPsiElement(node) {
  override fun getPresentation(): ItemPresentation? {
    return if (this is ItemPresentation) this else null
  }
}

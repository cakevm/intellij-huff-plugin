package com.github.com.cakevm.intellij_huff_plugin.ide.structure

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFile
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.util.PsiTreeUtil

class HuffStructureViewElement(private val element: NavigatablePsiElement) : StructureViewTreeElement, SortableTreeElement {
  override fun getPresentation(): ItemPresentation = element.presentation ?: TODO()

  override fun navigate(requestFocus: Boolean) = element.navigate(requestFocus)

  override fun canNavigate(): Boolean = element.canNavigate()

  override fun canNavigateToSource(): Boolean = element.canNavigateToSource()

  override fun getValue(): Any = element

  override fun getAlphaSortKey(): String = element.name ?: ""

  override fun getChildren(): Array<TreeElement> {
    return when (element) {
      is HuffFile -> {
        PsiTreeUtil.findChildrenOfType(element, HuffMacroDefinition::class.java).map { HuffStructureViewElement(it) }.toTypedArray()
      }
      else -> {
        emptyArray()
      }
    }
  }
}

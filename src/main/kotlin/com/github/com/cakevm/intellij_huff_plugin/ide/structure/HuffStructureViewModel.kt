package com.github.com.cakevm.intellij_huff_plugin.ide.structure

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

class HuffStructureViewModel(psiFile: PsiFile, editor: Editor?) :
  StructureViewModelBase(psiFile, editor, HuffStructureViewElement(psiFile)), StructureViewModel.ElementInfoProvider {

  override fun getSorters() = arrayOf(Sorter.ALPHA_SORTER)

  override fun isAlwaysShowsPlus(element: StructureViewTreeElement?): Boolean = false

  override fun isAlwaysLeaf(element: StructureViewTreeElement?): Boolean = false
}

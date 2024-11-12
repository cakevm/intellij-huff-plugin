package com.github.com.cakevm.intellij_huff_plugin.ide.structure

import com.intellij.ide.structureView.*
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

class HuffStructureViewFactory : PsiStructureViewFactory {
  override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder {
    println(psiFile::class.java)
    return object : TreeBasedStructureViewBuilder() {
      // override fun isRootNodeShown() = false

      override fun createStructureViewModel(editor: Editor?): StructureViewModel {
        return HuffStructureViewModel(psiFile, editor)
      }
    }
  }
}

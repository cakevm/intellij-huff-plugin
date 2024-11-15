package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.ide.inspection.fix.IncludeFileAction
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFile
import com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin.HuffIncludePathMixin
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.source.tree.LeafElement

class HuffIncludePathReference(element: HuffIncludePathMixin) : HuffReferenceBase<HuffIncludePathMixin>(element) {
  override fun singleResolve(): PsiElement? {
    val importText = element.text
    if (importText.length < 2) {
      return null
    }
    val path = importText.substring(1, importText.length - 1)
    return findImportFile(element.containingFile.originalFile.virtualFile, path)?.let {
      PsiManager.getInstance(element.project).findFile(it)
    }
  }

  companion object {
    fun findImportFile(file: VirtualFile, path: String): VirtualFile? {
      return file.findFileByRelativePath("../$path")
    }
  }

  override fun doRename(identifier: PsiElement, newName: String) {
    if (identifier !is LeafElement) {
      return
    }
    val renamedElement = resolve()
    if (renamedElement !is HuffFile) {
      return
    }
    val name = renamedElement.name
    val currentPath: String? = (identifier as PsiElement).text
    if (currentPath == null) {
      return
    }
    val newImportPath = currentPath.replace(name, newName)
    identifier.replaceWithText(newImportPath)
  }

  override fun bindToElement(element: PsiElement): PsiElement {
    val file = element as? HuffFile ?: return element
    val newPath = IncludeFileAction.buildImportPath(element.project, this.element.containingFile.virtualFile, file.virtualFile)
    val identifier = this.element.referenceNameElement as? LeafElement ?: return element
    return identifier.replaceWithText("\"$newPath\"").psi ?: element
  }
}

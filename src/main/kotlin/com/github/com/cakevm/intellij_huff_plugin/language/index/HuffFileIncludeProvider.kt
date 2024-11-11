package com.github.com.cakevm.intellij_huff_plugin.language.index

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFile
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.github.com.cakevm.intellij_huff_plugin.language.psi.importPathString
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.impl.include.FileIncludeInfo
import com.intellij.psi.impl.include.FileIncludeProvider
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.Consumer
import com.intellij.util.indexing.FileContent

class HuffFileIncludeProvider : FileIncludeProvider() {
  override fun getId(): String {
    return HuffLanguage.INSTANCE.id
  }

  override fun acceptFile(virtualFile: VirtualFile): Boolean {
    return FileTypeRegistry.getInstance().isFileOfType(virtualFile, HuffFileType.INSTANCE)
  }

  override fun registerFileTypesUsedForIndexing(fileTypeSink: Consumer<in FileType>) {
    fileTypeSink.consume(HuffFileType.INSTANCE)
  }

  override fun getIncludeInfos(fileContent: FileContent): Array<FileIncludeInfo> {
    val file = fileContent.psiFile
    if (file !is HuffFile) {
      return emptyArray()
    }

    return PsiTreeUtil.findChildrenOfType(file, HuffIncludeDirective::class.java)
      .mapNotNull { import -> import.importPathString()?.let { FileIncludeInfo(it) } }
      .toTypedArray()
  }
}

package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class HuffFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, HuffLanguage.INSTANCE) {
  override fun getFileType(): FileType = HuffFileType.INSTANCE
}

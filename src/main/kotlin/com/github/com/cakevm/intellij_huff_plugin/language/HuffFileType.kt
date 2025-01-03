package com.github.com.cakevm.intellij_huff_plugin.language

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.vfs.VirtualFile
import java.nio.charset.StandardCharsets.UTF_8
import javax.swing.Icon

class HuffFileType : LanguageFileType(HuffLanguage.INSTANCE) {

  override fun getName(): String = HuffLanguage.NAME

  override fun getDescription(): String = "Huff source file"

  override fun getDefaultExtension(): String = "huff"

  override fun getIcon(): Icon = HuffIcons.HUFF_16x16

  override fun getCharset(file: VirtualFile, content: ByteArray): String = UTF_8.name()

  companion object {
    @JvmStatic val INSTANCE = HuffFileType()
  }
}

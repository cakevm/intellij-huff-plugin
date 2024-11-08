package com.github.com.cakevm.intellij_huff_plugin.language

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object HuffFileType : LanguageFileType(HuffLanguage.INSTANCE) {
  override fun getName(): String = HuffLanguage.NAME

  override fun getDescription(): String = "Huff source file"

  override fun getDefaultExtension(): String = "huff"

  override fun getIcon(): Icon = HuffIcons.HUFF_16x16
}

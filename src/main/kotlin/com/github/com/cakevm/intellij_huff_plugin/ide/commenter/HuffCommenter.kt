package com.github.com.cakevm.intellij_huff_plugin.ide.commenter

import com.intellij.lang.Commenter

class HuffCommenter : Commenter {
  override fun getLineCommentPrefixes(): MutableList<String> = mutableListOf("//", "///")

  override fun getLineCommentPrefix(): String = "//"

  override fun getBlockCommentPrefix(): String = "/*"

  override fun getBlockCommentSuffix(): String = "*/"

  override fun getCommentedBlockCommentPrefix(): String? = null

  override fun getCommentedBlockCommentSuffix(): String? = null
}

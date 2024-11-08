package com.github.com.cakevm.intellij_huff_plugin.language

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object HuffIcons {
  val HUFF_16x16 = load("/icons/huff.svg")

  private fun load(path: String): Icon = IconLoader.getIcon(path, HuffIcons::class.java)
}

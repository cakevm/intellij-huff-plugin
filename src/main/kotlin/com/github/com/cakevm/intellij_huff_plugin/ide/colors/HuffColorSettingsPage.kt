package com.github.com.cakevm.intellij_huff_plugin.ide.colors

import com.github.com.cakevm.intellij_huff_plugin.HuffBundle
import com.github.com.cakevm.intellij_huff_plugin.ide.highlight.HuffHighlighter
import com.github.com.cakevm.intellij_huff_plugin.language.HuffIcons
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.io.StreamUtil

class HuffColorSettingsPage : ColorSettingsPage {
  override fun getDisplayName() = HuffBundle.message("settings.huff.color.scheme.title")

  override fun getIcon() = HuffIcons.HUFF_16x16

  override fun getAttributeDescriptors() = ATTRS

  override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

  override fun getHighlighter() = HuffHighlighter()

  override fun getAdditionalHighlightingTagToDescriptorMap() = ANNOTATOR_TAGS

  override fun getDemoText() = DEMO_TEXT
}

private val ATTRS: Array<AttributesDescriptor> = HuffColor.entries.map { it.attributesDescriptor }.toTypedArray()
private val ANNOTATOR_TAGS: Map<String, TextAttributesKey> = HuffColor.entries.associateBy({ it.name }, { it.textAttributesKey })
private val DEMO_TEXT: String by lazy {
  val stream = HuffColorSettingsPage::class.java.classLoader.getResourceAsStream("highlighterDemoText.huff")
  val reader = stream?.bufferedReader()
  val text = reader?.readText() ?: ""
  StreamUtil.convertSeparators(text)
}

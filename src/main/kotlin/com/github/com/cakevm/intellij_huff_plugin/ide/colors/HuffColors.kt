package com.github.com.cakevm.intellij_huff_plugin.ide.colors

import com.github.com.cakevm.intellij_huff_plugin.HuffBundle
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.util.NlsContexts.AttributeDescriptor
import java.util.function.Supplier

enum class HuffColor(
  humanName: Supplier<@AttributeDescriptor String>,
  default: TextAttributesKey? = null,
) {
  COMMENT(HuffBundle.messagePointer("settings.huff.color.line_comment"), Default.LINE_COMMENT),
  BLOCK_COMMENT(
    HuffBundle.messagePointer("settings.huff.color.block_comment"),
    Default.BLOCK_COMMENT,
  ),
  HEXCODE(HuffBundle.messagePointer("settings.huff.color.hexcode"), Default.NUMBER),
  DEFINE(HuffBundle.messagePointer("settings.huff.color.define"), Default.KEYWORD),
  INCLUDE(HuffBundle.messagePointer("settings.huff.color.include"), Default.CLASS_REFERENCE),
  BRACES(HuffBundle.messagePointer("settings.huff.color.braces"), Default.BRACES);

  val textAttributesKey = TextAttributesKey.createTextAttributesKey("run.huff.$name", default)
  val attributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}

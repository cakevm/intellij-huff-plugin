package com.github.com.cakevm.intellij_huff_plugin.ide.colors

import com.github.com.cakevm.intellij_huff_plugin.HuffBundle
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.util.NlsContexts.AttributeDescriptor
import java.util.function.Supplier

enum class HuffColor(humanName: Supplier<@AttributeDescriptor String>, default: TextAttributesKey? = null) {
  // token based highlighting
  COMMENT(HuffBundle.messagePointer("settings.huff.color.line_comment"), Default.LINE_COMMENT),
  BLOCK_COMMENT(HuffBundle.messagePointer("settings.huff.color.block_comment"), Default.BLOCK_COMMENT),
  STRING(HuffBundle.messagePointer("settings.huff.color.string"), Default.STRING),
  DECIMAL(HuffBundle.messagePointer("settings.huff.color.number"), Default.NUMBER),
  HEXCODE(HuffBundle.messagePointer("settings.huff.color.hexcode"), Default.NUMBER),
  DEFINE(HuffBundle.messagePointer("settings.huff.color.define"), Default.KEYWORD),
  INCLUDE(HuffBundle.messagePointer("settings.huff.color.include"), Default.CLASS_REFERENCE),
  BRACES(HuffBundle.messagePointer("settings.huff.color.braces"), Default.BRACES),
  OPERATOR(HuffBundle.messagePointer("settings.huff.color.operator"), Default.OPERATION_SIGN),
  BUILTIN_FN(HuffBundle.messagePointer("settings.huff.color.builtin_fn"), Default.STATIC_METHOD),
  ARITHMETIC_AND_LOGICAL(HuffBundle.messagePointer("settings.huff.color.arithmetic_and_logical"), Default.HIGHLIGHTED_REFERENCE),
  STACK_MANAGEMENT(HuffBundle.messagePointer("settings.huff.color.stack_management"), Default.HIGHLIGHTED_REFERENCE),
  ENVIRONMENTAL_INFORMATION(HuffBundle.messagePointer("settings.huff.color.environmental_information"), Default.HIGHLIGHTED_REFERENCE),
  STORAGE_AND_MEMORY_ACCESS(HuffBundle.messagePointer("settings.huff.color.storage_and_memory_access"), Default.HIGHLIGHTED_REFERENCE),
  FLOW_CONTROL(HuffBundle.messagePointer("settings.huff.color.flow_control"), Default.HIGHLIGHTED_REFERENCE),
  SYSTEM_AND_CALL(HuffBundle.messagePointer("settings.huff.color.system_and_call"), Default.HIGHLIGHTED_REFERENCE),
  LOGGING(HuffBundle.messagePointer("settings.huff.color.logging"), Default.HIGHLIGHTED_REFERENCE),
  MISCELLANEOUS(HuffBundle.messagePointer("settings.huff.color.miscellaneous"), Default.HIGHLIGHTED_REFERENCE),
  GAS_AND_COST_MANAGEMENT(HuffBundle.messagePointer("settings.huff.color.gas_and_cost_management"), Default.HIGHLIGHTED_REFERENCE),

  // rule based highlighting
  CONSTANT(HuffBundle.messagePointer("settings.huff.color.constant"), Default.CONSTANT),
  MACRO_LABEL(HuffBundle.messagePointer("settings.huff.color.macro_label"), Default.STATIC_METHOD),
  MACRO_LABEL_CALL(HuffBundle.messagePointer("settings.huff.color.macro_label_call"), Default.TEMPLATE_LANGUAGE_COLOR),
  IDENTIFIER(HuffBundle.messagePointer("settings.huff.color.identifier"), Default.IDENTIFIER);

  val textAttributesKey = TextAttributesKey.createTextAttributesKey("run.huff.$name", default)
  val attributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}

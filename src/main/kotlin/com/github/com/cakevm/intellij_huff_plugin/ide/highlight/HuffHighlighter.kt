package com.github.com.cakevm.intellij_huff_plugin.ide.highlight

import HuffLexer
import com.github.com.cakevm.intellij_huff_plugin.ide.colors.HuffColor
import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.TokenIElementType

class HuffHighlighter : SyntaxHighlighterBase() {
  override fun getHighlightingLexer(): Lexer =
    ANTLRLexerAdaptor(HuffLanguage.INSTANCE, HuffLexer(null))

  override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
    return pack(map(tokenType)?.textAttributesKey)
  }

  companion object {
    init {
      // init here to get around https://github.com/antlr/antlr4-intellij-adaptor/issues/31
      val vocabulary = HuffParser.VOCABULARY
      PSIElementTypeFactory.defineLanguageIElementTypes(
        HuffLanguage.INSTANCE,
        (0..vocabulary.maxTokenType)
          .map { vocabulary.getLiteralName(it) ?: (vocabulary.getSymbolicName(it) ?: "<INVALID>") }
          .toTypedArray(),
        HuffParser.ruleNames,
      )
    }
  }
}

fun map(tokenType: IElementType?): HuffColor? {
  if (tokenType !is TokenIElementType) return null
  return when (tokenType.antlrTokenType) {
    HuffLexer.LINE_COMMENT -> HuffColor.COMMENT
    HuffLexer.BLOCK_COMMENT -> HuffColor.BLOCK_COMMENT
    HuffLexer.HEXCODE -> HuffColor.HEXCODE
    HuffLexer.DEFINE -> HuffColor.DEFINE
    HuffLexer.INCLUDE -> HuffColor.INCLUDE
    HuffLexer.L_BRACE,
    HuffLexer.R_BRACE -> HuffColor.BRACES

    else -> null
  }
}

package com.github.com.cakevm.intellij_huff_plugin.language.parser

import HuffLexer
import HuffParser
import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFile
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffPsiBuilder
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.TokenIElementType
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree

class HuffParserDefinition : ParserDefinition {
  private val file = IFileElementType(HuffLanguage)

  override fun createLexer(project: Project?): Lexer =
    ANTLRLexerAdaptor(HuffLanguage, HuffLexer(null))

  override fun createParser(project: Project?): PsiParser {
    val parser = HuffParser(null)
    return object : ANTLRParserAdaptor(HuffLanguage, parser) {
      override fun parse(parser: Parser, root: IElementType?): ParseTree? {
        return if (root is IFileElementType) {
          (parser as HuffParser).huffFileRoot()
        } else {
          (parser as HuffParser).identifier()
        }
      }
    }
  }

  override fun getFileNodeType(): IFileElementType = file

  override fun getCommentTokens(): TokenSet =
    PSIElementTypeFactory.createTokenSet(
      HuffLanguage,
      HuffLexer.LINE_COMMENT,
      HuffLexer.BLOCK_COMMENT,
      HuffLexer.NATSPEC_DOCBLOCK,
      HuffLexer.NATSPEC_SINGLELINE,
    )

  override fun getWhitespaceTokens(): TokenSet =
    PSIElementTypeFactory.createTokenSet(HuffLanguage, HuffLexer.WHITESPACE)

  override fun getStringLiteralElements(): TokenSet =
    PSIElementTypeFactory.createTokenSet(HuffLanguage, HuffLexer.IDENTIFIER)

  override fun createElement(node: ASTNode): PsiElement = HuffPsiBuilder.from(node)

  override fun createFile(viewProvider: FileViewProvider): PsiFile = HuffFile(viewProvider)

  companion object {
    var ID: TokenIElementType

    init {
      PSIElementTypeFactory.defineLanguageIElementTypes(
        HuffLanguage,
        HuffParser.tokenNames,
        HuffParser.ruleNames,
      )
      val tokenIElementTypes: List<TokenIElementType> =
        PSIElementTypeFactory.getTokenIElementTypes(HuffLanguage)
      ID = tokenIElementTypes[HuffLexer.IDENTIFIER]
    }
  }
}

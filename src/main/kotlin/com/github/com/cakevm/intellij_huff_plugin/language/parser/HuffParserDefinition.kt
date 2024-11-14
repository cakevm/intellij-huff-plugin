package com.github.com.cakevm.intellij_huff_plugin.language.parser

import com.github.com.cakevm.intellij_huff_plugin.language.HuffLexer
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFile
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffFileStub
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class HuffParserDefinition : ParserDefinition {

  override fun createParser(project: Project?): PsiParser = HuffParser()

  override fun createLexer(project: Project?): Lexer = HuffLexer()

  override fun getFileNodeType(): IFileElementType = HuffFileStub.Type

  override fun getCommentTokens(): TokenSet = COMMENTS

  override fun getWhitespaceTokens(): TokenSet = WHITESPACES

  override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

  override fun createElement(node: ASTNode): PsiElement = HuffElementTypes.Factory.createElement(node)

  override fun createFile(viewProvider: FileViewProvider): PsiFile = HuffFile(viewProvider)

  companion object {
    val WHITESPACES: TokenSet = TokenSet.create(TokenType.WHITE_SPACE)
    val COMMENTS: TokenSet = TokenSet.create(HuffElementTypes.COMMENT)
  }
}

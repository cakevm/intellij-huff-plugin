package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.github.com.cakevm.intellij_huff_plugin.language.util.HuffIncludeUtil
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile

fun HuffQuotedString.getTextRangeWithoutQuote(): TextRange {
  return TextRange.create(node.textRange.startOffset + 1, node.textRange.endOffset - 1)
}

fun HuffQuotedString.textWithoutQuote(): String = this.text.trim('"')

fun HuffIncludeDirective.importPathString(): String {
  return this.quotedString.textWithoutQuote()
}

fun HuffIncludeDirective.importedPsiFile(): PsiFile? {
  return this.importPathString().let { HuffIncludeUtil.resolveRelatively(this.containingFile, it) }
}

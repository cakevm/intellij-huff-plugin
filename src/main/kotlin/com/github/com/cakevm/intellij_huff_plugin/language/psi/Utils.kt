package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

fun PsiElement.rangeRelativeTo(ancestor: PsiElement): TextRange {
  check(ancestor.textRange.contains(textRange))
  return textRange.shiftRight(-ancestor.textRange.startOffset)
}

inline fun <reified T : PsiElement> PsiElement.childOfType(strict: Boolean = true): T? =
  PsiTreeUtil.findChildOfType(this, T::class.java, strict)

val PsiElement.parentRelativeRange: TextRange
  get() = rangeRelativeTo(parent)

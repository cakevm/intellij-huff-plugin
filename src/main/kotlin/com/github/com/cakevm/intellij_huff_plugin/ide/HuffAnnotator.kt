package com.github.com.cakevm.intellij_huff_plugin.ide

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement

class HuffAnnotator : Annotator {
  override fun annotate(psiElement: PsiElement, annotationHolder: AnnotationHolder) {
    when (psiElement) {}
  }
}

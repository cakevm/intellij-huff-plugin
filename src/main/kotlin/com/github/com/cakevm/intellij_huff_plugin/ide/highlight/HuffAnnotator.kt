package com.github.com.cakevm.intellij_huff_plugin.ide.highlight

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.psi.PsiFile

class HuffAnnotator : ExternalAnnotator<PsiFile, List<HuffAnnotator.IAnnotation>>() {
  interface IAnnotation {
    fun annotate(holder: AnnotationHolder)
  }
}

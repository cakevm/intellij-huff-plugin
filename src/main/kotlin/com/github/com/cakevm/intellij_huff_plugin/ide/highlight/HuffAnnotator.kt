package com.github.com.cakevm.intellij_huff_plugin.ide.highlight

import com.github.com.cakevm.intellij_huff_plugin.ide.colors.HuffColor
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.*
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

class HuffAnnotator : Annotator {
  override fun annotate(psiElement: PsiElement, holder: AnnotationHolder) {

    when (psiElement.elementType) {
      MACRO_CONSTANT_REFERENCE -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(HuffColor.MACRO_CONSTANT_REFERENCE.textAttributesKey)
          .create()
      }
      MACRO_LABEL -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(HuffColor.MACRO_LABEL.textAttributesKey)
          .create()
      }
      MACRO_LABEL_REFERENCE -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(HuffColor.MACRO_LABEL_REFERENCE.textAttributesKey)
          .create()
      }
    }
  }
}

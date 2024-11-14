package com.github.com.cakevm.intellij_huff_plugin.ide.highlight

import com.github.com.cakevm.intellij_huff_plugin.ide.colors.HuffColor
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.*
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

class HuffAnnotator : Annotator {
  override fun annotate(psiElement: PsiElement, holder: AnnotationHolder) {

    when (psiElement.elementType) {
      CONSTANT_DEFINITION_IDENTIFIER -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(HuffColor.CONSTANT.textAttributesKey)
          .create()
      }

      MACRO_CONSTANT_REFERENCE -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(HuffColor.CONSTANT.textAttributesKey)
          .create()
      }
      MACRO_LABEL -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(HuffColor.MACRO_LABEL.textAttributesKey)
          .create()
      }
      MACRO_LABEL_GO_TO -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(HuffColor.MACRO_LABEL_CALL.textAttributesKey)
          .create()
      }
      // Remove highlighting of lexer tokens for identifiers / parameters
      IDENTIFIER -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .enforcedTextAttributes(TextAttributes.ERASE_MARKER)
          .create()
      }
      PARAMETER_DEF -> {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(psiElement.textRange).create()
      }
      else -> {
        // do nothing
      }
    }
  }
}

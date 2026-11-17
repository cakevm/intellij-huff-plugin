package com.github.com.cakevm.intellij_huff_plugin.ide.highlight

import com.github.com.cakevm.intellij_huff_plugin.ide.colors.HuffColor
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.*
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffForLoopVariable
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
      CONSTANT_REFERENCE -> {
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
      MACRO_PARAMETER_REFERENCE -> {
        // `<name>` refers to a loop variable or a macro parameter, which have their own colors
        val isLoopVariable = psiElement.reference?.resolve() is HuffForLoopVariable
        val color = if (isLoopVariable) HuffColor.FOR_LOOP_VARIABLE else HuffColor.MACRO_PARAMETER
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(color.textAttributesKey)
          .create()
      }
      // Remove highlighting of lexer tokens for identifiers / parameters, except for decorator flag names which are highlighted below
      IDENTIFIER,
      PARAMETER_NAME -> {
        if (psiElement.parent.elementType == TEST_DECORATOR_FLAG_NAME) return
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .enforcedTextAttributes(TextAttributes.ERASE_MARKER)
          .create()
      }
      PARAMETER_DEF -> {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(psiElement.textRange).create()
      }
      TEST_DECORATOR_FLAG_NAME -> {
        holder
          .newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(psiElement.textRange)
          .textAttributes(HuffColor.BUILTIN_FN.textAttributesKey)
          .create()
      }
      else -> {
        // do nothing
      }
    }
  }
}

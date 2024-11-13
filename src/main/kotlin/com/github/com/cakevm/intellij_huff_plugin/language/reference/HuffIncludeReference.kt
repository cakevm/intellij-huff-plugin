package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.github.com.cakevm.intellij_huff_plugin.language.HuffIcons
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.github.com.cakevm.intellij_huff_plugin.language.psi.importPathString
import com.github.com.cakevm.intellij_huff_plugin.language.util.HuffIncludeUtil
import com.github.com.cakevm.intellij_huff_plugin.language.util.filePath
import com.github.com.cakevm.intellij_huff_plugin.language.util.getRelativePath
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.startOffset

class HuffIncludeReference(includeDirective: HuffIncludeDirective, private var textRange: TextRange) :
  PsiReferenceBase<HuffIncludeDirective>(includeDirective, textRange) {

  override fun resolve(): PsiElement? {
    return HuffIncludeUtil.resolveRelatively(element.containingFile, element.importPathString())
  }

  override fun getAbsoluteRange(): TextRange = textRange

  override fun getRangeInElement(): TextRange {
    return element.includePath.textRange.shiftLeft(element.startOffset - 1)
  }

  override fun getVariants(): Array<LookupElementBuilder> {
    val files = FileTypeIndex.getFiles(HuffFileType.INSTANCE, GlobalSearchScope.projectScope(element.project))
    return files
      .mapNotNull { file ->
        if (file.path == element.filePath()) {
          return@mapNotNull null
        }

        val relativePath = element.getRelativePath(file)
        if (relativePath === null) return@mapNotNull null

        val contextText = if (relativePath.contains('/')) "($relativePath)" else ""
        LookupElementBuilder.create(relativePath)
          .withPresentableText(file.name)
          .withTypeText(contextText)
          .withIcon(HuffIcons.HUFF_16x16)
          .withInsertHandler { context, _ ->
            context.document.replaceString(
              context.editor.caretModel.offset,
              context.editor.document.text.indexOf('"', context.editor.caretModel.offset),
              "",
            )
          }
      }
      .toTypedArray()
  }
}

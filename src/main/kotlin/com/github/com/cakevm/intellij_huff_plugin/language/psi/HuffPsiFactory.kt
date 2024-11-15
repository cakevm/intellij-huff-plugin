package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiParserFacade

class HuffPsiFactory(val project: Project) {
  fun createIdentifier(name: String): PsiElement {
    return createFromText<HuffMacroDefinition>("macro $name {}")?.macroIdentifier ?: error("Failed to create identifier: `$name`")
  }

  fun createIncludeDirective(content: String, addQuotes: Boolean = true): HuffIncludeDirective {
    val text = if (addQuotes) "\"$content\"" else content
    return createFromText("#include $text;") ?: error("Failed to create include: `$text`")
  }

  fun createNewLine(project: Project): PsiElement {
    return PsiParserFacade.getInstance(project).createWhiteSpaceFromText("\n")
  }

  private inline fun <reified T : HuffElement> createFromText(code: String): T? =
    PsiFileFactory.getInstance(project).createFileFromText("DUMMY.huff", HuffFileType.INSTANCE, code).childOfType()
}

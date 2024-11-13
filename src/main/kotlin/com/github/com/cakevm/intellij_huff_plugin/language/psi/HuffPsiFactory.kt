package com.github.com.cakevm.intellij_huff_plugin.language.psi

import com.github.com.cakevm.intellij_huff_plugin.language.HuffElement
import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory

class HuffPsiFactory(val project: Project) {
  fun createIdentifier(name: String): PsiElement {
    return createFromText<HuffMacroDefinition>("macro $name {}")?.macroIdentifierAndParameters?.macroIdentifier
      ?: error("Failed to create identifier: `$name`")
  }

  private inline fun <reified T : HuffElement> createFromText(code: String): T? =
    PsiFileFactory.getInstance(project).createFileFromText("DUMMY.huff", HuffFileType.INSTANCE, code).childOfType()
}

package com.github.com.cakevm.intellij_huff_plugin.ide.action

import com.github.com.cakevm.intellij_huff_plugin.HuffBundle
import com.github.com.cakevm.intellij_huff_plugin.language.HuffIcons
import com.intellij.ide.actions.*
import com.intellij.openapi.project.*
import com.intellij.psi.*

class CreateNewHuffFileAction :
  CreateFileFromTemplateAction(
    HuffBundle.message("actions.new-file.name"),
    HuffBundle.message("actions.new-file.description"),
    HuffIcons.HUFF_16x16,
  ),
  DumbAware {
  override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String =
    HuffBundle.message("actions.new-file.name")

  override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
    builder
      .setTitle(HuffBundle.message("actions.new-file.name"))
      .addKind("File", HuffIcons.HUFF_16x16, "EmptyHuffFile")
      .addKind("Main file", HuffIcons.HUFF_16x16, "MainHuffFile")
  }
}

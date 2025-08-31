package com.github.com.cakevm.intellij_huff_plugin.ide.inspection.fix

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffPsiFactory
import com.intellij.codeInsight.hint.QuestionAction
import com.intellij.ide.util.DefaultPsiElementCellRenderer
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.ui.popup.list.ListPopupImpl
import com.intellij.ui.popup.list.PopupListElementRenderer
import java.awt.BorderLayout
import java.io.File
import java.nio.file.Paths
import javax.swing.Icon
import javax.swing.JPanel
import javax.swing.ListCellRenderer

class IncludeFileAction(val editor: Editor, private val file: PsiFile, private val suggestions: Set<PsiFile>) : QuestionAction {

  val project: Project
    get() = file.project

  override fun execute(): Boolean {
    PsiDocumentManager.getInstance(project).commitAllDocuments()

    if (suggestions.size == 1) {
      addImport(project, file, suggestions.first())
    } else {
      chooseFileToImport()
    }

    return true
  }

  private fun chooseFileToImport() {
    val step =
      object : BaseListPopupStep<PsiFile>("File to Import", suggestions.toMutableList()) {
        override fun isAutoSelectionEnabled(): Boolean {
          return false
        }

        override fun isSpeedSearchEnabled(): Boolean {
          return true
        }

        override fun onChosen(selectedValue: PsiFile?, finalChoice: Boolean): PopupStep<*>? {
          if (selectedValue == null) {
            return FINAL_CHOICE
          }

          return doFinalStep {
            PsiDocumentManager.getInstance(project).commitAllDocuments()
            addImport(project, file, selectedValue)
          }
        }

        override fun hasSubstep(selectedValue: PsiFile?): Boolean {
          return true
        }

        override fun getTextFor(value: PsiFile): String {
          return value.name
        }

        override fun getIconFor(aValue: PsiFile): Icon? {
          return aValue.getIcon(0)
        }
      }

    val popup =
      object : ListPopupImpl(project, step) {
        override fun getListElementRenderer(): ListCellRenderer<PsiFile> {
          @Suppress("UNCHECKED_CAST") val baseRenderer = super.getListElementRenderer() as PopupListElementRenderer<PsiFile>
          val psiRenderer = DefaultPsiElementCellRenderer()
          return ListCellRenderer { list, value, index, isSelected, cellHasFocus ->
            val panel = JPanel(BorderLayout())
            baseRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            panel.add(baseRenderer.nextStepLabel, BorderLayout.EAST)
            panel.add(psiRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus))
            panel
          }
        }
      }
    popup.showInBestPositionFor(editor)
  }

  companion object {
    fun addImport(project: Project, file: PsiFile, to: PsiFile) {
      CommandProcessor.getInstance().runUndoTransparentAction {
        ApplicationManager.getApplication().runWriteAction {
          val after = file.children.filterIsInstance<HuffIncludeDirective>().lastOrNull() ?: file.children.firstOrNull()
          val factory = HuffPsiFactory(project)
          file.addAfter(factory.createIncludeDirective(buildImportPath(project, file.virtualFile, to.virtualFile)), after)
          file.addAfter(factory.createNewLine(project), after)
          // SolImportOptimizer().processFile(file, false).run()
        }
      }
    }

    fun readRemappingsFile(project: Project): Map<String, String> {
      val psiManager = PsiManager.getInstance(project)
      val file = LocalFileSystem.getInstance().findFileByIoFile(File(project.basePath + "/remappings.txt"))
      val psiFile: PsiFile? = file?.let { psiManager.findFile(it) }
      if (psiFile == null) {
        return emptyMap()
      }
      return CachedValuesManager.getCachedValue(psiFile) {
        val content = psiFile.text
        val remappingsMap =
          content
            .lines()
            .filter { it.isNotBlank() }
            .associate {
              val (a, b) = it.split("=")
              b.trim() to a.trim()
            }
        CachedValueProvider.Result.create(remappingsMap, PsiModificationTracker.MODIFICATION_COUNT)
      }
    }

    fun buildImportPath(project: Project, source: VirtualFile, destination: VirtualFile): String {
      return Paths.get(source.path)
        .parent
        .relativize(Paths.get(destination.path))
        .toString()
        .let { importPath ->
          val separator = File.separator

          when {
            importPath.contains("node_modules$separator") -> {
              val idx = importPath.indexOf("node_modules$separator")
              importPath.substring(idx + "node_modules$separator".length)
            }

            importPath.contains("lib$separator") -> {
              val mapping = readRemappingsFile(project)
              mapping.keys
                .firstOrNull { importPath.contains(it) }
                ?.let { importPath.substring(importPath.indexOf(it)).replaceFirst(it, mapping[it]!!) } ?: importPath
            }

            importPath.contains("installed_contracts$separator") -> {
              val idx = importPath.indexOf("installed_contracts$separator")
              importPath
                .substring(idx + "installed_contracts$separator".length)
                .replaceFirst("${separator}contracts${separator}", separator)
            }
            !importPath.startsWith(".") -> ".$separator$importPath"
            else -> importPath
          }
        }
        .replace("\\", "/")
    }
  }
}

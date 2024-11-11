package com.github.com.cakevm.intellij_huff_plugin.language.util

import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

object HuffIncludeUtil {
  fun resolveRelatively(psiFile: PsiFile, importPath: String): PsiFile? {
    val target = psiFile.originalFile.virtualFile.findFileByRelativePath("../${importPath}")
    return target?.let { PsiManager.getInstance(psiFile.project).findFile(it) }
  }
}

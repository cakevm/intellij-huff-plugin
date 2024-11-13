package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIncludeDirective
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffMacroCallElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffCallableElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.HuffNamedElementIndex
import com.github.com.cakevm.intellij_huff_plugin.language.types.HuffMember
import com.intellij.openapi.util.RecursionManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.psi.util.childrenOfType

object HuffResolver {

  fun resolveTypeNameUsingImports(element: PsiElement): Set<HuffNamedElement> =
    CachedValuesManager.getCachedValue(element) {
      val result =
        if (element is HuffMacroCallElement) {
          resolveTypeWhenMacroCallElement(element)
        } else {
          resolveTypeWhenMacroCallElement(element)
        }
      CachedValueProvider.Result.create(result, PsiModificationTracker.MODIFICATION_COUNT)
    }

  private fun resolveTypeWhenMacroCallElement(element: PsiElement) = resolveMacro(element)

  private fun resolveMacro(element: PsiElement): Set<HuffMacroDefinition> =
    resolveUsingImports(HuffMacroDefinition::class.java, element, element.containingFile)

  private fun <T : HuffNamedElement> resolveUsingImports(target: Class<T>, element: PsiElement, file: PsiFile): Set<T> {
    // If the elements has no name or text, we can't resolve it.
    val elementName = element.nameOrText ?: return emptySet()

    // Retrieve all PSI elements with the name we're trying to lookup.
    val elements: Collection<HuffNamedElement> =
      StubIndex.getElements( //
        HuffNamedElementIndex.KEY, //
        elementName, //
        element.project, //
        null, //
        HuffNamedElement::class.java, //
      )

    val resolvedImportedFiles = collectImports(file)
    val sameNameReferences =
      elements.filterIsInstance(target).filter {
        val containingFile = it.containingFile
        // During completion, IntelliJ copies PSI files, and therefore we need to ensure that we compare
        // files against its original file.
        val originalFile = file.originalFile
        // Below, either include
        containingFile == originalFile ||
          resolvedImportedFiles.any { (containingFile == it.file) && it.names.let { it.isEmpty() || it.any { it.name == elementName } } }
      }
    return sameNameReferences.toSet()
  }

  data class ImportRecord(val file: PsiFile, val names: List<HuffNamedElement>)

  fun collectImports(file: PsiFile): Collection<ImportRecord> {
    return collectImports(file.childrenOfType<HuffIncludeDirective>()).filter { it.file !== file }
  }

  fun collectImports(import: HuffIncludeDirective): Collection<ImportRecord> {
    return CachedValuesManager.getCachedValue(import) {
      val result = collectImports(listOf(import))
      CachedValueProvider.Result.create(result, result.map { it.file } + import)
    }
  }

  fun collectImports(imports: Collection<HuffIncludeDirective>): Collection<ImportRecord> {
    return RecursionManager.doPreventingRecursion(imports, true) {
      val visited: MutableSet<PsiFile> = hashSetOf()
      collectImports(imports, visited)
    } ?: emptySet()
  }

  /** Collects imports of all declarations for a given file recursively. */
  private fun collectImports(imports: Collection<HuffIncludeDirective>, visited: MutableSet<PsiFile>): Collection<ImportRecord> {
    if (!visited.add((imports.firstOrNull() ?: return emptyList()).containingFile)) {
      return emptySet()
    }

    val resolvedImportedFiles =
      imports.mapNotNull {
        val containingFile = it.includePath.reference?.resolve()?.containingFile ?: return@mapNotNull null
        val names =
          containingFile.childrenOfType<HuffCallableElement>().toList().flatMap {
            (if (it is HuffMacroDefinition) resolveMacroMembers(it) else emptyList()) + it
          }

        ImportRecord(containingFile, names.filterIsInstance<HuffNamedElement>())
      }
    return resolvedImportedFiles +
      resolvedImportedFiles.map { collectImports(it.file.childrenOfType<HuffIncludeDirective>(), visited) }.flatten()
  }

  fun resolveMacroMembers(macro: HuffMacroDefinition, skipThis: Boolean = false): List<HuffMember> {
    val members = if (!skipThis) listOf(macro) else emptyList()
    return emptyList()
  }
}

private val PsiElement.nameOrText
  get() =
    if (this is PsiNamedElement) {
      this.name
    } else {
      this.text
    }

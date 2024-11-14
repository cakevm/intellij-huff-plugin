package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.psi.*
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.HuffNamedElementIndex
import com.intellij.openapi.util.RecursionManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.*
import com.intellij.util.Processors

object HuffResolver {

  fun resolveTypeNameUsingImports(element: PsiElement): Set<HuffNamedElement> =
    CachedValuesManager.getCachedValue(element) {
      val result =
        when (element) {
          is HuffMacroCall -> {
            resolveUsingImports(HuffMacroDefinition::class.java, element, element.containingFile)
          }
          is HuffMacroLabelGoTo -> {
            resolveUsingImports(HuffMacroLabel::class.java, element, element.containingFile)
          }
          is HuffMacroConstantReference -> {
            resolveUsingImports(HuffConstantDefinition::class.java, element, element.containingFile)
          }
          else -> {
            resolveUsingImports(HuffMacroDefinition::class.java, element, element.containingFile)
          }
        }
      CachedValueProvider.Result.create(result, PsiModificationTracker.MODIFICATION_COUNT)
    }

  private fun <T : HuffNamedElement> resolveUsingImports(target: Class<T>, element: PsiElement, file: PsiFile): Set<T> {
    // If the elements has no name or text, we can't resolve it.
    val elementName = element.nameOrText ?: return emptySet()
    // println(elementName)

    // Retrieve all PSI elements with the name we're trying to lookup.
    val elements: Collection<HuffNamedElement> =
      StubIndex.getElements( //
        HuffNamedElementIndex.KEY, //
        elementName, //
        element.project, //
        null, //
        HuffNamedElement::class.java, //
      )
    // println(elements)

    val resolvedImportedFiles = collectImports(file)
    val sameNameReferences =
      elements.filterIsInstance(target).filter { it1 ->
        val containingFile = it1.containingFile
        // During completion, IntelliJ copies PSI files, and therefore we need to ensure that we compare
        // files against its original file.
        val originalFile = file.originalFile
        // Below, either include
        containingFile == originalFile ||
          resolvedImportedFiles.any { it2 ->
            (containingFile == it2.file) && it2.names.let { it3 -> it3.isEmpty() || it3.any { it4 -> it4.name == elementName } }
          }
      }
    return sameNameReferences.toSet()
  }

  data class ImportRecord(val file: PsiFile, val names: List<HuffNamedElement>)

  private fun collectImports(file: PsiFile): Collection<ImportRecord> {
    return collectImports(file.childrenOfType<HuffIncludeDirective>()).filter { it.file !== file }
  }

  private fun collectImports(import: HuffIncludeDirective): Collection<ImportRecord> {
    return CachedValuesManager.getCachedValue(import) {
      val result = collectImports(listOf(import))
      CachedValueProvider.Result.create(result, result.map { it.file } + import)
    }
  }

  private fun collectImports(imports: Collection<HuffIncludeDirective>): Collection<ImportRecord> {
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
        val names = containingFile.childrenOfType<HuffNamedElement>().toList()

        ImportRecord(containingFile, names)
      }
    return resolvedImportedFiles +
      resolvedImportedFiles.map { collectImports(it.file.childrenOfType<HuffIncludeDirective>(), visited) }.flatten()
  }

  fun collectUsedElements(o: HuffIncludeDirective): List<String> {
    val containingFile = o.containingFile

    val importedNames = collectIncludedNames(containingFile)

    val paths = collectImports(o).map { it.file }
    val importScope = GlobalSearchScope.filesScope(o.project, paths.map { it.virtualFile })

    val imported =
      paths.flatMap {
        CachedValuesManager.getCachedValue(it) {
          val allKeys = HashSet<String>()
          val scope = GlobalSearchScope.fileScope(it)
          StubIndex.getInstance().processAllKeys(HuffNamedElementIndex.KEY, Processors.cancelableCollectProcessor(allKeys), scope)
          CachedValueProvider.Result.create(
            allKeys
              .filter {
                StubIndex.getElements(HuffNamedElementIndex.KEY, it, scope.project!!, scope, HuffNamedElement::class.java).isNotEmpty()
              }
              .toSet(),
            PsiModificationTracker.MODIFICATION_COUNT,
          )
        }
      }
    val targetNames = importedNames.flatMap { (emptyList<HuffNamedElement>() + it.target + it.ref).mapNotNull { it.name } }.toSet()
    val used =
      imported.intersect(targetNames).filter {
        StubIndex.getElements(HuffNamedElementIndex.KEY, it, o.project, importScope, HuffNamedElement::class.java).all { e ->
          exportElements.any { it.isAssignableFrom(e.javaClass) }
        }
      }

    return used
  }

  private val exportElements = setOf(HuffMacroDefinition::class.java, HuffMacroLabel::class.java, HuffConstantDefinition::class.java)

  fun collectIncludedNames(root: PsiFile): Set<IncludedName> {
    return CachedValuesManager.getCachedValue(root) {
      val result =
        root
          .descendants()
          .filter { it.parentOfType<HuffIncludeDirective>() == null }
          .flatMap { ref ->
            (ref.reference as? HuffReference)?.multiResolve()?.mapNotNull {
              Pair(ref as? HuffNamedElement ?: return@mapNotNull null, it as? HuffNamedElement ?: return@mapNotNull null)
            } ?: emptyList()
          }
          .mapNotNull { (ref, it) ->
            IncludedName(
              ref,
              when {
                it.containingFile != root -> it
                else -> null
              } ?: return@mapNotNull null,
            )
          }
          .toSet()
      CachedValueProvider.Result.create(result, root)
    }
  }

  data class IncludedName(val ref: HuffNamedElement, val target: HuffNamedElement)
}

private val PsiElement.nameOrText
  get() =
    if (this is PsiNamedElement) {
      this.name
    } else {
      this.text
    }

package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.HuffElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.*
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffMacroCallElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffCallableElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.HuffNamedElementIndex
import com.github.com.cakevm.intellij_huff_plugin.language.types.HuffMember
import com.intellij.openapi.util.RecursionManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.*
import com.intellij.util.Processors
import com.intellij.util.takeWhileInclusive

object HuffResolver {

  fun resolveTypeNameUsingImports(element: PsiElement): Set<HuffNamedElement> =
    CachedValuesManager.getCachedValue(element) {
      val result =
        when (element) {
          is HuffMacroCallElement -> {
            resolveUsingImports(HuffMacroDefinition::class.java, element, element.containingFile)
          }
          is HuffMacroLabelGoTo -> {
            resolveUsingImports(HuffMacroLabel::class.java, element, element.containingFile)
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

  fun collectUsedElements(o: HuffIncludeDirective): List<String> {
    val containingFile = o.containingFile

    val importedNames = collectIncludedNames(containingFile)

    val pathes = collectImports(o).map { it.file }
    val importScope = GlobalSearchScope.filesScope(o.project, pathes.map { it.virtualFile })

    val imported =
      pathes.flatMap {
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

    return emptyList()
  }

  fun resolveMacroMembers(macro: HuffMacroDefinition, skipThis: Boolean = false): List<HuffMember> {
    val members = if (!skipThis) listOf(macro) else emptyList()
    return emptyList()
  }

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

  fun lexicalDeclarations(place: PsiElement, stop: (PsiElement) -> Boolean = { false }): Sequence<HuffNamedElement> {
    val visitedScopes = hashSetOf<Pair<PsiElement, PsiElement>>()
    return lexicalDeclarations0(visitedScopes, place, stop)
  }

  private fun lexicalDeclarations0(
    visitedScopes: HashSet<Pair<PsiElement, PsiElement>>,
    place: PsiElement,
    stop: (PsiElement) -> Boolean = { false },
  ): Sequence<HuffNamedElement> {

    return lexicalDeclRec(visitedScopes, place, stop).distinct()
  }

  private fun lexicalDeclRec(
    visitedScopes: HashSet<Pair<PsiElement, PsiElement>>,
    place: PsiElement,
    stop: (PsiElement) -> Boolean,
  ): Sequence<HuffNamedElement> {
    return place.ancestors
      .drop(1) // current element might not be a SolElement
      .takeWhileInclusive { it is HuffElement && !stop(it) }
      .flatMap { lexicalDeclarations(visitedScopes, it, place) }
  }

  private fun lexicalDeclarations(
    visitedScopes: HashSet<Pair<PsiElement, PsiElement>>,
    scope: PsiElement,
    place: PsiElement,
  ): Sequence<HuffNamedElement> {
    // Note that in some cases, loops are possible to encounter when searching for definitions.
    // To avoid the issue, ensure that we only visit place that haven't been visited before.
    if (!visitedScopes.add(scope to place)) {
      return emptySequence()
    }
    return when (scope) {
      is HuffMacroLabelGoTo -> lexicalDeclarations(visitedScopes, scope.firstChild, place)

      is HuffMacroBody -> {
        scope.macroStatementList.asSequence().map { lexicalDeclarations(visitedScopes, it, place) }.flatten()
      }

      is HuffFile -> {
        RecursionManager.doPreventingRecursion(scope.name, true) {
          val scopeChildren = scope.children

          /*
          val constants = scopeChildren.asSequence()
            .filterIsInstance<HuffConstantDefinition>()

           */
          val macros = scopeChildren.asSequence().filterIsInstance<HuffMacroDefinition>()

          val imports =
            scopeChildren
              .asSequence()
              .filterIsInstance<HuffIncludeDirective>()
              .mapNotNull { it.includePath.reference?.resolve()?.containingFile }
              .map { lexicalDeclarations(visitedScopes, it, place) }
              .flatten()
          imports + macros
        } ?: emptySequence()
      }

      else -> emptySequence()
    }
  }
}

private val PsiElement.nameOrText
  get() =
    if (this is PsiNamedElement) {
      this.name
    } else {
      this.text
    }

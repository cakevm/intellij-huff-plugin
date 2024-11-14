package com.github.com.cakevm.intellij_huff_plugin.language.reference

import com.github.com.cakevm.intellij_huff_plugin.language.HuffElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.IDENTIFIER
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffPsiFactory
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffReferenceElement
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.intellij.psi.util.elementType

abstract class HuffReferenceBase<T : HuffReferenceElement>(element: T) : PsiPolyVariantReferenceBase<T>(element), HuffReference {
  override fun calculateDefaultRangeInElement() = TextRange(0, element.textRange.length)

  override fun getVariants(): Array<out Any> = emptyArray()

  override fun resolve(): HuffElement? = super.resolve() as? HuffElement?

  final override fun multiResolve(incompleteCode: Boolean) =
    ResolveCache.getInstance(element.project)
      .resolveWithCaching(this, { r, _ -> r.multiResolve().map(::PsiElementResolveResult).toTypedArray() }, true, false)

  override fun multiResolve(): Collection<PsiElement> = singleResolve()?.let { listOf(it) } ?: emptyList()

  open fun singleResolve(): PsiElement? = null

  override fun handleElementRename(newName: String): PsiElement {
    doRename(element.referenceNameElement, newName)
    return element
  }

  protected open fun doRename(identifier: PsiElement, newName: String) {
    check(identifier.elementType == IDENTIFIER)
    identifier.replace(HuffPsiFactory(identifier.project).createIdentifier(newName.replace(".huff", "")))
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as HuffReferenceBase<*>
    return element == other.element
  }

  override fun hashCode() = element.hashCode()
}

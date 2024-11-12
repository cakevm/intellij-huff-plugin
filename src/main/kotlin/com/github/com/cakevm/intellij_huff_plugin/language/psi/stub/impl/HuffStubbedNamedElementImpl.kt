package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.IDENTIFIER
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement

abstract class HuffStubbedNamedElementImpl<S> : HuffStubbedElementImpl<S>, HuffNamedElement, PsiNameIdentifierOwner where
S : HuffNamedStub,
S : StubElement<*> {

  constructor(node: ASTNode) : super(node)

  constructor(stub: S, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

  override fun getNameIdentifier(): PsiElement? = findChildByType(IDENTIFIER)

  override fun getName() = stub?.name ?: nameIdentifier?.text

  override fun setName(name: String): PsiElement? {
    nameIdentifier
    return this
  }

  override fun getNavigationElement(): PsiElement = nameIdentifier ?: this

  override fun getTextOffset(): Int = nameIdentifier?.textOffset ?: super.getTextOffset()

  override fun getPresentation() = PresentationData(name, "", getIcon(0), null)
}

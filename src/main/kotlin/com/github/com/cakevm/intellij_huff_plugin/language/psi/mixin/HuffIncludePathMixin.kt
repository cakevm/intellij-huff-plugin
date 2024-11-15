package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.QUOTED_STRING
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffReferenceElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffIncludePathStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubbedNamedElementImpl
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffIncludePathReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType

open class HuffIncludePathMixin : HuffStubbedNamedElementImpl<HuffIncludePathStub>, HuffReferenceElement {
  constructor(node: ASTNode) : super(node)

  constructor(stub: HuffIncludePathStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

  override val referenceNameElement: PsiElement
    get() = findChildByType(QUOTED_STRING)!!

  override val referenceName: String
    get() = referenceNameElement.text

  override fun getReference() = HuffIncludePathReference(this)
}

package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base

import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffElement
import com.github.com.cakevm.intellij_huff_plugin.language.reference.base.HuffReference
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement

abstract class HuffStubbedElementImpl<S : StubElement<*>> : StubBasedPsiElementBase<S>, HuffElement {

  constructor(node: ASTNode) : super(node)

  constructor(stub: S, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

  override fun getReference(): HuffReference? = null

  // TODO: Enable getElementTypeImpl once it is stable.
  // Wait for stable API in:
  // https://github.com/JetBrains/intellij-community/blob/master/platform/core-api/src/com/intellij/psi/StubBasedPsiElement.java
  //
  // I can not find a stable API to get the element type of the stubbed element.
  // - getElementType() is deprecated
  // - and is getElementTypeImpl() is experimental
  // override fun toString(): String = "${javaClass.simpleName}(${elementTypeImpl})"
}

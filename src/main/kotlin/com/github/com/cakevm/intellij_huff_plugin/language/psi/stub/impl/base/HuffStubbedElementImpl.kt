package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base

import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffElement
import com.github.com.cakevm.intellij_huff_plugin.language.reference.HuffReference
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement

abstract class HuffStubbedElementImpl<S : StubElement<*>> : StubBasedPsiElementBase<S>, HuffElement {

  constructor(node: ASTNode) : super(node)

  constructor(stub: S, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

  override fun getReference(): HuffReference? = null

  // FQN isn't needed in paring tests
  override fun toString(): String = "${javaClass.simpleName}($elementType)"
}

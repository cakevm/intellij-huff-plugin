package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffConstantDefinitionStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffStubbedNamedElementImpl
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType

abstract class HuffConstantDefinitionMixin : HuffStubbedNamedElementImpl<HuffConstantDefinitionStub>, HuffConstantDefinition {
  constructor(node: ASTNode) : super(node)

  constructor(stub: HuffConstantDefinitionStub, type: IStubElementType<*, *>) : super(stub, type)
}

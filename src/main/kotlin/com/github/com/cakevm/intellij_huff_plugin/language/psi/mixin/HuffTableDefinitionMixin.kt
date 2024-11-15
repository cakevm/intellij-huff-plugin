package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffTableDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffTableDefinitionStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubbedNamedElementImpl
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType

abstract class HuffTableDefinitionMixin : HuffStubbedNamedElementImpl<HuffTableDefinitionStub>, HuffTableDefinition {
  constructor(node: ASTNode) : super(node)

  constructor(stub: HuffTableDefinitionStub, type: IStubElementType<*, *>) : super(stub, type)

  override fun getName(): String? {
    return this.identifier.text
  }

  override fun getPresentation(): PresentationData = PresentationData(identifier.text, null, null, null)

  override fun resolveElement(): HuffNamedElement? = this
}

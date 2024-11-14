package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffConstantDefinitionStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffStubbedNamedElementImpl
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType

abstract class HuffConstantDefinitionMixin : HuffStubbedNamedElementImpl<HuffConstantDefinitionStub>, HuffConstantDefinition {
  constructor(node: ASTNode) : super(node)

  constructor(stub: HuffConstantDefinitionStub, type: IStubElementType<*, *>) : super(stub, type)

  override fun getName(): String? {
    return this.constantDefinitionIdentifier.identifier.text
  }

  override fun getPresentation(): PresentationData = PresentationData(constantDefinitionIdentifier.identifier.text, null, null, null)

  override fun resolveElement(): HuffNamedElement? = this
}
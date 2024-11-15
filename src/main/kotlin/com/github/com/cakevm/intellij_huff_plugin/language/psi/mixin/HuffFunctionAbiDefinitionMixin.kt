package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFunctionAbiDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffFunctionAbiDefinitionElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffFunctionAbiDefinitionStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffStubbedNamedElementImpl
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType

abstract class HuffFunctionAbiDefinitionMixin :
  HuffStubbedNamedElementImpl<HuffFunctionAbiDefinitionStub>, HuffFunctionAbiDefinition, HuffFunctionAbiDefinitionElement {
  constructor(node: ASTNode) : super(node)

  constructor(stub: HuffFunctionAbiDefinitionStub, type: IStubElementType<*, *>) : super(stub, type)

  override fun getName(): String? {
    return this.identifier.text
  }

  override fun getPresentation(): PresentationData = PresentationData(identifier.text, null, null, null)

  override fun resolveElement(): HuffNamedElement? = this
}

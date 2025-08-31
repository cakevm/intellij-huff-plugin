package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffConstantDefinitionStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubbedNamedElementImpl
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType

@Suppress("unused")
abstract class HuffConstantDefinitionMixin : HuffStubbedNamedElementImpl<HuffConstantDefinitionStub>, HuffConstantDefinition {
  constructor(node: ASTNode) : super(node)

  constructor(stub: HuffConstantDefinitionStub, type: IStubElementType<*, *>) : super(stub, type)

  override fun getName(): String? {
    return this.constantDefinitionIdentifier.text
  }

  override fun getPresentation(): PresentationData = PresentationData(constantDefinitionIdentifier.text, null, null, null)

  override fun resolveElement(): HuffNamedElement? = this
}

package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffMacroDefinitionStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffStubbedNamedElementImpl
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType

abstract class HuffMacroDefinitionMixin : HuffStubbedNamedElementImpl<HuffMacroDefinitionStub>, HuffMacroDefinition {
  constructor(node: ASTNode) : super(node)

  constructor(stub: HuffMacroDefinitionStub, type: IStubElementType<*, *>) : super(stub, type)

  override fun getName(): String? {
    return this.macroIdentifier.text
  }

  override fun getPresentation(): PresentationData = PresentationData(macroIdentifier.text, null, null, null)

  override fun resolveElement(): HuffNamedElement? = this
}

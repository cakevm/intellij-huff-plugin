package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabel
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffMacroLabelStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubbedNamedElementImpl
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType

@Suppress("unused")
abstract class HuffMacroLabelMixin : HuffStubbedNamedElementImpl<HuffMacroLabelStub>, HuffMacroLabel {
  constructor(node: ASTNode) : super(node)

  constructor(stub: HuffMacroLabelStub, type: IStubElementType<*, *>) : super(stub, type)

  override fun getName(): String? {
    return this.identifier.text
  }

  override fun getPresentation(): PresentationData = PresentationData(identifier.text, null, null, null)

  override fun resolveElement(): HuffNamedElement? = this
}

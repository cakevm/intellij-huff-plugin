package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroLabel
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffMacroLabelImpl
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.indexMacroLabel
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.type.HuffStubElementType
import com.intellij.psi.stubs.*

class HuffMacroLabelStub(parent: StubElement<*>?, elementType: IStubElementType<*, *>, override val name: String?) :
  StubBase<HuffMacroLabel>(parent, elementType), HuffNamedStub {
  object Type : HuffStubElementType<HuffMacroLabelStub, HuffMacroLabel>("MACRO_LABEL") {
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) =
      HuffMacroLabelStub(parentStub, this, dataStream.readNameAsString())

    override fun serialize(stub: HuffMacroLabelStub, dataStream: StubOutputStream) = with(dataStream) { writeName(stub.name) }

    override fun createPsi(stub: HuffMacroLabelStub) = HuffMacroLabelImpl(stub, this)

    override fun createStub(psi: HuffMacroLabel, parentStub: StubElement<*>?) = HuffMacroLabelStub(parentStub, this, psi.name)

    override fun indexStub(stub: HuffMacroLabelStub, sink: IndexSink) = sink.indexMacroLabel(stub)
  }
}

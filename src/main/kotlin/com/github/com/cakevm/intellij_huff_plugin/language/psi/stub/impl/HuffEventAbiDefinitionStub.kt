package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffEventAbiDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffEventAbiDefinitionImpl
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubElementType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.indexEventAbiDefinition
import com.intellij.psi.stubs.*

class HuffEventAbiDefinitionStub(parent: StubElement<*>?, elementType: IStubElementType<*, *>, override val name: String?) :
  StubBase<HuffEventAbiDefinition>(parent, elementType), HuffNamedStub {
  object Type : HuffStubElementType<HuffEventAbiDefinitionStub, HuffEventAbiDefinition>("EVENT_ABI_DEFINITION") {
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) =
      HuffEventAbiDefinitionStub(parentStub, this, dataStream.readNameAsString())

    override fun serialize(stub: HuffEventAbiDefinitionStub, dataStream: StubOutputStream) = with(dataStream) { writeName(stub.name) }

    override fun createPsi(stub: HuffEventAbiDefinitionStub) = HuffEventAbiDefinitionImpl(stub, this)

    override fun createStub(psi: HuffEventAbiDefinition, parentStub: StubElement<*>?) =
      HuffEventAbiDefinitionStub(parentStub, this, psi.name)

    override fun indexStub(stub: HuffEventAbiDefinitionStub, sink: IndexSink) = sink.indexEventAbiDefinition(stub)
  }
}

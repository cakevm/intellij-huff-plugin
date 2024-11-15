package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffErrorDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffErrorDefinitionImpl
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubElementType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.indexErrorDefinition
import com.intellij.psi.stubs.*

class HuffErrorDefinitionStub(parent: StubElement<*>?, elementType: IStubElementType<*, *>, override val name: String?) :
  StubBase<HuffErrorDefinition>(parent, elementType), HuffNamedStub {
  object Type : HuffStubElementType<HuffErrorDefinitionStub, HuffErrorDefinition>("ERROR_DEFINITION") {
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) =
      HuffErrorDefinitionStub(parentStub, this, dataStream.readNameAsString())

    override fun serialize(stub: HuffErrorDefinitionStub, dataStream: StubOutputStream) = with(dataStream) { writeName(stub.name) }

    override fun createPsi(stub: HuffErrorDefinitionStub) = HuffErrorDefinitionImpl(stub, this)

    override fun createStub(psi: HuffErrorDefinition, parentStub: StubElement<*>?) = HuffErrorDefinitionStub(parentStub, this, psi.name)

    override fun indexStub(stub: HuffErrorDefinitionStub, sink: IndexSink) = sink.indexErrorDefinition(stub)
  }
}

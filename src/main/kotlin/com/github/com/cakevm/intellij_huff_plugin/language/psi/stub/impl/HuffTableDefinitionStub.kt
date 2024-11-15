package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffTableDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffTableDefinitionImpl
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubElementType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.indexTableDefinition
import com.intellij.psi.stubs.*

class HuffTableDefinitionStub(parent: StubElement<*>?, elementType: IStubElementType<*, *>, override val name: String?) :
  StubBase<HuffTableDefinition>(parent, elementType), HuffNamedStub {
  object Type : HuffStubElementType<HuffTableDefinitionStub, HuffTableDefinition>("TABLE_DEFINITION") {
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) =
      HuffTableDefinitionStub(parentStub, this, dataStream.readNameAsString())

    override fun serialize(stub: HuffTableDefinitionStub, dataStream: StubOutputStream) = with(dataStream) { writeName(stub.name) }

    override fun createPsi(stub: HuffTableDefinitionStub) = HuffTableDefinitionImpl(stub, this)

    override fun createStub(psi: HuffTableDefinition, parentStub: StubElement<*>?) = HuffTableDefinitionStub(parentStub, this, psi.name)

    override fun indexStub(stub: HuffTableDefinitionStub, sink: IndexSink) = sink.indexTableDefinition(stub)
  }
}

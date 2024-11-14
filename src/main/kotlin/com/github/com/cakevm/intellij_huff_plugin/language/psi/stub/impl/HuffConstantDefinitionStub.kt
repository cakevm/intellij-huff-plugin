package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffConstantDefinitionImpl
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.indexConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.type.HuffStubElementType
import com.intellij.psi.stubs.*

class HuffConstantDefinitionStub(parent: StubElement<*>?, elementType: IStubElementType<*, *>, override val name: String?) :
  StubBase<HuffConstantDefinition>(parent, elementType), HuffNamedStub {
  object Type : HuffStubElementType<HuffConstantDefinitionStub, HuffConstantDefinition>("CONSTANT_DEFINITION") {
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) =
      HuffConstantDefinitionStub(parentStub, this, dataStream.readNameAsString())

    override fun serialize(stub: HuffConstantDefinitionStub, dataStream: StubOutputStream) = with(dataStream) { writeName(stub.name) }

    override fun createPsi(stub: HuffConstantDefinitionStub) = HuffConstantDefinitionImpl(stub, this)

    override fun createStub(psi: HuffConstantDefinition, parentStub: StubElement<*>?) =
      HuffConstantDefinitionStub(parentStub, this, psi.name)

    override fun indexStub(stub: HuffConstantDefinitionStub, sink: IndexSink) = sink.indexConstantDefinition(stub)
  }
}

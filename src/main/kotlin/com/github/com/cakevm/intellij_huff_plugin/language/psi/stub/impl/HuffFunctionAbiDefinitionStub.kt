package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFunctionAbiDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffFunctionAbiDefinitionImpl
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubElementType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.indexFunctionAbiDefinition
import com.intellij.psi.stubs.*

class HuffFunctionAbiDefinitionStub(parent: StubElement<*>?, elementType: IStubElementType<*, *>, override val name: String?) :
  StubBase<HuffFunctionAbiDefinition>(parent, elementType), HuffNamedStub {
  object Type : HuffStubElementType<HuffFunctionAbiDefinitionStub, HuffFunctionAbiDefinition>("FUNCTION_ABI_DEFINITION") {
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) =
      HuffFunctionAbiDefinitionStub(parentStub, this, dataStream.readNameAsString())

    override fun serialize(stub: HuffFunctionAbiDefinitionStub, dataStream: StubOutputStream) = with(dataStream) { writeName(stub.name) }

    override fun createPsi(stub: HuffFunctionAbiDefinitionStub) = HuffFunctionAbiDefinitionImpl(stub, this)

    override fun createStub(psi: HuffFunctionAbiDefinition, parentStub: StubElement<*>?) =
      HuffFunctionAbiDefinitionStub(parentStub, this, psi.name)

    override fun indexStub(stub: HuffFunctionAbiDefinitionStub, sink: IndexSink) = sink.indexFunctionAbiDefinition(stub)
  }
}

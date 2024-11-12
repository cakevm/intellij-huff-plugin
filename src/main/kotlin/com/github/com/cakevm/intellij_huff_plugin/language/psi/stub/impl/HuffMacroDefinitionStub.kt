package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffMacroDefinitionImpl
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.indexFunctionDef
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.type.HuffStubElementType
import com.intellij.psi.stubs.*

class HuffMacroDefinitionStub(parent: StubElement<*>?, elementType: IStubElementType<*, *>, override val name: String?) :
  StubBase<HuffMacroDefinition>(parent, elementType), HuffNamedStub {
  object Type : HuffStubElementType<HuffMacroDefinitionStub, HuffMacroDefinition>("MACRO_DEFINITION") {
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) =
      HuffMacroDefinitionStub(parentStub, this, dataStream.readNameAsString())

    override fun serialize(stub: HuffMacroDefinitionStub, dataStream: StubOutputStream) = with(dataStream) { writeName(stub.name) }

    override fun createPsi(stub: HuffMacroDefinitionStub) = HuffMacroDefinitionImpl(stub, this)

    override fun createStub(psi: HuffMacroDefinition, parentStub: StubElement<*>?) = HuffMacroDefinitionStub(parentStub, this, psi.name)

    override fun indexStub(stub: HuffMacroDefinitionStub, sink: IndexSink) = sink.indexFunctionDef(stub)
  }
}

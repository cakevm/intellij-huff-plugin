package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffIncludePathImpl
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.base.HuffStubElementType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index.indexIncludePathDefinition
import com.intellij.psi.stubs.*

class HuffIncludePathStub(parent: StubElement<*>?, elementType: IStubElementType<*, *>, override val name: String?, val path: String?) :
  StubBase<HuffIncludePathImpl>(parent, elementType), HuffNamedStub {

  object Type : HuffStubElementType<HuffIncludePathStub, HuffIncludePathImpl>("INCLUDE_PATH") {
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) =
      HuffIncludePathStub(parentStub, this, dataStream.readNameAsString(), dataStream.readUTFFast())

    override fun serialize(stub: HuffIncludePathStub, dataStream: StubOutputStream) =
      with(dataStream) {
        writeName(stub.name)
        writeUTFFast(stub.path ?: "")
      }

    override fun createPsi(stub: HuffIncludePathStub) = HuffIncludePathImpl(stub, this)

    override fun createStub(psi: HuffIncludePathImpl, parentStub: StubElement<*>?) =
      HuffIncludePathStub(parentStub, this, psi.name, psi.text)

    override fun indexStub(stub: HuffIncludePathStub, sink: IndexSink) = sink.indexIncludePathDefinition(stub)
  }
}

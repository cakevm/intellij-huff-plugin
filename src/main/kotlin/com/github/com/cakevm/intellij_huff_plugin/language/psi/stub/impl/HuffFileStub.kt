package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFile
import com.intellij.psi.PsiFile
import com.intellij.psi.StubBuilder
import com.intellij.psi.stubs.*
import com.intellij.psi.tree.IStubFileElementType

class HuffFileStub(file: HuffFile?) : PsiFileStubImpl<HuffFile>(file) {
  override fun getType() = Type

  object Type : IStubFileElementType<HuffFileStub>(HuffLanguage.INSTANCE) {
    // Update this version number if the stub structure changes
    override fun getStubVersion() = 6

    override fun getBuilder(): StubBuilder =
      object : DefaultStubBuilder() {
        override fun createStubForFile(file: PsiFile) = HuffFileStub(file as HuffFile)
      }

    override fun serialize(stub: HuffFileStub, dataStream: StubOutputStream) {}

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?) = HuffFileStub(null)

    override fun getExternalId(): String = "Huff.file"
  }
}

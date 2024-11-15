package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index

import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffFileStub
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey

class HuffFunctionAbiDefinitionIndex : StringStubIndexExtension<HuffNamedElement>() {
  companion object {
    val KEY: StubIndexKey<String, HuffNamedElement> = StubIndexKey.createIndexKey(HuffFunctionAbiDefinitionIndex::class.java.simpleName)
  }

  override fun getVersion(): Int = HuffFileStub.Type.stubVersion

  override fun getKey(): StubIndexKey<String, HuffNamedElement> = KEY
}

package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index

import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffNamedElement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffFileStub
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey

class HuffMacroDefinitionIndex : StringStubIndexExtension<HuffNamedElement>() {
  companion object {
    val KEY: StubIndexKey<String, HuffNamedElement> = StubIndexKey.createIndexKey(HuffMacroDefinitionIndex::class.java.canonicalName)
  }

  override fun getVersion(): Int = HuffFileStub.Type.stubVersion

  override fun getKey(): StubIndexKey<String, HuffNamedElement> = KEY
}

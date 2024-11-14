package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index

import com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin.HuffIncludePathMixin
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffFileStub
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey

class HuffIncludeIndex : StringStubIndexExtension<HuffIncludePathMixin>() {
  companion object {
    val KEY: StubIndexKey<String, HuffIncludePathMixin> = StubIndexKey.createIndexKey(HuffIncludeIndex::class.java.simpleName)
  }

  override fun getVersion(): Int = HuffFileStub.Type.stubVersion

  override fun getKey(): StubIndexKey<String, HuffIncludePathMixin> = KEY
}

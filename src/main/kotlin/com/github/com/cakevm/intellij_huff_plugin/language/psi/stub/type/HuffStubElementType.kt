package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.type

import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffElement
import com.intellij.psi.stubs.*

abstract class HuffStubElementType<S : StubElement<*>, P : HuffElement>(debugName: String) :
  IStubElementType<S, P>(debugName, HuffLanguage.INSTANCE) {

  final override fun getExternalId(): String = "huff.${super.toString()}"
}

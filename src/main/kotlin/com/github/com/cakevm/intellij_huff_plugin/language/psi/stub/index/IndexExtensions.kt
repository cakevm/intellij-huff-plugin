package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index

import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffMacroDefinitionStub
import com.intellij.psi.stubs.IndexSink

fun IndexSink.indexFunctionDef(stub: HuffMacroDefinitionStub) {
  indexNamedStub(stub)
  indexFunction(stub)
}

private fun IndexSink.indexNamedStub(stub: HuffNamedStub) {
  stub.name?.let { occurrence(HuffNamedElementIndex.KEY, it) }
}

private fun IndexSink.indexFunction(stub: HuffMacroDefinitionStub) {
  stub.name?.let { occurrence(HuffMacroIndex.KEY, it) }
}

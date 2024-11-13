package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index

import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffIncludePathStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffMacroDefinitionStub
import com.intellij.psi.stubs.IndexSink

private fun IndexSink.indexNamedStub(stub: HuffNamedStub) {
  stub.name?.let { occurrence(HuffNamedElementIndex.KEY, it) }
}

fun IndexSink.indexMacroDefinition(stub: HuffMacroDefinitionStub) {
  indexNamedStub(stub)
  indexMacro(stub)
}

private fun IndexSink.indexMacro(stub: HuffMacroDefinitionStub) {
  stub.name?.let { occurrence(HuffMacroIndex.KEY, it) }
}

fun IndexSink.indexIncludePathDefinition(stub: HuffIncludePathStub) {
  indexNamedStub(stub)
  indexIncludePath(stub)
}

private fun IndexSink.indexIncludePath(stub: HuffIncludePathStub) {
  stub.path?.let { occurrence(HuffIncludeIndex.KEY, it) }
}

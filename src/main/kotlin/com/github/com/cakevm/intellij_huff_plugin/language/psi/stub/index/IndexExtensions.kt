package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index

import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffConstantDefinitionStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffIncludePathStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffMacroDefinitionStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.HuffMacroLabelStub
import com.intellij.psi.stubs.IndexSink

private fun IndexSink.indexNamedStub(stub: HuffNamedStub) {
  stub.name?.let { occurrence(HuffNamedElementIndex.KEY, it) }
}

fun IndexSink.indexConstantDefinition(stub: HuffConstantDefinitionStub) {
  indexNamedStub(stub)
  stub.name?.let { occurrence(HuffConstantDefinitionIndex.KEY, it) }
}

fun IndexSink.indexMacroDefinition(stub: HuffMacroDefinitionStub) {
  indexNamedStub(stub)
  stub.name?.let { occurrence(HuffMacroDefinitionIndex.KEY, it) }
}

fun IndexSink.indexMacroLabel(stub: HuffMacroLabelStub) {
  indexNamedStub(stub)
  stub.name?.let { occurrence(HuffMacroLabelIndex.KEY, it) }
}

fun IndexSink.indexIncludePathDefinition(stub: HuffIncludePathStub) {
  indexNamedStub(stub)
  stub.path?.let { occurrence(HuffIncludeIndex.KEY, it) }
}

package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.index

import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.HuffNamedStub
import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl.*
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

fun IndexSink.indexErrorDefinition(stub: HuffErrorDefinitionStub) {
  indexNamedStub(stub)
  stub.name?.let { occurrence(HuffErrorDefinitionIndex.KEY, it) }
}

fun IndexSink.indexFunctionAbiDefinition(stub: HuffFunctionAbiDefinitionStub) {
  indexNamedStub(stub)
  stub.name?.let { occurrence(HuffFunctionAbiDefinitionIndex.KEY, it) }
}

fun IndexSink.indexEventAbiDefinition(stub: HuffEventAbiDefinitionStub) {
  indexNamedStub(stub)
  stub.name?.let { occurrence(HuffEventAbiDefinitionIndex.KEY, it) }
}

fun IndexSink.indexMacroLabel(stub: HuffMacroLabelStub) {
  indexNamedStub(stub)
  stub.name?.let { occurrence(HuffMacroLabelIndex.KEY, it) }
}

fun IndexSink.indexIncludePathDefinition(stub: HuffIncludePathStub) {
  indexNamedStub(stub)
  stub.path?.let { occurrence(HuffIncludeIndex.KEY, it) }
}

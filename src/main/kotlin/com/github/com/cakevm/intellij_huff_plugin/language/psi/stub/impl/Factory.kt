package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.type.HuffStubElementType

fun factory(name: String): HuffStubElementType<*, *> =
  when (name) {
    "MACRO_DEFINITION" -> HuffMacroDefinitionStub.Type

    else -> error("Unknown element $name")
  }

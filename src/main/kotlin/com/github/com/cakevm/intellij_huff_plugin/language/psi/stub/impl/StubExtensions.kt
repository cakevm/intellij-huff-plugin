package com.github.com.cakevm.intellij_huff_plugin.language.psi.stub.impl

import com.intellij.psi.stubs.StubInputStream

fun StubInputStream.readNameAsString(): String? = readName()?.string

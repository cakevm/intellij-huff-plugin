package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementBase
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffQuotedString
import com.intellij.lang.ASTNode

abstract class HuffQuotedStringMixin(node: ASTNode) : HuffElementBase(node), HuffQuotedString

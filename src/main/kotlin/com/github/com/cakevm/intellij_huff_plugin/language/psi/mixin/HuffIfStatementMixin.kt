package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffIfStatement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffElementImpl
import com.intellij.lang.ASTNode

abstract class HuffIfStatementMixin(node: ASTNode) : HuffElementImpl(node), HuffIfStatement

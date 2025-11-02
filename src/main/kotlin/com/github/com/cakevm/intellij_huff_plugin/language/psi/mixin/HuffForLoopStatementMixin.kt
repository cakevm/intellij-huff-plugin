package com.github.com.cakevm.intellij_huff_plugin.language.psi.mixin

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffForLoopStatement
import com.github.com.cakevm.intellij_huff_plugin.language.psi.impl.HuffElementImpl
import com.intellij.lang.ASTNode

abstract class HuffForLoopStatementMixin(node: ASTNode) : HuffElementImpl(node), HuffForLoopStatement

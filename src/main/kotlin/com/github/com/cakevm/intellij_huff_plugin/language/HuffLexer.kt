package com.github.com.cakevm.intellij_huff_plugin.language

import com.github.com.cakevm.intellij_huff_plugin.language.lexer._HuffLexer
import com.intellij.lexer.FlexAdapter
import java.io.Reader

class HuffLexer : FlexAdapter(_HuffLexer(null as Reader?))

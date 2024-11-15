package com.github.com.cakevm.intellij_huff_plugin.language.psi.type

import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class HuffElementType(@NonNls debugName: String) : IElementType(debugName, HuffLanguage.INSTANCE)

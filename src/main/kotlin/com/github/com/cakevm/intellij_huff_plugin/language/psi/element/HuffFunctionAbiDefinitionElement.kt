package com.github.com.cakevm.intellij_huff_plugin.language.psi.element

import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.base.HuffCallableElement
import com.github.com.cakevm.intellij_huff_plugin.language.types.HuffMember

interface HuffFunctionAbiDefinitionElement : HuffNamedElement, HuffMember, HuffCallableElement

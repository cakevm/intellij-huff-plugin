package com.github.com.cakevm.intellij_huff_plugin.language.psi.element

import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiNamedElement

interface HuffNamedElement : HuffElement, PsiNamedElement, NavigatablePsiElement

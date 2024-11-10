package com.github.com.cakevm.intellij_huff_plugin

import com.github.com.cakevm.intellij_huff_plugin.language.parser.HuffParserDefinition
import com.intellij.testFramework.ParsingTestCase

class HuffParsingTest : ParsingTestCase("", "huff", HuffParserDefinition()) {
  fun testParsingTestData() {
    doTest(true)
  }

  override fun getTestDataPath(): String {
    return "src/test/resources"
  }

  override fun includeRanges(): Boolean {
    return true
  }
}

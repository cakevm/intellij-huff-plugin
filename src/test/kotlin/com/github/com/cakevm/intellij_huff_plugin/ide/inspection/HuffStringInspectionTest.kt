package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class HuffStringInspectionTest : BasePlatformTestCase() {

  override fun setUp() {
    super.setUp()
    myFixture.enableInspections(HuffStringConstantDirectPushInspection::class.java, HuffBytesArgumentInspection::class.java)
  }

  fun testStringConstantWithBytesIsValid() {
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define constant GREETING = "hello"

      #define macro MAIN() = takes(0) returns(0) {
          __BYTES([GREETING])
          stop
      }
      """
        .trimIndent(),
    )

    val highlights = myFixture.doHighlighting()
    val errors = highlights.filter { it.severity.name == "ERROR" }
    assertTrue("Should have no errors for valid __BYTES usage", errors.isEmpty())
  }

  fun testStringConstantDirectPushIsError() {
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define constant GREETING = "hello"

      #define macro MAIN() = takes(0) returns(0) {
          [GREETING]
          stop
      }
      """
        .trimIndent(),
    )

    val highlights = myFixture.doHighlighting()
    val errors = highlights.filter { it.severity.name == "ERROR" }
    assertTrue("Should have error for string constant direct push", errors.size == 1)
    assertTrue("Error should mention __BYTES", errors[0].description.contains("__BYTES"))
  }

  fun testBytesWithNonStringConstantIsError() {
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define constant MY_HEX = 0x1234

      #define macro MAIN() = takes(0) returns(0) {
          __BYTES([MY_HEX])
          stop
      }
      """
        .trimIndent(),
    )

    val highlights = myFixture.doHighlighting()
    val errors = highlights.filter { it.severity.name == "ERROR" }
    assertTrue("Should have error for non-string constant in __BYTES", errors.size == 1)
    assertTrue("Error should mention string constant requirement", errors[0].description.contains("string constant"))
  }

  fun testBytesWithEmptyStringIsError() {
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define macro MAIN() = takes(0) returns(0) {
          __BYTES("")
          stop
      }
      """
        .trimIndent(),
    )

    val highlights = myFixture.doHighlighting()
    val errors = highlights.filter { it.severity.name == "ERROR" }
    assertTrue("Should have error for empty string in __BYTES", errors.size == 1)
    assertTrue("Error should mention empty string", errors[0].description.contains("Empty string"))
  }

  fun testBytesWithStringLiteralIsValid() {
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define macro MAIN() = takes(0) returns(0) {
          __BYTES("hello")
          stop
      }
      """
        .trimIndent(),
    )

    val highlights = myFixture.doHighlighting()
    val errors = highlights.filter { it.severity.name == "ERROR" }
    assertTrue("Should have no errors for valid __BYTES with string literal", errors.isEmpty())
  }

  fun testHexConstantDirectPushIsValid() {
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define constant MY_VALUE = 0x42

      #define macro MAIN() = takes(0) returns(0) {
          [MY_VALUE]
          stop
      }
      """
        .trimIndent(),
    )

    val highlights = myFixture.doHighlighting()
    val errors = highlights.filter { it.severity.name == "ERROR" }
    assertTrue("Should have no errors for hex constant direct push", errors.isEmpty())
  }

  fun testMultipleStringConstants() {
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define constant HELLO = "hello"
      #define constant WORLD = "world"

      #define macro MAIN() = takes(0) returns(0) {
          __BYTES([HELLO])
          __BYTES([WORLD])
          [HELLO]
          stop
      }
      """
        .trimIndent(),
    )

    val highlights = myFixture.doHighlighting()
    val errors = highlights.filter { it.severity.name == "ERROR" }
    assertTrue("Should have exactly 1 error for the direct push of HELLO", errors.size == 1)
    assertTrue("Error should be about HELLO", errors[0].description.contains("HELLO"))
  }
}

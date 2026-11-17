package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffConstantReference
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffErrorDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffFunctionAbiDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.element.HuffReferenceElement
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class HuffTestDecoratorInspectionTest : BasePlatformTestCase() {

  override fun setUp() {
    super.setUp()
    myFixture.enableInspections(HuffTestDecoratorInspection::class.java, HuffResolveNameInspection::class.java)
  }

  private fun problems(source: String): List<String> {
    myFixture.configureByText(HuffFileType.INSTANCE, source.trimIndent())
    return myFixture.doHighlighting().filter { it.severity >= HighlightSeverity.WARNING }.mapNotNull { it.description }
  }

  fun testValidDecoratorsHaveNoProblems() {
    val problems =
      problems(
        """
        #define constant OWNER_SLOT = FREE_STORAGE_POINTER()
        #define function transfer(address, uint256) nonpayable returns ()
        #define error InsufficientBalance(uint256)

        #[calldata(__FUNC_SIG(transfer), caller, 100), store([OWNER_SLOT], 0xbeef, at = this)]
        #[expect_revert(__ERROR(InsufficientBalance)), expect_balance(caller, min = 1), skip("slow")]
        #define test TRANSFER() = { stop }
        """
      )
    assertEmpty(problems)
  }

  fun testUnknownFlagAndIdentifier() {
    val problems =
      problems(
        """
        #[calldata(owner), expect_reverts]
        #define test T() = { stop }
        """
      )
    assertSameElements(problems, "Unknown identifier 'owner', expected `this` or `caller`", "Unknown decorator flag 'expect_reverts'")
  }

  fun testDecoratorOnMacro() {
    val problems =
      problems(
        """
        #[calldata("0x01")]
        #define macro M() = takes(0) returns(0) { stop }
        """
      )
    assertEquals(listOf("Decorators only apply to tests, not to a 'macro'"), problems)
  }

  fun testUndefinedReferencesInDecorators() {
    val problems =
      problems(
        """
        #[store([MISSING_SLOT], 0x01), calldata(__FUNC_SIG(missing)), expect_revert(__ERROR(MissingError))]
        #define test T() = { stop }
        """
      )
    assertSameElements(
      problems,
      "'MISSING_SLOT' constant is undefined",
      "'missing' function ABI is undefined",
      "'MissingError' error is undefined",
    )
  }

  fun testConstantUsedOnlyInDecoratorIsNotUnused() {
    myFixture.enableInspections(HuffUnusedElementInspection::class.java)
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define constant OWNER_SLOT = FREE_STORAGE_POINTER()

      #[store([OWNER_SLOT], 0x01)]
      #define test T() = { stop }
      """
        .trimIndent(),
    )
    val unused = myFixture.doHighlighting().filter { it.description?.contains("OWNER_SLOT") == true }
    assertEmpty(unused.map { it.description })
  }

  fun testReferencesInDecoratorsResolve() {
    myFixture.configureByText(
      HuffFileType.INSTANCE,
      """
      #define constant OWNER_SLOT = FREE_STORAGE_POINTER()
      #define function transfer(address, uint256) nonpayable returns ()
      #define error InsufficientBalance(uint256)

      #[store([OWNER_SLOT], 0x01), calldata(__FUNC_SIG(transfer)), expect_revert(__ERROR(InsufficientBalance))]
      #define test T() = { stop }
      """
        .trimIndent(),
    )
    val references = PsiTreeUtil.findChildrenOfType(myFixture.file, HuffReferenceElement::class.java)
    val targets = references.mapNotNull { it.reference?.resolve() }.map { it::class.java.interfaces.first().simpleName }
    assertTrue(
      PsiTreeUtil.findChildOfType(myFixture.file, HuffConstantReference::class.java)?.reference?.resolve() is HuffConstantDefinition
    )
    assertTrue("function ABI should resolve, got $targets", references.any { it.reference?.resolve() is HuffFunctionAbiDefinition })
    assertTrue("error should resolve, got $targets", references.any { it.reference?.resolve() is HuffErrorDefinition })
  }
}

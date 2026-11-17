package com.github.com.cakevm.intellij_huff_plugin

import com.github.com.cakevm.intellij_huff_plugin.ide.inspection.HuffResolveNameInspection
import com.github.com.cakevm.intellij_huff_plugin.language.HuffFileType
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffForLoopVariable
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroParameter
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroParameterReference
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

/** Syntax added in huff-neo 1.5.9 to 1.7.1, as described by the huff-neo pest grammar. */
class HuffNeoSyntaxTest : BasePlatformTestCase() {

  private fun parse(source: String): PsiFile = myFixture.configureByText(HuffFileType.INSTANCE, source.trimIndent())

  private fun assertParses(source: String) {
    val errors = PsiTreeUtil.findChildrenOfType(parse(source), PsiErrorElement::class.java)
    assertTrue(
      "File should parse without errors, but found: ${errors.map { "${it.errorDescription} at '${it.parent.text}'" }}",
      errors.isEmpty(),
    )
  }

  private fun warnings(source: String): List<String> {
    myFixture.enableInspections(HuffResolveNameInspection::class.java)
    parse(source)
    return myFixture.doHighlighting().filter { it.severity == HighlightSeverity.WARNING }.map { it.description }
  }

  fun testAmsterdamOpcodesWithOperands() =
    assertParses(
      """
      #define constant DEPTH = 0x11

      #define macro MAIN(depth) = takes(0) returns(0) {
          slotnum
          dupn 17
          dupn 0x11
          swapn [DEPTH]
          swapn <depth>
          exchange 1 2
          exchange 0x01 <depth>
          dupn ([DEPTH] + 1)
          for (i in 17..20) { dupn <i> }
          stop
      }
      """
    )

  fun testPushWithOperand() =
    assertParses(
      """
      #define constant SIZE = 0x20

      #define macro MAIN(value) = takes(0) returns(0) {
          push0
          push1 0x10
          push2 [SIZE]
          push1 <value>
          push4 ([SIZE] * 2)
          push1 16
          stop
      }
      """
    )

  fun testExpressionsInConstants() =
    assertParses(
      """
      #define constant A = 5
      #define constant B = 5 + 0x10
      #define constant C = [A] > [B]
      #define constant D = !!0x00
      #define constant E = - -[A]
      """
    )

  fun testDecimalsAndBooleansAsMacroArguments() =
    assertParses(
      """
      #define macro ADD(a, b) = takes(0) returns(1) { <a> <b> add }

      #define macro MAIN() = takes(0) returns(0) {
          ADD(18, 0x12)
          ADD(true, false)
          for (i in 0..3) { ADD(<i>, 1) }
          stop
      }
      """
    )

  fun testAssertPcExpressions() =
    assertParses(
      """
      #define constant BASE = 0x10

      #define macro MAIN(pc) = takes(0) returns(0) {
          __ASSERT_PC(0x00)
          __ASSERT_PC([BASE])
          __ASSERT_PC(<pc>)
          for (i in 0..2) { __ASSERT_PC([BASE] + <i> * 3) }
          stop
      }
      """
    )

  fun testArgsStartAndCodesizeRuntime() =
    assertParses(
      """
      #define macro CONSTRUCTOR() = takes(0) returns(0) {
          __argsstart() dup1 codesize sub swap1 0x00 codecopy
          __codesize(RUNTIME)
      }
      """
    )

  fun testCodesizeRuntimeIsNoUndefinedMacro() {
    val warnings =
      warnings(
        """
        #define macro MAIN() = takes(0) returns(0) {
            __codesize(RUNTIME) __codesize(MAIN) __argsstart()
        }
        """
      )
    assertEmpty(warnings)
  }

  fun testRemovedCodecopyDynArgIsUndefined() {
    val warnings =
      warnings(
        """
        #define macro CONSTRUCTOR() = takes(0) returns(0) {
            __CODECOPY_DYN_ARG()
        }
        """
      )
    assertEquals(listOf("'__CODECOPY_DYN_ARG' macro is undefined"), warnings)
  }

  fun testTestDecorators() =
    assertParses(
      """
      #define constant OWNER_SLOT = FREE_STORAGE_POINTER()
      #define constant AMOUNT = 0x10
      #define function transfer(address, uint256) nonpayable returns ()
      #define error InsufficientBalance(uint256)

      #[calldata("0x01")] #[value(0x01),]
      #define test STACKED() = { stop }

      #[calldata(__FUNC_SIG(transfer), caller, 100), value([AMOUNT])]
      #[store([OWNER_SLOT], 0xbeef), store(0x01, 0x02, at = 0x1000)]
      #[caller(0xbeef), timestamp(1700000000), block_number(100), gas_limit(30000000), chain_id(1)]
      #[deal(caller, 1000), etch(0x1000, "0x5f5ff3"), nonce(this, 5)]
      #[expect_revert(__ERROR(InsufficientBalance), 100), expect_gas(max = 30000)]
      #[expect_storage([OWNER_SLOT], 0x02), expect_balance(this, 5), expect_balance(caller, min = 1, max = 10,)]
      #define test DECORATED() = { stop }

      #[skip("needs a mainnet fork")]
      #define test SKIPPED() = { stop }

      #[skip, expect_halt]
      #define test BARE_FLAGS() = { stop }

      #[calldata("0x01") value(0x01)]
      #define test WITHOUT_COMMA() = { stop }
      """
    )

  fun testTupleTypes() =
    assertParses(
      """
      #define function fill((address maker, uint256 amount) order) nonpayable returns (bool)
      #define function batch((address,(bool,bytes))[] orders, uint256[3] amounts, bytes32[][] proofs) view returns ()
      #define event Filled((address maker, uint256 amount) indexed order, (bool, bytes)[] data)
      #define error BadOrder((address maker, uint256 amount) order)
      """
    )

  fun testTableSuffix() =
    assertParses(
      """
      #define table CODE_TABLE() = { 0x6080 }
      #define table CODE_TABLE_EQ = { 0x6080 }
      #define jumptable ROUTES() = { main }

      #define macro MAIN() = takes(0) returns(0) {
          main:
              stop
      }
      """
    )

  fun testMacroParametersNamedLikeOpcodesAndKeywords() {
    val file =
      parse(
        """
        #define macro ADD6(pc, origin, for, if, else, true) = takes(0) returns(1) {
            <pc> <origin> add <for> add <if> add <else> add <true> add
        }
        """
      )
    val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
    assertTrue("File should parse without errors, but found: ${errors.map { it.errorDescription }}", errors.isEmpty())

    val references = PsiTreeUtil.findChildrenOfType(file, HuffMacroParameterReference::class.java)
    assertEquals(6, references.size)
    references.forEach {
      assertTrue("<${it.parameterName.text}> should resolve to a parameter", it.reference?.resolve() is HuffMacroParameter)
    }
  }

  fun testLoopVariableReferencesResolve() {
    val file =
      parse(
        """
        #define macro HELPER(x) = takes(0) returns(0) { <x> pop }

        #define macro MAIN(i) = takes(0) returns(0) {
            for (j in 0..2) {
                <j> pop
                HELPER(<j>)
                if (<j> == 0x01) { <i> pop }
                for (k in <j>..(<j> + 2)) { dupn (<k> + 17) }
            }
        }
        """
      )
    val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
    assertTrue("File should parse without errors, but found: ${errors.map { it.errorDescription }}", errors.isEmpty())

    val references = PsiTreeUtil.findChildrenOfType(file, HuffMacroParameterReference::class.java)
    val resolved = references.associate { "${it.text}@${it.textOffset}" to it.reference?.resolve() }
    resolved.forEach { (ref, target) -> assertNotNull("$ref should resolve", target) }
    assertTrue(references.filter { it.parameterName.text in setOf("j", "k") }.all { it.reference?.resolve() is HuffForLoopVariable })
    assertTrue(references.filter { it.parameterName.text in setOf("i", "x") }.all { it.reference?.resolve() is HuffMacroParameter })
  }

  fun testUndefinedArgumentReferenceIsReported() {
    val warnings =
      warnings(
        """
        #define macro MAIN(a) = takes(0) returns(0) {
            <a> <b> add
        }
        """
      )
    assertEquals(listOf("'b' is neither a macro parameter nor a loop variable"), warnings)
  }

  fun testNestedBlockComments() =
    assertParses(
      """
      /* outer /* nested */ still a comment */
      #define macro MAIN() = takes(0) returns(0) {
          stop
      }
      """
    )
}

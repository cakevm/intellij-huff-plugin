package com.github.com.cakevm.intellij_huff_plugin.ide.inspection

import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffMacroDefinition
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffTestDecoratorFlag
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffTestDecoratorValue
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffVisitor
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor

/**
 * Reports test decorators that `hnc test` rejects:
 * - decorators above a `#define macro` or `#define fn`, as decorators only apply to tests
 * - flag names the test runner does not know
 * - bare identifiers as values other than `this` (the address of the test) and `caller` (the sender of the test)
 */
class HuffTestDecoratorInspection : LocalInspectionTool() {

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : HuffVisitor() {
      override fun visitMacroDefinition(o: HuffMacroDefinition) {
        if (o.macroType.text == "test") return
        o.testDecoratorList.forEach {
          holder.registerProblem(it, "Decorators only apply to tests, not to a '${o.macroType.text}'", ProblemHighlightType.GENERIC_ERROR)
        }
      }

      override fun visitTestDecoratorFlag(o: HuffTestDecoratorFlag) {
        val name = o.testDecoratorFlagName.text
        if (name !in FLAG_NAMES) {
          holder.registerProblem(o.testDecoratorFlagName, "Unknown decorator flag '$name'", ProblemHighlightType.WARNING)
        }
      }

      override fun visitTestDecoratorValue(o: HuffTestDecoratorValue) {
        val identifier = o.identifier ?: return
        if (identifier.text !in VALUE_KEYWORDS) {
          holder.registerProblem(
            identifier,
            "Unknown identifier '${identifier.text}', expected `this` or `caller`",
            ProblemHighlightType.WARNING,
          )
        }
      }
    }
  }

  companion object {
    /** Decorator flags known to `hnc test` (huff-neo v1.7.1). */
    val FLAG_NAMES =
      setOf(
        "calldata",
        "value",
        "store",
        "deal",
        "etch",
        "nonce",
        "caller",
        "coinbase",
        "gas_limit",
        "gas_price",
        "block_number",
        "timestamp",
        "basefee",
        "blob_basefee",
        "chain_id",
        "prevrandao",
        "slot_number",
        "expect_revert",
        "expect_partial_revert",
        "expect_halt",
        "expect_return",
        "expect_gas",
        "expect_storage",
        "expect_balance",
        "skip",
      )

    /** Bare identifiers allowed as decorator values. */
    private val VALUE_KEYWORDS = setOf("this", "caller")
  }
}

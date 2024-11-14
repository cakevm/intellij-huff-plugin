package com.github.com.cakevm.intellij_huff_plugin.ide.documentation

import kotlinx.serialization.Serializable

@Serializable
class InstructionDocumentation(
  val name: String,
  val description: String,
  val instrArgs: List<String>,
  val instrCategory: String,
  val instrGas: Int,
  val instrOpcode: Int,
  val instrReturns: List<String>,
) {
  fun toHtml(): String {
    return """
      <pre><b>
      $name(${instrArgs.joinToString(", ")})${if (instrReturns.isNotEmpty()) ": " + instrReturns.joinToString(", ") else ""}
      </b></pre>
      <p><i>min gas: $instrGas, opcode: $instrOpcode, category: $instrCategory</i></p>
      <p>$description</p>
    """
      .trimIndent()
  }
}

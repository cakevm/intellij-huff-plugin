package com.github.com.cakevm.intellij_huff_plugin.ide.documentation

import kotlinx.serialization.Serializable

@Serializable
class BuiltinFunctionDocumentation(
  val name: String,
  val description: String,
  val syntax: String,
  val returns: String,
  val example: String? = null,
) {
  fun toHtml(): String {
    val exampleSection = if (example != null) "<p><b>Example:</b><br/><code>$example</code></p>" else ""
    return """
      <pre><b>$name$syntax</b></pre>
      <p><i>Returns: $returns</i></p>
      <p>$description</p>
      $exampleSection
    """
      .trimIndent()
  }
}

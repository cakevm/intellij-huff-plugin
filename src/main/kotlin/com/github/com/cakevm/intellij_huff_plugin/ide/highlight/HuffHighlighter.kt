package com.github.com.cakevm.intellij_huff_plugin.ide.highlight

import com.github.com.cakevm.intellij_huff_plugin.ide.colors.HuffColor
import com.github.com.cakevm.intellij_huff_plugin.language.HuffLexer
import com.github.com.cakevm.intellij_huff_plugin.language.psi.HuffElementTypes.*
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class HuffHighlighter : SyntaxHighlighterBase() {
  override fun getHighlightingLexer(): Lexer = HuffLexer()

  override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
    return pack(tokenMapping[tokenType])
  }

  // Only token from lexer
  private val tokenMapping: Map<IElementType, TextAttributesKey> =
    mapOf(
        COMMENT to HuffColor.COMMENT,
        DEFINE to HuffColor.DEFINE,

        // Define statements
        FUNCTION to HuffColor.DEFINE,
        FN to HuffColor.DEFINE,
        ERROR to HuffColor.DEFINE,
        EVENT to HuffColor.DEFINE,
        MACRO to HuffColor.DEFINE,
        CONSTANT to HuffColor.DEFINE,
        JUMPTABLE to HuffColor.DEFINE,
        JUMPTABLE_PACKED to HuffColor.DEFINE,
        TABLE to HuffColor.DEFINE,
        TEST to HuffColor.DEFINE,
        FOR to HuffColor.DEFINE,
        IN to HuffColor.DEFINE,
        STEP to HuffColor.DEFINE,
        // literals
        HEXCODE to HuffColor.HEXCODE,
        STRING to HuffColor.STRING,
        DECIMAL to HuffColor.DECIMAL,
        INCLUDE to HuffColor.INCLUDE,
        L_BRACE to HuffColor.BRACES,
        R_BRACE to HuffColor.BRACES,
        // Arithmetic operators
        PLUS to HuffColor.OPERATOR,
        MINUS to HuffColor.OPERATOR,
        STAR to HuffColor.OPERATOR,
        SLASH to HuffColor.OPERATOR,
        PERCENT to HuffColor.OPERATOR,
      )
      .plus(buildInFns().map { it to HuffColor.BUILTIN_FN })
      .plus(opcodeNamesStackManagement().map { it to HuffColor.STACK_MANAGEMENT })
      .plus(opcodeNamesEnvironmentalInformation().map { it to HuffColor.ENVIRONMENTAL_INFORMATION })
      .plus(opcodeNamesStorageAndMemoryAccess().map { it to HuffColor.STORAGE_AND_MEMORY_ACCESS })
      .plus(opcodeNamesFlowControl().map { it to HuffColor.FLOW_CONTROL })
      .plus(opcodeNamesSystemAndCallRelated().map { it to HuffColor.SYSTEM_AND_CALL })
      .plus(opcodeNamesLogging().map { it to HuffColor.LOGGING })
      .plus(opcodeNamesMiscellaneous().map { it to HuffColor.MISCELLANEOUS })
      .plus(opcodeNamesGasAndCostManagement().map { it to HuffColor.GAS_AND_COST_MANAGEMENT })
      .mapValues { it.value.textAttributesKey }

  private fun buildInFns() =
    setOf<IElementType>(
      BUILTIN_FN_TABLESIZE,
      BUILTIN_FN_CODESIZE,
      BUILTIN_FN_TABLESTART,
      BUILTIN_FN_FUNC_SIG,
      BUILTIN_FN_EVENT_HASH,
      BUILTIN_FN_ERROR,
      BUILTIN_FN_LEFTPAD,
      BUILTIN_FN_RIGHTPAD,
      BUILTIN_FN_CODECOPY_DYN_ARG,
      BUILTIN_FN_VERBATIM,
      BUILTIN_FN_BYTES,
      BUILTIN_FN_ASSERT_PC,
      FREE_STORAGE_POINTER,
      BUILTIN_CONST_NOOP,
    )

  private fun opcodeNamesStackManagement() =
    setOf<IElementType>(
      // Stack Management Opcodes
      PUSH0,
      PUSH1,
      PUSH2,
      PUSH3,
      PUSH4,
      PUSH5,
      PUSH6,
      PUSH7,
      PUSH8,
      PUSH9,
      PUSH10,
      PUSH11,
      PUSH12,
      PUSH13,
      PUSH14,
      PUSH15,
      PUSH16,
      PUSH17,
      PUSH18,
      PUSH19,
      PUSH20,
      PUSH21,
      PUSH22,
      PUSH23,
      PUSH24,
      PUSH25,
      PUSH26,
      PUSH27,
      PUSH28,
      PUSH29,
      PUSH30,
      PUSH31,
      PUSH32,
      POP,
      DUP1,
      DUP2,
      DUP3,
      DUP4,
      DUP5,
      DUP6,
      DUP7,
      DUP8,
      DUP9,
      DUP10,
      DUP11,
      DUP12,
      DUP13,
      DUP14,
      DUP15,
      DUP16,
      SWAP1,
      SWAP2,
      SWAP3,
      SWAP4,
      SWAP5,
      SWAP6,
      SWAP7,
      SWAP8,
      SWAP9,
      SWAP10,
      SWAP11,
      SWAP12,
      SWAP13,
      SWAP14,
      SWAP15,
      SWAP16,
      // Arithmetic and Logic Opcodes
      ADD,
      SUB,
      MUL,
      DIV,
      SDIV,
      MOD,
      SMOD,
      ADDMOD,
      MULMOD,
      EXP,
      SIGNEXTEND,
      LT,
      GT,
      SLT,
      SGT,
      EQ,
      ISZERO,
      AND,
      OR,
      XOR,
      NOT,
      BYTE,
      SHL,
      SHR,
      SAR,
      SHA3,
      CLZ,
    )

  private fun opcodeNamesEnvironmentalInformation() =
    setOf<IElementType>(
      // Environmental Information Opcodes
      ADDRESS,
      BALANCE,
      ORIGIN,
      CALLER,
      CALLVALUE,
      CALLDATALOAD,
      CALLDATASIZE,
      CALLDATACOPY,
      CHAINID,
      CODESIZE,
      CODECOPY,
      GASPRICE,
      EXTCODESIZE,
      EXTCODECOPY,
      EXTCODEHASH,
      RETURNDATASIZE,
      RETURNDATACOPY,
      BLOCKHASH,
      COINBASE,
      TIMESTAMP,
      NUMBER,
      DIFFICULTY,
      GASLIMIT,
      PREVRANDAO,
      BASEFEE,
      SELFBALANCE,
      BLOBHASH,
      BLOBBASEFEE,
    )

  private fun opcodeNamesStorageAndMemoryAccess() =
    setOf<IElementType>(
      // Storage and Memory Access Opcodes
      SLOAD,
      SSTORE,
      MLOAD,
      MSTORE,
      MSTORE8,
      MSIZE,
      TLOAD,
      TSTORE,
      MCOPY,
    )

  private fun opcodeNamesFlowControl() =
    setOf<IElementType>(
      // Flow Control Opcodes
      JUMP,
      JUMPI,
      PC,
      JUMPDEST,
    )

  private fun opcodeNamesSystemAndCallRelated() =
    setOf<IElementType>(
      // System and Call-Related Opcodes
      CALL,
      CALLCODE,
      DELEGATECALL,
      STATICCALL,
      CREATE,
      CREATE2,
      SELFDESTRUCT,
    )

  private fun opcodeNamesLogging() =
    setOf<IElementType>(
      // Logging Opcodes
      LOG0,
      LOG1,
      LOG2,
      LOG3,
      LOG4,
    )

  private fun opcodeNamesMiscellaneous() =
    setOf<IElementType>(
      // Miscellaneous Opcodes
      STOP,
      RETURN,
      REVERT,
      INVALID,
    )

  private fun opcodeNamesGasAndCostManagement() =
    setOf<IElementType>(
      // Gas and Cost Management Opcodes
      GAS
    )
}

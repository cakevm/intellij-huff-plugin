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

  private val tokenMapping: Map<IElementType, TextAttributesKey> =
    mapOf(
        COMMENT to HuffColor.COMMENT,
        DEFINE to HuffColor.DEFINE,
        HEXCODE to HuffColor.HEXCODE,
        STRING to HuffColor.STRING,
        INCLUDE to HuffColor.INCLUDE,
        L_BRACE to HuffColor.BRACES,
        R_BRACE to HuffColor.BRACES,
      )
      .plus(buildinFns().map { it to HuffColor.BUILDIN_FN })
      .mapValues { it.value.textAttributesKey }

  private fun buildinFns() =
    setOf<IElementType>(
      BUILDIN_FN_TABLESIZE,
      BUILDIN_FN_CODESIZE,
      BUILDIN_FN_TABLESTART,
      BUILDIN_FN_FUNC_SIG,
      BUILDIN_FN_EVENT_HASH,
      BUILDIN_FN_ERROR,
      BUILDIN_FN_RIGHTPAD,
      BUILDIN_FN_CODECOPY_DYN_ARG,
      BUILDIN_FN_VERBATIM,
      FREE_STORAGE_POINTER,
    )

  private fun opcodeNames() =
    setOf<IElementType>(
      // Stack Management Opcodes
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

      // Environmental Information Opcodes
      ADDRESS,
      BALANCE,
      ORIGIN,
      CALLER,
      CALLVALUE,
      CALLDATALOAD,
      CALLDATASIZE,
      CALLDATACOPY,
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

      // Storage and Memory Access Opcodes
      SLOAD,
      SSTORE,
      MLOAD,
      MSTORE,
      MSTORE8,
      MSIZE,

      // Flow Control Opcodes
      JUMP,
      JUMPI,
      PC,
      JUMPDEST,

      // System and Call-Related Opcodes
      CALL,
      CALLCODE,
      DELEGATECALL,
      STATICCALL,
      CREATE,
      CREATE2,
      SELFDESTRUCT,

      // Logging Opcodes
      LOG0,
      LOG1,
      LOG2,
      LOG3,
      LOG4,

      // Miscellaneous Opcodes
      STOP,
      RETURN,
      REVERT,
      INVALID,

      // Gas and Cost Management Opcodes
      GAS,
    )
}

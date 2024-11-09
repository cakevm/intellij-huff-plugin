package com.github.com.cakevm.intellij_huff_plugin.ide.highlight

import HuffLexer
import com.github.com.cakevm.intellij_huff_plugin.ide.colors.HuffColor
import com.github.com.cakevm.intellij_huff_plugin.language.HuffLanguage
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.lexer.TokenIElementType

class HuffHighlighter : SyntaxHighlighterBase() {
  override fun getHighlightingLexer(): Lexer =
    ANTLRLexerAdaptor(HuffLanguage.INSTANCE, HuffLexer(null))

  override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
    return pack(map(tokenType)?.textAttributesKey)
  }

  companion object {
    init {
      // init here to get around https://github.com/antlr/antlr4-intellij-adaptor/issues/31
      val vocabulary = HuffParser.VOCABULARY
      PSIElementTypeFactory.defineLanguageIElementTypes(
        HuffLanguage.INSTANCE,
        (0..vocabulary.maxTokenType)
          .map { vocabulary.getLiteralName(it) ?: (vocabulary.getSymbolicName(it) ?: "<INVALID>") }
          .toTypedArray(),
        HuffParser.ruleNames,
      )
    }
  }
}

fun map(tokenType: IElementType?): HuffColor? {
  if (tokenType !is TokenIElementType) return null
  return when (tokenType.antlrTokenType) {
    HuffLexer.LINE_COMMENT -> HuffColor.COMMENT
    HuffLexer.BLOCK_COMMENT -> HuffColor.BLOCK_COMMENT
    HuffLexer.HEXCODE -> HuffColor.HEXCODE
    HuffLexer.STRING -> HuffColor.STRING
    HuffLexer.DEFINE -> HuffColor.DEFINE
    HuffLexer.INCLUDE -> HuffColor.INCLUDE
    HuffLexer.L_BRACE,
    HuffLexer.R_BRACE -> HuffColor.BRACES

    // Build-in functions
    HuffLexer.BUILDIN_FN_TABLESIZE,
    HuffLexer.BUILDIN_FN_CODESIZE,
    HuffLexer.BUILDIN_FN_TABLESTART,
    HuffLexer.BUILDIN_FN_FUNC_SIG,
    HuffLexer.BUILDIN_FN_EVENT_HASH,
    HuffLexer.BUILDIN_FN_ERROR,
    HuffLexer.BUILDIN_FN_RIGHTPAD,
    HuffLexer.BUILDIN_FN_CODECOPY_DYN_ARG,
    HuffLexer.BUILDIN_FN_VERBATIM,
    HuffLexer.FREE_STORAGE_POINTER -> HuffColor.BUILDIN_FN

    // Stack Management Opcodes
    HuffLexer.PUSH1,
    HuffLexer.PUSH2,
    HuffLexer.PUSH3,
    HuffLexer.PUSH4,
    HuffLexer.PUSH5,
    HuffLexer.PUSH6,
    HuffLexer.PUSH7,
    HuffLexer.PUSH8,
    HuffLexer.PUSH9,
    HuffLexer.PUSH10,
    HuffLexer.PUSH11,
    HuffLexer.PUSH12,
    HuffLexer.PUSH13,
    HuffLexer.PUSH14,
    HuffLexer.PUSH15,
    HuffLexer.PUSH16,
    HuffLexer.PUSH17,
    HuffLexer.PUSH18,
    HuffLexer.PUSH19,
    HuffLexer.PUSH20,
    HuffLexer.PUSH21,
    HuffLexer.PUSH22,
    HuffLexer.PUSH23,
    HuffLexer.PUSH24,
    HuffLexer.PUSH25,
    HuffLexer.PUSH26,
    HuffLexer.PUSH27,
    HuffLexer.PUSH28,
    HuffLexer.PUSH29,
    HuffLexer.PUSH30,
    HuffLexer.PUSH31,
    HuffLexer.PUSH32,
    HuffLexer.POP,
    HuffLexer.DUP1,
    HuffLexer.DUP2,
    HuffLexer.DUP3,
    HuffLexer.DUP4,
    HuffLexer.DUP5,
    HuffLexer.DUP6,
    HuffLexer.DUP7,
    HuffLexer.DUP8,
    HuffLexer.DUP9,
    HuffLexer.DUP10,
    HuffLexer.DUP11,
    HuffLexer.DUP12,
    HuffLexer.DUP13,
    HuffLexer.DUP14,
    HuffLexer.DUP15,
    HuffLexer.DUP16,
    HuffLexer.SWAP1,
    HuffLexer.SWAP2,
    HuffLexer.SWAP3,
    HuffLexer.SWAP4,
    HuffLexer.SWAP5,
    HuffLexer.SWAP6,
    HuffLexer.SWAP7,
    HuffLexer.SWAP8,
    HuffLexer.SWAP9,
    HuffLexer.SWAP10,
    HuffLexer.SWAP11,
    HuffLexer.SWAP12,
    HuffLexer.SWAP13,
    HuffLexer.SWAP14,
    HuffLexer.SWAP15,
    HuffLexer.SWAP16 -> HuffColor.STACK_MANAGEMENT
    // Arithmetic and Logic Opcodes

    HuffLexer.ADD,
    HuffLexer.SUB,
    HuffLexer.MUL,
    HuffLexer.DIV,
    HuffLexer.SDIV,
    HuffLexer.MOD,
    HuffLexer.SMOD,
    HuffLexer.ADDMOD,
    HuffLexer.MULMOD,
    HuffLexer.EXP,
    HuffLexer.SIGNEXTEND,
    HuffLexer.LT,
    HuffLexer.GT,
    HuffLexer.SLT,
    HuffLexer.SGT,
    HuffLexer.EQ,
    HuffLexer.ISZERO,
    HuffLexer.AND,
    HuffLexer.OR,
    HuffLexer.XOR,
    HuffLexer.NOT,
    HuffLexer.BYTE,
    HuffLexer.SHL,
    HuffLexer.SHR,
    HuffLexer.SAR -> HuffColor.ARITHMETIC_AND_LOGICAL

    // Environmental Information Opcodes
    HuffLexer.ADDRESS,
    HuffLexer.BALANCE,
    HuffLexer.ORIGIN,
    HuffLexer.CALLER,
    HuffLexer.CALLVALUE,
    HuffLexer.CALLDATALOAD,
    HuffLexer.CALLDATASIZE,
    HuffLexer.CALLDATACOPY,
    HuffLexer.CODESIZE,
    HuffLexer.CODECOPY,
    HuffLexer.GASPRICE,
    HuffLexer.EXTCODESIZE,
    HuffLexer.EXTCODECOPY,
    HuffLexer.EXTCODEHASH,
    HuffLexer.RETURNDATASIZE,
    HuffLexer.RETURNDATACOPY,
    HuffLexer.BLOCKHASH,
    HuffLexer.COINBASE,
    HuffLexer.TIMESTAMP,
    HuffLexer.NUMBER,
    HuffLexer.DIFFICULTY,
    HuffLexer.GASLIMIT -> HuffColor.ENVIRONMENTAL_INFORMATION

    // Storage and Memory Access Opcodes
    HuffLexer.SLOAD,
    HuffLexer.SSTORE,
    HuffLexer.MLOAD,
    HuffLexer.MSTORE,
    HuffLexer.MSTORE8,
    HuffLexer.MSIZE -> HuffColor.STORAGE_AND_MEMORY_ACCESS

    // Flow Control Opcodes
    HuffLexer.JUMP,
    HuffLexer.JUMPI,
    HuffLexer.PC,
    HuffLexer.JUMPDEST -> HuffColor.FLOW_CONTROL

    // System and Call-Related Opcodes
    HuffLexer.CALL,
    HuffLexer.CALLCODE,
    HuffLexer.DELEGATECALL,
    HuffLexer.STATICCALL,
    HuffLexer.CREATE,
    HuffLexer.CREATE2,
    HuffLexer.SELFDESTRUCT -> HuffColor.SYSTEM_AND_CALL

    // Logging Opcodes
    HuffLexer.LOG0,
    HuffLexer.LOG1,
    HuffLexer.LOG2,
    HuffLexer.LOG3,
    HuffLexer.LOG4 -> HuffColor.LOGGING

    // Miscellaneous Opcodes
    HuffLexer.STOP,
    HuffLexer.RETURN,
    HuffLexer.REVERT,
    HuffLexer.INVALID -> HuffColor.MISCELLANEOUS

    // Gas and Cost Management Opcodes
    HuffLexer.GAS -> HuffColor.GAS_AND_COST_MANAGEMENT

    else -> null
  }
}

parser grammar HuffParser;
options { tokenVocab=HuffLexer; }

// Start for the root of the huff file
huffFileRoot: (lineComment | blockComment | defineDefinition | includeDefinition | macroDefinition)* EOF;

blockComment: BLOCK_COMMENT | NATSPEC_DOCBLOCK;
lineComment: LINE_COMMENT | NATSPEC_SINGLELINE;

// #include statement
includeDefinition: INCLUDE stringLiteral;

// #define function / event
// abi spec for details: https://docs.soliditylang.org/en/latest/abi-spec.html
functionDefinition: Function abiSignatureDefinition;
defineDefinition: DEFINE ( eventDefinition | functionDefinition | constantDefinition | errorDefinition | jumptableDefinition | tableDefinition);

// #define constant
constantDefinition: CONSTANT identifier EQUAL (FREE_STORAGE_POINTER | HEXCODE | macroIdentifier  );

// #define error
errorDefinition: ERROR identifier L_PAREN typeName R_PAREN;

// #define jumptable
jumpTableBody: L_BRACE identifier identifier identifier identifier R_BRACE;
jumptableDefinition: JUMPTABLE identifier jumpTableBody;

// #define table
tableBody: L_BRACE HEXCODE+ R_BRACE;
tableDefinition: TABLE identifier tableBody;

// #define macro
macroIdentifier: (MAIN | CONSTRUCTOR | identifier) L_PAREN (identifier (COMMA identifier)*)? R_PAREN; // [MACRO_NAME]
macroTakesParameters: TAKES L_PAREN parameter=DECIMAL R_PAREN; // takes(1)
macroReturnsParameters: RETURNS L_PAREN parameter=DECIMAL R_PAREN; // returns(1)
bodyDefinition: L_BRACK identifier R_BRACK;
bodyLabel: identifier COLON;
macroCall: (buildinFnSignature | identifier) L_PAREN (HEXCODE | stringLiteral | identifier) R_PAREN;
bodyMacroParameter: LESS_THAN identifier GREATER_THAN;
bodyStatement: ( HEXCODE | opcode | bodyDefinition | macroIdentifier | macroCall | bodyLabel | bodyMacroParameter | identifier )+;
macroBody: L_BRACE ( LINE_COMMENT | bodyStatement )* R_BRACE;
buildinFnSignature: (BUILDIN_FN_TABLESIZE | BUILDIN_FN_CODESIZE | BUILDIN_FN_TABLESTART | BUILDIN_FN_FUNC_SIG | BUILDIN_FN_EVENT_HASH | BUILDIN_FN_ERROR | BUILDIN_FN_RIGHTPAD | BUILDIN_FN_CODECOPY_DYN_ARG | BUILDIN_FN_VERBATIM);
macroDefinition: macroDecorator? DEFINE (MACRO |TEST) macroIdentifier EQUAL macroTakesParameters? macroReturnsParameters? body=macroBody;
// #define macro with decorator
macroDecorator: DECORATOR_START (macroCall (COMMA macroCall)*)? R_BRACK;


// ALL OPCODES
opcode
    : stackManagement
    | arithmeticAndLogic
    | environmentalInformation
    | storageAndMemoryAccess
    | flowControl
    | systemAndCall
    | logging
    | miscellaneous
    | gasManagement;

// Stack Management Opcodes
stackManagement
    : PUSH1 | PUSH2 | PUSH3 | PUSH4 | PUSH5 | PUSH6 | PUSH7 | PUSH8
    | PUSH9 | PUSH10 | PUSH11 | PUSH12 | PUSH13 | PUSH14 | PUSH15 | PUSH16
    | PUSH17 | PUSH18 | PUSH19 | PUSH20 | PUSH21 | PUSH22 | PUSH23 | PUSH24
    | PUSH25 | PUSH26 | PUSH27 | PUSH28 | PUSH29 | PUSH30 | PUSH31 | PUSH32
    | POP
    | DUP1 | DUP2 | DUP3 | DUP4 | DUP5 | DUP6 | DUP7 | DUP8
    | DUP9 | DUP10 | DUP11 | DUP12 | DUP13 | DUP14 | DUP15 | DUP16
    | SWAP1 | SWAP2 | SWAP3 | SWAP4 | SWAP5 | SWAP6 | SWAP7 | SWAP8
    | SWAP9 | SWAP10 | SWAP11 | SWAP12 | SWAP13 | SWAP14 | SWAP15 | SWAP16;

// Arithmetic and Logic Opcodes
arithmeticAndLogic
    : ADD | SUB | MUL | DIV | SDIV | MOD | SMOD | ADDMOD | MULMOD | EXP | SIGNEXTEND
    | LT | GT | SLT | SGT | EQ | ISZERO
    | AND | OR | XOR | NOT | BYTE
    | SHL | SHR | SAR;

// Environmental Information Opcodes
environmentalInformation
    : ADDRESS | BALANCE | ORIGIN | CALLER | CALLVALUE
    | CALLDATALOAD | CALLDATASIZE | CALLDATACOPY
    | CODESIZE | CODECOPY
    | GASPRICE
    | EXTCODESIZE | EXTCODECOPY | EXTCODEHASH
    | RETURNDATASIZE | RETURNDATACOPY
    | BLOCKHASH | COINBASE | TIMESTAMP | NUMBER | DIFFICULTY | GASLIMIT;

// Storage and Memory Access Opcodes
storageAndMemoryAccess
    : SLOAD | SSTORE
    | MLOAD | MSTORE | MSTORE8 | MSIZE;

// Flow Control Opcodes
flowControl
    : JUMP | JUMPI
    | PC
    | JUMPDEST;

// System and Call-Related Opcodes
systemAndCall
    : CALL | CALLCODE | DELEGATECALL | STATICCALL
    | CREATE | CREATE2
    | SELFDESTRUCT;

// Logging Opcodes
logging: LOG0 | LOG1 | LOG2 | LOG3 | LOG4;

// Miscellaneous Opcodes
miscellaneous: STOP | RETURN | REVERT | INVALID;

// Gas and Cost Management Opcodes
gasManagement: GAS;

// A full string literal consists of either one or several consecutive quoted strings.
stringLiteral: (NON_EMPTY_STRING_LITERAL | EMPTY_STRING_LITERAL)+;

// MODIFIED FROM: https://github.com/ethereum/solidity/blob/fca0bd31c4915c2022e697f0217c6982c078d41c/docs/grammar/SolidityParser.g4
// SEE Abi spec for details: https://docs.soliditylang.org/en/latest/abi-spec.html
identifier: IDENTIFIER | FROM | ERROR | REVERT | GLOBAL | TRANSIENT | CALLDATA | opcode;

eventDefinition:
	Event name=identifier
	L_PAREN (parameters+=eventParameter (COMMA parameters+=eventParameter)*)? R_PAREN;

eventParameter: type=typeName INDEXED? name=identifier?;

stateMutability: PURE | VIEW | PAYABLE | NONPAYABLE;

abiSignatureDefinition: identifier L_PAREN (arguments=parameterList)? R_PAREN stateMutability? (RETURNS L_PAREN returnParameters=parameterList R_PAREN)?;

parameterList: parameters+=parameterDeclaration (COMMA parameters+=parameterDeclaration)*;

dataLocation: MEMORY | STORAGE | CALLDATA;
parameterDeclaration: type=typeName location=dataLocation? name=identifier?;

inlineArrayExpression: L_BRACK (expression ( COMMA expression)* ) R_BRACK;
expression: L_BRACK index=expression? R_BRACK | inlineArrayExpression;

typeName: elementaryTypeName | typeName L_BRACK expression? R_BRACK;
elementaryTypeName: ADDRESS | BOOL | STRING | BYTES | SIGNED_INTEGER_TYPE | UNSIGNED_INTEGER_TYPE | FIXED_BYTES | FIXED | UFIXED;

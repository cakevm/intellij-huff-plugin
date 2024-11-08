lexer grammar HuffLexer;

// Comments
LINE_COMMENT: '//' ~[\r\n]*;
BLOCK_COMMENT: '/*' .*? '*/';

// NatSpec (see: https://docs.soliditylang.org/en/develop/natspec-format.html)
NATSPEC_DOCBLOCK: '/**' .*? '*/' ;
NATSPEC_SINGLELINE: '///' ~( '\r' | '\n' )*;
NATSPEC_TITLE: '@title';
NATSPEC_AUTHOR: '@author';
NATSPEC_NOTICE: '@notice';
NATSPEC_DEV: '@dev';
NATSPEC_PARAM: '@param';
NATSPEC_RETURN: '@return';

INCLUDE: '#include';
DEFINE: '#define';
DECORATOR_START: '#[';

// Define statements
Function: 'function';
Event: 'event';
MACRO: 'macro';
CONSTANT: 'constant';
ERROR: 'error';
JUMPTABLE: 'jumptable';
TABLE: 'table';
TEST: 'test';

// Constants
FREE_STORAGE_POINTER: 'FREE_STORAGE_POINTER()';

// reserved macro names
MAIN: 'MAIN';
CONSTRUCTOR: 'CONSTRUCTOR';

// macro optioal returns
TAKES: 'takes';
RETURNS: 'returns';

// Build-in functions
BUILDIN_FN_TABLESIZE: '__tablesize';
BUILDIN_FN_CODESIZE: '__codesize';
BUILDIN_FN_TABLESTART: '__tablestart';
BUILDIN_FN_FUNC_SIG: '__FUNC_SIG';
BUILDIN_FN_EVENT_HASH: '__EVENT_HASH';
BUILDIN_FN_ERROR: '__ERROR';
BUILDIN_FN_RIGHTPAD: '__RIGHTPAD';
BUILDIN_FN_CODECOPY_DYN_ARG: '__CODECOPY_DYN_ARG';
BUILDIN_FN_VERBATIM: '__VERBATIM';


// FROM: https://github.com/ethereum/solidity/blob/fca0bd31c4915c2022e697f0217c6982c078d41c/docs/grammar/SolidityLexer.g4

// A non-empty quoted string literal restricted to printable characters.
NON_EMPTY_STRING_LITERAL: '"' DOUBLE_QUOTED_PRINTABLE + '"' | '\'' SINGLE_QUOTED_PRINTABLE+ '\'';
EMPTY_STRING_LITERAL: '"' '"' | '\'' '\'';
// Any printable character except single quote or back slash.
fragment SINGLE_QUOTED_PRINTABLE: [\u0020-\u0026\u0028-\u005B\u005D-\u007E];
// Any printable character except double quote or back slash.
fragment DOUBLE_QUOTED_PRINTABLE: [\u0020-\u0021\u0023-\u005B\u005D-\u007E];


FROM: 'from';
//ERROR: 'error';
REVERT: 'revert';
GLOBAL: 'global';
TRANSIENT: 'transient';
INDEXED: 'indexed';

// state mutability
NONPAYABLE: 'nonpayable';
PAYABLE: 'payable';
PURE: 'pure';
VIEW: 'view';

// Data location
MEMORY: 'memory';
STORAGE: 'storage';
CALLDATA: 'calldata';

L_PAREN: '(';
R_PAREN: ')';
L_BRACK: '[';
R_BRACK: ']';
COMMA: ',';
L_BRACE: '{';
R_BRACE: '}';
EQUAL: '=';
DOUBLE_QUOTE: '"';
COLON: ':';
LESS_THAN: '<';
GREATER_THAN: '>';


ADDRESS: 'address';
BOOL: 'bool';
BYTES: 'bytes';
FIXED: 'fixed' | ('fixed' [1-9][0-9]* 'x' [1-9][0-9]*);

// Bytes types of fixed length.
FIXED_BYTES:
	'bytes1' | 'bytes2' | 'bytes3' | 'bytes4' | 'bytes5' | 'bytes6' | 'bytes7' | 'bytes8' |
	'bytes9' | 'bytes10' | 'bytes11' | 'bytes12' | 'bytes13' | 'bytes14' | 'bytes15' | 'bytes16' |
	'bytes17' | 'bytes18' | 'bytes19' | 'bytes20' | 'bytes21' | 'bytes22' | 'bytes23' | 'bytes24' |
	'bytes25' | 'bytes26' | 'bytes27' | 'bytes28' | 'bytes29' | 'bytes30' | 'bytes31' | 'bytes32';

// Sized signed integer types. "int" is an alias of int256.
SIGNED_INTEGER_TYPE:
	'int' | 'int8' | 'int16' | 'int24' | 'int32' | 'int40' | 'int48' | 'int56' | 'int64' |
	'int72' | 'int80' | 'int88' | 'int96' | 'int104' | 'int112' | 'int120' | 'int128' |
	'int136' | 'int144' | 'int152' | 'int160' | 'int168' | 'int176' | 'int184' | 'int192' |
	'int200' | 'int208' | 'int216' | 'int224' | 'int232' | 'int240' | 'int248' | 'int256';
STRING: 'string';
UFIXED: 'ufixed' | ('ufixed' [1-9][0-9]+ 'x' [1-9][0-9]+);

// Sized unsigned integer types. "uint" is an alias of uint256.
UNSIGNED_INTEGER_TYPE:
	'uint' | 'uint8' | 'uint16' | 'uint24' | 'uint32' | 'uint40' | 'uint48' | 'uint56' | 'uint64' |
	'uint72' | 'uint80' | 'uint88' | 'uint96' | 'uint104' | 'uint112' | 'uint120' | 'uint128' |
	'uint136' | 'uint144' | 'uint152' | 'uint160' | 'uint168' | 'uint176' | 'uint184' | 'uint192' |
	'uint200' | 'uint208' | 'uint216' | 'uint224' | 'uint232' | 'uint240' | 'uint248' | 'uint256';

HEXCODE: '0x' [0-9a-fA-F]+;


//// OP Codes
// Stack Management
PUSH1 : 'push1';
PUSH2 : 'push2';
PUSH3 : 'push3';
PUSH4 : 'push4';
PUSH5 : 'push5';
PUSH6 : 'push6';
PUSH7 : 'push7';
PUSH8 : 'push8';
PUSH9 : 'push9';
PUSH10 : 'push10';
PUSH11 : 'push11';
PUSH12 : 'push12';
PUSH13 : 'push13';
PUSH14 : 'push14';
PUSH15 : 'push15';
PUSH16 : 'push16';
PUSH17 : 'push17';
PUSH18 : 'push18';
PUSH19 : 'push19';
PUSH20 : 'push20';
PUSH21 : 'push21';
PUSH22 : 'push22';
PUSH23 : 'push23';
PUSH24 : 'push24';
PUSH25 : 'push25';
PUSH26 : 'push26';
PUSH27 : 'push27';
PUSH28 : 'push28';
PUSH29 : 'push29';
PUSH30 : 'push30';
PUSH31 : 'push31';
PUSH32 : 'push32';

POP : 'pop';
DUP1 : 'dup1';
DUP2 : 'dup2';
DUP3 : 'dup3';
DUP4 : 'dup4';
DUP5 : 'dup5';
DUP6 : 'dup6';
DUP7 : 'dup7';
DUP8 : 'dup8';
DUP9 : 'dup9';
DUP10 : 'dup10';
DUP11 : 'dup11';
DUP12 : 'dup12';
DUP13 : 'dup13';
DUP14 : 'dup14';
DUP15 : 'dup15';
DUP16 : 'dup16';

SWAP1 : 'swap1';
SWAP2 : 'swap2';
SWAP3 : 'swap3';
SWAP4 : 'swap4';
SWAP5 : 'swap5';
SWAP6 : 'swap6';
SWAP7 : 'swap7';
SWAP8 : 'swap8';
SWAP9 : 'swap9';
SWAP10 : 'swap10';
SWAP11 : 'swap11';
SWAP12 : 'swap12';
SWAP13 : 'swap13';
SWAP14 : 'swap14';
SWAP15 : 'swap15';
SWAP16 : 'swap16';

// Arithmetic and Logic
ADD : 'add';
SUB : 'sub';
MUL : 'mul';
DIV : 'div';
SDIV : 'sdiv';
MOD : 'mod';
SMOD : 'smod';
ADDMOD : 'addmod';
MULMOD : 'mulmod';
EXP : 'exp';
SIGNEXTEND : 'signextend';
SHR : 'shr';
SHL : 'shl';
SAR : 'sar';

LT : 'lt';
GT : 'gt';
SLT : 'slt';
SGT : 'sgt';
EQ : 'eq';
ISZERO : 'iszero';
AND : 'and';
OR : 'or';
XOR : 'xor';
NOT : 'not';
BYTE : 'byte';

// Environmental Information
//ADDRESS : 'address';
BALANCE : 'balance';
ORIGIN : 'origin';
CALLER : 'caller';
CALLVALUE : 'callvalue';
CALLDATALOAD : 'calldataload';
CALLDATASIZE : 'calldatasize';
CALLDATACOPY : 'calldatacopy';
CODESIZE : 'codesize';
CODECOPY : 'codecopy';
GASPRICE : 'gasprice';
EXTCODESIZE : 'extcodesize';
EXTCODECOPY : 'extcodecopy';
RETURNDATASIZE : 'returndatasize';
RETURNDATACOPY : 'returndatacopy';
EXTCODEHASH : 'extcodehash';
BLOCKHASH : 'blockhash';
COINBASE : 'coinbase';
TIMESTAMP : 'timestamp';
NUMBER : 'number';
DIFFICULTY : 'difficulty';
GASLIMIT : 'gaslimit';

// Storage and Memory Access
SLOAD : 'sload';
SSTORE : 'sstore';
MLOAD : 'mload';
MSTORE : 'mstore';
MSTORE8 : 'mstore8';
MSIZE : 'msize';

// Flow Control
JUMP : 'jump';
JUMPI : 'jumpi';
PC : 'pc';
JUMPDEST : 'jumpdest';

// System and Call-Related Opcodes
CALL : 'call';
CALLCODE : 'callcode';
DELEGATECALL : 'delegatecall';
STATICCALL : 'staticcall';
CREATE : 'create';
CREATE2 : 'create2';
SELFDESTRUCT : 'selfdestruct';

// Logging
LOG0 : 'log0';
LOG1 : 'log1';
LOG2 : 'log2';
LOG3 : 'log3';
LOG4 : 'log4';

// Miscellaneous
STOP : 'stop';
RETURN : 'return';
//REVERT : 'revert';
INVALID : 'invalid';

// Gas and Cost Management
GAS : 'gas';

/**
 * An identifier in solidity has to start with a letter, a dollar-sign or an underscore and
 * may additionally contain numbers after the first symbol.
 */
IDENTIFIER: IDENTIFIER_START IDENTIFIER_PART*;
//@doc:inline
fragment IDENTIFIER_START: [a-zA-Z$_];
//@doc:inline
fragment IDENTIFIER_PART: [a-zA-Z0-9$_];

//NEWLINE: ('\r' '\n' | '\n' | '\r') -> channel(HIDDEN);
WHITESPACE: [ \t\r\n\u000C]+ -> channel(HIDDEN);
DECIMAL: [0-9]+;

// Intellij required that even on a error the parser must return
ERRCHAR: . -> channel(HIDDEN);

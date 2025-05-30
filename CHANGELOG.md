<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# IntelliJ Huff Plugin Changelog

## [Unreleased]

## [1.0.5] - 2025-05-30
- Fixes CI issues for the plugin release.

## [1.0.4] - 2025-05-30
- Use Java 21 as minimum required JDK version.

## [1.0.3] - 2025-05-30
- Update dependencies to the latest version.
- Use `2025.01` as minimum required IDE version.

## [1.0.2] - 2025-03-19

- Add RustRover and WebStorm to the compatible IDEs.
- Do not highlight tests as unused.

## [1.0.1] - 2025-02-01

- Do not highlight `value` as error if used as identifier.
- Highlight new `__LEFTPAD` as built-in functions.
- Allow to use a constant reference as a parameter for a built-in function.
- Allow to use built-in functions in constant assignment.
  - Example: `#define constant FUNC_TEST = __FUNC_SIG("test(uint256)")`

## [1.0.0] - 2025-02-01

- Mark this as the first stable release of the IntelliJ Huff Plugin.
- Highlight `chainid`
- Allow to use constants in code tables.

## [0.0.20] - 2025-01-29

- Allow to use built-in function calls in a code table.
- Restrict usage of only labels in `jumptable` and `jumptable__packed`.

## [0.0.19] - 2025-01-29

- Add support for built-in `__BYTES("hello")`.
  - This can also be used here: `__RIGHTPAD(__BYTES("hello"))`.
- Refactor the parsing and fix spelling errors.

## [0.0.18] - 2025-01-29

- Allow to use `__FUNC_SIG` as a parameter for `__RIGHTPAD`.
  - Example: `__RIGHTPAD(__FUNC_SIG('log(bytes)'))`.
  - Remark: This change permits invalid combinations. When `huff-neo` evolves, this will be fixed.

## [0.0.17] - 2025-01-26

- Fix parsing for `value` in test decorators
- Add syntax highlighting for test decorator flags `calldata` and `value`

## [0.0.16] - 2024-11-27

- Fix highlighting for `push0`

## [0.0.15] - 2024-11-15

- Resolve references to tables

## [0.0.14] - 2024-11-15

- Resolve references to error abis

## [0.0.13] - 2024-11-15

- Resolve references to abi functions and error
- Highlight unused function abis and macros
- Highlight undefined function abi and macro calls

## [0.0.12] - 2024-11-14

- Fix constant references resolution

## [0.0.11] - 2024-11-14

- Display instructions documentation on hover

## [0.0.10] - 2024-11-14

- Fix all inspection name conflicts by prefixing with `Huff`

## [0.0.9] - 2024-11-14

- Fix plugin name conflict with Soldity plugin for `ResolveName`

## [0.0.8] - 2024-11-14

- Add code folding for labels
- Structure view for macros
- Show error for duplicate labels
- Follow references for constants, macros and labels
  - Currently, multiple definitions of the same name are not interpreted correctly
- Show unused elements

## [0.0.7] - 2024-11-11

- Fix usage of internal IntelliJ API

## [0.0.6] - 2024-11-11

- Create new file action
- Fix macro call parameter as macro call
- Fix jump table parsing
- Fix NatSpec parsing
- Add `jumptable__packed` parsing
- Parse macro statement syntax sugar `false`/`true`
- Follow include references

## [0.0.5] - 2024-11-11

- Fix parsing of macro calls
- Handling error definitions
- Allow to define macros using `fn`

## [0.0.4] - 2024-11-11

- Switch lexer and parser to JetBrains Grammar-Kit
- Limit autocomplete for opcode names to macro body
- Add highlighting for labels and references
- Add missing opcode names
- Fix parsing of macro calls with multiple parameters

## [0.0.3] - 2024-11-09

- Fix folding for block comments
- Highlight opcode names
- Opcode names completion

## [0.0.2] - 2024-11-08

- Add plugin icon
- Adapt description

## [0.0.1] - 2024-11-08

- Initial Huff Grammar + Highlighting

[Unreleased]: https://github.com/cakevm/intellij-huff-plugin/compare/v1.0.2...HEAD
[1.0.2]: https://github.com/cakevm/intellij-huff-plugin/compare/v1.0.1...v1.0.2
[1.0.1]: https://github.com/cakevm/intellij-huff-plugin/compare/v1.0.0...v1.0.1
[1.0.0]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.20...v1.0.0
[0.0.20]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.19...v0.0.20
[0.0.19]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.18...v0.0.19
[0.0.18]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.17...v0.0.18
[0.0.17]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.16...v0.0.17
[0.0.16]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.15...v0.0.16
[0.0.15]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.14...v0.0.15
[0.0.14]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.13...v0.0.14
[0.0.13]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.12...v0.0.13
[0.0.12]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.11...v0.0.12
[0.0.11]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.10...v0.0.11
[0.0.10]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.9...v0.0.10
[0.0.9]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.8...v0.0.9
[0.0.8]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.7...v0.0.8
[0.0.7]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.6...v0.0.7
[0.0.6]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.5...v0.0.6
[0.0.5]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.4...v0.0.5
[0.0.4]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.3...v0.0.4
[0.0.3]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.2...v0.0.3
[0.0.2]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.1...v0.0.2
[0.0.1]: https://github.com/cakevm/intellij-huff-plugin/commits/v0.0.1

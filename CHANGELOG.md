<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# IntelliJ Huff Plugin Changelog

## [Unreleased]

## [0.0.10] - 2024-11-14

- Fix all inspection name conflicts by prefixing with `Huff`

## [0.0.9] - 2024-11-12

- Fix all inspection name conflicts by prefixing with `Huff`
- Fix plugin name conflict with Soldity plugin for `ResolveName`

## [0.0.8] - 2024-11-12

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

[Unreleased]: https://github.com/cakevm/intellij-huff-plugin/compare/v0.0.10...HEAD
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

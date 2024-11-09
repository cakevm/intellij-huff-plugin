<div align="center">

[![Build](https://github.com/cakevm/intellij-huff-plugin/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/cakevm/intellij-huff-plugin/actions/workflows/build.yml)
[![IntelliJ Huff plugins](https://img.shields.io/jetbrains/plugin/v/25782-huff-language.svg)](https://plugins.jetbrains.com/plugin/25782-huff-language)

</div>

# IntelliJ Huff Plugin
<!-- Plugin description -->
This plugin adds [Huff Language](https://huff.sh) support to IntelliJ IDEs. It provides currently only syntax highlighting.
<!-- Plugin description end -->

![IDE example](./.github/ide_example.png)

## Installation
The plugin is available in the [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/25782-huff-language). You can install it directly from the IDE by searching for "Huff Language". As alternative, you can download the latest release from the [releases page](https://github.com/cakevm/intellij-huff-plugin/releases) and install it manually.

## Status of development
Lexing and parsing of Huff files and basic syntax highlighting is working.

## Acknowledgements
This plugin is based on the [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template) and the base for the plugin code is from [intellij-gleam](https://github.com/kvakvs/intellij-gleam). The icon is taken from [vscode-huff](https://github.com/huff-language/vscode-huff/blob/master/resources/huff.png). Thanks Soldity for the [Antlr lexer and parser](https://github.com/ethereum/solidity/blob/develop/docs/grammar/SolidityLexer.g4) definition. The definition for functions and events is taken from there.

## Licence
This project is dual licensed under the [Apache 2.0](./LICENSE-APACHE) or [MIT](./LICENSE-MIT) licenses.

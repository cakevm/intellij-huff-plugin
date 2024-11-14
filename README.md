<div align="center">

[![Build](https://github.com/cakevm/intellij-huff-plugin/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/cakevm/intellij-huff-plugin/actions/workflows/build.yml)
[![Version](https://img.shields.io/jetbrains/plugin/v/25782-huff-language.svg)](https://plugins.jetbrains.com/plugin/25782-huff-language)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/25782-huff-language.svg)](https://plugins.jetbrains.com/plugin/25782-huff-language)
[![Telegram Chat](https://img.shields.io/badge/telegram-Intellij_Huff_Plugin-2CA5E0?style=plastic&logo=telegram)](https://t.me/intellij_huff_plugin)

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
The Lexer, Parser and some code for language and ide classes is from or based on [intellij-solidity](https://github.com/intellij-solidity/intellij-solidity). The include reference is from [candid-intellij-plugin](https://github.com/Alaanor/candid-intellij-plugin). Thank you! The foundation for this plugin is the [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template). The icon is taken from [vscode-huff](https://github.com/huff-language/vscode-huff/blob/master/resources/huff.png). hanks Soldity for the [Antlr lexer and parser](https://github.com/ethereum/solidity/blob/develop/docs/grammar/SolidityLexer.g4) definition that helped alot in the beginning.

## Licence
This project is dual licensed under the [Apache 2.0](./LICENSE-APACHE) or [MIT](./LICENSE-MIT) licenses.

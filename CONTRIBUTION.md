# Contribute to IntelliJ Huff Plugin
All contributions are welcome. Please follow the guidelines below.

## Setup Development Environment
Clone the repository and open it in IntelliJ IDEA. 

### Generate Lexer and Parser
When changing the grammar it is better run `generateLexer` and `generateParser` tasks without caching to avoid any issues. Run the following command:
```sh
gradle --no-configuration-cache --no-build-cache generateLexer generateParser
```
Somtimes is also helps to delete the `gen` folder and run the command again.

## Before Commit
Check the code for errors and format it. Verify that you can run the plugin.

### Format Code
For formatting the code, run the following command:
```sh
gradle spotlessApply
```

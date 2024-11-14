#! /usr/bin/env python3
# -*- coding: utf-8 -*-
# Author : <github.com/tintinweb>
import evmdasm

template = {
    "prefix": "pragma",
    "body": "{{ $1 }}",
    "description": "Info:\n$1 tes {{ tes test}} test  *test* \"test\"",
    "example": "{% pragma ${name} %}",
    "security": "do not use experimental features!"
}

def main():
    hover_asm = {}

    for instr in evmdasm.registry.INSTRUCTIONS:
        name = instr.name.lower()
        if name.startswith("unofficial_"):
            continue
        hover_asm[name] = {
            "name":name,
            "description":instr.description,
            "instrOpcode":instr.opcode,
            "instrGas": instr.gas,
            "instrArgs": ["%s"%(a) for a in instr.args],
            "instrReturns": ["%s"%(a) for a in instr.returns],
            "instrCategory": instr.category,
        }
    import json
    print(json.dumps(hover_asm, sort_keys=True, indent=4, separators=(',', ': ')))

if __name__ == "__main__":
    main()

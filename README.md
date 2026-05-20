# P-Code VM

Educational P-Code Virtual Machine and Debugger plugin for IntelliJ IDEA.
P-Code VM helps students learn compiler construction, intermediate code generation, stack machines, recursion, frames, and runtime execution through an interactive IntelliJ plugin.

## Features

- P-Code syntax highlighting
- Step-by-step VM execution
- Register stack visualization
- Symbol table inspection
- Call stack tracking
- Runtime frames
- Method calls and recursion
- Return value support
- P-Code editor integration for IntelliJ IDEA

## Example

### Recursive Factorial

```text
factorial-int,method,global,1
n,int,factorial-int,0
#P,label,global,15
#E1,label,global,8
@
STO n,0
LOD n,0
LIT 1,0
OPR 15,0
JMC #E1,false
LIT 1,0
OPR 1,0
LOD n,0
LOD n,0
LIT 1,0
OPR 3,0
CAL factorial-int,0
OPR 4,0
OPR 1,0
LIT 5,0
CAL factorial-int,0
OPR 21,0
OPR 0,0
```

Output:

```text
120
```

## Included Components

### Virtual Machine

The VM supports:

- `LIT`
- `LOD`
- `STO`
- `JMP`
- `JMC`
- `CAL`
- `OPR`

including arithmetic, logical operations, comparisons, method calls, recursion, and returns.


## Runtime Model

The VM uses:

- Operand stack (`register_zero`)
- Runtime frames (`Stack<Frame>`)
- Return address stack (`Stack<Integer> returnAddresses`)

This architecture allows recursive execution and nested method calls.


## Built With

- Java
- IntelliJ Platform SDK
- Gradle


## Plugin Actions

- **Execute P-Code**
- **Step P-Code**

Accessible directly from the IntelliJ editor context menu.


## Educational Goals

This project was designed as an educational tool for compiler and virtual machine courses, demonstrating:

- intermediate code execution
- stack-based computation
- runtime environments
- activation records
- recursion
- compiler-generated P-Code


## Status

Version 1.0 Educational Release.

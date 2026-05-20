## Runtime Model

P-Code VM uses a classical stack-machine runtime architecture inspired by educational compiler virtual machines.

The runtime system is designed to support:

- arithmetic execution
- method calls
- recursion
- nested scopes
- conditional execution
- stack-based computation

The runtime components are as follows

## Operand Stack

```text
Stack<Symbol> register_zero
```

The operand stack is the core computation structure of the VM.

It stores:
- literals
- temporary values
- arithmetic results
- boolean results
- method return values

Most instructions interact directly with this stack.


## Runtime Frames

```text
Stack<Frame> frames
```

Each method call creates a runtime frame.

A frame represents one active method invocation.

Frames contain:
- local variables
- parameters
- method scope information

This allows:
- recursion
- nested method calls
- local variable isolation


## Return Address Stack

```text
Stack<Integer> returnAddresses
```

Stores instruction addresses for method returns.

When a method finishes:

```text
OPR 1,0
```

the VM:
1. removes the current frame
2. restores the return address
3. resumes execution after the original CAL instruction


## Program Counter

```text
int pc
```

The program counter tracks the current instruction being executed.

The VM updates the PC:
- sequentially
- during jumps
- during conditional jumps
- during method calls
- during method returns


## Symbol Table

```text
Hashtable<String, Vector<Symbol>>
```

The symbol table stores:
- global variables
- labels
- methods
- parameters
- local variables


## Method Calls

Method calls are implemented using:

```text
CAL methodName,0
```

The VM:
- saves the return address
- creates a frame
- transfers execution

Return values remain on the operand stack.


## Method Returns

Returns are implemented using:

```text
OPR 1,0
```

The VM:
- destroys the current frame
- restores the previous execution address
- continues execution

The return value remains on the operand stack for the caller.


## Conditional Execution

Conditional execution uses:

```text
JMC label,false
```

The VM:
1. pops a boolean value from the stack
2. compares it against the condition
3. jumps if the condition matches


## Execution Cycle

The VM execution loop performs:

1. fetch instruction
2. decode instruction
3. execute instruction
4. update runtime state
5. advance program counter

This process repeats until:

```text
OPR 0,0
```

terminates execution.


## Educational Purpose

The runtime model demonstrates core concepts used in compiler and virtual machine courses:

- stack machines
- activation records
- recursion
- runtime environments
- instruction execution
- symbol resolution
- intermediate code interpretation

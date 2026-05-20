# P-Code Instruction Set

P-Code VM executes a stack-based intermediate language commonly used in compiler construction courses.


# Core Instructions

## LIT

Pushes a literal value onto the operand stack.

### Syntax

```text
LIT value,0
```

### Example

```text
LIT 5,0
```

### Stack Behavior

```text
Before: []
After:  [5]
```


## LOD

Loads a variable value onto the operand stack.

### Syntax

```text
LOD variable,0
```

### Example

```text
LOD x,0
```

### Stack Behavior

```text
Before: []
After:  [value_of_x]
```


## STO

Stores the top stack value into a variable.

### Syntax

```text
STO variable,0
```

### Example

```text
STO x,0
```

### Description

Removes the top value from the operand stack and assigns it to the variable.



## JMP

Performs an unconditional jump.

### Syntax

```text
JMP label,0
```

### Example

```text
JMP #LOOP,0
```

### Description

Changes the program counter to the specified label.



## JMC

Conditional jump.

### Syntax

```text
JMC label,condition
```

### Example

```text
JMC #END,false
```

### Description

Pops the top boolean value from the stack.
If the value matches the condition parameter, execution jumps to the label.


## CAL

Calls a method.

### Syntax

```text
CAL methodName,0
```

### Example

```text
CAL factorial-int,0
```

### Description

The VM:
- stores the return address
- creates a runtime frame
- loads local variables
- jumps to the method body


## OPR

Executes arithmetic, logical, comparison, return, and output operations.

### Syntax

```text
OPR operationCode,0
```


# Important Operators

| Operator | Description |
|---|---|
| OPR 0 | Program end |
| OPR 1 | Return from method |
| OPR 2 | Addition |
| OPR 3 | Subtraction |
| OPR 4 | Multiplication |
| OPR 5 | Division |
| OPR 8 | OR |
| OPR 9 | AND |
| OPR 10 | NOT |
| OPR 11 | Greater than |
| OPR 12 | Less than |
| OPR 14 | Not equal |
| OPR 15 | Equal |
| OPR 20 | Print |
| OPR 21 | Print line |

---

# Example Arithmetic Execution

```text
LIT 5,0
LIT 3,0
OPR 2,0
```

Execution:

```text
Push 5
Push 3
Add
```

Result:

```text
8
```

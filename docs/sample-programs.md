## Sample Programs

This file contains sample P-Code programs that can be used to test the P-Code VM plugin.


## 1. Simple Arithmetic

Computes:

```text
5 + 3
```

Expected output:

```text
8
```

```text
@
LIT 5,0
LIT 3,0
OPR 2,0
OPR 21,0
OPR 0,0
```


## 2. Boolean Comparison

Computes:

```text
10 > 5
```

Expected output:

```text
true
```

```text
@
LIT 10,0
LIT 5,0
OPR 11,0
OPR 21,0
OPR 0,0
```


## 3. Conditional Jump

This program checks whether `5 == 5`.

Expected output:

```text
100
```

```text
#P,label,global,1
#E1,label,global,7
@
LIT 5,0
LIT 5,0
OPR 15,0
JMC #E1,false
LIT 100,0
OPR 21,0
OPR 0,0
LIT 0,0
OPR 21,0
OPR 0,0
```


## 4. Sum Method

Computes:

```text
sum(3, 4)
```

Expected output:

```text
7
```

```text
sum-int-int,method,global,1
a,int,sum-int-int,0
b,int,sum-int-int,0
#P,label,global,7
@
STO b,0
STO a,0
LOD a,0
LOD b,0
OPR 2,0
OPR 1,0
LIT 3,0
LIT 4,0
CAL sum-int-int,0
OPR 21,0
OPR 0,0
```


## 5. Nested Method Calls

Computes:

```text
square(sum(3, 4))
```

Expected output:

```text
49
```

```text
square-int,method,global,1
n,int,square-int,0
sum-int-int,method,global,6
a,int,sum-int-int,0
b,int,sum-int-int,0
#P,label,global,12
@
STO n,0
LOD n,0
LOD n,0
OPR 4,0
OPR 1,0
STO b,0
STO a,0
LOD a,0
LOD b,0
OPR 2,0
OPR 1,0
LIT 3,0
LIT 4,0
CAL sum-int-int,0
CAL square-int,0
OPR 21,0
OPR 0,0
```


## 6. Method Calls Inside Expressions

Computes:

```text
square(3) + sum(4, 5)
```

Expected output:

```text
18
```

```text
square-int,method,global,1
n,int,square-int,0
sum-int-int,method,global,6
a,int,sum-int-int,0
b,int,sum-int-int,0
#P,label,global,12
@
STO n,0
LOD n,0
LOD n,0
OPR 4,0
OPR 1,0
STO b,0
STO a,0
LOD a,0
LOD b,0
OPR 2,0
OPR 1,0
LIT 3,0
CAL square-int,0
LIT 4,0
LIT 5,0
CAL sum-int-int,0
OPR 2,0
OPR 21,0
OPR 0,0
```


## 7. Recursive Factorial

Computes:

```text
factorial(5)
```

Expected output:

```text
120
```

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


## 8. Recursive Factorial Stress Test

Computes:

```text
factorial(10)
```

Expected output:

```text
3628800
```

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
LIT 10,0
CAL factorial-int,0
OPR 21,0
OPR 0,0
```


## 9. Recursive Fibonacci

Computes:

```text
fib(8)
```

Expected output:

```text
21
```

```text
fib-int,method,global,1
n,int,fib-int,0
#P,label,global,24
#E1,label,global,8
#E2,label,global,14
@
STO n,0
LOD n,0
LIT 0,0
OPR 15,0
JMC #E1,false
LIT 0,0
OPR 1,0
LOD n,0
LIT 1,0
OPR 15,0
JMC #E2,false
LIT 1,0
OPR 1,0
LOD n,0
LIT 1,0
OPR 3,0
CAL fib-int,0
LOD n,0
LIT 2,0
OPR 3,0
CAL fib-int,0
OPR 2,0
OPR 1,0
LIT 8,0
CAL fib-int,0
OPR 21,0
OPR 0,0
```

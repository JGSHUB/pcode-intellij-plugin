# Sample Programs

## Recursive Factorial

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

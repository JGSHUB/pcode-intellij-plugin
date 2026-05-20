package javiergs.vm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterpreterTest {

  private String runProgram(String program) {

    StringBuilder output = new StringBuilder();

    Interpreter vm = new Interpreter();

    vm.setListener(new VmListener() {

      @Override
      public void onConsole(String s) {
      }

      @Override
      public void onOutput(String text) {
        output.append(text).append("\n");
      }

      @Override
      public void onInstructionExecuted(int i, Instruction instruction) {
      }

      @Override
      public void onStackChanged(java.util.List<Symbol> list) {
      }

      @Override
      public void onFinished() {
      }

      @Override
      public void onSymbolTableChanged(java.util.List<Symbol> symbols) {
      }

      @Override
      public void onCallStackChanged(java.util.List<Integer> callStack) {
      }

      @Override
      public void onFramesChanged(java.util.List<Frame> frames) {
      }
    });

    vm.loadProgram(program);
    vm.run();

    return output.toString().trim();
  }

  @Test
  void factorial10() {

    String program = """
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
        """;

    String output = runProgram(program);

    assertTrue(output.contains("3628800"));
  }

  @Test
  void fibonacci8() {

    String program = """
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
        """;

    String output = runProgram(program);

    assertTrue(output.contains("21"));
  }

  @Test
  void nestedMethodCalls() {

    String program = """
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
        """;

    String output = runProgram(program);

    assertTrue(output.contains("49"));
  }

  @Test
  void methodCallsInsideExpressions() {

    String program = """
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
        """;

    String output = runProgram(program);

    assertTrue(output.contains("18"));
  }
}
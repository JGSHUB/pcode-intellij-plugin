package javiergs.pcode;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import javiergs.vm.Instruction;
import javiergs.vm.Interpreter;
import javiergs.vm.Symbol;
import javiergs.vm.VmListener;
import com.intellij.openapi.editor.Editor;
import java.util.List;
import javiergs.vm.Frame;

public class PCodeSession {

  private static boolean running = false;
  private static Interpreter vm;
  private static boolean finished = false;
  private static String currentCode;

  private static int instructionStartLine = 0;

  public static int findInstructionStartLine(String code) {
    String[] lines = code.split("\\R");
    for (int i = 0; i < lines.length; i++) {
      if (lines[i].trim().equals("@")) {
        return i + 1;
      }
    }
    return 0;
  }

  public static void start(String code, Editor editor) {
    currentCode = code;
    currentEditor = editor;
    finished = false;
    instructionStartLine = findInstructionStartLine(code);

    //PCodeEditorHighlighter.clear(editor);
    PCodeConsoleWindow.clear();
    PCodeConsoleWindow.clearStack();
    PCodeConsoleWindow.clearSymbolTable();
    PCodeConsoleWindow.clearCallStack();
    PCodeConsoleWindow.clearFrames();

    vm = new Interpreter();

    vm.setListener(new VmListener() {
      @Override

      public void onCallStackChanged(List<Integer> callStack) {

        if (!running) {

          PCodeConsoleWindow.updateCallStack(callStack);

        }

      }
      @Override

      public void onFramesChanged(List<Frame> frames) {

        PCodeConsoleWindow.updateFrames(frames);

      }

      @Override

      public void onSymbolTableChanged(List<Symbol> symbols) {

        if (!running) {

          PCodeConsoleWindow.updateSymbolTable(symbols);

        }

      }

      @Override
      public void onConsole(String s) {
        PCodeConsoleWindow.println(s);
      }

      @Override
      public void onOutput(String text) {
        PCodeConsoleWindow.println(text);
      }

      @Override
      public void onInstructionExecuted(int pc, Instruction instruction) {
      }

      @Override

      public void onStackChanged(List<Symbol> stack) {

        if (!running) {

          PCodeConsoleWindow.updateStack(stack);

        }

      }

      @Override
      public void onFinished() {
        PCodeConsoleWindow.println("=== Program Ended ===");
        //PCodeEditorHighlighter.clear(editor);
      }
    });

    try {
      vm.loadProgram(code);
      editor.getSettings().setLineNumbersShown(false);
      PCodeInstructionNumberDecorator.setCurrentInstruction(
          editor,
          code,
          1
      );

      //PCodeEditorHighlighter.highlight(editor, instructionStartLine);
        } catch (Exception ex) {
      PCodeConsoleWindow.println("ERROR:");
      PCodeConsoleWindow.println(ex.getMessage());
    }
  }

  private static Editor currentEditor;



  public static void step(Project project) {
    if (finished) {
      PCodeConsoleWindow.println("=== Press Restart to run again. ===");
      return;
    }

    if (vm == null && !ensureStarted(project)) {
      return;
    }

    if (vm == null) {
      PCodeConsoleWindow.println("No program loaded.");
      return;
    }

    try {
      boolean vmFinished = vm.step();

      if (vmFinished) {
        PCodeInstructionNumberDecorator.clearCurrentInstruction(
            currentEditor,
            currentCode
        );

        PCodeConsoleWindow.println("=== Program Ended ===");
        finished = true;
        return;
      }

      PCodeInstructionNumberDecorator.setCurrentInstruction(
          currentEditor,
          currentCode,
          vm.getPc() + 1
      );

    } catch (Exception ex) {
      PCodeConsoleWindow.println("ERROR:");
      PCodeConsoleWindow.println(ex.getMessage());
    }
  }


  private static boolean ensureStarted(Project project) {
    if (vm != null) {
      return true;
    }
    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
    if (editor == null) {
      PCodeConsoleWindow.println("No editor is open.");
      return false;
    }
    String code = editor.getDocument().getText();
    start(code, editor);
    return true;
  }

  public static void runFromCurrentEditor(Project project) {
    FileEditorManager manager = FileEditorManager.getInstance(project);
    Editor editor = manager.getSelectedTextEditor();
    if (editor == null) {
      PCodeConsoleWindow.println("No editor is open.");
      return;
    }
    String code = editor.getDocument().getText();
    start(code, editor);
  }

  public static void stop() {

    if (currentEditor != null && currentCode != null) {

      PCodeInstructionNumberDecorator.clearCurrentInstruction(

          currentEditor,

          currentCode

      );

    }

    finished = true;

    PCodeConsoleWindow.println("=== Program Stopped ===");

  }

  public static void run(Project project) {
    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

    if (editor == null) {
      PCodeConsoleWindow.println("No editor is open.");
      return;
    }

    String code = editor.getDocument().getText();
    start(code, editor);

    new Thread(() -> {
      try {
        int maxSteps = 10_000;
        int steps = 0;

        running = true;
        while (!vm.isFinished()) {
          vm.step();
          steps++;

          if (steps > maxSteps) {
            throw new IllegalStateException("Possible infinite loop. pc=" + vm.getPc());
          }
        }
        running = false;

        finished = true;

        javax.swing.SwingUtilities.invokeLater(() -> {
          PCodeInstructionNumberDecorator.clearCurrentInstruction(currentEditor, currentCode);
          PCodeConsoleWindow.println("=== Program Ended ===");
        });

      } catch (Exception ex) {
        javax.swing.SwingUtilities.invokeLater(() -> {
          PCodeConsoleWindow.println("ERROR:");
          PCodeConsoleWindow.println(ex.getMessage());
        });
      }
    }).start();
  }

  public static void restart(Project project) {

    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

    if (editor == null) {

      PCodeConsoleWindow.println("No editor is open.");

      return;

    }

    String code = editor.getDocument().getText();

    start(code, editor);

    PCodeConsoleWindow.println("=== Program Restarted ===");

  }

  public static void decorateCurrentEditor(Project project) {

    Editor editor =

        FileEditorManager.getInstance(project)

            .getSelectedTextEditor();

    if (editor == null) {

      return;

    }

    String code = editor.getDocument().getText();

    editor.getSettings().setLineNumbersShown(false);

    PCodeInstructionNumberDecorator.decorate(

        editor,

        code

    );

  }

}
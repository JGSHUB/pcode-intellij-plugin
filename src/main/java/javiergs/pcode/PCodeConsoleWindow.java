package javiergs.pcode;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import javiergs.vm.Symbol;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import com.intellij.ui.table.JBTable;
import com.intellij.ui.components.JBScrollPane;
import javiergs.vm.Frame;


public class PCodeConsoleWindow {

  private static final DefaultListModel<String> callStackModel = new DefaultListModel<>();

  private static final DefaultListModel<String> frameModel = new DefaultListModel<>();

  private static JTextArea console;

  private static final DefaultListModel<String> stackModel =
      new DefaultListModel<>();

  private static final DefaultTableModel symbolModel =
      new DefaultTableModel(
          new String[]{"Name", "Type", "Scope", "Value"},
          0
      );

  public PCodeConsoleWindow(Project project, ToolWindow toolWindow) {

    console = new JTextArea();
    console.setEditable(false);

    JList<String> stackList = new JList<>(stackModel);
    JList<String> callStackList = new JList<>(callStackModel);
    JList<String> frameList = new JList<>(frameModel);

    JBTable symbolTable = new JBTable(symbolModel);

    symbolTable.setFillsViewportHeight(true);
    symbolTable.setRowHeight(28);
    symbolTable.setShowGrid(false);
    symbolTable.setIntercellSpacing(new Dimension(0, 0));
    symbolTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    symbolTable.getTableHeader().setReorderingAllowed(false);
    symbolTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JPanel panel = new JPanel(new BorderLayout());

    ActionToolbar toolbar = createToolbar(project, panel);

    panel.add(toolbar.getComponent(), BorderLayout.WEST);

    JPanel columns = new JPanel();

    columns.setLayout(new BoxLayout(columns, BoxLayout.X_AXIS));

    JPanel consolePane =
        wrap("Console", new JBScrollPane(console));

    JPanel stackPane =
        wrap("Stack", new JBScrollPane(stackList));

    JPanel callPane =
        wrap("Calls", new JBScrollPane(callStackList));

    JPanel symbolPane =
        wrap("Symbols", new JBScrollPane(symbolTable));

    JPanel framePane =
        wrap("Frames", new JBScrollPane(frameList));

    consolePane.setPreferredSize(new Dimension(320, 0));

    stackPane.setPreferredSize(new Dimension(120, 0));

    callPane.setPreferredSize(new Dimension(90, 0));

    symbolPane.setPreferredSize(new Dimension(340, 0));

    framePane.setPreferredSize(new Dimension(220, 0));

    columns.add(consolePane);

    columns.add(stackPane);

    columns.add(callPane);

    columns.add(symbolPane);

    columns.add(framePane);

    panel.add(columns, BorderLayout.CENTER);

    Content content = ContentFactory.getInstance()
        .createContent(panel, null, false);

    toolWindow.getContentManager().addContent(content);
  }
  public static void clearFrames() {

    SwingUtilities.invokeLater(frameModel::clear);

  }

  public static void updateFrames(List<Frame> frames) {

    SwingUtilities.invokeLater(() -> {

      frameModel.clear();

      for (Frame frame : frames) {

        frameModel.addElement(frame.getMethodName());

        for (Symbol symbol : frame.getLocals().values()) {

          frameModel.addElement(

              "  " + symbol.getName() + " = " + symbol.getValue()

          );

        }

      }

    });

  }

  private ActionToolbar createToolbar(Project project, JComponent target) {

    DefaultActionGroup group = new DefaultActionGroup();

    group.add(new AnAction("Run", "Run P-Code", AllIcons.Actions.Execute) {
      @Override
      public void actionPerformed(@NotNull AnActionEvent e) {
        PCodeSession.run(project);
      }
    });

    group.add(new AnAction("Step", "Step P-Code", AllIcons.Actions.TraceInto) {
      @Override
      public void actionPerformed(@NotNull AnActionEvent e) {
        PCodeSession.step(project);
      }
    });

    group.add(new AnAction("Restart", "Restart P-Code", AllIcons.Actions.Restart) {
      @Override
      public void actionPerformed(@NotNull AnActionEvent e) {
        PCodeSession.restart(project);
      }
    });

    group.add(new AnAction("Stop", "Stop P-Code", AllIcons.Actions.Suspend) {
      @Override
      public void actionPerformed(@NotNull AnActionEvent e) {
        PCodeSession.stop();
      }
    });

    ActionToolbar toolbar = ActionManager.getInstance()
        .createActionToolbar("PCodeToolbar", group, false);

    toolbar.setTargetComponent(target);

    return toolbar;
  }

  private JPanel wrap(String title, JComponent component) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel(title), BorderLayout.NORTH);
    panel.add(component, BorderLayout.CENTER);
    return panel;
  }

  public static void clear() {
    if (console != null) {
      console.setText("");
    }
  }

  public static void clearStack() {
    SwingUtilities.invokeLater(stackModel::clear);
  }

  public static void clearSymbolTable() {
    SwingUtilities.invokeLater(() -> symbolModel.setRowCount(0));
  }

  public static void print(String text) {
    if (console != null) {
      console.append(text);
    }
  }

  public static void println(String text) {
    print(text + "\n");
  }

  public static void updateStack(List<Symbol> stack) {
    SwingUtilities.invokeLater(() -> {
      stackModel.clear();

      for (Symbol s : stack) {
        stackModel.addElement(String.valueOf(s.getValue()));
      }
    });
  }

  public static void updateCallStack(List<Integer> callStack) {

    SwingUtilities.invokeLater(() -> {

      callStackModel.clear();

      for (Integer address : callStack) {
        callStackModel.addElement(String.valueOf(address));
      }
    });
  }

  public static void updateSymbolTable(List<Symbol> symbols) {
    SwingUtilities.invokeLater(() -> {
      symbolModel.setRowCount(0);

      for (Symbol s : symbols) {
        symbolModel.addRow(new Object[]{
            s.getName(),
            s.getType(),
            s.getScope(),
            s.getValue()
        });
      }
    });
  }

  public static void clearCallStack() {

    SwingUtilities.invokeLater(callStackModel::clear);

  }
}
package javiergs.pcode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

public class RunPCodeAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    Editor editor = e.getData(CommonDataKeys.EDITOR);

    if (project == null) {
      return;
    }

    if (editor == null) {
      Messages.showWarningDialog("No editor is open.", "Execute P-Code");
      return;
    }

    ToolWindow toolWindow = ToolWindowManager
        .getInstance(project)
        .getToolWindow("P-Code Console");

    if (toolWindow != null) {
      toolWindow.show();
    } else {
      Messages.showErrorDialog(
          "P-Code Console tool window was not found. Check plugin.xml registration.",
          "Execute P-Code"
      );
      return;
    }

    String code = editor.getDocument().getText();
    PCodeConsoleWindow.clear();
    PCodeConsoleWindow.println("=== P-Code Program Loaded ===");
    //PCodeSession.start(code);
    PCodeSession.start(code, editor);
    PCodeConsoleWindow.println("=== Program Loaded ===");
    PCodeConsoleWindow.println("Use Step P-Code to execute.");

  }
}
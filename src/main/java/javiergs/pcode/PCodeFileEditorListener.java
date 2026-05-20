package javiergs.pcode;

import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;

public class PCodeFileEditorListener implements FileEditorManagerListener {

  @Override
  public void selectionChanged(FileEditorManagerEvent event) {

    Project project = event.getManager().getProject();

    Editor editor =
        FileEditorManager.getInstance(project)
            .getSelectedTextEditor();

    if (editor == null) {
      return;
    }

    String text = editor.getDocument().getText();

    editor.getSettings().setLineNumbersShown(false);
    PCodeInstructionNumberDecorator.decorate(editor, text);

    if (!text.contains("@")) {

      return;

    }

    PCodeInstructionNumberDecorator.setCurrentInstruction(

        editor,

        text,

        1

    );
  }
}

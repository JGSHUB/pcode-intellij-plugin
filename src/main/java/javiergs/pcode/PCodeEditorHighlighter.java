package javiergs.pcode;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.RangeHighlighter;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;

import javax.swing.*;

import java.awt.*;
public class PCodeEditorHighlighter {
  private static RangeHighlighter current;

  public static void highlight(Editor editor, int line) {
    ApplicationManager.getApplication().invokeLater(() -> {
      if (current != null) {
        editor.getMarkupModel().removeHighlighter(current);
      }

      current = editor.getMarkupModel().addLineHighlighter(
          line,
          HighlighterLayer.FIRST,
          null
      );

      current.setGutterIconRenderer(new GutterIconRenderer() {
        @Override
        public Icon getIcon() {
          return AllIcons.Actions.Forward;       }

        @Override
        public String getTooltipText() {
          return "Next P-Code instruction";
        }

        @Override
        public boolean equals(Object obj) {
          return this == obj;
        }

        @Override
        public int hashCode() {
          return System.identityHashCode(this);
        }
      });

      editor.getScrollingModel().scrollToCaret(ScrollType.CENTER);
    });
  }

  public static void clear(Editor editor) {
    if (current != null) {
      editor.getMarkupModel().removeHighlighter(current);
      current = null;
    }
  }
}
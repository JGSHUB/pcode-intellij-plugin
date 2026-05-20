package javiergs.pcode;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.RangeHighlighter;

import javax.swing.*;
import java.awt.Color;

import java.awt.Component;

import java.awt.Graphics;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.Objects;

public class PCodeInstructionNumberDecorator {

  private static final Map<Editor, List<RangeHighlighter>> highlightersByEditor =
      new HashMap<>();

  private static int currentInstructionNumber = -1;

  public static void decorate(Editor editor, String code) {
    if (editor == null || code == null) return;

    clear(editor);

    String[] lines = code.split("\\R", -1);

    boolean inCode = false;
    int instruction = 1;

    List<RangeHighlighter> highlighters = new ArrayList<>();

    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();

      if (line.equals("@")) {
        inCode = true;
        continue;
      }

      if (!inCode || line.isEmpty()) {
        continue;
      }

      boolean current = instruction == currentInstructionNumber;

      RangeHighlighter highlighter =
          editor.getMarkupModel().addLineHighlighter(
              i,
              HighlighterLayer.ADDITIONAL_SYNTAX,
              null
          );

      highlighter.setGutterIconRenderer(
          new NumberRenderer(instruction, current)
      );

      highlighters.add(highlighter);
      instruction++;
    }

    highlightersByEditor.put(editor, highlighters);
  }

  public static void setCurrentInstruction(
      Editor editor,
      String code,
      int instructionNumber
  ) {
    currentInstructionNumber = instructionNumber;
    decorate(editor, code);
  }

  public static void clearCurrentInstruction(Editor editor, String code) {
    currentInstructionNumber = -1;
    decorate(editor, code);
  }

  public static void clear(Editor editor) {
    if (editor == null) return;

    List<RangeHighlighter> highlighters =
        highlightersByEditor.remove(editor);

    if (highlighters == null) return;

    for (RangeHighlighter h : highlighters) {
      if (h != null && h.isValid()) {
        editor.getMarkupModel().removeHighlighter(h);
      }
    }
  }

  private static class NumberRenderer extends GutterIconRenderer {

    private final int number;
    private final boolean current;

    NumberRenderer(int number, boolean current) {
      this.number = number;
      this.current = current;
    }

    @Override
    public Icon getIcon() {
      return new NumberIcon(String.valueOf(number), current);
    }

    @Override
    public String getTooltipText() {
      return "P-Code instruction " + number;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!(obj instanceof NumberRenderer)) return false;

      NumberRenderer other = (NumberRenderer) obj;
      return number == other.number && current == other.current;
    }

    @Override
    public int hashCode() {
      return Objects.hash(number, current);
    }
  }

  private static class NumberIcon implements Icon {

    private final String text;
    private final boolean current;

    NumberIcon(String text, boolean current) {
      this.text = text;
      this.current = current;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
      g.setColor(Color.GRAY);
      g.drawString(text, x + 2, y + 12);

      if (current) {
        g.setColor(new Color(120, 170, 255));
        g.drawString("→", x + 16, y + 12);
      }
    }

    @Override
    public int getIconWidth() {
      return 36;
    }

    @Override
    public int getIconHeight() {
      return 16;
    }
  }
}
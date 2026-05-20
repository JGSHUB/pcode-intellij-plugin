package javiergs.pcode;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PCodeInstructionInlayDecorator {
  private static final List<Inlay<?>> inlays = new ArrayList<>();

  public static void decorate(Editor editor, String code) {
    clear();
    String[] lines = code.split("\\R");
    boolean inCode = false;
    int number = 1;

    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();

      if (line.equals("@")) {
        inCode = true;
        continue;
      }

      if (!inCode || line.isEmpty()) {
        continue;
      }

      int offset = editor.getDocument().getLineStartOffset(i);
      Inlay<?> inlay = editor.getInlayModel().addInlineElement(
          offset,
          true,
          new InstructionNumberRenderer(number)
      );

      if (inlay != null) {
        inlays.add(inlay);
      }

      number++;
    }
  }

  public static void clear() {
    for (Inlay<?> inlay : inlays) {
      inlay.dispose();
    }
    inlays.clear();
  }

  private static class InstructionNumberRenderer implements EditorCustomElementRenderer {
    private final int number;

    InstructionNumberRenderer(int number) {
      this.number = number;
    }

    @Override
    public int calcWidthInPixels(@NotNull Inlay inlay) {
      return 36;
    }

    @Override
    public void paint(@NotNull Inlay inlay,
                      @NotNull Graphics g,
                      @NotNull Rectangle targetRegion,
                      @NotNull TextAttributes textAttributes) {
      g.setColor(Color.GRAY);
      g.drawString(number + " |", targetRegion.x, targetRegion.y + 14);
    }
  }
}
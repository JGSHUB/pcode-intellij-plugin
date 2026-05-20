package javiergs.pcode;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.editor.markup.TextAttributes;
import java.awt.*;

public class PCodeSyntaxHighlighter extends SyntaxHighlighterBase {

  public static final TextAttributesKey PARAM1 =
      TextAttributesKey.createTextAttributesKey(
          "PCODE_PARAM1",
          DefaultLanguageHighlighterColors.STRING
      );

  public static final TextAttributesKey PARAM2 =
      TextAttributesKey.createTextAttributesKey(
          "PCODE_PARAM2",
          DefaultLanguageHighlighterColors.NUMBER
      );

  public static final TextAttributesKey SYMBOL_DEF =
      TextAttributesKey.createTextAttributesKey(
          "PCODE_SYMBOL_DEF",
          DefaultLanguageHighlighterColors.INSTANCE_FIELD
      );

  public static final TextAttributesKey INSTRUCTION =
      TextAttributesKey.createTextAttributesKey(
          "PCODE_INSTRUCTION",
          DefaultLanguageHighlighterColors.KEYWORD
      );

  public static final TextAttributesKey NUMBER =
      TextAttributesKey.createTextAttributesKey(
          "PCODE_NUMBER",
          DefaultLanguageHighlighterColors.NUMBER
      );

  public static final TextAttributesKey DELIMITER =
      TextAttributesKey.createTextAttributesKey(
          "PCODE_DELIMITER",
          DefaultLanguageHighlighterColors.OPERATION_SIGN
      );

  public static final TextAttributesKey LABEL =
      TextAttributesKey.createTextAttributesKey(
          "PCODE_LABEL",
          DefaultLanguageHighlighterColors.INSTANCE_FIELD
      );

  @Override
  public @NotNull Lexer getHighlightingLexer() {
    return new PCodeLexer();
  }

  @Override
  public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
    if (tokenType == PCodeTokenTypes.INSTRUCTION) {
      return pack(INSTRUCTION);
    }

    if (tokenType == PCodeTokenTypes.PARAM1) {
      return pack(PARAM1);
    }

    if (tokenType == PCodeTokenTypes.PARAM2) {
      return pack(PARAM2);
    }

    if (tokenType == PCodeTokenTypes.SYMBOL_DEF) {
      return pack(SYMBOL_DEF);
    }

    if (tokenType == PCodeTokenTypes.NUMBER) {
      return pack(NUMBER);
    }

    if (tokenType == PCodeTokenTypes.COMMA || tokenType == PCodeTokenTypes.AT) {
      return pack(DELIMITER);
    }

    if (tokenType == PCodeTokenTypes.LABEL) {
      return pack(LABEL);
    }

    return TextAttributesKey.EMPTY_ARRAY;
  }
}
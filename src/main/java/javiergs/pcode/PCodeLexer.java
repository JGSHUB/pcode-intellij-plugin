package javiergs.pcode;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PCodeLexer extends LexerBase {

  private CharSequence buffer;
  private int startOffset;
  private int endOffset;
  private int position;
  private IElementType tokenType;

  private boolean inInstructions;
  private int tokenIndexOnLine;

  private boolean hasAtBefore(int offset) {

    for (int i = 0; i < offset && i < buffer.length(); i++) {

      if (buffer.charAt(i) == '@') {
        return true;
      }
    }

    return false;
  }

  @Override
  public void start(@NotNull CharSequence buffer,
                    int startOffset,
                    int endOffset,
                    int initialState) {
    this.buffer = buffer;
    this.startOffset = startOffset;
    this.endOffset = endOffset;
    this.position = startOffset;
    this.inInstructions = initialState == 1 || hasAtBefore(startOffset);
    this.tokenIndexOnLine = 0;

    advance();
  }

  @Override

  public int getState() {

    return inInstructions ? 1 : 0;

  }

  @Override
  public @Nullable IElementType getTokenType() {
    return tokenType;
  }

  @Override
  public int getTokenStart() {
    return startOffset;
  }

  @Override
  public int getTokenEnd() {
    return position;
  }

  @Override
  public void advance() {
    startOffset = position;

    if (position >= endOffset) {
      tokenType = null;
      return;
    }

    char c = buffer.charAt(position);

    if (Character.isWhitespace(c)) {
      boolean hasNewLine = false;

      while (position < endOffset &&
          Character.isWhitespace(buffer.charAt(position))) {
        if (buffer.charAt(position) == '\n' || buffer.charAt(position) == '\r') {
          hasNewLine = true;
        }
        position++;
      }

      if (hasNewLine) {
        tokenIndexOnLine = 0;
      }

      tokenType = TokenType.WHITE_SPACE;
      return;
    }

    if (c == '@') {
      position++;
      inInstructions = true;
      tokenIndexOnLine = 0;
      tokenType = PCodeTokenTypes.AT;
      return;
    }

    if (c == ',') {
      position++;
      tokenType = PCodeTokenTypes.COMMA;
      return;
    }

    int tokenStart = position;

    while (position < endOffset) {
      char ch = buffer.charAt(position);

      if (Character.isWhitespace(ch) || ch == ',') {
        break;
      }

      position++;
    }

    String word = buffer.subSequence(tokenStart, position).toString();

    if (!inInstructions) {
      tokenType = PCodeTokenTypes.SYMBOL_DEF;
      return;
    }

    if (tokenIndexOnLine == 0 && isInstruction(word)) {
      tokenIndexOnLine++;
      tokenType = PCodeTokenTypes.INSTRUCTION;
      return;
    }

    if (tokenIndexOnLine == 1) {
      tokenIndexOnLine++;
      tokenType = PCodeTokenTypes.PARAM1;
      return;
    }

    tokenType = PCodeTokenTypes.PARAM2;
  }

  private boolean isInstruction(String word) {
    return word.equalsIgnoreCase("LIT")
        || word.equalsIgnoreCase("LOD")
        || word.equalsIgnoreCase("STO")
        || word.equalsIgnoreCase("JMP")
        || word.equalsIgnoreCase("JMC")
        || word.equalsIgnoreCase("OPR")
        || word.equalsIgnoreCase("CAL");
  }

  @Override
  public @NotNull CharSequence getBufferSequence() {
    return buffer;
  }

  @Override
  public int getBufferEnd() {
    return endOffset;
  }
}
package javiergs.pcode;

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import org.jetbrains.annotations.NotNull;

public class PCodeSyntaxHighlighterFactory
    extends SingleLazyInstanceSyntaxHighlighterFactory {

  @Override
  protected @NotNull SyntaxHighlighter createHighlighter() {
    return new PCodeSyntaxHighlighter();
  }
}

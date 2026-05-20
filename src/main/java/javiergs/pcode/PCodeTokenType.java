package javiergs.pcode;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PCodeTokenType extends IElementType {

  public PCodeTokenType(@NotNull @NonNls String debugName) {
    super(debugName, PCodeLanguage.INSTANCE);
  }

  @Override
  public String toString() {
    return "PCodeTokenType." + super.toString();
  }
}
package javiergs.pcode;

import com.intellij.lang.Language;

public class PCodeLanguage extends Language {
  public static final PCodeLanguage INSTANCE = new PCodeLanguage();

  private PCodeLanguage() {
    super("PCode");
  }
}
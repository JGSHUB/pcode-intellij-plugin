package javiergs.pcode;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PCodeFileType extends LanguageFileType {

  public static final PCodeFileType INSTANCE = new PCodeFileType();

  private PCodeFileType() {
    super(PCodeLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "P-Code VM File";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "P-Code virtual machine file";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "pcode";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return null;
  }
}
package javiergs.pcode;

import com.intellij.psi.tree.IElementType;

public interface PCodeTokenTypes {

  IElementType INSTRUCTION =
      new PCodeTokenType("INSTRUCTION");

  IElementType PARAM1 =
      new PCodeTokenType("PARAM1");

  IElementType PARAM2 =
      new PCodeTokenType("PARAM2");

  IElementType SYMBOL_DEF =
      new PCodeTokenType("SYMBOL_DEF");

  IElementType NUMBER =
      new PCodeTokenType("NUMBER");

  IElementType LABEL =
      new PCodeTokenType("LABEL");

  IElementType COMMA =
      new PCodeTokenType("COMMA");

  IElementType AT =
      new PCodeTokenType("AT");

  IElementType IDENTIFIER =
      new PCodeTokenType("IDENTIFIER");

  IElementType BAD_CHARACTER =
      new PCodeTokenType("BAD_CHARACTER");
}
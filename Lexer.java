/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package LAB_COMP;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author guilh
 */
public class Lexer {
   private List<Token> tokens;
   private List<AFD> afds;
   private CharacterIterator code;

   public Lexer(String code) {
      tokens = new ArrayList<>();
      afds = new ArrayList<>();
      this.code = new StringCharacterIterator(code);
      afds.add(new SingleToken());
      afds.add(new Number());
      afds.add(new ID());
   }

   public void SkipWhiteSpace() {
      while (code.current() == ' ' || code.current() == '\n') {
         code.next();
      }
   }

   public List<Token> getTokens() {
      Token t;
      do {
         SkipWhiteSpace();
         t = searchNextToken();
         if (t == null)
            error();
         tokens.add(t);

      } while (!t.tipo.equals("EOF"));
      return tokens;
   }

   private Token searchNextToken() {
      int pos = code.getIndex();
      for (AFD afd : afds) {
         Token t = afd.evaluate(code);
         if (t != null)
            return t;
         code.setIndex(pos);
      }
      return null;
   }

   private void error() {
      throw new RuntimeException("Error : Token not recognized : " + code.current());
   }
}

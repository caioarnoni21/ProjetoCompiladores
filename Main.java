
package LAB_COMP;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author guilh
 */
public class Main {
    public static void main(String[] args) throws IOException {
        List<Token> tokens = null;
        String data = "comparator (x < 10) "; // -> aqui que coloca as informacoes
        Lexer lexer = new Lexer(data);
        tokens = lexer.getTokens();
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

}

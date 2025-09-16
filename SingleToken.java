/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LAB_COMP;

import java.text.CharacterIterator;

/**
 *
 * @author guilh
 */
public class SingleToken extends AFD {
    @Override
    public Token evaluate(CharacterIterator code) {
        switch (code.current()) {
            case '+':
                code.next();
                if (code.current() == '+') {
                    code.next();
                    return new Token("INC", "++");
                } else if (code.current() == '=') {
                    code.next();
                    return new Token("PLUS_EQ", "+=");
                }
                return new Token("PLUS", "+");

            case '-':
                code.next();
                if (code.current() == '-') {
                    code.next();
                    return new Token("DEC", "--");
                } else if (code.current() == '=') {
                    code.next();
                    return new Token("MINUS_EQ", "-=");
                } else if (code.current() == '>') {
                    code.next();
                    return new Token("ARROW", "->");
                }
                return new Token("MINUS", "-");

            case '*':
                code.next();
                return new Token("MUL", "*");

            case '/':
                code.next();
                return new Token("DIV", "/");

            case '=':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("EQ", "==");
                }
                return new Token("ATRIB", "=");

            case '!':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("NEQ", "!=");
                }
                return null; // só ! sozinho não está na gramática

            case '<':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("LEQ", "<=");
                } else if (code.current() == '-') {
                    code.next();
                    return new Token("HOPPER_ASSIGN", "<-");
                }
                return new Token("LT", "<");

            case '>':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("GEQ", ">=");
                }
                return new Token("GT", ">");

            case '(':
                code.next();
                return new Token("LPAREN", "(");

            case ')':
                code.next();
                return new Token("RPAREN", ")");

            case '{':
                code.next();
                return new Token("LBRACE", "{");

            case '}':
                code.next();
                return new Token("RBRACE", "}");

            case ';':
                code.next();
                return new Token("SEMICOLON", ";");

            case ',':
                code.next();
                return new Token("COMMA", ",");

            case CharacterIterator.DONE:
                return new Token("EOF", "$");

            default:
                return null;
        }
    }

}

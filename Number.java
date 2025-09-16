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
public class Number extends AFD {
    @Override
    public Token evaluate(CharacterIterator code) {
        if (!Character.isDigit(code.current()))
            return null;

        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(code.current())) {
            sb.append(code.current());
            code.next();
        }
        if (code.current() == '.') {
            sb.append('.');
            code.next();
            if (!Character.isDigit(code.current())) {
                throw new RuntimeException("Malformed decimal literal");
            }
            while (Character.isDigit(code.current())) {
                sb.append(code.current());
                code.next();
            }
        }
        return new Token("NUM", sb.toString()); // <- SEM checar separador
    }
}

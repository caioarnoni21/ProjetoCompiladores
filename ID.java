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
public class ID extends AFD {
    @Override
    public Token evaluate(CharacterIterator code) {
        if (!Character.isLetter(code.current()))
            return null;
        String id = readID(code);
        return new Token("ID", id); // <- SEM checar separador
    }

    private String readID(CharacterIterator code) {
        StringBuilder sb = new StringBuilder();
        while (Character.isLetterOrDigit(code.current()) || code.current() == '_') {
            sb.append(code.current());
            code.next();
        }
        return sb.toString();
    }

}

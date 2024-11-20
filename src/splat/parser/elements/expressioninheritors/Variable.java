package splat.parser.elements.expressioninheritors;

import splat.lexer.Token;
import splat.parser.elements.Expression;

import java.util.List;

public class Variable extends Expression {

    public Variable(Token tok, String name) {
        super(tok);
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}

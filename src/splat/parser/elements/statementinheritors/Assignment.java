package splat.parser.elements.statementinheritors;

import splat.lexer.Token;
import splat.parser.elements.Expression;
import splat.parser.elements.Statement;

public class Assignment extends Statement {

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Assignment(Token tok, String name, String value) {
        super(tok);
        this.name = name;
        this.value = value;
    }
}

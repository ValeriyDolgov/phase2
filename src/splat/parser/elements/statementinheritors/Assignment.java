package splat.parser.elements.statementinheritors;

import splat.lexer.Token;
import splat.parser.elements.ASTElement;
import splat.parser.elements.Expression;
import splat.parser.elements.Statement;

public class Assignment extends Statement {

    private String name;

    private ASTElement value;

    public String getName() {
        return name;
    }

    public ASTElement getValue() {
        return value;
    }

    public Assignment(Token tok, String name, ASTElement value) {
        super(tok);
        this.name = name;
        this.value = value;
    }
}

package splat.parser.elements.statementinheritors;

import splat.lexer.Token;
import splat.parser.elements.Expression;
import splat.parser.elements.Statement;

import java.util.List;

public class WhileLoop extends Statement {

    private String condition;

    private List<Statement> body;

    public WhileLoop(Token tok, String condition, List<Statement> body) {
        super(tok);
        this.condition = condition;
        this.body = body;
    }

    public List<Statement> getBody() {
        return body;
    }

    public String getCondition() {
        return condition;
    }
}

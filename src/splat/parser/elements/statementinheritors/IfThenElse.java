package splat.parser.elements.statementinheritors;

import splat.lexer.Token;
import splat.parser.elements.Statement;

import java.util.List;

public class IfThenElse extends Statement {

    private String ifStatement;
    private List<Statement> thenStatement;
    private List<Statement> elseStatement;

    public IfThenElse(Token tok, String ifStatement, List<Statement> thenStatement, List<Statement> elseStatement) {
        super(tok);
        this.ifStatement = ifStatement;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public String getIfStatement() {
        return ifStatement;
    }

    public List<Statement> getThenStatement() {
        return thenStatement;
    }

    public List<Statement> getElseStatement() {
        return elseStatement;
    }
}

package splat.parser.elements.expressioninheritors;

import splat.lexer.Token;
import splat.parser.elements.Expression;
import splat.parser.elements.statementinheritors.FunctionCall;

public class NonVoidFunctionCall extends Expression {

    private String functionName;

    private Variable variable;

    private FunctionCall functionCall;

    public NonVoidFunctionCall(Token tok) {
        super(tok);
    }
}

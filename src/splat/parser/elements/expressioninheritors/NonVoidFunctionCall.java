package splat.parser.elements.expressioninheritors;

import splat.lexer.Token;
import splat.parser.elements.Expression;

public class NonVoidFunctionCall extends Expression {
    public NonVoidFunctionCall(Token tok) {
        super(tok);
    }
}

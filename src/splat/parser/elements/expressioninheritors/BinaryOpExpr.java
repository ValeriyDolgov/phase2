package splat.parser.elements.expressioninheritors;

import splat.lexer.Token;
import splat.parser.elements.Expression;

public class BinaryOpExpr extends Expression {
    public BinaryOpExpr(Token tok) {
        super(tok);
    }
}

package splat.parser.elements.statementinheritors;

import splat.lexer.Token;
import splat.parser.elements.Statement;

public class PrintLine extends Statement {
    public PrintLine(Token tok) {
        super(tok);
    }
}

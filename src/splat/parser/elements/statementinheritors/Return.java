package splat.parser.elements.statementinheritors;

import splat.lexer.Token;
import splat.parser.elements.Statement;

public class Return extends Statement {

    private String returnedValue;

    public String getReturnedValue() {
        return returnedValue;
    }

    public Return(Token tok, String printedValue) {
        super(tok);
        this.returnedValue = printedValue;
    }
}

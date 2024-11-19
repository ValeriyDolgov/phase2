package splat.parser.elements.statementinheritors;

import splat.lexer.Token;
import splat.parser.elements.Statement;

public class Print extends Statement {

    private String printedValue;

    public String getPrintedValue() {
        return printedValue;
    }

    public Print(Token tok, String printedValue) {
        super(tok);
        this.printedValue = printedValue;
    }
}

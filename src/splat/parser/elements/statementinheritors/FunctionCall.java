package splat.parser.elements.statementinheritors;

import splat.lexer.Token;
import splat.parser.elements.Expression;
import splat.parser.elements.Statement;
import splat.parser.elements.expressioninheritors.Variable;

import java.util.List;

public class FunctionCall extends Statement {

    private String functionName;

    private List<Variable> arguments;

    public String getFunctionName() {
        return functionName;
    }

    public List<Variable> getArguments() {
        return arguments;
    }

    public FunctionCall(Token tok, String functionName, List<Variable> arguments) {
        super(tok);
        this.functionName = functionName;
        this.arguments = arguments;
    }
}

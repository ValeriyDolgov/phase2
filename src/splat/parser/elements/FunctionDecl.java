package splat.parser.elements;

import splat.lexer.Token;

import java.util.List;

public class FunctionDecl extends Declaration {

	private String funcName;
	private List<VariableDecl> params;
	private String returnType;

	
	// Need to add extra arguments for setting fields in the constructor 
	public FunctionDecl(Token tok) {
		super(tok);
	}

	// Getters?
	
	// Fix this as well
	public String toString() {
		return null;
	}
}

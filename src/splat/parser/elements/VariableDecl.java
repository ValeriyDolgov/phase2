package splat.parser.elements;

import splat.lexer.Token;

public class VariableDecl extends Declaration {

	private String lable;
	private String type; //Maybe use Object? private Object variable

	public VariableDecl(Token tok, String label, String type) {
		super(tok);
		this.lable = label;
		this.type = type;
	}

	public String getLable() {
		return lable;
	}

	public String getType() {
		return type;
	}

	// Fix this as well
	public String toString() {
		return "VariableDecl[lable='" + lable + "', type=" + type + ", line=" + super.getLine() + "]";
	}
}

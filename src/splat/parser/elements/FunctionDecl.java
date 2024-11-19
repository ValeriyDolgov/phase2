package splat.parser.elements;

import splat.lexer.Token;

import java.util.List;

public class FunctionDecl extends Declaration {

	private String funcName;
	private List<VariableDecl> params;
	private String returnType;
	private List<Statement> body;

	public FunctionDecl(Token tok, String funcName, List<VariableDecl> params, String returnType, List<Statement> body) {
		super(tok);
		this.funcName = funcName;
		this.params = params;
		this.returnType = returnType;
		this.body = body;
	}

	public List<VariableDecl> getParams() {
		return params;
	}

	public String getReturnType() {
		return returnType;
	}

	public List<Statement> getBody() {
		return body;
	}

	public String getFuncName() {
		return funcName;
	}

	@Override
	public String toString() {
		var paramsStr = new StringBuilder();
		if (params != null) {
			paramsStr.append("(");
			for (VariableDecl param : params) {
				paramsStr.append(param.toString());
				paramsStr.append(",");
			}
			paramsStr.append(")");
		}
		var bodyStr = new StringBuilder();
		if (body != null) {
			bodyStr.append("(");
			for (Statement statement : body) {
				bodyStr.append(statement.toString());
				bodyStr.append(",");
			}
			bodyStr.append(")");
		}
		return "FunctionDecl{" +
			   "funcName='" +
			   funcName +
			   '\'' +
			   ", params=" +
			   paramsStr +
			   ", returnType='" +
			   returnType +
			   '\'' +
			   ", body=" +
			   bodyStr +
			   '}';
	}
}

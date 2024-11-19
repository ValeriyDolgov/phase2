package splat.parser;

import java.util.ArrayList;
import java.util.List;

import splat.lexer.Token;
import splat.parser.elements.*;
import splat.parser.elements.expressioninheritors.NonVoidFunctionCall;
import splat.parser.elements.statementinheritors.*;

public class Parser {

	private List<Token> tokens;
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	/**
	 * Compares the next token to an expected value, and throws
	 * an exception if they don't match.  This removes the front-most
	 * (next) token  
	 * 
	 * @param expected value of the next token
	 * @throws ParseException if the actual token doesn't match what 
	 * 			was expected
	 */
	private void checkNext(String expected) throws ParseException {

		Token tok = tokens.remove(0);
		
		if (!tok.getValue().equals(expected)) {
			throw new ParseException("Expected '"+ expected + "', got '" 
					+ tok.getValue()+ "'.", tok);
		}
	}
	
	/**
	 * Returns a boolean indicating whether or not the next token matches
	 * the expected String value.  This does not remove the token from the
	 * token list.
	 * 
	 * @param expected value of the next token
	 * @return true iff the token value matches the expected string
	 */
	private boolean peekNext(String expected) {
		return tokens.get(0).getValue().equals(expected);
	}
	
	/**
	 * Returns a boolean indicating whether or not the token directly after
	 * the front most token matches the expected String value.  This does 
	 * not remove any tokens from the token list.
	 * 
	 * @param expected value of the token directly after the next token
	 * @return true iff the value matches the expected string
	 */
	private boolean peekTwoAhead(String expected) {
		return tokens.get(1).getValue().equals(expected);
	}
	
	
	/*
	 *  <program> ::= program <decls> begin <stmts> end ;
	 */
	public ProgramAST parse() throws ParseException {
		
		try {
			// Needed for 'program' token position info
			Token startTok = tokens.get(0);
			
			checkNext("program");
			
			List<Declaration> decls = parseDecls();
			
			checkNext("begin");
			
			List<Statement> stmts = parseStmts();
			
			checkNext("end");
			checkNext(";");
	
			return new ProgramAST(decls, stmts, startTok);
			
		// This might happen if we do a tokens.get(), and nothing is there!
		} catch (IndexOutOfBoundsException ex) {
			
			throw new ParseException("Unexpectedly reached the end of file.", -1, -1);
		}
	}
	
	/*
	 *  <decls> ::= (  <decl>  )*
	 */
	private List<Declaration> parseDecls() throws ParseException {
		
		List<Declaration> decls = new ArrayList<Declaration>();
		
		while (!peekNext("begin")) {
			Declaration decl = parseDecl();
			decls.add(decl);
		}
		
		return decls;
	}
	
	/*
	 * <decl> ::= <var-decl> | <func-decl>
	 */
	private Declaration parseDecl() throws ParseException {

		if (peekTwoAhead(":")) {
			return parseVarDecl();
		} else if (peekTwoAhead("(")) {
			return parseFuncDecl();
		} else {
			Token tok = tokens.get(0);
			throw new ParseException("Declaration expected", tok);
		}
	}
	
	/*
	 * <func-decl> ::= <label> ( <params> ) : <ret-type> is 
	 * 						<loc-var-decls> begin <stmts> end ;
	 */
	private FunctionDecl parseFuncDecl() throws ParseException {
		var startTok = tokens.get(0);
		var funcName = startTok.getValue();
		var listOfVars = new ArrayList<VariableDecl>();
		if (!peekTwoAhead("(")) {
			throw new ParseException("Malformed function. Nof found symbol \"(\"", tokens.get(0));
		}
		checkNext(funcName);//Remove func name
		checkNext("(");//Remove (
		while (!peekNext(")")) {
			listOfVars.add(parseVarDecl());
		}
		checkNext(")");
		if (!peekNext(":")) {
			throw new ParseException("Malformed function. Nof found symbol \":\"", tokens.get(0));
		}
		checkNext(":"); // Remove :
		var returnType = tokens.remove(0).getValue();
		while (!peekNext("begin")) {
			tokens.remove(0);
		}
		checkNext("begin");
		
		List<Statement> stmts = parseStmts();

		checkNext("end");
		checkNext(";");
		
		return new FunctionDecl(startTok, funcName, listOfVars, returnType, stmts);
	}

	/*
	 * <var-decl> ::= <label> : <type> ;
	 */
	private VariableDecl parseVarDecl() throws ParseException {
		var currentToken = tokens.get(0);
		var type = "";
		tokens.remove(0);
		if (peekTwoAhead("Integer")) {
			type = "Integer";
		} else if (peekTwoAhead("String")) {
			type = "String";
		} else if (peekTwoAhead("Boolean")) {
			type = "Boolean";
		} else if (peekTwoAhead("Double")) {
			type = "Double";
		} else if (peekTwoAhead("Float")) {
			type = "Float";
		} else if (peekTwoAhead("Long")) {
			type = "Long";
		}
		checkNext(":");
		if (peekTwoAhead(",")) {
			while (!peekNext(",")) {
				tokens.remove(0);
			}
			checkNext(",");
			return new VariableDecl(currentToken, currentToken.getValue(), type);
		} else if (peekTwoAhead(")")) {
			while (!peekNext(")")) {
				tokens.remove(0);
			}
			return new VariableDecl(currentToken, currentToken.getValue(), type);
		}
		while (!peekNext(";")) {
			tokens.remove(0);
		}
		checkNext(";");
		return new VariableDecl(currentToken, currentToken.getValue(), type);
	}
	
	/*
	 * <stmts> ::= (  <stmt>  )*
	 */
	private List<Statement> parseStmts() throws ParseException {
		var listOfStatements = new ArrayList<Statement>();
		while (!peekNext("end")) {
			var currentToken = tokens.get(0);
			if (peekNext("else")) {
				break;
			}
			if (peekTwoAhead(":=")) {
				addAssignment(currentToken, listOfStatements);
			} else if (peekNext("if")) {
				addIfThenElse(listOfStatements, currentToken);
			} else if (peekNext("print")) {
				addPrint(currentToken, listOfStatements);
			} else if (peekNext("return")) {
				addPrint(currentToken, listOfStatements);
			} else if (peekNext("while")) { //TODO Add realisation
				
			} else if (peekNext("print_line")) {
				addPrintLine(currentToken, listOfStatements);
			} else  {
				new NonVoidFunctionCall(currentToken); //TODO
			}
			checkNext(";");
		}
		return listOfStatements;
	}

	private void addIfThenElse(ArrayList<Statement> listOfStatements, Token currentToken) throws ParseException {
		StringBuilder ifStatement = new StringBuilder();
		var thenListOfStatements = new ArrayList<Statement>();
		var elseListOfStatements = new ArrayList<Statement>();
		tokens.remove(0);
		while (!peekNext("then")) {
			ifStatement.append(tokens.get(0).getValue());
			tokens.remove(0);
		}
		if (peekNext("then")) {
			checkNext("then");
			while (!peekNext("end")) {
				while (!peekNext("else")) {
					if (peekNext("end")) {
						break;
					}
					thenListOfStatements = (ArrayList<Statement>) parseStmts();
				}
				if (peekNext("else")) {
					checkNext("else");
					while (!peekNext("end") && !peekTwoAhead("if")) {
						elseListOfStatements = (ArrayList<Statement>) parseStmts();
					}
				}
			}
		}
		checkNext("end");//Remove end
		checkNext("if");//Remove if
		listOfStatements.add(new IfThenElse(currentToken, ifStatement.toString(), thenListOfStatements, elseListOfStatements));
	}

	private void addAssignment(Token currentToken, ArrayList<Statement> listOfStatements) {
		var name = currentToken.getValue();
		StringBuilder value = new StringBuilder();
		tokens.remove(0);
		if (peekNext(":=")) {
			tokens.remove(0);
		}
		while (!peekNext(";")) {
			value.append(tokens.get(0).getValue());
			tokens.remove(0);
		}
		listOfStatements.add(new Assignment(currentToken, name, value.toString()));
	}

	private void addPrint(Token currentToken, ArrayList<Statement> listOfStatements) {
		tokens.remove(0);
		StringBuilder printedValue = new StringBuilder();
		while (!peekNext(";")) {
			printedValue.append(tokens.get(0).getValue());
			tokens.remove(0);
		}
		listOfStatements.add(new Print(currentToken, printedValue.toString()));
	}

	private void addReturn(Token currentToken, ArrayList<Statement> listOfStatements) {
		tokens.remove(0);
		StringBuilder returnedValue = new StringBuilder();
		while (!peekNext(";")) {
			returnedValue.append(tokens.get(0).getValue());
			tokens.remove(0);
		}
		listOfStatements.add(new Return(currentToken, returnedValue.toString()));
	}

	private void addPrintLine(Token currentToken, ArrayList<Statement> listOfStatements) {
		tokens.remove(0);
		listOfStatements.add(new PrintLine(currentToken));
	}

}

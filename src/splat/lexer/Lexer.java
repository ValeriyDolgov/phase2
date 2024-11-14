package splat.lexer;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Lexer {

	private final File inputFile;


	public Lexer(File fileToProcess) {
		this.inputFile = fileToProcess;
	}


	private final Set<String> reservedWords = new HashSet<>(
			Arrays.asList("program", "begin", "end", "is", "while", "do",
					"if", "then", "else", "print", "print_line", "return"));


	private final Pattern[] tokenPatterns = {
			Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*"),
			Pattern.compile("\\d+"),
			Pattern.compile("\"[^\"]*\""),
			Pattern.compile("\\s+"),
			Pattern.compile("\\("),
			Pattern.compile("\\)"),
			Pattern.compile("=="),
			Pattern.compile("not"),
			Pattern.compile("<="),
			Pattern.compile("<"),
			Pattern.compile(">="),
			Pattern.compile(">"),
			Pattern.compile("and"),
			Pattern.compile("or"),
			Pattern.compile(";"),
			Pattern.compile(":="),
			Pattern.compile(":"),
			Pattern.compile(","),
			Pattern.compile("\\+"),
			Pattern.compile("-"),
			Pattern.compile("/"),
			Pattern.compile("\\*"),
			Pattern.compile("%")
	};


	public List<Token> tokenize() throws LexException {
		List<Token> tokenList = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {
			String currentLine;
			int lineNumber = 0;


			while ((currentLine = bufferedReader.readLine()) != null) {
				lineNumber++;
				tokenList.addAll(tokenizeLine(currentLine, lineNumber));
			}

		} catch (IOException e) {

			System.err.println("Error reading file: " + e.getMessage());
		}

		return tokenList;
	}


	private List<Token> tokenizeLine(String lineContent, int lineNumber) throws LexException {
		List<Token> tokensInLine = new ArrayList<>();
		int positionInLine = 0;

		while (positionInLine < lineContent.length()) {
			boolean matchedPattern = false;


			String remaining = lineContent.substring(positionInLine);


			for (Pattern pattern : tokenPatterns) {
				Matcher matcher = pattern.matcher(remaining);
				if (matcher.find() && matcher.start() == 0) {
					matchedPattern = true;
					String matchedToken = matcher.group();


					if (!matchedToken.trim().isEmpty()) {
						tokensInLine.add(new Token(matchedToken, lineNumber, positionInLine + 1));
					}
					positionInLine += matchedToken.length();
					break;
				}
			}


			if (!matchedPattern) {
				throw new LexException("Unknown token encountered", lineNumber, positionInLine + 1);
			}
		}

		return tokensInLine;
	}
}

package splat.lexer;


public class Token {
    private String value;
    private Integer line;
    private Integer column;


    public Token(String value, Integer line, Integer column) {
        this.value = value;
        this.line = line;
        this.column = column;
    }


    public String getValue() {
        return value;
    }


    public Integer getLine() {
        return line;
    }


    public Integer getColumn() {
        return column;
    }


    @Override
    public String toString() {
        return "Token[value='" + value + "', line=" + line + ", column=" + column + "]";
    }
}

package br.unirondon.compiler;

public class Syntax {

	private Lexer lexer;

	public Syntax(Lexer lexer) {
		super();
		this.lexer = lexer;
	}

	public Lexer getLexer() {
		return lexer;
	}

	public void setLexer(Lexer lexer) {
		this.lexer = lexer;
	}

}

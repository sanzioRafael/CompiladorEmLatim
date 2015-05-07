package br.unirondon.compiler;

import java.io.File;

public class Compiler {

	private File sourceCode;
	private Lexer lexer;

	public Compiler(File sourceCode) {
		this.sourceCode = sourceCode;
		this.lexer = new Lexer(sourceCode);
		
		this.lexer.startLexer();
	}

	public File getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(File sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Lexer getLexer() {
		return lexer;
	}

	public void setLexer(Lexer lexer) {
		this.lexer = lexer;
	}

}

package br.unirondon.compiler;

import java.io.File;

import br.unirondon.interfaces.CompileOnActions;

public class Compiler {

	private File sourceCode;
	private Lexer lexer;
	private CompileOnActions compileOnActions;

	public Compiler(File sourceCode, CompileOnActions compileOnActions) {
		this.sourceCode = sourceCode;
		this.lexer = new Lexer(sourceCode);
		this.compileOnActions = compileOnActions;
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
	
	public CompileOnActions getCompileOnActions() {
		return compileOnActions;
	}

	public void setCompileOnActions(CompileOnActions compileOnActions) {
		this.compileOnActions = compileOnActions;
	}

	public void startCompilation() {
		this.lexer.startLexer();
		
		if (!this.lexer.getOut().isEmpty()) {
			compileOnActions.writeConsole(this.lexer.getOut());
		}
		
		for (Token t : this.lexer.getSymbolTable()) {
			System.out.println(t.toString());
		}
	}

}

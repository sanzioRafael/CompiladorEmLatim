package br.unirondon.compiler;

import java.io.File;

import br.unirondon.exception.BasicException;
import br.unirondon.interfaces.CompileOnActions;

public class Compiler {

	private File sourceCode;
	private Lexer lexer;
	private Syntax syntax;
	private CompileOnActions compileOnActions;

	public Compiler(File sourceCode, CompileOnActions compileOnActions) {
		this.sourceCode = sourceCode;
		this.lexer = new Lexer(sourceCode);
		this.syntax = new Syntax(lexer);
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
			this.compileOnActions.writeConsole(this.lexer.getOut());
			try {
				this.syntax.checkProgramNameDeclaration();
			} catch (BasicException e) {
				this.compileOnActions.writeConsole(e.getMessage());
			}
		}
	}

}

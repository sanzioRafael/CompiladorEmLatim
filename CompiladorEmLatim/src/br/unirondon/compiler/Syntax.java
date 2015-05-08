package br.unirondon.compiler;

import br.unirondon.compiler.enume.TypeToken;
import br.unirondon.exception.BasicException;
import br.unirondon.values.AppConfig;

public class Syntax {

	private Lexer lexer;

	public Syntax(Lexer lexer) {
		this.lexer = lexer;
	}

	public Lexer getLexer() {
		return lexer;
	}

	public void setLexer(Lexer lexer) {
		this.lexer = lexer;
	}

	public boolean checkProgramNameDeclaration() throws BasicException {
		int row = 0;
		int count = 0;
		
		if (!this.lexer.getSymbolTable().get(0).getValue().equalsIgnoreCase(AppConfig.getSymbolString("program"))
				&& this.lexer.getSymbolTable().get(0).getType() != TypeToken.KEYWORDS) {
			throw new BasicException(AppConfig.getPropertie("App.mainCompilerWaitedException")
					+ AppConfig.getSymbolString("program") + " : "
					+ this.lexer.getSymbolTable().get(0).toString());
		}

		row = this.lexer.getSymbolTable().get(0).getRow();
		for (Token t : this.lexer.getSymbolTable()) {
			if (t.getRow() == row) {
				count++;
			}
		}
		
		if (count != 4) {
			throw new BasicException(AppConfig.getPropertie("App.mainCompilerProgramDeclarationException")
					+ this.lexer.getSymbolTable().get(0).toString());
		}
		
		if (this.lexer.getSymbolTable().get(1).getType() != TypeToken.SPACE) {
			throw new BasicException(AppConfig.getPropertie("App.mainCompilerWaitedSpaceException")
					+ this.lexer.getSymbolTable().get(1).toString());
		}

		if (this.lexer.getSymbolTable().get(2).getType() != TypeToken.IDENTIFICATOR) {
			throw new BasicException(AppConfig.getPropertie("")
					+ this.lexer.getSymbolTable().get(2).toString());
		}
		
		if (!this.lexer.getSymbolTable().get(3).getValue().equalsIgnoreCase(AppConfig.getSymbolString("semicolon"))) {
			throw new BasicException(AppConfig.getPropertie("App.mainCompilerWaitedSemiColonException")
					+ this.lexer.getSymbolTable().get(3).toString());
		}
		
		return true;
	}
	
	public boolean checkProgramVar() {
		boolean only = false;
		int line = 1;
		
		for (Token t : this.lexer.getSymbolTable()) {
			
		}
		
		return true;
	}
}

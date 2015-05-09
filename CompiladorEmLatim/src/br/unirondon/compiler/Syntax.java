package br.unirondon.compiler;

import java.util.regex.Pattern;

import br.unirondon.compiler.enume.TypeToken;
import br.unirondon.values.AppConfig;

public class Syntax {

	private Lexer lexer;
	private Pattern pattern;

	public Syntax(Lexer lexer) {
		this.lexer = lexer;
	}

	public Lexer getLexer() {
		return lexer;
	}

	public void setLexer(Lexer lexer) {
		this.lexer = lexer;
	}

	public String startSyntaxAnalizator() {
		String erro = "";
		
		erro = checkProgramNameDeclaration() + checkProgramVar();
		
		return erro;
	}
	
	private String checkProgramNameDeclaration () {
		int row = 0;
		int count = 0;
		String erro = "";
		
		try {
			if (!this.lexer.getSymbolTable().get(0).getValue().equalsIgnoreCase(AppConfig.getSymbolString("program"))
					&& this.lexer.getSymbolTable().get(0).getType() != TypeToken.KEYWORDS) {
				erro += AppConfig.getPropertie("App.mainCompilerWaitedException") + AppConfig.getSymbolString("program")
						+ " : " + this.lexer.getSymbolTable().get(0).toString();
			}

			row = this.lexer.getSymbolTable().get(0).getRow();
			for (Token t : this.lexer.getSymbolTable()) {
				if (t.getRow() == row) {
					count++;
				}
			}
			
			if (count != 4) {
				erro += AppConfig.getPropertie("App.mainCompilerProgramDeclarationException")
						+ this.lexer.getSymbolTable().get(0).toString();
			}
			
			if (this.lexer.getSymbolTable().get(1).getType() != TypeToken.SPACE
					&& this.lexer.getSymbolTable().get(1).getRow() == row) {
				erro += AppConfig.getPropertie("App.mainCompilerWaitedSpaceException")
						+ this.lexer.getSymbolTable().get(1).toString();
			}

			if (this.lexer.getSymbolTable().get(2).getType() != TypeToken.IDENTIFICATOR
					&& this.lexer.getSymbolTable().get(1).getRow() == row) {
				erro += AppConfig.getPropertie("App.mainCompilerWaitedIdentificatorException")
						+ this.lexer.getSymbolTable().get(2).toString();
			}
			
			if (!this.lexer.getSymbolTable().get(3).getValue().equalsIgnoreCase(AppConfig.getSymbolString("semicolon"))
					&& this.lexer.getSymbolTable().get(1).getRow() == row) {
				erro += AppConfig.getPropertie("App.mainCompilerWaitedSemiColonException")
						+ this.lexer.getSymbolTable().get(3).toString();
			}
		} catch (Exception e) {
			System.out.println(AppConfig.getPropertie("App.mainCompilerIndexOutBoundException") + e.getMessage());
		}
		
		return erro;
	}
	
	private String checkProgramVar () {
		int cont = 0;
		int row = 0;
		String erro = "";
		
		try {
			for (int i = 0; i < this.lexer.getSymbolTable().size(); i++) {
				if (this.lexer.getSymbolTable().get(i).getValue().equalsIgnoreCase(AppConfig.getSymbolString("var"))) {
					cont++;
					if (i == 0) {
						row = this.lexer.getSymbolTable().get(i).getRow();
					}
					
					if (this.lexer.getSymbolTable().get(i).getRow() == row) {
						erro += AppConfig.getPropertie("App.mainCompilerVarBeforeProgramException")
								+ this.lexer.getSymbolTable().get(i).toString();
					}
					
					if (cont > 1) {
						erro += AppConfig.getPropertie("App.mainCompilerVarsException")
								+ this.lexer.getSymbolTable().get(i).toString();
					}
				}
			}
			
			if (cont < 1) {
				erro = AppConfig.getPropertie("App.mainCompilerNoVarException");
			}
		} catch (Exception e) {
			System.out.println(AppConfig.getPropertie("App.mainCompilerIndexOutBoundException") + e.getMessage());
		}
		
		return erro;
	}
	
	private String checkVars () {
		String erro = "";
		int i = 0;
		
		return erro;
	}
	
}

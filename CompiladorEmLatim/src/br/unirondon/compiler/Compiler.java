package br.unirondon.compiler;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import br.unirondon.compiler.enume.ColorEnum;
import br.unirondon.exception.BasicException;
import br.unirondon.interfaces.CompileOnActions;
import br.unirondon.values.AppConfig;

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
		this.compileOnActions.clearConsole();
		String erroSyntax;
		String erroSemantics;
		
		
		if (this.sourceCode.length() > 2) {
			this.compileOnActions.writeConsole(AppConfig.getPropertie("App.mainCompilerLexer"), ColorEnum.BLACK);
			this.compileOnActions.writeConsole(this.lexer.getOut(), ColorEnum.BLACK);
			
			try {
				erroSyntax = this.syntax.startSyntaxAnalizator().isEmpty() ? "" :
					AppConfig.getPropertie("App.mainCompilerSyntax") + this.syntax.startSyntaxAnalizator();
				
				if (!erroSyntax.isEmpty()) {
					throw new BasicException(erroSyntax);
				} else {
					this.compileOnActions.writeConsole(AppConfig.getPropertie("App.mainCompilerSyntax")
							+ "\n" + AppConfig.getPropertie("App.mainCompilerSyntaxNoError"), ColorEnum.BLACK);
				}
				
			} catch (BasicException e) {
				this.compileOnActions.writeConsole(e.getMessage(), ColorEnum.RED);
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);

			alert.setTitle(AppConfig.getPropertie("Dialogs.titleInformation"));
			alert.setHeaderText(AppConfig.getPropertie("Dialogs.titleInformation"));
			alert.setContentText(AppConfig.getPropertie("Dialogs.sourceCodeIsEmpty"));

			alert.show();
		}
	}

}

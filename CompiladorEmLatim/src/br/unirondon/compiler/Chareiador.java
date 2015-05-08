package br.unirondon.compiler;

import br.unirondon.values.AppConfig;

public class Chareiador {

	private String sourceCode;
	private int currentChar;

	public Chareiador(String sourceCode) {
		this.currentChar = 0;
		this.sourceCode = sourceCode;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public int getCurrentChar() {
		return currentChar;
	}

	public void setCurrentChar(int currentChar) {
		this.currentChar = currentChar;
	}

	public String next() {
		String returnChar;
		try {
			if (currentChar != sourceCode.length()) {
				returnChar = sourceCode.substring(currentChar, currentChar + 1) == null ?
						AppConfig.getSymbolString("outOf") : sourceCode.substring(currentChar, currentChar + 1);					
			} else {
				returnChar = AppConfig.getSymbolString("outOf");
			}
		} catch (Exception e) {
			returnChar = AppConfig.getSymbolString("outOf");
		}

		currentChar++;

		return returnChar;
	}

	public String before() {
		String returnChar;
		try {
			if (currentChar != sourceCode.length()) {
				returnChar = sourceCode.substring(currentChar, currentChar--) == null ?
						sourceCode.substring(currentChar, currentChar++) : sourceCode.substring(currentChar, currentChar--);					
			} else {
				returnChar = sourceCode.substring(currentChar, currentChar++);
			}
		} catch (Exception e) {
			returnChar = AppConfig.getSymbolString("outOf");
		}

		return returnChar;
	}
}

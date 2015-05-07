package br.unirondon.compiler;

import br.unirondon.compiler.enume.TypeToken;

public class Token {

	private String value;
	private TypeToken type;

	public Token() {
		this.value = "";
		this.type = TypeToken.UNDEFINED;
	}

	public Token(String sourceCode, TypeToken type) {
		this.value = sourceCode;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String sourceCode) {
		this.value = sourceCode;
	}

	public TypeToken getType() {
		return type;
	}

	public void setType(TypeToken type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.value + " - " + this.type;
	}

}

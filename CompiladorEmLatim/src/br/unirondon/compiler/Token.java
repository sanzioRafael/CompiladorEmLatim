package br.unirondon.compiler;

import br.unirondon.compiler.enume.TypeToken;

public class Token {

	private String value;
	private TypeToken type;
	private int row;
	private int column;

	public Token() {
		this.value = "";
		this.type = TypeToken.UNDEFINED;
		this.row = 0;
		this.column = 0;
	}

	public Token(String value, TypeToken type, int row, int column) {
		super();
		this.value = value;
		this.type = type;
		this.row = row;
		this.column = column;
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

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return "[" + this.value + ", " + this.type + ", " + this.row + ", " + this.column + "]";
	}

}

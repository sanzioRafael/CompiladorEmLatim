package br.unirondon.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.unirondon.compiler.enume.TypeToken;
import br.unirondon.exception.BasicException;
import br.unirondon.values.AppConfig;

public class Lexer {

	private HashSet<Alphabetic> lyrics;
	private HashSet<String> numbers;
	private HashSet<String> operators;
	private HashSet<String> words;
	private HashSet<String> aliens;
	private FileReader fileReader;
	private BufferedReader reader;
	private String sourceCode;
	private Chareiador c;
	private String out;
	private List<Token> symbolTable;

	private String[] alphabeticUpper = new String[] {
			AppConfig.getSymbolString("upperA"),
			AppConfig.getSymbolString("upperB"),
			AppConfig.getSymbolString("upperC"),
			AppConfig.getSymbolString("upperD"),
			AppConfig.getSymbolString("upperE"),
			AppConfig.getSymbolString("upperF"),
			AppConfig.getSymbolString("upperG"),
			AppConfig.getSymbolString("upperH"),
			AppConfig.getSymbolString("upperI"),
			AppConfig.getSymbolString("upperJ"),
			AppConfig.getSymbolString("upperK"),
			AppConfig.getSymbolString("upperL"),
			AppConfig.getSymbolString("upperM"),
			AppConfig.getSymbolString("upperN"),
			AppConfig.getSymbolString("upperO"),
			AppConfig.getSymbolString("upperP"),
			AppConfig.getSymbolString("upperQ"),
			AppConfig.getSymbolString("upperR"),
			AppConfig.getSymbolString("upperS"),
			AppConfig.getSymbolString("upperT"),
			AppConfig.getSymbolString("upperU"),
			AppConfig.getSymbolString("upperV"),
			AppConfig.getSymbolString("upperW"),
			AppConfig.getSymbolString("upperX"),
			AppConfig.getSymbolString("upperY"),
			AppConfig.getSymbolString("upperZ") };
	private String[] alphabeticLower = new String[] {
			AppConfig.getSymbolString("lowerA"), AppConfig.getSymbolString("lowerB"),
			AppConfig.getSymbolString("lowerC"),
			AppConfig.getSymbolString("lowerD"),
			AppConfig.getSymbolString("lowerE"),
			AppConfig.getSymbolString("lowerF"),
			AppConfig.getSymbolString("lowerG"),
			AppConfig.getSymbolString("lowerH"),
			AppConfig.getSymbolString("lowerI"),
			AppConfig.getSymbolString("lowerJ"),
			AppConfig.getSymbolString("lowerK"),
			AppConfig.getSymbolString("lowerL"),
			AppConfig.getSymbolString("lowerM"),
			AppConfig.getSymbolString("lowerN"),
			AppConfig.getSymbolString("lowerO"),
			AppConfig.getSymbolString("lowerP"),
			AppConfig.getSymbolString("lowerQ"),
			AppConfig.getSymbolString("lowerR"),
			AppConfig.getSymbolString("lowerS"),
			AppConfig.getSymbolString("lowerT"),
			AppConfig.getSymbolString("lowerU"),
			AppConfig.getSymbolString("lowerV"),
			AppConfig.getSymbolString("lowerW"),
			AppConfig.getSymbolString("lowerX"),
			AppConfig.getSymbolString("lowerY"),
			AppConfig.getSymbolString("lowerZ") };
	private String[] nums = new String[] {
			AppConfig.getSymbolString("one"), AppConfig.getSymbolString("two"), AppConfig.getSymbolString("three"),
			AppConfig.getSymbolString("fuor"), AppConfig.getSymbolString("five"), AppConfig.getSymbolString("six"),
			AppConfig.getSymbolString("seven"), AppConfig.getSymbolString("eight"), AppConfig.getSymbolString("nine"),
			AppConfig.getSymbolString("zero")
			};
	private String[] ops = new String[] {
			AppConfig.getSymbolString("addition"), AppConfig.getSymbolString("subtraction"), AppConfig.getSymbolString("multiplication"),
			AppConfig.getSymbolString("division"), AppConfig.getSymbolString("bigger"), AppConfig.getSymbolString("less"),
			AppConfig.getSymbolString("biggerEquals"), AppConfig.getSymbolString("attribution"), AppConfig.getSymbolString("lessEquals"),
			AppConfig.getSymbolString("equals"), AppConfig.getSymbolString("different"), AppConfig.getSymbolString("booleanAnd"),
			AppConfig.getSymbolString("booleanOr"), AppConfig.getSymbolString("simpleAsphas"), AppConfig.getSymbolString("doubleAsphas")
			};
	private String[] als = new String[] {
			AppConfig.getSymbolString("addition"), AppConfig.getSymbolString("subtraction"), AppConfig.getSymbolString("multiplication"),
			AppConfig.getSymbolString("division"), AppConfig.getSymbolString("bigger"), AppConfig.getSymbolString("less"),
			AppConfig.getSymbolString("attribution"), AppConfig.getSymbolString("logicNot"), AppConfig.getSymbolString("logicAnd"),
			AppConfig.getSymbolString("logicOr"), AppConfig.getSymbolString("semicolon"), AppConfig.getSymbolString("dot")
			};
	private String[] keyWords = new String[] {
			AppConfig.getSymbolString("program"), AppConfig.getSymbolString("begin"), AppConfig.getSymbolString("end"),
			AppConfig.getSymbolString("var"), AppConfig.getSymbolString("int"), AppConfig.getSymbolString("float"),
			AppConfig.getSymbolString("boolean"), AppConfig.getSymbolString("char"), AppConfig.getSymbolString("string"),
			AppConfig.getSymbolString("if"), AppConfig.getSymbolString("else"), AppConfig.getSymbolString("for"),
			AppConfig.getSymbolString("while"), AppConfig.getSymbolString("to"), AppConfig.getSymbolString("do"),
			AppConfig.getSymbolString("then"), AppConfig.getSymbolString("write"), AppConfig.getSymbolString("read")
			};

	public Lexer(File source) {
		try {
			this.fileReader = new FileReader(source);
			this.reader = new BufferedReader(fileReader);
			this.out = "";

			buildHash();
		} catch (FileNotFoundException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(AppConfig
					.getPropertie("Dialogs.titleException"), AppConfig
					.getPropertie("Dialogs.masterheadException"), AppConfig
					.getPropertie("Dialogs.messageFileNotFoundException"),
					AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
		}
	}

	public List<Token> getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(List<Token> symbolTable) {
		this.symbolTable = symbolTable;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public void startLexer() {
		try {
			this.sourceCode = "";
			String charC, line;
			Token token = new Token();

			charC = "";
			line = null;

			while ((line = reader.readLine()) != null) {
				this.sourceCode += line + "\n";
			}

			this.reader.close();
			this.fileReader.close();

			this.c = new Chareiador(sourceCode);
			this.symbolTable = new ArrayList<Token>();

			while (!charC.equals(AppConfig.getSymbolString("outOf"))) {
				charC = c.next();

				while (charC == " ")
					charC = c.next();

				if (this.lyrics.contains(new Alphabetic(charC.toLowerCase(),
						charC.toUpperCase()))) {
					while (this.lyrics.contains(new Alphabetic(charC.toLowerCase(), charC.toUpperCase())) || this.numbers.contains(charC)) {
						token.setValue(token.getValue() + charC);
						charC = c.next();
					}

					if (this.words.contains(token.getValue().toUpperCase())) {
						token.setType(TypeToken.KEYWORDS);
					} else {
						token.setType(TypeToken.IDENTIFICATOR);
					}

					this.symbolTable.add(token);
					buildinSymbolTable(token);

					token = new Token();
				}

				if (this.numbers.contains(charC)) {
					while (this.numbers.contains(charC)) {
						token.setValue(token.getValue() + charC);
						charC = c.next();
					}

					token.setType(TypeToken.CONSTANT);

					this.symbolTable.add(token);
					buildinSymbolTable(token);

					token = new Token();
				}

				if (this.aliens.contains(charC)) {
					while (this.aliens.contains(charC)) {
						token.setValue(token.getValue() + charC);
						charC = c.next();
					}

					if (operators.contains(token.getValue())) {
						token.setType(TypeToken.OPERATORS);
					} else {
						token.setType(TypeToken.CHARACTER);
					}

					this.symbolTable.add(token);
					buildinSymbolTable(token);

					token = new Token();
				}

				if (charC.equals("\n") || charC.equals("\t")) {
					token = new Token(charC, TypeToken.SPECIAL);
					buildinSymbolTable(token);
					token = new Token();
				}
			}

			System.out.println("::Tabela de Simbolos::");

			for (int i = 0; i < symbolTable.size(); i++) {
				System.out.println("Índice: " + i + ": " + symbolTable.get(i).getValue());
			}

			System.out.println("::Programa::\n" + out);
		} catch (IOException e) {
			new BasicException(e.getMessage(), e).showExceptioDialog(
					AppConfig.getPropertie("Dialogs.titleException"),
					AppConfig.getPropertie("Dialogs.masterheadException"),
					AppConfig.getPropertie("Dialogs.messageIOException"),
					AppConfig.getPropertie("Dialogs.messageExceptionWas"), e);
		}
	}

	private void buildHash() {
		this.lyrics = new HashSet<Alphabetic>();
		for (int i = 0; i < alphabeticLower.length; i++) {
			this.lyrics.add(new Alphabetic(alphabeticLower[i],
					alphabeticUpper[i]));
		}

		this.numbers = new HashSet<>();
		for (int j = 0; j < nums.length; j++) {
			this.numbers.add(nums[j]);
		}

		this.operators = new HashSet<>();
		for (int k = 0; k < ops.length; k++) {
			this.operators.add(ops[k]);
		}

		this.words = new HashSet<>();
		for (int l = 0; l < keyWords.length; l++) {
			this.words.add(keyWords[l]);
		}

		this.aliens = new HashSet<>();
		for (int m = 0; m < als.length; m++) {
			this.aliens.add(als[m]);
		}
	}

	private void buildinSymbolTable(Token token) {
		int index;

		if (token.getType() == TypeToken.IDENTIFICATOR) {
			index = this.symbolTable.indexOf(token);

			if (index != -1) {
				this.out += "[" + token.getType().getDesc() + ", " + index
						+ "] ";
			} else {
				this.out += "[" + token.getType().getDesc() + ", "
						+ this.symbolTable.size() + "]";
			}

		} else {
			if (token.getType() == TypeToken.CONSTANT) {
				index = this.symbolTable.indexOf(token);

				if (index != -1) {
					this.out += "[" + token.getType().getDesc() + ", " + index
							+ "] ";
				} else {
					this.out += "[" + token.getType().getDesc() + ", " + this.symbolTable.size() + "]";
				}
			} else {
				if (token.getType() == TypeToken.SPECIAL) {
					this.out += token.getValue();
				} else {
					this.out += "[" + token.getValue() + "]";
				}
			}

		}
	}

}

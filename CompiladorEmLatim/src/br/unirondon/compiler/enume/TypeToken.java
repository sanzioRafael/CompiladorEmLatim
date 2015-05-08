package br.unirondon.compiler.enume;

import br.unirondon.values.AppConfig;

public enum TypeToken {

	IDENTIFICATOR(AppConfig.getPropertie("App.mainCompilerIdentificator")),
	CONSTANT(AppConfig.getPropertie("App.mainCompilerConstant")),
	OPERATORS(AppConfig.getPropertie("App.mainCompilerOperators")),
	CHARACTER(AppConfig.getPropertie("App.mainCompilerCharacter")),
	ALIENS(AppConfig.getPropertie("App.mainCompilerAliens")),
	KEYWORDS(AppConfig.getPropertie("App.mainCompilerKeyWords")),
	SPECIAL(AppConfig.getPropertie("App.mainCompilerSpecial")),
	SPACE(AppConfig.getPropertie("App.mainCompilerSpace")),
	UNDEFINED(AppConfig.getPropertie("App.mainCompilerUndefined"));

	private String desc;

	private TypeToken(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}

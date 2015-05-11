package br.unirondon.compiler.enume;

import br.unirondon.values.AppConfig;

public enum ColorEnum {

	BLACK(AppConfig.getPropertie("App.mainTxtBlack")),
	PURPLE(AppConfig.getPropertie("App.mainTxtPurple")),
	BLUE(AppConfig.getPropertie("App.mainTxtBlue")),
	GREEN(AppConfig.getPropertie("App.mainTxtGreen")),
	RED(AppConfig.getPropertie("App.mainTxtRed"));

	private String desc;

	private ColorEnum (String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}

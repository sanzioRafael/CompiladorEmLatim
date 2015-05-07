package br.unirondon.view.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bin")
public class Binary {

	private String binName;

	public Binary() {

	}

	public Binary(String binName) {
		super();
		this.binName = binName;
	}

	@XmlElement(name = "binName")
	public String getBinName() {
		return binName;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}
	
	@Override
	public String toString() {
		return this.binName;
	}
	
}

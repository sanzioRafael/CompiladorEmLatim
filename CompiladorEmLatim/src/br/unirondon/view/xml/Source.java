package br.unirondon.view.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "src")
public class Source {

	private String srcName;

	public Source() {

	}

	public Source(String srcName) {
		this.srcName = srcName;
	}

	@XmlElement(name = "srcName")
	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	@Override
	public String toString() {
		return this.srcName;
	}
	
}

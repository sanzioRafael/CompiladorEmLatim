package br.unirondon.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UsuarioConfig")
public class UsuarioConfig {

	private List<Directory> directories;

	public UsuarioConfig() {

	}

	public UsuarioConfig(List<Directory> directories) {
		this.directories = directories;
	}

	@XmlElement(name="Directories")
	public List<Directory> getDirectories() {
		return directories;
	}

	public void setDirectories(List<Directory> directories) {
		this.directories = directories;
	}

}

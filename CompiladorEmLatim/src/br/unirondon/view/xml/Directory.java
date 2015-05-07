package br.unirondon.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Directory")
public class Directory {

	private String directoryPath;

	private boolean selected;

	private List<Project> projects;

	public Directory() {

	}

	public Directory(String directoryPath, boolean selected, List<Project> projects) {
		this.directoryPath = directoryPath;
		this.selected = selected;
		this.projects = projects;
	}

	@XmlElement(name = "directoryPath")
	public String getDirectoryPath() {
		return directoryPath;
	}

	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	@XmlElement(name = "isSelected")
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@XmlElement(name = "Projects")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	@Override
	public String toString() {
		return this.directoryPath;
	}
	
}

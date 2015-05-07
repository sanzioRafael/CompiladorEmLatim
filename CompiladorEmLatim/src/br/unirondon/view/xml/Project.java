package br.unirondon.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Project")
public class Project {

	private String name;

	private List<Source> srcs;

	private List<Binary> bins;

	public Project() {

	}

	public Project(String name, List<Source> srcs, List<Binary> bins) {
		this.name = name;
		this.srcs = srcs;
		this.bins = bins;
	}

	@XmlElement(name = "projectName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "sources")
	public List<Source> getSrcs() {
		return srcs;
	}

	public void setSrcs(List<Source> srcs) {
		this.srcs = srcs;
	}

	@XmlElement(name = "binares")
	public List<Binary> getBins() {
		return bins;
	}

	public void setBins(List<Binary> bins) {
		this.bins = bins;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}

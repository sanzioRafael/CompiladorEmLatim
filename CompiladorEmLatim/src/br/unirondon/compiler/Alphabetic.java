package br.unirondon.compiler;

import java.io.Serializable;

public class Alphabetic implements Serializable, Comparable<Alphabetic> {

	private static final long serialVersionUID = 1L;

	private String lowerLyric;
	private String upperLyric;

	public Alphabetic(String lowerLyric, String upperLyric) {
		this.lowerLyric = lowerLyric;
		this.upperLyric = upperLyric;
	}

	public String getLowerLyric() {
		return lowerLyric;
	}

	public void setLowerLyric(String lowerLyric) {
		this.lowerLyric = lowerLyric;
	}

	public String getUpperLyric() {
		return upperLyric;
	}

	public void setUpperLyric(String upperLyric) {
		this.upperLyric = upperLyric;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((lowerLyric == null) ? 0 : lowerLyric.hashCode());
		result = prime * result + ((upperLyric == null) ? 0 : upperLyric.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Alphabetic other = (Alphabetic) obj;

		if (lowerLyric == null) {
			if (other.lowerLyric != null)
				return false;
		} else if (!lowerLyric.equals(other.lowerLyric))
			return false;

		if (upperLyric == null) {
			if (other.upperLyric != null)
				return false;
		} else if (!upperLyric.equals(other.upperLyric))
			return false;

		return true;
	}

	@Override
	public int compareTo(Alphabetic alpha) {
		return alpha.getLowerLyric().equals(this.lowerLyric) && alpha.getUpperLyric().equals(this.upperLyric) ? 0 : -1;
	}
}

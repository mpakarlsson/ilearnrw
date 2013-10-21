package ilearnrw.user.problems;

import ilearnrw.user.IlearnException;

import java.io.Serializable;

public class ProblemDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title, URI;
	private Category type;


	public ProblemDefinition(String URI, Category type) {
		this.URI = URI;
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public Category getType() {
		return type;
	}

	public void setType(Category type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProblemDefinition [URI=" + URI + ", type=" + type + "]\n";
	}

}
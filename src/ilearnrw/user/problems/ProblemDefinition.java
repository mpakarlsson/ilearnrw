package ilearnrw.user.problems;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ProblemDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	private String severityType;
	
	private String uri;
	private Category type;

	public ProblemDefinition() {
		this.uri = null;
		this.type = null;
	}

	public ProblemDefinition(String URI, Category type) {
		this.uri = URI;
		this.type = type;
	}

	public String getSeverityType() {
		return severityType;
	}

	public void setSeverityType(String title) {
		this.severityType = title;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uRI) {
		uri = uRI;
	}

	public Category getType() {
		return type;
	}

	public void setType(Category type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Problem Definition:\nURI:" + uri + type.toString();
	}

}
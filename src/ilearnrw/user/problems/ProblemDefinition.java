package ilearnrw.user.problems;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ProblemDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	private String severityType;
	
	@SerializedName("uri")
	private String URI;
	private Category type;

	public ProblemDefinition() {
		this.URI = null;
		this.type = null;
	}

	public ProblemDefinition(String URI, Category type) {
		this.URI = URI;
		this.type = type;
	}

	public String getSeverityType() {
		return severityType;
	}

	public void setSeverityType(String title) {
		this.severityType = title;
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
		return "Problem Definition:\nURI:" + URI + type.toString();
	}

}
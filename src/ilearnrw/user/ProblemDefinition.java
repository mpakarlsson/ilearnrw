package ilearnrw.user;

import java.io.Serializable;
import java.util.ArrayList;

public class ProblemDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	String URI;
	Category type;
	// Category:: I replaced the 'string'...
	// String gives no semantically information about the category!
	int scoreUpperBound;
	ArrayList<LanguageCode> availableLanguages;

	public ProblemDefinition(String URI, Category type, int scoreUpperBound,
			ArrayList<LanguageCode> availableLanguages) {
		this.URI = URI;
		this.type = type;
		this.scoreUpperBound = scoreUpperBound;
		this.availableLanguages = availableLanguages;
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

	public void setSubcategory(Category type) {
		this.type = type;
	}

	public int getScoreUpperBound() {
		return scoreUpperBound;
	}

	public void setScoreUpperBound(int scoreUpperBound) {
		this.scoreUpperBound = scoreUpperBound;
	}

	public ArrayList<LanguageCode> getAvailableLanguages() {
		return availableLanguages;
	}

	public void setAvailableLanguages(ArrayList<LanguageCode> availableLanguages) {
		this.availableLanguages = availableLanguages;
	}

	@Override
	public String toString() {
		return "ProblemDefinition [URI=" + URI + ", type=" + type
				+ ",\n scoreUpperBound=" + scoreUpperBound
				+ ",\n availableLanguages=" + availableLanguages + "]\n";
	}

}
package ilearnrw.user;

import java.util.ArrayList;

public class ProblemDefinition {
	String URI;
	String category;
	//Category:: the string has to be replaced! It gives no semantically information about the category!
	int scoreUpperBound;
	ArrayList<LanguageCode> availableLanguages;
	public ProblemDefinition(String URI, String category, int scoreUpperBound,
			ArrayList<LanguageCode> availableLanguages) {
		this.URI = URI;
		this.category = category;
		this.scoreUpperBound = scoreUpperBound;
		this.availableLanguages = availableLanguages;
	}
	
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	
	
}

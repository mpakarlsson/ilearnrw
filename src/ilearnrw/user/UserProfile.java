package ilearnrw.user;

import java.io.Serializable;

public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	private LanguageCode language;
	private UserProblemsList problems;
	private UserPreferences preferences;
	
	public UserProfile(LanguageCode language, UserProblemsList problems,
			UserPreferences preferences) {
		this.language = language;
		this.problems = problems;
		this.preferences = preferences;
	}
	
	public UserProfile(LanguageCode language) {
		this.language = language;
		//this.problems = sets problems to default values;
		//this.preferences = sets preferences to default values;
	}
	
	public UserProfile() {
		this.language = LanguageCode.EN;
		this.problems = new UserProblemsList();//has to set the problems to default values;
		this.preferences = new UserPreferences();//has to set the preferences to default values;
	}
	
	public UserProfile(int userId) {
		this.language = LanguageCode.EN;
		this.problems = new UserProblemsList();//userId);//has to set the problems to default values;
		this.preferences = new UserPreferences(userId);//has to set the preferences to default values;
	}

	public LanguageCode getLanguage() {
		return language;
	}

	public void setLanguage(LanguageCode language) {
		this.language = language;
	}

	public UserProblemsList getProblemsList() {
		return problems;
	}

	public void setProblemsList(UserProblemsList problems) {
		this.problems = problems;
	}

	public UserPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(UserPreferences preferences) {
		this.preferences = preferences;
	}

	

}

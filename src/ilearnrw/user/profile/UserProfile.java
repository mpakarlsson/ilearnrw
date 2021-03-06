package ilearnrw.user.profile;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.UserPreferences;
import ilearnrw.utils.LanguageCode;

import java.io.Serializable;

public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	private UserProblems userProblems;
	private UserPreferences preferences;

	private LanguageCode language;

	public UserProfile(LanguageCode language, UserProblems problemsMatrix, UserPreferences preferences) {
		this.language = language;
		this.userProblems = problemsMatrix;
		this.preferences = preferences;
		
		this.userProblems.updateSystemCluster();
	//	this.userProblems.setSystemCluster(calculateSystemCluster());
		
	}
	

	
	
	public UserProfile() {
		this.userProblems = new UserProblems();//has to set the problems to default values;
		this.preferences = new UserPreferences();//has to set the preferences to default values;
	}
	
	public UserProfile(int userId) {
		this.userProblems = new UserProblems();//userId);//has to set the problems to default values;
		this.preferences = new UserPreferences(userId);//has to set the preferences to default values;
	}

	public UserProblems getUserProblems() {
		return userProblems;
	}

	public void setProblems(UserProblems userProblems) {
		this.userProblems = userProblems;
	}

	public UserPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(UserPreferences preferences) {
		this.preferences = preferences;
	}
	
	public LanguageCode getLanguage() {
		return language;
	}

	public void setLanguage(LanguageCode language) {
		this.language = language;
	}

}

package ilearnrw.user.profile;

import ilearnrw.user.UserPreferences;
import ilearnrw.utils.LanguageCode;

import java.io.Serializable;

public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	private UserSeveritiesToProblems userSeveritiesToProblems;
	private UserPreferences preferences;
	
	public UserProfile(UserSeveritiesToProblems problemsMatrix, UserPreferences preferences) {
		this.userSeveritiesToProblems = problemsMatrix;
		this.preferences = preferences;
	}
	
	public UserProfile() {
		this.userSeveritiesToProblems = new UserSeveritiesToProblems();//has to set the problems to default values;
		this.preferences = new UserPreferences();//has to set the preferences to default values;
	}
	
	public UserProfile(int userId) {
		this.userSeveritiesToProblems = new UserSeveritiesToProblems();//userId);//has to set the problems to default values;
		this.preferences = new UserPreferences(userId);//has to set the preferences to default values;
	}

	public UserSeveritiesToProblems getUserSeveritiesToProblems() {
		return userSeveritiesToProblems;
	}

	public void setUserSeveritiesToProblems(UserSeveritiesToProblems userSeveritiesToProblems) {
		this.userSeveritiesToProblems = userSeveritiesToProblems;
	}

	public UserPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(UserPreferences preferences) {
		this.preferences = preferences;
	}

	

}

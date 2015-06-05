package ilearnrw.user;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int userId;
	private UserProfile profile;
	private UserDetails details;
	private ArrayList<UserSession> session;
	private ArrayList<UserSession> oldSessions;
	
	public User(int userId, UserProfile profile, UserDetails details,
			ArrayList<UserSession> oldSessions) {
		this.userId = userId;
		this.profile = profile;
		this.details = details;
		this.oldSessions = oldSessions;
		this.session = new ArrayList<UserSession>();
	}
	
	public User(LanguageCode lc) {
		this.userId = -1;
		this.profile = new UserProfile();
		this.details = new UserDetails(lc);
		this.oldSessions = new ArrayList<UserSession>();
		this.session = new ArrayList<UserSession>();
	}
	
	public User(int userId) {
		this.userId = userId;
		this.profile = new UserProfile(userId);
		this.details = new UserDetails(userId);
		this.oldSessions = new ArrayList<UserSession>();
		this.session = new ArrayList<UserSession>();
	}
	
	public int getUserId() {
		return userId;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	public UserDetails getDetails() {
		return details;
	}

	public void setDetails(UserDetails details) {
		this.details = details;
	}

	public ArrayList<UserSession> getSession() {
		return session;
	}

	public void setSession(ArrayList<UserSession> session) {
		this.session = session;
	}

	public ArrayList<UserSession> getOldSessions() {
		return oldSessions;
	}

	public void setOldSessions(ArrayList<UserSession> oldSessions) {
		this.oldSessions = oldSessions;
	}
	
	
	
}

package com.ilearnrw.services.rest;

public class RefreshTokenData {
	private String userName;
	private String password;

	public RefreshTokenData() {
		this.userName = "";
		this.password = "";
	}

	public RefreshTokenData(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

package com.ilearnrw.loginapp.network;

public class Authentication {
	
	String auth = null;
	String refresh = null;

	public Authentication(String auth, String refresh) {
		this.auth = auth;
		this.refresh = refresh;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getRefresh() {
		return refresh;
	}
	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}
}

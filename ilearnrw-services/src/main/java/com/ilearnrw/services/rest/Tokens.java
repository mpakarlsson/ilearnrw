package com.ilearnrw.services.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class Tokens implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -2348072910628985011L;
	
	
	@JsonProperty("auth")
	private String authToken;
	
	@JsonProperty("refresh")
	private String refreshToken;

	@JsonProperty("auth")
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	@JsonProperty("refresh")
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public Tokens()
	{
		this.authToken = "";
		this.refreshToken = "";
	}
	
	public Tokens(String auth, String refresh) {
		this.authToken = auth;
		this.refreshToken = refresh;
	}
}

package com.ilearnrw.services.rest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.token.Token;

import java.util.Collection;


public class RestToken extends UsernamePasswordAuthenticationToken {

	
    public RestToken(String key, String credentials) {
        super(key, credentials);
    }

    public RestToken(String key, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(key, credentials, authorities);
    }

    public String getPrincipal() {
        return (String) super.getPrincipal();
    }

    public String getCredentials() {
        return (String) super.getCredentials();
    }

	public static RestToken fromToken(Token receivedToken) {
		return new RestToken(receivedToken.getExtendedInformation(), "");
	}
}

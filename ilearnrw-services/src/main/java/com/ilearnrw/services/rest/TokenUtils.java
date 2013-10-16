package com.ilearnrw.services.rest;

import java.util.Date;

import org.springframework.security.core.token.Token;

public class TokenUtils {
	private static final int TIMEOUT = 100000;

	public static boolean isExpired(Token token) {
		long timeDiff = new Date().getTime() - token.getKeyCreationTime();
		
		return (timeDiff > TIMEOUT);
	}
	
}

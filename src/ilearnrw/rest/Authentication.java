package ilearnrw.rest;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
public class Authentication {
	String auth = null;
	String refresh = null;
	public Authentication(String auth, String refresh) {
		this.auth = auth;
		this.refresh = refresh;
	}
}

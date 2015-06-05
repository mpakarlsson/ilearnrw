package ilearnrw.user;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */

import ilearnrw.utils.IlearnException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @note At the moment there will be issues if a user has 2
 * 		 concurrent sessions. The reason for this is that the
 * 		 UserActions are not tied directly to a session.
 * 		 To fix: Add deviceid to session and actions.
 */
public class UserSession implements Serializable {
	private static final long serialVersionUID = 1L;
	boolean isActive;
	Timestamp start, end;

	/**
	 * sets the timestamp and turns the boolean variable 'isActive' to true!
	 */
	public void startSession(){
		if (isActive) return;
		Date date = new Date();
		start = new Timestamp(date.getTime());
		isActive = true;
	}
	/**
	 * sets the timestamp and turns the boolean variable 'isActive' to true!
	 */
	public void stopSession(){
		if (!isActive) return;
		Date date = new Date();
		end = new Timestamp(date.getTime());
		isActive = false;
	}
	
	public void insertAction(UserAction x) throws IlearnException{
		if (!isActive) throw new IlearnException("session has not started!");
	}
}

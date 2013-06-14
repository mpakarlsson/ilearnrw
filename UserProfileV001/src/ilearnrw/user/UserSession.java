package ilearnrw.user;

import java.awt.Desktop.Action;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class UserSession {
	boolean isActive;
	Timestamp start, end;
	ArrayList<Action> history;
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
	
	public void insertAction(Action x) throws IlearnException{
		if (!isActive) throw new IlearnException("session has not started!");
		else history.add(x);		
	}
}

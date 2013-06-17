package ilearnrw.user;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class UserSession {
	boolean isActive;
	Timestamp start, end;
	ArrayList<UserAction> history;
	/**
	 * sets the timestamp and turns the boolean variable 'isActive' to true!
	 */
	public void startSession(){
		if (isActive) return;
		Date date = new Date();
		start = new Timestamp(date.getTime());
		isActive = true;
		this.history = new ArrayList<UserAction>();
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
		else history.add(x);		
	}
	
	public void loadSession(int userId){
		//it has to load the specific entries fro the database!
		this.history = new ArrayList<UserAction>();
	}
}

package ilearnrw.user;

import ilearnrw.application.ApplicationId;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/** 
 * @todo Add some documentation about this class.
 * @todo Add get/setters
 */
public class UserAction implements Serializable{
	private static final long serialVersionUID = 2L;
	
	private String mTag;
	private String mValue;
	private ApplicationId mApplicationId;
	private int mUserId;
	/**
	 * Not sure about the datatype Date.
	 * Does it contain the time as well?
	 * Might have to change.
	 */
	private Date mTimeStamp;
	
	/** Default constructor used by applications when logging a user action.
	 * 
	 * @param tag
	 * @param value
	 * @param applicationId
	 * @param userId
	 */
	public UserAction(String tag, String value, ApplicationId applicationId, int userId)
	{
		
		mTimeStamp = new Date();
		//mTimeStamp = createRandomDate(1980, 2014).getTime();
		mTag = tag;
		mValue = value;
		mApplicationId = applicationId;
		mUserId = userId;
	}

	
	public int getUserId() {
		return mUserId;
	}
	
	public ApplicationId getApplicationId(){
		return mApplicationId;
	}
	
	public String getTag(){
		return mTag;
	}
	
	public String getText(){
		return mValue;
	}
	
	public Date getTimeStamp(){
		return mTimeStamp;
	}
	
	public void setUserId(int userId){
		mUserId = userId;
	}
	
	public void setApplicationId(ApplicationId applicationId){
		mApplicationId = applicationId;
	}
	
	
	/** @todo: remove these after testing UserActions with UserActionFilter enough
	 */

	@SuppressWarnings("unused")
	private Calendar createRandomDate(int startYear, int endYear){
		Calendar cal = new GregorianCalendar();
		int year = randBetween(startYear, endYear);
		cal.set(Calendar.YEAR, year);
		int dayOfYear = randBetween(1, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
		cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
		return cal;
		
	}
	private static int randBetween(int start, int end){
		return start + (int)Math.round(Math.random() * ( end - start ));
	}
}

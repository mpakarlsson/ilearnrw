package ilearnrw.user;

import java.io.Serializable;
import java.util.Date;

/** 
 * @todo Add some documentation about this class.
 * @todo Add get/setters
 */
public class UserAction implements Serializable{
	private static final long serialVersionUID = 2L;
	
	private String mTag;
	private String mValue;
	private String mApplicationId;
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
	public UserAction(String tag, String value, String applicationId, int userId)
	{
		mTimeStamp = new Date();
		mTag = tag;
		mValue = value;
		mApplicationId = applicationId;
		mUserId = userId;
	}

	
	public int getUserId() {
		return mUserId;
	}
	
	public String getApplicationId(){
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
	
	public void setApplicationId(String applicationId){
		mApplicationId = applicationId;
	}
	
}

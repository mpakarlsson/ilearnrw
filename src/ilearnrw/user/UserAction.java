package ilearnrw.user;

import java.util.Date;

/** 
 * @todo Add some documentation about this class.
 * @todo Add get/setters
 */
public class UserAction {
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
	}

	
	public int getUserId() {
		return mUserId;
	}
	
}

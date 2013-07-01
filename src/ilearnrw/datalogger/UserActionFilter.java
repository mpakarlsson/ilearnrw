package ilearnrw.datalogger;

import java.util.Date;
import java.util.List;

import ilearnrw.application.ApplicationId;

/** The UserActionFilter is used to query the datalogger for information
 * 
 * @todo Add getters/setters, appropriate functions etc.
 */
public class UserActionFilter {

	int mUserId;
	ApplicationId mApplicationId;
	List<String> mTags;
	
	Date mTimeStart;
	Date mTimeEnd;
	
	
	public UserActionFilter(int userId, ApplicationId appId, Date start, Date end, List<String> tags){
		mUserId = userId;
		mApplicationId = appId;
		mTags = tags;
		mTimeStart = start;
		mTimeEnd = end;
	}
	
}

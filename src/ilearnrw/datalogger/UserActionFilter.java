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
	
	/** Creates a filter that is used on the UserAction list
	 * 
	 * @param 
	 * */
	public UserActionFilter(int userId, ApplicationId appId, Date start, Date end, List<String> tags){
		mUserId = userId;
		mApplicationId = appId;
		mTags = tags;
		mTimeStart = start;
		mTimeEnd = end;
	}
	
	public int getUserId(){
		return mUserId;
	}
	public ApplicationId getApplicationId(){
		return mApplicationId;
	}
	public List<String> getTags(){
		return mTags;
	}
	public Date getStartTime(){
		return mTimeStart;
	}
	public Date getEndTime(){
		return mTimeEnd;
	}
	
}

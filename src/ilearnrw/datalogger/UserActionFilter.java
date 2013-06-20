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
	
}

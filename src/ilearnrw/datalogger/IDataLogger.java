package ilearnrw.datalogger;

import java.util.List;

import ilearnrw.user.UserAction;


/** \brief Provides an interface for applications to log UserActions.
 * 
 */
public interface IDataLogger {

	/** Given a UserActionFilter this function can return a list of UserActions.
	 * 
	 */
	List<UserAction> getUserActions(UserActionFilter filter);
	/** \brief
	 * 
	 * The Timestamp of the UserAction should be updated before the actions
	 * is stored.
	 * The timestamp should be local time of the server when this function is called.
	 */
	void logAction(UserAction action);
}

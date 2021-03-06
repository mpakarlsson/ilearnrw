package ilearnrw.datalogger;

import java.util.List;

import ilearnrw.user.UserAction;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */

/** \brief Provides an interface for applications to log UserActions.
 * 
 */
public interface IDataLogger {

	
	/** Given a UserActionFilter this function can return a list of UserActions.
	 * 
	 */
	List<UserAction> getActions(UserActionFilter filter);
	/** \brief
	 * 
	 * The Timestamp of the UserAction should be updated before the actions
	 * is stored.
	 * The timestamp should be local time of the server when this function is called.
	 * (Added some temporary code that generates random years within an interval
	 * since I needed to test the UserActionFilter date handling, remove it when done)
	 */
	void logAction(UserAction action);
	
	
	/**
	 * \brief
	 * 
	 * A small class for handling UserActions
	 * - Saving, Loading, getting, creating
	 * */
	
	//UserActions getUserActions();
	
	
	
}

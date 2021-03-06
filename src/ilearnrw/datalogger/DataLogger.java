/** \brief DataLogger package.
 *
 * 
 * Four main interfaces has been defined at the moment.
 * 
 * 1. IDataLogger
 * 2. ILoginProvider
 * 3. IProfileAccessUpdater
 * 4. IUserAdministration
 * 
 * 
 * The DataLogger object implements IDataLogger, ILoginProvider and IProfileAccessUpdater.
 * 
 * See their respective documentation for details.
 * 
 */
package ilearnrw.datalogger;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
/** \addtogroup DolphinTodo_20130619
 *  @{
 * 		@todo Implement IProfileAccessUpdater
 * 		@todo *DONE:* Move interfaces to DataLogger
 * 		@todo Implement all usecases provided by IProfileAccessUpdater into console application.
 * 		@todo *DONE:* cleanup the code
 * 		@todo *DONE:* Merge with the main branch.
 *  @}
 */

import java.util.List;

import ilearnrw.datalogger.IDataLogger;
import ilearnrw.games.NumberProblems;
import ilearnrw.prototype.application.UserActions;
/** \page "DataLogger Log format"
 *
 * Following is a quick description about how data is, or will be, stored.
 * 
 * ##Users
 * 
 * Users are stored in the UserStore. Initially the UserStore just uses Java serialization
 * to write the User objects to disk. 
 * This requires that any objects that has a relationship (via class members) to the
 * User object has to implement the java.io.Serializable interface.
 * 
 * This could be changed to a SQL backend in the future.
 * 
 * ##User Actions
 * 
 * The User actions are envisioned to be stored in a SQL database with a similar setup as
 * described below.
 * 
 * Table UserActions:
 * 
 * |Type|Name|
 * |---:|:---|
 * |PK          | actionId  
 * |FK          | userId d
 * |FK          | marker r
 * |FK          | applicationId 
 * |TimeStamp   | timestamp 
 * |VARCHAR()   | tag 
 * |VARCHAR()   | value 
 *
 *
 * This would result in SQL output similar to:
 *
 * Id|Profile|Marker|Application|TimeStamp|Tag|Value
 * --|-------|------|-----------|---------|------|-----
 * 1 |child1 |Teach1|some_id|2013-06-14|ADD|cat
 * 2 |child1 |child1|some_id|2013-06-14|REMOVE|cat
 * 
 * 
 * Using the getUserActions() function of the IDataLogger interface the database
 * can be queried. To filter the result a UserActionFilter object is provided.
 */
import ilearnrw.user.User;
import ilearnrw.user.UserAction;
import ilearnrw.user.UserDetails;
import ilearnrw.user.UserPreferences;
import ilearnrw.user.UserSession;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.profile.UserProblems;
import ilearnrw.utils.LanguageCode;


/** DataLogger class.
 * 
 * The Datalogger is the main object that Applications will use to query and update the
 * user object and it's properties.
 * 
 */
public class DataLogger implements IProfileAccessUpdater, ILoginProvider, IDataLogger
{
	/** The object that store users.
	 */
	private UserStore mUserStore = null;
	private UserActions mUserActions = null;
	
	/** Loads the UserStore from disk.
	 * 
	 * This has to be called during initialization.
	 * 
	 * @note filePath does not have to exist.
	 * 	 	 But the directory it is in has to.
	 * 
	 * @param filePath FilePath to a disk copy.
	 * @return true if successful. false if already loaded.
	 */
	public boolean loadUserStore(String filePath) {
		if( mUserStore != null )
			return false;
		mUserStore = new UserStore(filePath);
		return true;
	}
	
	/** Loads the UserActions from disk.
	 * 
	 * This has to be called during initialization.
	 * 
	 * @note filePath does not have to exist.
	 * 	 	 But the directory it is in has to.
	 * 
	 * @param filePath FilePath to a disk copy.
	 * @return true if successful. false if already loaded.
	 */
	public boolean loadUserActions(String filePath){
		if( mUserActions != null)
			return false;

		mUserActions = new UserActions(filePath);
		return true;
	}

	@Override
	public void logAction(UserAction action) {
		if(action == null)
			return;
		
		mUserActions.addUserAction(action);
	}
	
	/**
	 * The list of user actions if you only are interested in the list of user actions
	 */
	@Override
	public List<UserAction> getActions(UserActionFilter filter) {
		return mUserActions.getActions(filter);
	}

	/** The current user to be updated.
	 * 
	 */
	private User mUser = null;
	/** The `marker` to use when logging changes.
	 */
	private User mMarker = null;
	/** Flag to keep track on whether mUser is dirty;
	 */
	private boolean mUserIsDirty = false;
	
	private void setDirty() {
		mUserIsDirty = true;
	}

	/** Gets a User object from a username and password.
	 * 
	 * @return A User object if successful. Otherwise null.
	 */
	@Override
	public User getUser(String username, String password) {
		if( mUserStore == null )
			return null;
		return mUserStore.getUser(username, password);
	}

	@Override
	public void setCurrentUser(User user,
			User marker) throws PermissionException,
			PendingChangesAvailable {
		if( mUserIsDirty )
			throw new PendingChangesAvailable("Current user has changes. Write them first or discard them.");
		mUser = user;
		mMarker = marker;
	}

	@Override
	public boolean registerUserSession(UserSession session) {
		if(session != null){
			mUser.getSession().add(session);

			setDirty();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean moveToOldSessions(){
		for(UserSession session : mUser.getSession()){
			mUser.getOldSessions().add(session);
		}
		mUser.getSession().clear();
		setDirty();
		
		return true;
	}

	@Override
	public boolean setPreference(UserPreferences preferences) {
		if(preferences != null){
			mUser.getProfile().setPreferences(preferences);
			setDirty();
			return true;
		}
		return false;
	}

	@Override
	public boolean setUserDetail(UserDetails userDetails) {
		if(userDetails != null){
			mUser.setDetails(userDetails);
			setDirty();
			return true;
		}
		return false;
	}

	@Override
	public boolean setUserProblemMatrix(UserProblems problemsMatrix) {
		mUser.getProfile().setProblems(problemsMatrix);
		setDirty();
		return true;
	}

	@Override
	public boolean setUserLanguage(LanguageCode language) {
		if(language != mUser.getProfile().getLanguage()){
			mUser.getProfile().setLanguage(language);
			setDirty();
			return true;
		}
		return false;
	}

	@Override
	public User writePendingChanges() {
		if(!mUserIsDirty)
			return mUser;
		mUserIsDirty = false;
		return mUserStore.update(mUser);
	}

	@Override
	public boolean hasPendingChanges() {
		return mUserIsDirty;
	}

	/** Will discard the temporary user object.
	 */
	@Override
	public void discardPendingChanges() {
		mUser = null;
		mMarker = null;
		mUserIsDirty = false;
	}

	@Override
	public IUserAdministration getUserAdministration() {
		return mUserStore;
	}

}
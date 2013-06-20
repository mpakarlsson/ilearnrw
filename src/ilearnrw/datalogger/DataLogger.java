/** \brief DataLogger package.
 *
 * \note Exactly what package the IProfileAccessUpdater
 * should be part of is still unknown.
 * 
 */
package ilearnrw.datalogger;

/** \addtogroup DolphinTodo_20130619
 *  @{
 * 		@todo Implement IProfileAccessUpdater
 * 		@todo Move interfaces to DataLogger
 * 		@todo implement all usecases provided by iprofileaccessupdater into console application.
 * 		@todo cleanup the code
 * 		@todo merge chris changes.
 *  @}
 */


import java.util.List;

import ilearnrw.datalogger.IDataLogger;
/** \page "Log format"
 *
 * Actions
 * * Add difficult word
 * * Remove difficult word
 *
 * Profile|Marker|Application|TimeStamp|Tag|Value
 * -------|------|-----------|---------|------|-----
 * child1 |Teach1|some_id|2013-06-14|ADD|cat
 * child1 |child1|some_id|2013-06-14|REMOVE|cat
 * 
 *
 */
import ilearnrw.user.LanguageCode;
import ilearnrw.user.User;
import ilearnrw.user.UserAction;
import ilearnrw.user.UserDetails;
import ilearnrw.user.UserPreferences;
import ilearnrw.user.UserProfile;
import ilearnrw.user.UserSession;


/** DataLogger class.
 * 
 */
public class DataLogger implements IProfileAccessUpdater, ILoginProvider, IDataLogger
{
	/** The object that store users.
	 */
	private UserStore mUserStore = null;
	
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
	
	

	@Override
	public List<UserAction> getUserActions(UserActionFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logAction(UserAction action) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setPreference(UserPreferences preferences) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setUserDetail(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProblemDefinition(String URI) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeProblemDefinition(String URI) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setUserLanguage(LanguageCode Language) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User writePendingChanges() {
		if(!mUserIsDirty)
			return mUser;
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
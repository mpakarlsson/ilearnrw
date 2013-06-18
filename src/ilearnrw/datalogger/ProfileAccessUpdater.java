package ilearnrw.datalogger;

import ilearnrw.datalogger.IProfileAccessUpdater;

import ilearnrw.user.LanguageCode;
import ilearnrw.user.User;
import ilearnrw.user.UserDetails;
import ilearnrw.user.UserPreferences;
import ilearnrw.user.UserProfile;
import ilearnrw.user.UserSession;
/** \brief
 * 
 * \note This class currently does not make use of the 
 *       potential to log every action and the marker.
 *
 *
 */

public class ProfileAccessUpdater implements IProfileAccessUpdater {


    /** \brief The currently selected marker profile.
     *
     * The marker profile is used to show who made a
     * certain change/log in the system
     */
    private User mMarker;
    /** \brief The profile to be updated.
     */
    private User mUser;

    /** \brief Access to storage-backend.
     * 
     */
    //private ProfileBackend mBackend;

    private boolean mIsDirty;

    private void setDirty()
    {
        mIsDirty = true;
    }

	@Override
	public boolean setCurrentProfile(UserProfile profile,
			UserProfile markerProfile) throws PermissionException,
			PendingChangesAvailable {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerUserSession(UserSession session) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
    public boolean setPreference(UserPreferences pref)
    {
        //mUserProfile.set(pref);
        setDirty();
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
    public UserProfile writePendingChanges()
    {
        //backend.storeUser(mUserProfile);
        return mUser.getProfile();
    }
	@Override
	public boolean hasPendingChanges() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean discardPendingChanges() {
		// TODO Auto-generated method stub
		return false;
	}
}

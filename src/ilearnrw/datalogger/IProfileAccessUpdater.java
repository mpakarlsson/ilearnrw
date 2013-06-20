package ilearnrw.datalogger;

import ilearnrw.user.LanguageCode;
import ilearnrw.user.User;
import ilearnrw.user.UserDetails;
import ilearnrw.user.UserPreferences;
import ilearnrw.user.UserSession;

/** \brief Interface to update profiles in the iLearnRW system.
 *
 * This interface assumes that `Apps` would get a read-only
 * copy of the users profile and would then have to use
 * this interface to make changes to the profile. When
 * the profile has been updated a new readonly copy
 * would have to be fetched.
 * 
 * A nice side-effect is that we can make sure to
 * log all changes to the profile.
 *
 * Updater also manages 
 *
 * Registry - 
 *      Handles/stores users/profiles.
 *
 * @note This is the interface that the application would use.
 * 		 Not an interface appropriate to a webservice.
 * 		 The implementer of this interface could still use a
 * 		 webservice to store the information in.
 *
 *  UserAdd/delete etc..
 *  Get user (given userid, password)
 */
public interface IProfileAccessUpdater {

	/** @brief The marker does not have permission to perform the
	 * 	requested change.
	 */
	public class PermissionException extends Exception {
		private static final long serialVersionUID = 1L;
		public PermissionException(String message) {
	        super(message);
	    }
	    public PermissionException(String message, Throwable throwable) {
	        super(message, throwable);
	    }
	    public String getMessage(){
	        return super.getMessage();
	    }
	}
	/** @brief Indicates that an action cannot be performed due
	 * 		   to pending changes.
	 * 
	 * If the action was to be performed the pending changes would
	 * be lost.
	 */
	public class PendingChangesAvailable extends Exception {
		private static final long serialVersionUID = 1L;
		public PendingChangesAvailable(String message) {
	        super(message);
	    }
	    public PendingChangesAvailable(String message, Throwable throwable) {
	        super(message, throwable);
	    }
	    public String getMessage(){
	        return super.getMessage();
	    }
	}
    /** \brief Sets the userprofile to work with and who
     *         will be the `marker` for the changes.
     *
     * Doing this once saves the application
     * from passing the userprofile to all 
     * functions.
     * 
     * The `marker` can be the same as the profile to be
     * edited.
     *
     * \throws PermissionException
     *      \brief Thrown when the admin profile does not
     *             have permissions to update the profile.
     *
     * \throws PendingChangesAvailable
     *      \brief Thrown when there are pending changes
     *             are available.
     */
    void setCurrentUser(
                              User user,  /*!< Profile of the user to update */
                              User marker /*!< Profile of the user who is doing the update*/
                              ) throws
        PermissionException, PendingChangesAvailable;

    /** \brief Registers a new User session in the log
     */
    boolean registerUserSession(UserSession session);
    
    /** \brief Registers a change to the users preferences.
     *
     * Should changes to user preferences be logged so that
     * teachers/experts can be notified/see that a
     * preference has been changed?
     *
     */
    boolean setPreference(UserPreferences preferences);

    /** \brief Updates a UserDetail.
     */
    boolean setUserDetail(UserDetails userDetails);

    /** \brief Adds a predefined ProblemDefinition
     *         to the user profile.
     *
     */
    boolean addProblemDefinition(String URI);

    /** \brief Removes a ProblemDefinition.
     */
    boolean removeProblemDefinition(String URI);
    
    /** \brief Sets the userlanguage.
    */
    boolean setUserLanguage(LanguageCode Language);

    /** \brief Writes unwritten changes to the back end (persistent storage).
     *
     * This will flush all pending operations in the same
     * order as they are added. This is important if for
     * example the same preference is set twice.
     *
     * \return The new user after the changes has been applied.
     */
    User writePendingChanges();

    /** \brief Checks if there are any changes pending.
     */
    boolean hasPendingChanges();

    /** \brief Discards all pending changes and resets the internal
     * 		   state.
     * 	
     * 	In order to add new changes setCurrentUser has to be called again.
     */
    void discardPendingChanges();
}

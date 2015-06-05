package ilearnrw.datalogger;

import java.util.List;

import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
/** \brief Provides User administration tasks.
 */
public interface IUserAdministration {
	
	/** @brief The marker does not have permission to perform the
	 * 	requested change.
	 */
	public class AuthenticationException extends Exception {
		private static final long serialVersionUID = 1L;
		public AuthenticationException(String message) {
	        super(message);
	    }
	    public AuthenticationException(String message, Throwable throwable) {
	        super(message, throwable);
	    }
	    public String getMessage(){
	        return super.getMessage();
	    }
	}
	/** Authenticates an Administrator by requiring a password.
	 * 
	 * @param adminPassword The administrator password.
	 * 						This could be set in a configuration file.
	 */
	void authenticateAdmin(String adminPassword) throws AuthenticationException;
	boolean isAuthenticated();

	/** Creates a new User.
     * @param username
     * @param password
     * @return The new User.
     */
    User createUser(String username, String password, LanguageCode lc) throws AuthenticationException;

    User updatePassword(String username, String newPassword) throws AuthenticationException;

    User changeUsername(String oldUsername, String newUsername) throws AuthenticationException;
    
    boolean deleteUser(String username) throws AuthenticationException;
    
    List<User> getAllUsers() throws AuthenticationException;
}

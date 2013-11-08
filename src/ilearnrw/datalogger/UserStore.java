package ilearnrw.datalogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import ilearnrw.user.User;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.user.profile.UserSeveritiesToProblems;
/**
 * @brief The UserStore is used to create and store user objects persistantly.
 * 
 * @todo Replace storage backed with SQL database.
 * @todo Enforce unique usernames.
 * 
 * @note At the moment Java's built in serialization is used
 * 		 to store users to disk. This requires all objects to
 * 		 be stored to implement the Serializable interface.
 * 
 * Implements a CRUD interface for users and save/load functions to
 * store the data persistently.
 */
class UserStore implements ILoginProvider, IUserAdministration {
	
	private ArrayList<User> mLoadedUsers;
	private static String mFilePath; //!< The complete path to the file to read/write the user information from/to.
	private Logger mLogger;
	private boolean mHasAdminAuth = false;
		
	
	/**
	 * 
	 * @param filePath The full path to the file to use to store the user profiles etc.
	 */
	public UserStore(String filePath)
	{
		mLogger = Logger.getLogger("UserStore");
		mFilePath = filePath;
		load();
	}
	
	/** Create a new user.
	 * 
	 * Will generate a new UserId for the user.
	 * 
	 * @return A new User object.
	 */
	public User create() {
		SecureRandom random = new SecureRandom();
		int userId = random.nextInt(Integer.MAX_VALUE);
		if( read_noCopy(userId) != null ) // If we managed to randomize an already existing user, just try again (Stackoverflow potential).
			return create();
		User newUser = new User(userId);
		mLoadedUsers.add(newUser);
		save();
		return deepCopy(newUser);
	}
	
	/** Reads a User from the UserStore
	 *
	 * @param userId The userId of the user to load
	 * @return The user with userId == userId
	 */
	public User read(int userId) {
		User u = read_noCopy(userId);
		if( u == null )
			return null;
		return deepCopy(u);
	}
	
	private User read(String username) {
		for( User u : mLoadedUsers )
			if( u.getDetails().getUsername().equals(username))
				return u;
		return null;
	}
	
	private User read_noCopy(int userId) {
		for(User u : mLoadedUsers) {
			if( u.getUserId() == userId )
				return u;
		}
		return null;
	}
	
	/** Updates the user currently stored with the one supplied.
	 * 
	 * @todo Don't allow multiple users with the same username.
	 * 
	 * @param user The new user copy to store.
	 * @return A new copy of the user object.
	 */
	public User update(User user)
	{
		User oldUser = read_noCopy(user.getUserId());
		if( oldUser == null )
			return null;
		mLoadedUsers.remove(oldUser);
		mLoadedUsers.add(user);
		save();
		return deepCopy(user);
	}

	/** Deletes a user from the store.
	 * 
	 * @param userId userId of the user to delete
	 * @return	true if successful.
	 */
	public boolean delete(int userId) {
		User u = read_noCopy(userId);
		if( u == null )
			return false;
		boolean ret = mLoadedUsers.remove(u);
		save();
		return ret;
	}
	
	/**
	 * 
	 * @return A list of all stored userid's.
	 */
	public LinkedList<Integer> getUserIds() {
		LinkedList<Integer> ret = new LinkedList<Integer>();
		for( User u : mLoadedUsers )
			ret.add(u.getUserId());
		return ret;
	}
	

	/** Returns a copy of a User object
	 * 
	 * This is called from functions returning a user
	 * in this class since we do not want to give
	 * out the actual reference stored in the _loadedUsers
	 * list.
	 * */
	private User deepCopy(User user) {
		User obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(user);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
            obj = (User) in.readObject();
        }
        catch(IOException ex) {
        	mLogger.severe("Could not copy User object: " + ex.toString());
        }
        catch(ClassNotFoundException ex) {
        	mLogger.severe("Could not copy User object: " + ex.toString());
        }
        return obj;
	}

	/** @brief Loads the serialized objects in `mFilePath` into mLoadedUsers
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void load() {
		try {
			FileInputStream is = new FileInputStream(new File(mFilePath));
			ObjectInputStream ois = new ObjectInputStream(is);
			mLoadedUsers = (ArrayList<User>) ois.readObject();
			ois.close();
		}
		catch (ClassNotFoundException ex)
		{
        	mLogger.severe("Could not load Users: " + ex.toString());
			mLoadedUsers = new ArrayList<User>();
		}
		catch (FileNotFoundException ex)
		{
        	//mLogger.severe("Could not load Users: " + ex.toString());
			mLoadedUsers = new ArrayList<User>();
		}
		catch (IOException ex)
		{
        	mLogger.severe("Could not load Users: " + ex.toString());
			mLoadedUsers = new ArrayList<User>();
		}
		catch (ClassCastException ex) {
        	mLogger.severe("Could not load Users: " + ex.toString());
			mLoadedUsers = new ArrayList<User>();
		}
	}
	
	/** @brief Serializes mLoadedUsers into `mFilePath`
	 * 
	 */
	private void save() {
		try {
			FileOutputStream os = new FileOutputStream(new File(mFilePath));
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(mLoadedUsers);
			os.close();
		}
		catch (FileNotFoundException ex) {
        	mLogger.severe("Could not save Users object: " + ex.toString());
			mLoadedUsers.clear();
		}
		catch (IOException ex) {
        	mLogger.severe("Could not save Users object: " + ex.toString());
			mLoadedUsers.clear();
		}
	}

	public User getUser(String username, String password) {
		for(User u : mLoadedUsers)
		{
			if( u.getDetails().getUsername().equals(username) )
				if( u.getDetails().checkPassword(password)){
					u.getProfile().getUserSeveritiesToProblems().loadTestGreekProblems();
					return deepCopy(u);
				}
		}
		return null;
	}

	@Override
	public void authenticateAdmin(String adminPassword)
			throws AuthenticationException {
		if( adminPassword.equals("ilearn"))
			mHasAdminAuth = true;
		checkAdminAuth();
	}
	@Override
	public boolean isAuthenticated() {
		return mHasAdminAuth;
	}

	@Override
	public User createUser(String username, String password)
			throws AuthenticationException {
		checkAdminAuth();

		User u = create();
		u.getDetails().setUsername(username);
		u.getDetails().setPassword(password);
		return update(u);
	}

	@Override
	public User updatePassword(String username, String newPassword)
			throws AuthenticationException {
		checkAdminAuth();
		
		User u = read(username);
		if( u == null )
			return null;
		u.getDetails().setPassword(newPassword);
		return update(u);
	}

	@Override
	public User changeUsername(String oldUsername, String newUsername)
			throws AuthenticationException {
		checkAdminAuth();

		User u = read(oldUsername);
		if( u == null )
			return null;
		u.getDetails().setUsername(newUsername);
		return update(u);
	}

	@Override
	public boolean deleteUser(String username) throws AuthenticationException {
		checkAdminAuth();

		User u = read(username);
		if(u == null)
			return false;
		return delete(u.getUserId());
	}
	private void checkAdminAuth() throws AuthenticationException {
		if(!mHasAdminAuth)
			throw new AuthenticationException("Not authenticated for administration tasks.");
		
	}

	@Override
	public IUserAdministration getUserAdministration() {
		return this;
	}

	@Override
	public List<User> getAllUsers() throws AuthenticationException {
		checkAdminAuth();
		List<User> ret = new ArrayList<User>();
		for( User u : mLoadedUsers )
			ret.add(deepCopy(u));
		return ret;
	}
}

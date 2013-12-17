package ilearnrw.user;

import ilearnrw.utils.LanguageCode;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @todo Something to think about is whether this should
	 * 		 be case-sensitive and if unicode should be supported.
	 * 		 Unicode can create some headaches, there was a blog post
	 * 		 about this recently (of which i only read the headline)
	 * 		 see: http://labs.spotify.com/2013/06/18/creative-usernames/
	 */
	private String username;
	/** @brief Stores hash(plainTextPassword + mPasswordSalt)
	 * 
	 * @note In the future we might want to change this to store the
	 * 		 value in a base64 encoded String;
	 */
	private byte[] mPasswordHash;
	/** @brief Stores the salt used to hash the password.
	 * 
	 * @note In the future we might want to change this to store the
	 * 		 value in a base64 encoded String;
	 */
	private byte[] mPasswordSalt;

	public UserDetails(String username, int password, LanguageCode language) {
		this.username = username;
		mPasswordHash = null;
		mPasswordSalt = null;
	}
	
	public UserDetails(LanguageCode language) {
		this.username = "username";
		mPasswordHash = null;
		mPasswordSalt = null;
	}
	
	public UserDetails(int userId) {
		//it has to load the corresponding details to the user that has this userId!
		this.username = "username";
		mPasswordHash = null;
		mPasswordSalt = null;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	private static final int iterations = 10*1024;
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;

	private static byte[] hash(String plaintextPassword, byte[] salt) 
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		if( plaintextPassword == null || plaintextPassword.length() == 0)
			throw new IllegalArgumentException("Password cannot be null or len == 0");
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = factory.generateSecret(new PBEKeySpec(plaintextPassword.toCharArray(),
															  salt,
															  iterations,
															  desiredKeyLen)
											  );
		return key.getEncoded();
	}
	final public void setPassword(String plaintextPassword)
	{
		try {
			mPasswordSalt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
			mPasswordHash = hash(plaintextPassword, mPasswordSalt);
		}
		catch(NoSuchAlgorithmException ex)
		{
			/* Log error*/
			mPasswordSalt = null;
			mPasswordHash = null;
		}
		catch(InvalidKeySpecException ex)
		{
			/* Log error*/
			mPasswordSalt = null;
			mPasswordHash = null;
		}
	}

	
	final public boolean checkPassword(String plaintextPassword)
	{
		if(!passwordIsSet())
			return false;
		try {
			byte[] passwordHash = hash(plaintextPassword, mPasswordSalt);
			return Arrays.equals(passwordHash, mPasswordHash);
		}
		catch(NoSuchAlgorithmException ex)
		{
			/* Log error*/
		}
		catch(InvalidKeySpecException ex)
		{
			/* Log error*/
		}
		return false;
	}
	
	public boolean passwordIsSet()
	{
		if(mPasswordHash == null || mPasswordHash == null ) {
			return false;
		}
		return true;
	}
	
	public boolean hasPassword()
	{
		return mPasswordHash != null;
	}
	
	final public void setPasswordHash(byte[] hash, byte[] salt)
	{
		mPasswordHash = hash;
		mPasswordSalt = salt;
	}
	
}

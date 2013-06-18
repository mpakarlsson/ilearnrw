package ilearnrw.user;

import java.io.Serializable;

public class UserDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private int password;
	private LanguageCode language;
	
	public UserDetails(String username, int password, LanguageCode language) {
		this.username = username;
		this.password = password;
		this.language = language;
	}
	
	public UserDetails(LanguageCode language) {
		this.username = "username";
		this.password = 1216985755;//hasCode of the word 'password'!
		this.language = language;
	}
	
	public UserDetails(int userId) {
		//it has to load the corresponding details to the user that has this userId!
		this.username = "username";
		this.password = 1216985755;//hasCode of the word 'password'!
		this.language = LanguageCode.GR;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 
	 * @return the hash value of the user's password
	 */
	public int getPassword() {
		return password;
	}
	
	/**
	 * 
	 * @param the hash value of the users password
	 */
	public void setPassword(int password) {
		this.password = password;
	}
	
	/**
	 * 
	 * @param the user's password as a string
	 */
	public void setPassword(String password) {
		this.password = password.hashCode();
	}
	
	public LanguageCode getLanguage() {
		return language;
	}
	/**
	 * 
	 * @param the language code as a enum item
	 */
	public void setLanguage(LanguageCode language) {
		this.language = language;
	}
	/**
	 * 
	 * @param the language code as a enum item
	 */
	public void setLanguage(byte languageCode) {
		this.language = languageCode==LanguageCode.getEnglishCode() ?
				LanguageCode.EN:LanguageCode.GR;
	}
	
}

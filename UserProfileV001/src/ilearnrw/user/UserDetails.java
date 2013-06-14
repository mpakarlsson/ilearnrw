package ilearnrw.user;

public class UserDetails {
	private String username;
	private int password;
	private LanguageCode language;
	
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

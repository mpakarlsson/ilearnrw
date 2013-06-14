package ilearnrw.user;

public class UserPreferences {
	private int fontSize;
	

	public UserPreferences() {
		this.setFontSize(14);
	}

	public UserPreferences(int fontSize) {
		this.setFontSize(fontSize);
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		if (fontSize<10) this.fontSize = 10;
		else if (fontSize>20) this.fontSize = 20;
		else this.fontSize = fontSize;
	}
	
}

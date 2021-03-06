package ilearnrw.user;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.io.Serializable;

public class UserPreferences implements Serializable {
	private static final long serialVersionUID = 1L;

	private int fontSize;
	//we have to decide if we are going to use enumerators (or static fields), 
	//and special 'validity check' methods for the variables fontstyle, fontcolour etc.

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

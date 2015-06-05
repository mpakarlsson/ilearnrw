package ilearnrw.utils;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
public enum LanguageCode {
	EN, GR;
	public static byte getEnglishCode(){
		return 1;
	}
	public static byte getGreekCode(){
		return 0;
	}
	
	public static LanguageCode fromString(String languageCode) {
		if (languageCode == null)
			// default is EN
			return EN;
		
		if (languageCode.compareToIgnoreCase("EN") == 0) {
			return EN;
		}
		if (languageCode.compareToIgnoreCase("GR") == 0) {
			return GR;
		}
		
		// default is EN
		return EN;
	}
	
	public byte getCode() {
		if (this.equals(EN))
			return getEnglishCode();
		if (this.equals(GR))
			return getGreekCode();
		return -1;
	}
}

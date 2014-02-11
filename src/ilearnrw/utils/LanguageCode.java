package ilearnrw.utils;

public enum LanguageCode {
	EN, GR;
	public static byte getEnglishCode(){
		return 1;
	}
	public static byte getGreekCode(){
		return 0;
	}
	
	public static LanguageCode fromString(String languageCode) {
		if (languageCode.compareToIgnoreCase("EN") == 0) {
			return EN;
		}
		if (languageCode.compareToIgnoreCase("GR") == 0) {
			return GR;
		}
		
		// default is EN
		return EN;
	}
}

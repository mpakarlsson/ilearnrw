package ilearnrw.utils;

public enum LanguageCode {
	EN, GR;
	public static byte getEnglishCode(){
		return 1;
	}
	public static byte getGreekCode(){
		return 0;
	}
	public static LanguageCode fromInteger(int code) {
		if (code==1)
			return EN;
		else
			return GR;
	}
}

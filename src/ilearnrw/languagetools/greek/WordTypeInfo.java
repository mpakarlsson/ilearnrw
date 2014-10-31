package ilearnrw.languagetools.greek;

import com.google.gson.Gson;

public class WordTypeInfo {
	private String gender, number, theCase, person, voice, tense, mood;
	public WordTypeInfo(){
		gender = "";
		number = "";
		theCase = "";
		person = "";
		voice = "";
		tense = "";
		mood = "";
	}
	public WordTypeInfo(String json){
		WordTypeInfo t = new Gson().fromJson(json, WordTypeInfo.class);
		gender = t.getGender();
		number = t.getNumber();
		theCase = t.getTheCase();
		person = t.getPerson();
		voice = t.getVoice();
		tense = t.getTense();
		mood = t.getMood();
	}

	public String getGender() {
		return gender;
	}
	public String getNumber() {
		return number;
	}
	public String getTheCase() {
		return theCase;
	}
	public String getPerson() {
		return person;
	}
	public String getVoice() {
		return voice;
	}
	public String getTense() {
		return tense;
	}
	public String getMood() {
		return mood;
	}

}

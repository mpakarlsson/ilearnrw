package ilearnrw.languagetools.greek;

import java.util.ArrayList;

public class DictionaryEntry {
	//ΛΕΞΗ	ΛΗΜΜΑ	Μ.τ.Λ.	ΧΑΡΑΚΤΗΡΙΣΜΟΙ
	private String lemma, partOfSpeech;
	private ArrayList<String> extras;
	public DictionaryEntry() {
		lemma = null;
		partOfSpeech = null;
		extras = new ArrayList<String>();
	}

	public DictionaryEntry(String lemma, String partOfSpeech, ArrayList<String> extras) {
		this();
		this.lemma = lemma;
		this.partOfSpeech = partOfSpeech;
		this.extras = extras;
	}


	public boolean isActive(){
		return !(partOfSpeech==null || partOfSpeech.isEmpty()); 
	}
	public String getLemma() {
		return lemma;
	}
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	public String getPartOfSpeech() {
		return partOfSpeech;
	}
	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	public ArrayList<String> getExtras() {
		return extras;
	}
	public void setExtras(ArrayList<String> extras) {
		this.extras = extras;
	}
	public void addExtras(String extra) {
		this.extras.add(extra);
	}
	
	@Override
	public String toString(){
		return "["+lemma+"]\t["+partOfSpeech+"]\t"+extras.toString()+"\n"; 
	}

}

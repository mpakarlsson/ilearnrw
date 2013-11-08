package ilearnrw.textclassification;

import ilearnrw.utils.LanguageCode;

public class Word {
	protected String word;
	protected WordType type;
	protected String[] syllables;
	protected String cvForm;
	protected String phonetics;
	protected int numSyllables;
	protected LanguageCode lc;
	protected double frequency;
	
	public Word(){
	}
			
	public Word(String word){
		word = word.replaceAll("(\\«)|(\\»)|(\\()|(\\))|(\\{)|(\\})|(\\[)|(\\])|(\\<)|(\\>)|(\\=)|(\\%)|(\\€)|(\\$)", "");
		this.word = word.toLowerCase();
		//all initializations must go here!!!
		
		numSyllables = 0;
		frequency = 0;
		type = WordType.Unknown;
		cvForm = "";
		phonetics = "";
		lc = LanguageCode.GR;
	}
		
	public String getWord() {
		return word;
	}
	
	public WordType getType() {
		return type;
	}
	
	public String[] getSyllables() {
		return syllables;
	}

	public int getNumberOfSyllables() {
		return numSyllables;
	}
	
	public String getCVForm() {
		return cvForm;
	}
	
	public String getPhonetics(){
		return phonetics;
	}
	
	public int getLength(){
		return word.length();
	}
	
	public double getFrequency(){
		return frequency;
	}
	
	public LanguageCode getLanguageCode(){
		return lc;
	}
			
	public String getWordInToSyllables(){
		String res = "-";
		for (int i=0;i<syllables.length;i++){
			String tmp = syllables[i].toUpperCase();
			res = res+tmp;
			res = res+"-";
		}
		return res;
	}
	
	public String getWordInToPhonemes(){
		String res = "-";
		return res;
	}
		
	@Override
	public boolean equals(Object x){
		Word w = (Word)x;
		return w.getWord().equalsIgnoreCase(this.word);
	}
	
	@Override
	public String toString(){
		return word;
	}
	
	@Override
	public int hashCode() {
		return word.hashCode();
	}

}

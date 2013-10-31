package ilearnrw.textclassification;

import ilearnrw.user.LanguageCode;

public class Word {
	protected String word;
	protected WordType type;
	protected String[] syllables;
	protected String cvForm;
	protected String phonetics;
	protected int numSyllables;
	protected LanguageCode lc;
	
	public Word(){
	}
			
	public Word(String word){
		this.word = word.toLowerCase();
		//all initializations must go here!!!
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
		return lc == LanguageCode.GR ? syllables.length : numSyllables;
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

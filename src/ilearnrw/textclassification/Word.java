package ilearnrw.textclassification;
import ilearnrw.textclassification.speller.GreekSpeller;
import ilearnrw.textclassification.speller.Speller;

public class Word {
	protected String word;
	protected WordType type;
	protected String[] syllables;
	protected String cvForm;
	
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
		return syllables.length;
	}
	
	public String getCVForm() {
		return cvForm;
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
		GreekWord w = (GreekWord)x;
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

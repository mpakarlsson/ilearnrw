package ilearnrw.languagetools.extras;

import java.util.ArrayList;

public class WordList {
	private ArrayList<String> words;

	public WordList() {
		this.words = new ArrayList<String>();
	}
	
	public WordList(ArrayList<String> words) {
		this.words = words;
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	
	public void add(String w){
		words.add(w);
	}
	
	public String get(int i){
		return words.get(i);
	}
}

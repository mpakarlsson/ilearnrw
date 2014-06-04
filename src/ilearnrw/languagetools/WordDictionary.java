package ilearnrw.languagetools;

import ilearnrw.structs.sets.SortedTreeSet;
import ilearnrw.textclassification.Word;

public class WordDictionary {
	protected SortedTreeSet words;

	public WordDictionary() {
		words = new SortedTreeSet();
	}

	public WordDictionary(SortedTreeSet gw) {
		words = gw;
	}

	public SortedTreeSet getWords() {
		return words;
	}

	public void setWords(SortedTreeSet greekWords) {
		this.words = greekWords;
	}

	public boolean isEmpty(){
		return words.isEmpty();
	}

	public boolean contains(Word w){
		return words.contains(w);
	}
	
	public boolean contains(String w){
		for(Word word : words){
			if(word.getWord().equals(w))
				return true;
		}
		return false;
	}

	public Word get(Word w){
		return words.subSet(w, true, w, true).first();
	}
	
	public Word get(String w){
		for(Word word : words){
			if(word.getWord().equals(w))
				return word;
		}
		
		return null;
	}
	
	//has to   be overridden!
	public void loadWords(){
	}

	public int size(){
		return words.size();
	}

}

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

	public Word get(Word w){
		for (Word gw: words){
			if (gw.equals(w))
				return gw;
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

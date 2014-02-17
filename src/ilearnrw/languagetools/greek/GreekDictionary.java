package ilearnrw.languagetools.greek;

import ilearnrw.structs.sets.SortedTreeSet;
import ilearnrw.textclassification.Word;

public class GreekDictionary {
	SortedTreeSet greekWords;

	public GreekDictionary() {
		greekWords = new SortedTreeSet();
	}

	public GreekDictionary(SortedTreeSet gw) {
		greekWords = gw;
	}

	public SortedTreeSet getWords() {
		return greekWords;
	}

	public void setWords(SortedTreeSet greekWords) {
		this.greekWords = greekWords;
	}

	public boolean isEmpty(){
		return greekWords.isEmpty();
	}

	public boolean contains(Word w){
		return greekWords.contains(w);
	}

	public Word get(Word w){
		for (Word gw: greekWords){
			if (gw.equals(w))
				return gw;
		}
		return null;
	}
	
	public void loadWords(){
		GreekDictionaryLoader gl = new GreekDictionaryLoader(false);
		greekWords = gl.getWords();
	}

	public int size(){
		return greekWords.size();
	}
	
}

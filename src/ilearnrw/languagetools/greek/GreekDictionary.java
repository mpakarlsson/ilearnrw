package ilearnrw.languagetools.greek;

import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.greek.GreekWord;

import java.util.ArrayList;

public class GreekDictionary {
	ArrayList<Word> greekWords;

	public GreekDictionary() {
		greekWords = new ArrayList<Word>();
	}

	public GreekDictionary(ArrayList<Word> gw) {
		greekWords = gw;
	}

	public ArrayList<Word> getWords() {
		return greekWords;
	}

	public void setWords(ArrayList<Word> greekWords) {
		this.greekWords = greekWords;
	}

	public boolean isEmpty(){
		return greekWords.isEmpty();
	}

	public boolean contains(Word w){
		return greekWords.contains(w);
	}

	public Word get(int i){
		return greekWords.get(i);
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

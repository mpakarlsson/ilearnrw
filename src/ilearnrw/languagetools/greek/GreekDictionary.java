package ilearnrw.languagetools.greek;

import ilearnrw.textclassification.greek.GreekWord;

import java.util.ArrayList;

public class GreekDictionary {
	ArrayList<GreekWord> greekWords;

	public GreekDictionary() {
		greekWords = new ArrayList<GreekWord>();
	}

	public GreekDictionary(ArrayList<GreekWord> gw) {
		greekWords = gw;
	}

	public ArrayList<GreekWord> getGreekWords() {
		return greekWords;
	}

	public void setGreekWords(ArrayList<GreekWord> greekWords) {
		this.greekWords = greekWords;
	}

	public boolean isEmpty(){
		return greekWords.isEmpty();
	}

	public boolean contains(GreekWord w){
		return greekWords.contains(w);
	}

	public GreekWord get(int i){
		return greekWords.get(i);
	}

	public GreekWord get(GreekWord w){
		for (GreekWord gw: greekWords){
			if (gw.equals(w))
				return gw;
		}
		return null;
	}

	public int size(){
		return greekWords.size();
	}
	
}

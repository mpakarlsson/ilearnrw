package ilearnrw.textclassification;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.util.ArrayList;

public class WordsList {
	private ArrayList<Word> wordList;

	public WordsList() {
		wordList = new ArrayList<Word>();
	}

	public ArrayList<Word> getWordList() {
		return wordList;
	}

	public void setWordList(ArrayList<Word> wordList) {
		this.wordList = wordList;
	}
	
	public void add(Word w){
		wordList.add(w);
	}
	
	public Word get(int i){
		return wordList.get(i);
	}
	
	public boolean isEmpty(){
		return wordList.isEmpty();
	}
	
	@Override
	public String toString(){
		if (wordList == null || wordList.isEmpty())
			return "Empty";
		else 
			return wordList.toString();
	}

}

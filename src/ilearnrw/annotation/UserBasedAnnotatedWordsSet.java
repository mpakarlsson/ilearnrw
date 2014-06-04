package ilearnrw.annotation;

import ilearnrw.textclassification.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserBasedAnnotatedWordsSet {
	private ArrayList<UserBasedAnnotatedWord> words;
	private Map<Integer, Integer> idCorrespondance;
	
	public UserBasedAnnotatedWordsSet() {
		super();
		this.words = new ArrayList<UserBasedAnnotatedWord>();
		this.idCorrespondance = new HashMap<Integer, Integer>();
	}

	public void setWords(ArrayList<UserBasedAnnotatedWord> words) {
		this.words = words;
	}
	
	public ArrayList<UserBasedAnnotatedWord> getWords() {
		return this.words;
	}
	
	public void addWord(UserBasedAnnotatedWord word, int id) {
		int i;
		for (i=0;i<words.size();i++){
			if (word.equals(words.get(i))){
				idCorrespondance.put(id,i);
				return;
			}
		}
		words.add(word);
		idCorrespondance.put(id, words.size()-1);
	}
	public Integer obj(int id){
		return idCorrespondance.get(id);
	}
	public Map<Integer, Integer> getIdCorrespondance() {
		return idCorrespondance;
	}
	public void setIdCorrespondance(Map<Integer, Integer> idCorrespondance) {
		this.idCorrespondance = idCorrespondance;
	}
	
}

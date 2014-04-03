package ilearnrw.annotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserBasedAnnotatedWordsSet {
	private ArrayList<UserBasedAnnotatedWord> words;
	private Map<Integer, UserBasedAnnotatedWord> idCorrespondance;
	public UserBasedAnnotatedWordsSet() {
		super();
		this.words = new ArrayList<UserBasedAnnotatedWord>();
		this.idCorrespondance = new HashMap<Integer, UserBasedAnnotatedWord>();
	}

	public void setWords(ArrayList<UserBasedAnnotatedWord> words) {
		this.words = words;
	}
	public void addWord(UserBasedAnnotatedWord word, int id) {
		int i;
		for (i=0;i<words.size();i++){
			if (word.equals(words.get(i))){
				idCorrespondance.put(id, words.get(i));
				return;
			}
		}
		words.add(word);
		idCorrespondance.put(id, words.get(words.size()-1));
	}
	public UserBasedAnnotatedWord getWordById(int id){
		return idCorrespondance.get(id);
	}
	public Map<Integer, UserBasedAnnotatedWord> getIdCorrespondance() {
		return idCorrespondance;
	}
	public void setIdCorrespondance(Map<Integer, UserBasedAnnotatedWord> idCorrespondance) {
		this.idCorrespondance = idCorrespondance;
	}
	
}

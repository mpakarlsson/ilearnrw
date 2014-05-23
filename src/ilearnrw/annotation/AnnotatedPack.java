package ilearnrw.annotation;

import ilearnrw.textclassification.Word;

import java.util.ArrayList;
import java.util.List;

public class AnnotatedPack {
	private String html = "";
	private UserBasedAnnotatedWordsSet wordSet;
	private List<Word> trickyWordList;
	
	public AnnotatedPack(String html, UserBasedAnnotatedWordsSet wordSet,
			List<Word> trickyWordList) {
		super();
		this.html = html;
		this.wordSet = wordSet;
		this.trickyWordList = trickyWordList;
	}
	
	public AnnotatedPack() {
		this.html = "";
		this.wordSet = new UserBasedAnnotatedWordsSet();
		this.trickyWordList = new ArrayList<Word>();
	}

	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public UserBasedAnnotatedWordsSet getWordSet() {
		return wordSet;
	}
	public void setWordSet(UserBasedAnnotatedWordsSet wordSet) {
		this.wordSet = wordSet;
	}
	public List<Word> getTrickyWordList() {
		return trickyWordList;
	}
	public void setTrickyWordList(List<Word> trickyWordList) {
		this.trickyWordList = trickyWordList;
	}
	
}

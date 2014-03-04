package ilearnrw.textclassification;

import java.util.ArrayList;

import ilearnrw.utils.LanguageCode;

public class WordProblemInfo {

	private LanguageCode lc;
	private int category, index;
	private ArrayList<StringMatchesInfo> matched;
	private boolean found;
	
	public WordProblemInfo(LanguageCode lc) {
		this.lc = lc;
		this.category = -1;
		this.index = -1;
		this.found = false;
		this.matched = null;
	}
	
	public void setProblemInfo(int posI, int posJ, ArrayList<StringMatchesInfo> smi) {
		if (smi!=null) {
			this.found = true;
			this.category = posI;
			this.index = posJ;
			this.matched = smi;
		}
		else 
			this.found = false;
	}

	public LanguageCode languageCode() {
		return this.lc;
	}

	public int getCategory() {
		return category;
	}

	public void setCategtory(int posI) {
		this.category = posI;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int posJ) {
		this.index = posJ;
	}

	public boolean found() {
		return found;
	}

	public ArrayList<StringMatchesInfo> getMatched() {
	    return matched;
	}

	public void setMatched(ArrayList<StringMatchesInfo> matched) {
	    this.matched = matched;
	}

	@Override
	public String toString() {
		return "WordProblemInfo [problem:(" + category + ", " + index + "), matched=" + matched + "]";
	}
	


}
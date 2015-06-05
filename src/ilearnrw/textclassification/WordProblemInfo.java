package ilearnrw.textclassification;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.util.ArrayList;

public class WordProblemInfo {
	protected int category, index;
	protected ArrayList<StringMatchesInfo> matched;
	protected boolean found;
	
	public WordProblemInfo() {
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
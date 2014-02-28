package ilearnrw.textclassification;

import java.util.ArrayList;

import ilearnrw.utils.LanguageCode;

public class WordProblemInfo {

	private LanguageCode lc;
	private int posI, posJ;
	private ArrayList<StringMatchesInfo> matched;
	private boolean found;
	
	public WordProblemInfo(LanguageCode lc) {
		this.lc = lc;
		this.posI = -1;
		this.posJ = -1;
		this.found = false;
		this.matched = null;
	}
	
	public void setProblemInfo(int posI, int posJ, ArrayList<StringMatchesInfo> smi) {
		if (smi!=null) {
			this.found = true;
			this.posI = posI;
			this.posJ = posJ;
			this.matched = smi;
		}
		else 
			this.found = false;
	}

	public LanguageCode getLanguageCode() {
		return this.lc;
	}

	public int getPosI() {
		return posI;
	}

	public void setposI(int posI) {
		this.posI = posI;
	}

	public int getPosJ() {
		return posJ;
	}

	public void setposJ(int posJ) {
		this.posJ = posJ;
	}

	public boolean getFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public ArrayList<StringMatchesInfo> getMatched() {
	    return matched;
	}

	public void setMatched(ArrayList<StringMatchesInfo> matched) {
	    this.matched = matched;
	}

	@Override
	public String toString() {
		return "WordProblemInfo [problem:(" + posI + ", " + posJ + "), matched=" + matched + "]";
	}
	


}
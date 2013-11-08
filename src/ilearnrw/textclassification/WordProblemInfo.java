package ilearnrw.textclassification;

import ilearnrw.utils.LanguageCode;

public class WordProblemInfo {

	private LanguageCode lc;
	private int posI, posJ;
	private String what; 
	private boolean found; 
	private int start, end;
	
	public WordProblemInfo(LanguageCode lc) {
		this.lc = lc;
		this.posI = -1;
		this.posJ = -1;
		this.what = null;
		this.found = false;
		this.start = -1;
		this.end = -1;
	}
	
	public void setProblemInfo(int posI, int posJ, StringMatchesInfo smi) {
		if (smi!=null && smi.isMatched()) {
			this.found = true;
			this.posI = posI;
			this.posJ = posJ;
			this.what = smi.getWhat();
			this.start = smi.getStart();
			this.end = smi.getEnd();
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

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public boolean getFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "WordProblemInfo [position:(" + posI + ", " + posJ + "), what=" + what + ", found="
				+ found + ", start=" + start + ", end=" + end + "]";
	}
	


}
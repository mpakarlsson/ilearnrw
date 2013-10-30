package ilearnrw.textclassification;

import ilearnrw.user.problems.ProblemDefinition;

public class WordProblemInfo {

	private ProblemDefinition type;
	private String what; 
	private boolean found; 
	private int start, end, severity, workingIndex, problemIndex, indexLength;
	
	public WordProblemInfo() {
		this.type = null;
		this.what = null;
		this.found = false;
		this.start = -1;
		this.end = -1;
		this.severity = -1;
		this.workingIndex = -1;
		this.problemIndex = -1;
		this.indexLength = -1;
	}
	
	public void setProblemInfo(ProblemDefinition type, StringMatchesInfo smi, 
			int severity, int workingIndex, int problemIndex, int indexLength) {
		if (smi!=null && smi.isMatched()) {
			this.found = true;
			this.type = type;
			this.what = smi.getWhat();
			this.start = smi.getStart();
			this.end = smi.getEnd();
			this.severity = severity;
			this.workingIndex = workingIndex;
			this.problemIndex = problemIndex;
			this.indexLength = indexLength;
		}
		else 
			this.found = false;
	}

	public ProblemDefinition getType() {
		return type;
	}

	public void setType(ProblemDefinition type) {
		this.type = type;
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

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public int getWorkingIndex() {
		return workingIndex;
	}

	public void setWorkingIndex(int workingIndex) {
		this.workingIndex = workingIndex;
	}

	public int getProblemIndex() {
		return problemIndex;
	}

	public void setProblemIndex(int problemIndex) {
		this.problemIndex = problemIndex;
	}

	public int getIndexLength() {
		return indexLength;
	}

	public void setIndexLength(int indexLength) {
		this.indexLength = indexLength;
	}

	@Override
	public String toString() {
		return "WordProblemInfo [type=" + type + ", what=" + what + ", found="
				+ found + ", start=" + start + ", end=" + end + ", severity="
				+ severity + ", workingIndex=" + workingIndex
				+ ", problemIndex=" + problemIndex + ", indexLength="
				+ indexLength + "]";
	}
	


}

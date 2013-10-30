package ilearnrw.textclassification;

import ilearnrw.user.problems.ProblemDefinition;

public class WordProblemInfo {

	private ProblemDefinition type;
	private String what; 
	private boolean found; 
	private int start, end, severity, workingIndex, problemIndex, indexLength;
	
	public WordProblemInfo(ProblemDefinition type, String what, boolean found, int start,
			int end, int severity, int workingIndex, int problemIndex,
			int indexLength) {
		this.type = type;
		this.what = what;
		this.found = found;
		this.start = start;
		this.end = end;
		this.severity = severity;
		this.workingIndex = workingIndex;
		this.problemIndex = problemIndex;
		this.indexLength = indexLength;
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

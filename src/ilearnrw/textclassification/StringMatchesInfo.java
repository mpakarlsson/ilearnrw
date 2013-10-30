package ilearnrw.textclassification;

public class StringMatchesInfo {

	private String what;
	private int start, end;
	
	public StringMatchesInfo() {
		what = null;
		start = -1;
		end = -1;
	}

	public StringMatchesInfo(String what, int start, int end) {
		this.what = what;
		this.start = start;
		this.end = end;
	}

	public boolean isMatched(){
		return what != null && !what.isEmpty();
	}
	
	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
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
		return "StringMatchesInfo [what=" + what + ", start=" + start
				+ ", end=" + end + "]";
	}
	
	
	

}

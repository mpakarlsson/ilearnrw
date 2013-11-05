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

	
	public StringMatchesInfo equals(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.equals(str[i])){
				return new StringMatchesInfo(str[i], 0, ws.length());
			}
		}
		return null;
	}
	
	public StringMatchesInfo contains(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}
	
	public StringMatchesInfo endsWith(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.endsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.lastIndexOf(str[i]), ws.lastIndexOf(str[i])+str[i].length());
			}
		}
		return null;
	}
	//or
	public StringMatchesInfo startsWith(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo containsPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo hasInternalPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i]) && !ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo startsWithPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo containsPattern(String str[], Word w){
		String ws = w.getCVForm();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}
	
	

}

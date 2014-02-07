package ilearnrw.textclassification;

public class GraphemePhonemePair {
	private String grapheme, phoneme;

	public GraphemePhonemePair(String grapheme, String phoneme) {
		this.grapheme = grapheme;
		this.phoneme = phoneme;
	}

	public GraphemePhonemePair() {
		this.grapheme = null;
		this.phoneme = null;
	}

	public String getGrapheme() {
		return grapheme;
	}

	public void setGrapheme(String grapheme) {
		this.grapheme = grapheme;
	}

	public String getPhoneme() {
		return phoneme;
	}

	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
	}

	@Override
	public String toString() {
		return "[ grapheme = "+grapheme+", phoneme = "+phoneme+" ]";
	}
	
}

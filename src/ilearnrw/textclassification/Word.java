package ilearnrw.textclassification;

import java.io.Serializable;
import java.util.ArrayList;

import ilearnrw.utils.LanguageCode;

public class Word implements Serializable, Comparable {
	protected String word, wordUnmodified;
	protected WordType type;
	protected String[] syllables;
	protected String cvForm;
	protected String phonetics;
	protected String stem;
	protected int numSyllables;
	protected LanguageCode languageCode;
	protected double frequency;
	protected ArrayList<GraphemePhonemePair> graphemesPhonemes;

	// @JsonIgnore
	public Word() {
	}

	public Word(String word) {
		word = word
				.replaceAll(
						"(\\«)|(\\*)|(\\»)|(\\()|(\\))|(\\{)|(\\})|(\\[)|(\\])|(\\<)|(\\>)|(\\=)|(\\%)|(\\€)|(\\$)",
						"");
		this.wordUnmodified = word.trim();
		this.word = word.toLowerCase().trim();
		// all initializations must go here!!!

		numSyllables = 0;
		frequency = 0;
		type = WordType.Unknown;
		cvForm = "";
		phonetics = "";
		stem = "";
		languageCode = null;
		graphemesPhonemes = null;
	}

	public Word(String word, WordType wt) {
		word = word
				.replaceAll(
						"(\\«)|(\\*)|(\\»)|(\\()|(\\))|(\\{)|(\\})|(\\[)|(\\])|(\\<)|(\\>)|(\\=)|(\\%)|(\\€)|(\\$)",
						"");
		this.wordUnmodified = word.trim();
		this.word = word.toLowerCase();
		// all initializations must go here!!!

		numSyllables = 0;
		frequency = 0;
		type = wt;
		cvForm = "";
		phonetics = "";
		stem = "";
		languageCode = null;
		graphemesPhonemes = new ArrayList<GraphemePhonemePair>();
	}

	public String getWord() {
		return word;
	}

	public String getWordUnmodified() {
		return wordUnmodified;
	}

	public void setType(WordType x) {
		this.type = x;
	}

	public WordType getType() {
		return type;
	}

	public String[] getSyllables() {
		return syllables;
	}

	public int getNumberOfSyllables() {
		return numSyllables;
	}

	public String getCVForm() {
		return cvForm;
	}
    
	public String getStem() {
		return stem;
	}
	
	public void setStem(String stem) {
		this.stem = stem;
	}

	public String getPhonetics() {
		return phonetics;
	}

	public int getLength() {
		return word.length();
	}

	public double getFrequency() {
		return frequency;
	}

	public LanguageCode getLanguageCode() {
		return languageCode;
	}

	public ArrayList<GraphemePhonemePair> getGraphemesPhonemes() {
		return graphemesPhonemes;
	}

	public String getWordInToSyllables() {
		if(syllables == null)
			syllables = new String[]{""};
		
		String res = "-";
		for (int i = 0; i < syllables.length; i++) {
			String tmp = syllables[i].toUpperCase();
			res = res + tmp;
			res = res + "-";
		}
		return res;
	}

	@Override
	public boolean equals(Object x) {
		Word w = (Word) x;
		return w.getWord().equalsIgnoreCase(this.word);
	}

	@Override
	public String toString() {
		return word;
	}

	@Override
	public int hashCode() {
		return word.hashCode();
	}

	private String eliminateStartingSymbols() {
		int i = 0;
		while (i < word.length()
				&& (word.charAt(i) == '\'' || word.charAt(i) == '`' || word
						.charAt(i) == '΄')) {
			i++;
		}
		return word.substring(i);
	}

	@Override
	public int compareTo(Object o) {
		Word w = (Word) o;
		// String theWord = w.getWord();
		return (this.eliminateStartingSymbols()).compareTo(w
				.eliminateStartingSymbols());
	}
}

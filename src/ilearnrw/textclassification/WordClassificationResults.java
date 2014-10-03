package ilearnrw.textclassification;

import ilearnrw.user.UserTextCounters;
import ilearnrw.utils.LanguageCode;

public class WordClassificationResults{
	//word
	private String word, wordInSyllables;
	private WordType type;
	private String[] syllables;
	private String cvForm;
	private String phonetics;
	private int numberOfSyllables, length;
	private LanguageCode lc;
	private double frequency;
	
	//user problems to word
	private WordVsProblems wprobs;
	private UserTextCounters userWordCounters;
	private boolean isDifficult, isVeryDifficult;
	private int totalHits, userHits, Wscore, wordRank;

	public WordClassificationResults() {
		// TODO Auto-generated constructor stub
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public WordType getType() {
		return type;
	}

	public void setType(WordType type) {
		this.type = type;
	}
	
	public String getWordInSyllables() {
		return wordInSyllables;
	}

	public void setWordInSyllables(String wordInSyllables) {
		this.wordInSyllables = wordInSyllables;
	}

	public String[] getSyllables() {
		return syllables;
	}

	public void setSyllables(String[] syllables) {
		this.syllables = syllables;
	}

	public String getCvForm() {
		return cvForm;
	}

	public void setCvForm(String cvForm) {
		this.cvForm = cvForm;
	}

	public String getPhonetics() {
		return phonetics;
	}

	public void setPhonetics(String phonetics) {
		this.phonetics = phonetics;
	}

	public int getNumberOfSyllables() {
		return numberOfSyllables;
	}

	public void setNumberOfSyllables(int numberOfSyllables) {
		this.numberOfSyllables = numberOfSyllables;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public LanguageCode getLanguageCode() {
		return lc;
	}

	public void setLanguageCode(LanguageCode lc) {
		this.lc = lc;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public WordVsProblems getWprobs() {
		return wprobs;
	}

	public void setWprobs(WordVsProblems wprobs) {
		this.wprobs = wprobs;
	}

	public UserTextCounters getUserCounters() {
		return userWordCounters;
	}

	public void setUserCounters(UserTextCounters userTextCounters) {
		this.userWordCounters = userTextCounters;
	}

	public boolean isDifficult() {
		return isDifficult;
	}

	public void setDifficult(boolean isDifficult) {
		this.isDifficult = isDifficult;
	}

	public boolean isVeryDifficult() {
		return isVeryDifficult;
	}

	public void setVeryDifficult(boolean isVeryDifficult) {
		this.isVeryDifficult = isVeryDifficult;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public int getUserHits() {
		return userHits;
	}

	public void setUserHits(int userHits) {
		this.userHits = userHits;
	}

	public int getWscore() {
		return Wscore;
	}

	public void setWscore(int wscore) {
		Wscore = wscore;
	}

	public int getWordRank() {
		return wordRank;
	}

	public void setWordRank(int wordRank) {
		this.wordRank = wordRank;
	}

}

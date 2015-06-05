/**
 * @author Chris Litsas 
 * 16 Δεκ 2013 4:27:05 μ.μ.
**/
package ilearnrw.textclassification;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.UserTextCounters;
import ilearnrw.utils.LanguageCode;

import java.util.HashMap;

public class TextClassificationResults {

	//user problems to text
	private double SDW, Tscore;
	private int totalHits, userHits, diffWords, veryDiffWords;
	private ProblematicWords problematicWords;
	private UserTextCounters userCounters;
	private HashMap<Word, Integer> wordScores;
	
	//text
	private int numberOfTotalWords, numberOfDistinctWords, numberOfSentences, numberOfSyllables, numberOfBigSentences,
		longestWordLength, longestSentenceLength, numberOfPolysyllabicWords, numberOfLettersAndNumbers, numberOfParagraphs;
	private double averageWordLength, averageLongestWordLength, averageWordsPerSentence, averageSyllablesPerWord, averageWordRank;
	private double flesch, fleschKincaid, automated, colemanLiau, smog, gunningFog, daleChall;
	private LanguageCode lc;
	private HashMap<Word, Integer> wordsFreq;	
	
	public TextClassificationResults(){
	}
	
	/*
	public ClassificationResults(UserProfile userProfile, Text text, LanguageAnalyzerAPI languageAnalyzer) {
		//user problems to text
		UserProblemsToText userProblemsToText = new UserProblemsToText(userProfile, text, languageAnalyzer);
		SDW = userProblemsToText.getSDW();
		Tscore = userProblemsToText.getTscore();
		totalHits = userProblemsToText.getTotalHits();
		userHits = userProblemsToText.getUserHits();
		diffWords = userProblemsToText.getDiffWords();
		veryDiffWords = userProblemsToText.getVeryDiffWords();
		problematicWords = userProblemsToText.getProblematicWords();
		userCounters = userProblemsToText.getUserCounters();
		wordScores = userProblemsToText.getWordScores();
		
		//text
		numberOfTotalWords = text.getNumberOfWords();
		numberOfDistinctWords = text.getNumberOfDistinctWords();
		numberOfSentences = text.getNumberOfSentences();
		numberOfSyllables = text.getNumberOfSyllables();
		numberOfBigSentences = text.getNumberOfBigSentences();
		longestWordLength = text.getLongestWordLength();
		longestSentenceLength = text.getLongestSentenceLength();
		numberOfPolysyllabicWords = text.getNumberOfPolysyllabicWords();
		numberOfLettersAndNumbers = text.getNumberOfLettersAndNumbers();
		numberOfParagraphs = text.getNumberOfParagraphs();
		averageWordLength = text.getAverageWordLength();
		averageLongestWordLength = text.getAverageLongestWordLength();
		averageWordsPerSentence = text.getWordsPerSentence();
		averageSyllablesPerWord = text.getSyllablesPerWord();
		flesch = text.flesch();
		fleschKincaid = text.fleschKincaid();
		automated = text.automated();
		colemanLiau = text.colemanLiau();
		smog = text.smog();
		gunningFog = text.gunningFog();
		daleChall = text.daleChall();
		lc = text.getLanguageCode();
		wordsFreq = text.getWordsFreq();
	}*/
	
	public double getSDW() {
		return SDW;
	}

	public void setSDW(double sDW) {
		SDW = sDW;
	}

	public double getTscore() {
		return Tscore;
	}

	public void setTscore(double tscore) {
		Tscore = tscore;
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

	public int getDiffWords() {
		return diffWords;
	}

	public void setDiffWords(int diffWords) {
		this.diffWords = diffWords;
	}

	public int getVeryDiffWords() {
		return veryDiffWords;
	}

	public void setVeryDiffWords(int veryDiffWords) {
		this.veryDiffWords = veryDiffWords;
	}

	public ProblematicWords getProblematicWords() {
		return problematicWords;
	}

	public void setProblematicWords(ProblematicWords problematicWords) {
		this.problematicWords = problematicWords;
	}

	public UserTextCounters getUserCounters() {
		return userCounters;
	}

	public void setUserCounters(UserTextCounters userCounters) {
		this.userCounters = userCounters;
	}

	public HashMap<Word, Integer> getWordScores() {
		return wordScores;
	}

	public void setWordScores(HashMap<Word, Integer> wordScores) {
		this.wordScores = wordScores;
	}

	public int getNumberOfTotalWords() {
		return numberOfTotalWords;
	}

	public void setNumberOfTotalWords(int numberOfTotalWords) {
		this.numberOfTotalWords = numberOfTotalWords;
	}

	public int getNumberOfDistinctWords() {
		return numberOfDistinctWords;
	}

	public void setNumberOfDistinctWords(int numberOfDistinctWords) {
		this.numberOfDistinctWords = numberOfDistinctWords;
	}

	public int getNumberOfSentences() {
		return numberOfSentences;
	}

	public void setNumberOfSentences(int numberOfSentences) {
		this.numberOfSentences = numberOfSentences;
	}

	public int getNumberOfSyllables() {
		return numberOfSyllables;
	}

	public void setNumberOfSyllables(int numberOfSyllables) {
		this.numberOfSyllables = numberOfSyllables;
	}

	public int getNumberOfBigSentences() {
		return numberOfBigSentences;
	}

	public void setNumberOfBigSentences(int numberOfBigSentences) {
		this.numberOfBigSentences = numberOfBigSentences;
	}

	public int getLongestWordLength() {
		return longestWordLength;
	}

	public void setLongestWordLength(int longestWordLength) {
		this.longestWordLength = longestWordLength;
	}

	public int getLongestSentenceLength() {
		return longestSentenceLength;
	}

	public void setLongestSentenceLength(int longestSentenceLength) {
		this.longestSentenceLength = longestSentenceLength;
	}

	public int getNumberOfPolysyllabicWords() {
		return numberOfPolysyllabicWords;
	}

	public void setNumberOfPolysyllabicWords(int numberOfPolysyllabicWords) {
		this.numberOfPolysyllabicWords = numberOfPolysyllabicWords;
	}

	public int getNumberOfLettersAndNumbers() {
		return numberOfLettersAndNumbers;
	}

	public void setNumberOfLettersAndNumbers(int numberOfLettersAndNumbers) {
		this.numberOfLettersAndNumbers = numberOfLettersAndNumbers;
	}

	public int getNumberOfParagraphs() {
		return numberOfParagraphs;
	}

	public void setNumberOfParagraphs(int numberOfParagraphs) {
		this.numberOfParagraphs = numberOfParagraphs;
	}

	public double getAverageWordLength() {
		return averageWordLength;
	}

	public void setAverageWordLength(double averageWordLength) {
		this.averageWordLength = averageWordLength;
	}

	public double getAverageLongestWordLength() {
		return averageLongestWordLength;
	}

	public void setAverageLongestWordLength(double averageLongestWordLength) {
		this.averageLongestWordLength = averageLongestWordLength;
	}

	public double getAverageWordsPerSentence() {
		return averageWordsPerSentence;
	}

	public void setAverageWordsPerSentence(double averageWordsPerSentence) {
		this.averageWordsPerSentence = averageWordsPerSentence;
	}

	public double getAverageSyllablesPerWord() {
		return averageSyllablesPerWord;
	}

	public void setAverageSyllablesPerWord(double averageSyllablesPerWord) {
		this.averageSyllablesPerWord = averageSyllablesPerWord;
	}

	public double getAverageWordRank() {
		return averageWordRank;
	}

	public void setAverageWordRank(double averageWordRank) {
		this.averageWordRank = averageWordRank;
	}

	public double getFlesch() {
		return flesch;
	}

	public void setFlesch(double flesch) {
		this.flesch = flesch;
	}

	public double getFleschKincaid() {
		return fleschKincaid;
	}

	public void setFleschKincaid(double fleschKincaid) {
		this.fleschKincaid = fleschKincaid;
	}

	public double getAutomated() {
		return automated;
	}

	public void setAutomated(double automated) {
		this.automated = automated;
	}

	public double getColemanLiau() {
		return colemanLiau;
	}

	public void setColemanLiau(double colemanLiau) {
		this.colemanLiau = colemanLiau;
	}

	public double getSmog() {
		return smog;
	}

	public void setSmog(double smog) {
		this.smog = smog;
	}
	
	public double getGunningFog() {
		return gunningFog;
	}

	public void setGunningFog(double gunningFog) {
		this.gunningFog = gunningFog;
	}

	public double getDaleChall() {
		return daleChall;
	}

	public void setDaleChall(double daleChall) {
		this.daleChall = daleChall;
	}

	public LanguageCode getLc() {
		return lc;
	}

	public void setLc(LanguageCode lc) {
		this.lc = lc;
	}

	public HashMap<Word, Integer> getWordsFreq() {
		return wordsFreq;
	}

	public void setWordsFreq(HashMap<Word, Integer> wordsFreq) {
		this.wordsFreq = wordsFreq;
	}
	

}

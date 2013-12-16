/**
 * @author Chris Litsas 
 * 16 Δεκ 2013 4:27:05 μ.μ.
**/
package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.user.User;
import ilearnrw.user.UserTextCounters;
import ilearnrw.utils.LanguageCode;

import java.util.HashMap;

public class ClassificationResults {

	//user problems to text
	private HashMap<Word, Integer> wordsScores;
	private double SDW, Tscore;
	private int totalHits, userHits, diffWords, veryDiffWords;
	private ProblematicWords problematicWords;
	private UserTextCounters userCounters;
	private HashMap<Word, Integer> wordScores;
	
	//text
	private int numberOfTotalWords, numberOfDistinctWords, numberOfSentences, numberOfSyllables, numberOfBigSentences,
		longestWordLength, longestSentenceLength, numberOfPolysyllabicWords, numberOfLettersAndNumbers, numberOfParagraphs;
	private double averageWordLength, averageLongestWordLength, averageWordsPerSentence, averageSyllablesPerWord;
	private double flesch, fleschKincaid, automated, colemanLiau, smog, gunningFog, daleChall;
	private LanguageCode lc;
	private HashMap<Word, Integer> wordsFreq;	
	
	public ClassificationResults(User user, Text text, LanguageAnalyzerAPI languageAnalyzer) {
		//user problems to text
		UserProblemsToText userProblemsToText = new UserProblemsToText(user, text, languageAnalyzer);
		wordsScores = userProblemsToText.getWordScores();
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
	}
	
	/**
	 * @return the wordsFreq
	 */
	public HashMap<Word, Integer> getWordsFreq() {
		return wordsFreq;
	}
	/**
	 * @return the problematicWords
	 */
	public ProblematicWords getProblematicWords() {
		return problematicWords;
	}
	/**
	 * @return the userCounters
	 */
	public UserTextCounters getUserCounters() {
		return userCounters;
	}
	/**
	 * @return the wordScores
	 */
	public HashMap<Word, Integer> getWordScores() {
		return wordScores;
	}
	/**
	 * @return the lc
	 */
	public LanguageCode getLc() {
		return lc;
	}
	/**
	 * @return the numberOfTotalWords
	 */
	public int getNumberOfTotalWords() {
		return numberOfTotalWords;
	}
	/**
	 * @return the numberOfDistinctWords
	 */
	public int getNumberOfDistinctWords() {
		return numberOfDistinctWords;
	}
	/**
	 * @return the numberOfSentences
	 */
	public int getNumberOfSentences() {
		return numberOfSentences;
	}
	/**
	 * @return the numberOfSyllables
	 */
	public int getNumberOfSyllables() {
		return numberOfSyllables;
	}
	/**
	 * @return the numberOfBigSentences
	 */
	public int getNumberOfBigSentences() {
		return numberOfBigSentences;
	}
	/**
	 * @return the longestWordLength
	 */
	public int getLongestWordLength() {
		return longestWordLength;
	}
	/**
	 * @return the longestSentenceLength
	 */
	public int getLongestSentenceLength() {
		return longestSentenceLength;
	}
	/**
	 * @return the numberOfPolysyllabicWords
	 */
	public int getNumberOfPolysyllabicWords() {
		return numberOfPolysyllabicWords;
	}
	/**
	 * @return the numberOfLettersAndNumbers
	 */
	public int getNumberOfLettersAndNumbers() {
		return numberOfLettersAndNumbers;
	}
	/**
	 * @return the numberOfParagraphs
	 */
	public int getNumberOfParagraphs() {
		return numberOfParagraphs;
	}
	/**
	 * @return the averageWordLength
	 */
	public double getAverageWordLength() {
		return averageWordLength;
	}
	/**
	 * @return the averageLongestWordLength
	 */
	public double getAverageLongestWordLength() {
		return averageLongestWordLength;
	}
	/**
	 * @return the averageWordsPerSentence
	 */
	public double getAverageWordsPerSentence() {
		return averageWordsPerSentence;
	}
	/**
	 * @return the averageSyllablesPerWord
	 */
	public double getAverageSyllablesPerWord() {
		return averageSyllablesPerWord;
	}
	/**
	 * @return the flesch
	 */
	public double getFlesch() {
		return flesch;
	}
	/**
	 * @return the fleschKincaid
	 */
	public double getFleschKincaid() {
		return fleschKincaid;
	}
	/**
	 * @return the automated
	 */
	public double getAutomated() {
		return automated;
	}
	/**
	 * @return the colemanLiau
	 */
	public double getColemanLiau() {
		return colemanLiau;
	}
	/**
	 * @return the smog
	 */
	public double getSmog() {
		return smog;
	}
	/**
	 * @return the gunningFog
	 */
	public double getGunningFog() {
		return gunningFog;
	}
	/**
	 * @return the daleChall
	 */
	public double getDaleChall() {
		return daleChall;
	}
	/**
	 * @return the wordsWeights
	 */
	public HashMap<Word, Integer> getWordsScores() {
		return wordsScores;
	}
	/**
	 * @return the sDW
	 */
	public double getSDW() {
		return SDW;
	}
	/**
	 * @return the tscore
	 */
	public double getTscore() {
		return Tscore;
	}
	/**
	 * @return the totalHits
	 */
	public int getTotalHits() {
		return totalHits;
	}
	/**
	 * @return the userHits
	 */
	public int getUserHits() {
		return userHits;
	}
	/**
	 * @return the diffWords
	 */
	public int getDiffWords() {
		return diffWords;
	}
	/**
	 * @return the veryDiffWords
	 */
	public int getVeryDiffWords() {
		return veryDiffWords;
	}
	

}

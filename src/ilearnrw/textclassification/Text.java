package ilearnrw.textclassification;
import ilearnrw.user.LanguageCode;

import java.util.HashMap;

public class Text implements TextAPI{
	private String text;
	private Sentence sentences[];
	private HashMap<Word, Integer> wordsFreq;
	private LanguageCode lc;

	private int numberOfTotalWords, numberOfDistinctWords, numberOfSentences, numberOfSyllables, numberOfBigSentences,
		longestWordLength, longestSentenceLength, numberOfPolysyllabicWords, numberOfLettersAndNumbers;
	private double averageWordLength, averageLongestWordLength;
	
	public Text(String text, LanguageCode lc){
		this.lc = lc;
		this.text = text;
		splitSentences();
		wordsFreq = new HashMap<Word, Integer>();
		metrics();
	}

	public int getNumberOfSentences(){
		return numberOfSentences;
	}

	public Sentence[] getSentences(){
		return sentences;
	}

	public int getNumberOfWords(){
		return numberOfTotalWords;
	}

	public int getNumberOfDistinctWords(){
		return numberOfDistinctWords;
	}

	public int getNumberOfSyllables(){
		return numberOfSyllables;
	}

	public int getNumberOfBigSentences(){
		return numberOfBigSentences;
	}

	public int getLongestWordLength(){
		return longestWordLength;
	}

	public int getLongestSentenceLength(){
		return longestSentenceLength;
	}

	public int getNumberOfPolysyllabicWords(){
		return numberOfPolysyllabicWords;
	}

	public int getNumberOfLettersAndNumbers(){
		return numberOfLettersAndNumbers;
	}

	public double getWordsPerSentence(){
		return (double)numberOfTotalWords/numberOfSentences;
	}

	public double getSyllablesPerWord(){
		return (double)numberOfSyllables/numberOfTotalWords;
	}

	public double getAverageWordLength(){
		return averageWordLength;
	}

	public double getAverageLongestWordLength(){
		return averageLongestWordLength;
	}
	
	private void splitSentences(){
		//question: ´εχω αλλαγή πρότασης στα (...) και (:) ?
		String tmp[] = text.trim().split("((\\.)*(\\.)\\s)|((\\!)*(\\!)\\s)|((\\;)*(\\;)\\s)|((\\?)*(\\?)\\s)");
		sentences = new Sentence[tmp.length];
		for (int i=0;i<tmp.length; i++){
			sentences[i] = new Sentence(tmp[i], lc);
		}
	}
	
	private void metrics(){
		numberOfTotalWords = 0;
		numberOfDistinctWords = 0;
		numberOfSyllables = 0;
		numberOfSentences = sentences.length;
		longestWordLength = 0;
		longestSentenceLength = 0;
		numberOfLettersAndNumbers = 0;
		averageWordLength = 0;
		averageLongestWordLength = 0;
		numberOfPolysyllabicWords = 0;
		numberOfBigSentences = 0;
		
		for (int i=0; i<numberOfSentences; i++){
			if (sentences[i].getNumberOfWords()>longestSentenceLength)
				longestSentenceLength = sentences[i].getNumberOfWords();
			
			Word words[] = sentences[i].getWords();
			numberOfTotalWords += words.length;
			numberOfSyllables += sentences[i].getNumberOfSyllables();
			numberOfPolysyllabicWords += sentences[i].getNumberOfPolysyllabicWords();
			
			averageLongestWordLength += sentences[i].getLongestWordLength();
			
			if (sentences[i].getLongestWordLength()>longestWordLength)
				longestWordLength = sentences[i].getLongestWordLength();
			
			if (sentences[i].getNumberOfWords() >= 15)
				numberOfBigSentences++;
			
			
			for (int j=0;j<words.length; j++){
				numberOfLettersAndNumbers += words[j].getLength();
				if (wordsFreq.containsKey(words[j])){
					wordsFreq.put(words[j], wordsFreq.get(words[j]) + 1);
				}
				else {
					numberOfDistinctWords++;
					wordsFreq.put(words[j], 1);
				}
			}
		}
		averageWordLength = (double)numberOfLettersAndNumbers/numberOfTotalWords;
		averageLongestWordLength = averageLongestWordLength/numberOfSentences;
	}

	@Override
	public double flesch() {
		// Flesch reading ease score
		double fres = 206.835 - (1.015 * numberOfTotalWords) / numberOfSentences - 
				(84.6 * numberOfSyllables) / numberOfTotalWords;
		return fres;
	}

	@Override
	public double fleschKincaid() {
		// Flesch-Kincaid grade level
		double fkgl = (0.39 * numberOfTotalWords) / numberOfSentences + 
				(11.8 * numberOfSyllables) / numberOfTotalWords - 15.59;
		return fkgl;
	}

	@Override
	public double automated() {
		// Automated readability index
		double ari = (4.71 * numberOfLettersAndNumbers) / numberOfTotalWords + 
				(0.5 * numberOfTotalWords) / numberOfSentences -21.43;
		return ari;
	}

	@Override
	public double colemanLiau() {
		// Coleman-Liau index
		double cl = (5.89 * numberOfLettersAndNumbers) / numberOfTotalWords - 
				(30.0 * numberOfSentences) / numberOfTotalWords - 15.8;
		return cl;
	}

	@Override
	public double smog() {
		// SMOG index
		double smog = Math.sqrt( numberOfPolysyllabicWords * 30.0 / numberOfSentences ) + 3.0;
		return smog;
	}

	@Override
	public double gunningFog() {
		// Gunning fog index
		double fog = 0.4 * ( (double)numberOfTotalWords / numberOfSentences + 
				(100.0 * numberOfPolysyllabicWords) / numberOfTotalWords );
		return fog;
	}
	
}

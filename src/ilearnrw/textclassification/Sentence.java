package ilearnrw.textclassification;

import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.utils.LanguageCode;

public class Sentence {
	private String sentence;
	private Word[] words;
	private int numberOfSyllables, longestWordLength, numberOfPolysyllabicWords, numberOfLettersAndNumbers;
	private double averageWordLength;
	private LanguageCode lc;

	public Sentence(String sentence, LanguageCode lc){
		this.lc = lc;
		this.sentence = sentence;
		splitWords();
		metrics();
	}
	
	public Word getWord(int i){
		return words[i];
	}
	
	public Word[] getWords(){
		return words;
	}
	
	public int getNumberOfWords(){
		return words.length;
	}
	
	public String getSentence(){
		return sentence;
	}

	public int getNumberOfSyllables(){
		return numberOfSyllables;
	}

	public int getNumberOfPolysyllabicWords(){
		return numberOfPolysyllabicWords;
	}

	public int getLongestWordLength(){
		return longestWordLength;
	}

	public int getNumberOfLettersAndNumbers(){
		return numberOfLettersAndNumbers;
	}

	public double getAverageWordLength(){
		return averageWordLength;
	}
	
	private void splitWords(){
		String theWords[] = sentence.split("[(\\s+)(\\.)(\\!)(\\,)(\\;)(\\-)(\\:)(\\?)(\\\")]");
		//String theWords[] = sentence.split("[(\\s+)(\\.)(\\')(\\!)(\\,)(\\;)(\\-)(\\:)(\\?)(\\\")]");
		String tmp;
		int size = 0;
		for (int i=0;i<theWords.length; i++){
			if (!theWords[i].trim().equals("")) size++;
		}
		if (lc == LanguageCode.GR){
			words = new GreekWord[size];
			int j = 0;
			for (int i=0;i<theWords.length;i++){
				if (theWords[i].trim().equals("")) continue;
				words[j++] = new GreekWord(theWords[i]);
			}
		}
		else {
			EnglishLanguageAnalyzer ea = new EnglishLanguageAnalyzer();
			words = new EnglishWord[size];
			int j = 0;
			for (int i=0;i<theWords.length;i++){
				if (theWords[i].trim().equals("")) continue;
				
				if(EnglishLanguageAnalyzer.dictionary.getDictionary().containsKey(theWords[i].toLowerCase())){
					words[j++] = EnglishLanguageAnalyzer.dictionary.getDictionary().get(theWords[i].toLowerCase());
				} else 
					words[j++] = new EnglishWord(theWords[i]);
			}
		}
	}
	
	private void metrics(){
		numberOfSyllables = 0;
		longestWordLength = 0;
		averageWordLength = 0;
		numberOfLettersAndNumbers = 0;
		numberOfPolysyllabicWords = 0;
		for (int i=0;i<words.length;i++){
			if (words[i].getLength() > longestWordLength) 
				longestWordLength = words[i].getLength();
			
			if(lc == LanguageCode.GR){
				if (words[i].getSyllables().length >= 3) 
					numberOfPolysyllabicWords++;
			} else if(lc == LanguageCode.EN) {
				if(words[i].getNumberOfSyllables() >= 3)
					numberOfPolysyllabicWords++;
			}
			numberOfSyllables += words[i].getNumberOfSyllables();
			numberOfLettersAndNumbers += words[i].getLength();
		}
		averageWordLength = (double)numberOfLettersAndNumbers/getNumberOfWords();
	}
	
	@Override
	public String toString(){
		return sentence;
	}
}

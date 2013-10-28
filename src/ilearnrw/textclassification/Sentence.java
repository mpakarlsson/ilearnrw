package ilearnrw.textclassification;
public class Sentence {
	protected String sentence;
	protected Word[] words;
	protected int numberOfSyllables, longestWordLength;
	protected double averageWordLength;

	public Sentence(String sentence){
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

	public int getLongestWordLength(){
		return longestWordLength;
	}

	public double getAverageWordLength(){
		return averageWordLength;
	}
	
	protected void splitWords(){
		String theWords[] = sentence.split("[(\\s+)(\\.)(\\')(\\!)(\\,)(\\;)(\\-)(\\:)(\\?)(\\\")]");
		String tmp;
		int size = 0;
		for (int i=0;i<theWords.length; i++){
			if (!theWords[i].trim().equals("")) size++;
		}
		words = new GreekWord[size];
		int j = 0;
		for (int i=0;i<theWords.length;i++){
			if (theWords[i].trim().equals("")) continue;
			words[j++] = new GreekWord(theWords[i]);
		}
	}
	
	protected void metrics(){
		numberOfSyllables = 0;
		longestWordLength = 0;
		averageWordLength = 0;
		for (int i=0;i<words.length;i++){
			if (words[i].getLength() > longestWordLength) 
				longestWordLength = words[i].getLength();
			numberOfSyllables += words[i].getNumberOfSyllables();
			averageWordLength += words[i].getLength();
		}
		averageWordLength = averageWordLength/getNumberOfWords();
	}
	
	@Override
	public String toString(){
		return sentence;
	}
}

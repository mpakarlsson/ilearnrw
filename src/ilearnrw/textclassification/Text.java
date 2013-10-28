package ilearnrw.textclassification;
import java.util.HashMap;

public class Text {
	protected String text;
	protected Sentence sentences[];
	protected HashMap<Word, Integer> wordsFreq;

	protected int numberOfTotalWords, numberOfDistinctWords, numberOfSentences, numberOfSyllables, 
		longestWordLength, longestSentenceLength;
	protected double averageWordLength;
	
	public Text(String text){
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

	public int getLongestWordLength(){
		return longestWordLength;
	}

	public int getLongestSentenceLength(){
		return longestSentenceLength;
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
	
	protected void splitSentences(){
		//question: ´εχω αλλαγή πρότασης στα (...) και (:) ?
		String tmp[] = text.trim().split("((\\.)*(\\.)\\s)|((\\!)*(\\!)\\s)|((\\;)*(\\;)\\s)|((\\?)*(\\?)\\s)");
		sentences = new Sentence[tmp.length];
		for (int i=0;i<tmp.length; i++){
			sentences[i] = new Sentence(tmp[i]);
		}
	}
	
	protected void metrics(){
		numberOfTotalWords = 0;
		numberOfDistinctWords = 0;
		numberOfSyllables = 0;
		numberOfSentences = sentences.length;
		longestWordLength = 0;
		longestSentenceLength = 0;
		averageWordLength = 0;
		
		for (int i=0; i<numberOfSentences; i++){
			if (sentences[i].getNumberOfWords()>longestSentenceLength)
				longestSentenceLength = sentences[i].getNumberOfWords();
			
			Word words[] = sentences[i].getWords();
			numberOfTotalWords += words.length;
			numberOfSyllables += sentences[i].getNumberOfSyllables();
			if (sentences[i].getLongestWordLength()>longestWordLength)
				longestWordLength = sentences[i].getLongestWordLength();
			
			
			for (int j=0;j<words.length; j++){
				averageWordLength += words[j].getLength();
				if (wordsFreq.containsKey(words[j])){
					wordsFreq.put(words[j], wordsFreq.get(words[j]) + 1);
				}
				else {
					numberOfDistinctWords++;
					wordsFreq.put(words[j], 1);
				}
			}
		}
		averageWordLength = averageWordLength/numberOfTotalWords;
	}
	
}

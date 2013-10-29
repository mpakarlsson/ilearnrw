package ilearnrw.textclassification;

public interface TextAPI {
	public int getNumberOfSentences();

	public Sentence[] getSentences();

	public int getNumberOfWords();
	
	public int getNumberOfDistinctWords();

	public int getNumberOfSyllables();

	public int getLongestWordLength();

	public int getLongestSentenceLength();

	public double getWordsPerSentence();

	public double getSyllablesPerWord();

	public double getAverageWordLength();

	public double getAverageLongestWordLength();
}

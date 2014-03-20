package ilearnrw.annotation;


public class DetailedSentence {
	private String sentence;
	private String[] parts;

	public DetailedSentence(String sentence){
		this.sentence = sentence;
		splitWords();
	}
	
	public String getWord(int i){
		return parts[i];
	}
	
	public String[] getWords(){
		return parts;
	}
	
	public int getNumberOfWords(){
		return parts.length;
	}
	
	public String getSentence(){
		return sentence;
	}
	
	private void splitWords(){
		parts = sentence.split("[(?=\\s+)(?=\\.)(?=\\!)(?=\\,)(?=\\;)(?=\\-)(?=\\:)(?=\\?)(?=\\\")]");
	}
	
	public boolean isSymbols(int i){
		if (parts.length<i)
			return false;
		String test[] = sentence.split("[(?=\\s+)(?=\\.)(?=\\!)(?=\\,)(?=\\;)(?=\\-)(?=\\:)(?=\\?)(?=\\\")]");
		System.out.println(test);
		return true;
	}
	
	@Override
	public String toString(){
		return sentence;
	}
}

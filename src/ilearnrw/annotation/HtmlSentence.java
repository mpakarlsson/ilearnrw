package ilearnrw.annotation;

import java.util.ArrayList;


public class HtmlSentence {
	private String sentence;
	private String[] parts;
	private char p[] = {' ','.','\n', ',', '`', '~', '!', '@', '#', '$',
			'%', '^', '&', '*', '(', ')', '-', '_', '+', '=', 
			'{', '[', '}', ']', '|', '\\', '\'', '<', '>', '/', 
			';', ':', '?', '\"'};

	public HtmlSentence(String sentence){
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
	
	private boolean notIn(char p[], char t){
		for (char x : p){
			if (x==t)
				return false;
		}
		return true;
	}
	
	private String replaceWithHtmlEntity(char c){
		if (c == '\n')
			return "<br>";
		return ""+c;
	}
	
	private void splitWords(){
		ArrayList<String> sentenceParts = new ArrayList<String>();
		for (int i=0;i<sentence.length();){
			String t = "";
			//t = t+sentence.charAt(i);
			if (notIn(p, sentence.charAt(i)))
				while (i<sentence.length() && notIn(p, sentence.charAt(i))){
					t = t+sentence.charAt(i++);
				}
			else 
				while (i<sentence.length() && !notIn(p, sentence.charAt(i)))
					t = t+replaceWithHtmlEntity(sentence.charAt(i++));
			sentenceParts.add(t);
		}
		parts = new String[sentenceParts.size()];
		for (int i=0; i<sentenceParts.size(); i++)
			parts[i] = sentenceParts.get(i);
	}
	
	public boolean isWord(int i){
		if (parts.length<i)
			return false;
		return notIn(p, parts[i].charAt(0));
	}
	
	@Override
	public String toString(){
		return sentence;
	}
}

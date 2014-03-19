package ilearnrw.annotation;

import ilearnrw.utils.LanguageCode;

import java.util.ArrayList;

public class TextPartsGenerator {
	private String text;
	private DetailedSentence sentences[][];
	private ArrayList<UserBasedAnnotatedWord> uniqueWords = new ArrayList<UserBasedAnnotatedWord>();

	public TextPartsGenerator(String text){	
		this.text = text;
		initialize();
	}

	public void setUserProfile() {
	}

	public int getParagraphsNumber(){
		return sentences.length;
	}

	public DetailedSentence[] getParagraph(int i){
		return sentences[i];
	}
	
	public DetailedSentence getSentence(int i, int j){
		return sentences[i][j];
	}
	
	public ArrayList<UserBasedAnnotatedWord> getUniqueWords(){
		return uniqueWords;
	}
	
	private void initialize(){
		String pars[] = text.trim().split("((\\n\\n)*(\\\n)\\s)");
		sentences = new DetailedSentence[pars.length][];
		for (int i=0;i<pars.length; i++){
			String tmp[] = pars[i].trim().split("(?<=(\\.)*(\\.)\\s)|(?<=(\\!)*(\\!)\\s)|(?<=(\\;)*(\\;)\\s)|(?<=(\\?)*(\\?)\\s)");
			sentences[i] = new DetailedSentence[tmp.length];
			for (int j=0;j<tmp.length; j++){
				sentences[i][j] = new DetailedSentence(tmp[j]);
			}
		}
	}
}

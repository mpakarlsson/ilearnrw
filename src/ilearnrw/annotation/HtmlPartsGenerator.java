package ilearnrw.annotation;

import java.util.ArrayList;

public class HtmlPartsGenerator {
	private String text;
	private HtmlSentence sentences[][];
	private ArrayList<UserBasedAnnotatedWord> uniqueWords = new ArrayList<UserBasedAnnotatedWord>();

	public HtmlPartsGenerator(String text){	
		this.text = text;
		initialize();
	}

	public void setUserProfile() {
	}

	public int getParagraphsNumber(){
		return sentences.length;
	}

	public HtmlSentence[] getParagraph(int i){
		return sentences[i];
	}
	
	public HtmlSentence getSentence(int i, int j){
		return sentences[i][j];
	}
	
	public ArrayList<UserBasedAnnotatedWord> getUniqueWords(){
		return uniqueWords;
	}
	
	private void initialize(){
		String pars[] = text.trim().split("((\\n)+\\s)");	
		sentences = new HtmlSentence[pars.length][];
		for (int i=0;i<pars.length; i++){
			if(pars[i].trim().isEmpty())
				continue;
			
			String tmp[] = pars[i].trim().split("(?<=(\\.)+\\s)|(?<=[;?!])(?:\\s*)");	
			sentences[i] = new HtmlSentence[tmp.length];
			for (int j=0;j<tmp.length; j++){
				sentences[i][j] = new HtmlSentence(tmp[j]);
			}
		}
	}
}

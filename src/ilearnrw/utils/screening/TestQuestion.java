package ilearnrw.utils.screening;
import java.io.Serializable;
import java.util.ArrayList;


public class TestQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	private String question;
	private ArrayList<String> relatedWords;
	

	public TestQuestion(String question, ArrayList<String> relatedWords, 
			int clusterNumber) {
		this.question = question;
		this.relatedWords = relatedWords;
	}

	public TestQuestion() {
		this.question = "";
		this.relatedWords = new ArrayList<String>();
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setRelatedWords(ArrayList<String> relatedWords) {
		this.relatedWords = relatedWords;
	}

	public void addRelatedWord(String relatedWord) {
		this.relatedWords.add(relatedWord);
	}

	public String getQuestion() {
		return question;
	}

	public ArrayList<String> getRelatedWords() {
		return relatedWords;
	}

	public String toString() {
		return "TestQuestion [\nquestion="
				+ question + "\nrelatedWords=" + relatedWords + "]";
	}

}

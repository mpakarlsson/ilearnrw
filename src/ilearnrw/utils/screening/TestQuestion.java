package ilearnrw.utils.screening;
import java.io.Serializable;
import java.util.ArrayList;


public class TestQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	private String question;
	private ArrayList<String> relatedWords;
	private boolean attachRelWords;
	private int id, parentCluster;

	public TestQuestion(String question, ArrayList<String> relatedWords, boolean attachRelWords, int id, int parentCluster) {
		this.question = question;
		this.relatedWords = relatedWords;
		this.attachRelWords = attachRelWords;
		this.id = id;
		this.parentCluster= parentCluster; 
	}

	public TestQuestion() {
		this.question = "";
		this.relatedWords = new ArrayList<String>();
		this.attachRelWords = true;
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

	public boolean isAttachRelWords() {
		return attachRelWords;
	}

	public void setAttachRelWords(boolean attachRelWords) {
		this.attachRelWords = attachRelWords;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentCluster() {
		return parentCluster;
	}

	public void setParentCluster(int parentCluster) {
		this.parentCluster = parentCluster;
	}
	
}

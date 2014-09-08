package ilearnrw.utils.screening;
import java.io.Serializable;
import java.util.ArrayList;


public class ClusterTestQuestions implements Serializable{
	private static final long serialVersionUID = 1L;
	private int clusterNumber;
	private ArrayList<TestQuestion> clusterQuestions;
	
	public ClusterTestQuestions() {
		this.clusterNumber = -1;
		this.clusterQuestions = new ArrayList<TestQuestion>();
	}
	
	public ClusterTestQuestions(int cluster) {
		this.clusterNumber = cluster;
		this.clusterQuestions = new ArrayList<TestQuestion>();
	}
	
	public ClusterTestQuestions(int clusterNumber,
			ArrayList<TestQuestion> clusterQuestions) {
		this.clusterNumber = clusterNumber;
		this.clusterQuestions = clusterQuestions;
	}
	
	public int getClusterNumber() {
		return clusterNumber;
	}
	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}
	public ArrayList<TestQuestion> getClusterQuestions() {
		return clusterQuestions;
	}
	public void setClusterQuestions(ArrayList<TestQuestion> clusterQuestions) {
		this.clusterQuestions = clusterQuestions;
	}

	public int addClusterQuestion(String question, ArrayList<String> relatedWords) {
		int id = getFirstAvailbleId();
		TestQuestion tq = new TestQuestion(question, relatedWords, id);
		this.clusterQuestions.add(tq);
		return id;
	}

	public void deleteClusterQuestion(int id) {
		for (int i=0;i<clusterQuestions.size();i++)
			if (clusterQuestions.get(i).getId() == id){
				clusterQuestions.remove(i);
				return;
			}
	}

	public boolean editClusterQuestion(int id, String question, ArrayList<String> relatedWords) {
		for (int i=0;i<clusterQuestions.size();i++)
			if (clusterQuestions.get(i).getId() == id){
				clusterQuestions.get(i).setQuestion(question);
				clusterQuestions.get(i).setRelatedWords(relatedWords);
				return true;
			}
		return false;
	}

	private int getFirstAvailbleId(){
		boolean p[] = new boolean[clusterQuestions.size()+1];
		for (int i=0; i<p.length; i++)
			p[i] = false;
		for (TestQuestion tq : clusterQuestions){
			if (0 <= tq.getId() && tq.getId()<p.length)
				p[tq.getId()] = true;
		}
		for (int i=0; i<p.length; i++)
			if (!p[i])
				return i;
		return 0;
	}
}

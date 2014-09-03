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

}

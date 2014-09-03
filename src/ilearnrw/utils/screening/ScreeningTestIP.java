package ilearnrw.utils.screening;
import java.util.ArrayList;

public interface ScreeningTestIP {
	public ArrayList<TestQuestion> getQuestionsList();
	public ArrayList<ClusterTestQuestions> getQuestions();
	public ArrayList<TestQuestion> getClusterQuestions(int c);
	public void storeTest(String filename);
	public void loadTest(String filename);
}

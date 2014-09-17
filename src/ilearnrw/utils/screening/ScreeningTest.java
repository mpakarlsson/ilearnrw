package ilearnrw.utils.screening;
import ilearnrw.user.profile.clusters.ProfileClusters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.Gson;


public class ScreeningTest implements ScreeningTestIP, Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<ClusterTestQuestions> questions;
	
	public ScreeningTest(ArrayList<ClusterTestQuestions> questions) {
		this.questions = questions;
	}

	public ScreeningTest() {
		this.questions = new ArrayList<ClusterTestQuestions>();
	}

	public ScreeningTest(ProfileClusters profileClusters) {
		this.questions = new ArrayList<ClusterTestQuestions>();
		for (Integer t : profileClusters.getClustersNumbers())
			this.questions.add(new ClusterTestQuestions(t));
	}
	
	public int addQuestion(String question, ArrayList<String> relatedWords, boolean attachRelWords, 
			int cluster) {
		for (ClusterTestQuestions ctq : questions){
			if (ctq.getClusterNumber() == cluster){
				return ctq.addClusterQuestion(question, relatedWords, attachRelWords);
			}
		}
		ClusterTestQuestions ctq = new ClusterTestQuestions(cluster);
		int id = ctq.addClusterQuestion(question, relatedWords, attachRelWords);
		questions.add(ctq);
		return id;
	}
	
	public void deleteQuestion(int cluster, int id) {
		for (ClusterTestQuestions ctq : questions){
			if (ctq.getClusterNumber() == cluster){
				ctq.deleteClusterQuestion(id);
				return;
			}
		}
	}
	
	public void editQuestion(int cluster, int id, String question, ArrayList<String> relatedWords, 
			boolean attachRelWords) {
		for (ClusterTestQuestions ctq : questions){
			if (ctq.getClusterNumber() == cluster){
				ctq.editClusterQuestion(id, question, relatedWords, attachRelWords);
				return;
			}
		}
	}
	
	public void setQuestions(ArrayList<ClusterTestQuestions> questions) {
		this.questions = questions;
	}
	
	public void setClusterQuestions(ArrayList<TestQuestion> questions, int cluster) {
		this.questions.get(cluster).setClusterQuestions(questions);
	}

	public ArrayList<TestQuestion> getSortedQuestionsListByType() {
		ArrayList<TestQuestion> res = new ArrayList<TestQuestion>();
		for (ClusterTestQuestions ctq : questions){
			for (TestQuestion tq : ctq.getClusterQuestions()){
				boolean in = false;
				if (!tq.isAttachRelWords()){
					res.add(tq);
					continue;
				}
				for (int i=res.size()-2;i>=0; i--)
					if (tq.getQuestion().equalsIgnoreCase(res.get(i).getQuestion())){
						res.add(i+1, tq);
						in = true;
						break;
					}
				if (!in){
					res.add(tq);
				}
			}
		}
		return res;
	}

	@Override
	public ArrayList<TestQuestion> getQuestionsList() {
		ArrayList<TestQuestion> res = new ArrayList<TestQuestion>();
		for (ClusterTestQuestions ctq : questions){
			for (TestQuestion tq : ctq.getClusterQuestions())
				res.add(tq);
		}
		return res;
	}

	@Override
	public void storeTest(String filename) {
		Gson gson = new Gson();
		String jsonRepresentation = gson.toJson(this);
		try {
			FileWriter Filewriter = new FileWriter(filename);
			Filewriter.write(jsonRepresentation);
			Filewriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadTest(String filename) {
		Gson gson = new Gson();
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader buffered = new BufferedReader(fileReader);
			ScreeningTest obj = gson.fromJson(buffered, ScreeningTest.class);
			this.questions = obj.getQuestions();
		} catch (IOException e) {

		}
	}

	@Override
	public ArrayList<ClusterTestQuestions> getQuestions() {
		return questions;
	}

	@Override
	public ArrayList<TestQuestion> getClusterQuestions(int c) {
		for (ClusterTestQuestions ctq : questions){
			if (ctq.getClusterNumber() == c)
				return ctq.getClusterQuestions();
		}
		return null;
	}

}

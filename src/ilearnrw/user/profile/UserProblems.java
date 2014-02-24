package ilearnrw.user.profile;

import ilearnrw.prototype.application.JsonHandler;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.textclassification.Word;
import ilearnrw.user.problems.EnglishProblems;
import ilearnrw.user.problems.GreekProblems;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDescription;
import ilearnrw.user.problems.Problems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class UserProblems implements Serializable {
	private static final long serialVersionUID = 1L;

	private ProblemDefinitionIndex problems;
	private UserSeverities userSeverities;
	private ArrayList<Word> trickyWords;
	
	public UserProblems(){
		problems = null;//new ProblemDefinitionIndex();
		userSeverities = new UserSeverities();
		setTrickyWords(new ArrayList<Word>());
	}
	
	public void setProblems(ProblemDefinitionIndex problems) {
		this.problems = problems;
	}

	public UserProblems(ProblemDefinitionIndex theProblems){
		initialize(theProblems, true);
		setTrickyWords(new ArrayList<Word>());
	}
	public UserProblems(ProblemDefinitionIndex theProblems, boolean initializeUserSeveruties) {
		initialize(theProblems, initializeUserSeveruties);
		setTrickyWords(new ArrayList<Word>());
	}
	
	private void initialize(ProblemDefinitionIndex theProblems, boolean initializeUserSeverities){
		this.problems = theProblems;
		userSeverities = new UserSeverities(theProblems.getIndexLength());
		
		if(initializeUserSeverities) {
			int idxLen = theProblems.getIndexLength();
			for (int i=0; i<idxLen; i++){
				userSeverities.constructRow(i, theProblems.getRowLength(i));
			}
		}
	}
	
	public void loadTestGreekProblems(){
		//JsonHandler handler = new JsonHandler("data/problem_definitions_greece.json", true);
		//GreekProblems greekProbs = (GreekProblems)handler.fromJson(GreekProblems.class);
		//System.out.println(greekProbs.getAllProblems().toString());
		
		GreekProblems greekProbs = new GreekProblems();
		initialize(greekProbs.getProblemDefinitionIndex(), true);
		Random rand = new Random();
		for (int i=0;i<problems.getIndexLength(); i++){
			int wi =2*problems.getRowLength(i)/4 + rand.nextInt(1);
			this.setSystemIndex(i, wi);
			this.setTeacherIndex(i, wi);
			for (int j=0; j<userSeverities.getSeverityLength(i); j++){
				if (j<wi/2)
					this.setSeverity(i, j, 0);// rand.nextInt(2));
				else if(j<wi)
					this.setSeverity(i, j, 1);// rand.nextInt(3));
				else if (j<(wi+userSeverities.getSeverityLength(i))/2)
					this.setSeverity(i, j, 2);// rand.nextInt(4));
				else 
					this.setSeverity(i, j, 3);//  rand.nextInt(3)+1);
			}
		}
	}
	
	public void loadTestEnglishProblems() {
		JsonHandler handler = new JsonHandler(Type.DATA, "problem_definitions_en.json", true);
		EnglishProblems enProbs = (EnglishProblems)handler.fromJson(EnglishProblems.class);
		//System.out.println(greekProbs.getAllProblems().toString());
		
		//GreekProblems greekProbs = new GreekProblems();
		initialize(enProbs.getProblemDefinitionIndex(), true);
		Random rand = new Random();
		for (int i=0;i<problems.getIndexLength(); i++){
			int wi =2*problems.getRowLength(i)/4 + rand.nextInt(1);
			this.setSystemIndex(i, wi);
			this.setTeacherIndex(i, wi);
			for (int j=0; j<userSeverities.getSeverityLength(i); j++){
				if (j<wi/2)
					this.setSeverity(i, j, 0);// rand.nextInt(2));
				else if(j<wi)
					this.setSeverity(i, j, 1);// rand.nextInt(3));
				else if (j<(wi+userSeverities.getSeverityLength(i))/2)
					this.setSeverity(i, j, 2);// rand.nextInt(4));
				else 
					this.setSeverity(i, j, 3);//  rand.nextInt(3)+1);
			}
		}
	}

	public ProblemDefinitionIndex getProblems(){
		return problems;
	}
	public UserSeverities getUserSeverities(){
		return userSeverities;
	}

	public void setUserSeverities(UserSeverities userSeverities) {
		this.userSeverities = userSeverities;
	}

	public void setSystemIndex(int i, int value) {
		userSeverities.setSystemIndex(i, value);
	}

	public void setTeacherIndex(int i, int value) {
		userSeverities.setTeacherIndex(i, value);
	}

	public void setSeverity(int i, int j, int value) {
		System.err.println("Here i am ("+i+", "+j+")");
		if (problems.getProblemDefinition(i).getSeverityType().
				equalsIgnoreCase("binary")){
			if (value != 0)
				value = 1;
		}
		else {
			if (value < 0) 
				value = 0;
			if (value > 3)
				value = 3;
		}
		userSeverities.setSeverity(i, j, value);
	}

	public int getSeverity(int i, int j) {
		return userSeverities.getSeverity(i,j);
	}

	public ArrayList<Word> getTrickyWords() {
		return trickyWords;
	}

	public void setTrickyWords(ArrayList<Word> trickyWords) {
		this.trickyWords = trickyWords;
	}

	public void addTrickyWord(Word trickyWord) {
		if (!this.trickyWords.contains(trickyWord))
			this.trickyWords.add(trickyWord);
	}

	public boolean removeTrickyWord(Word trickyWord) {
		if (!this.trickyWords.contains(trickyWord))
			return false;
		else {
			this.trickyWords.remove(trickyWord);
			return true;
		}
	}

	public int getSystemIndex(int i) {
		return userSeverities.getSystemIndex(i);
	}

	public int getTeacherIndex(int i) {
		return userSeverities.getTeacherIndex(i);
	}
	
	public int getNumerOfRows() {
		return userSeverities.getNumberOfRows();
	}
	
	public int getRowLength(int i) {
		return userSeverities.getSeverityLength(i);
	}

	public ProblemDescription getProblemDescription(int i, int j) {
		return problems.getProblemDescription(i,j);
	}

	public ProblemDefinition getProblemDefinition(int i) {
		return problems.getProblemDefinition(i);
	}
	
	@Override
	public String toString(){
		if (problems == null || userSeverities == null)
			return "null indices or problems matrix";
		String res = "";
		res = res + problems.toString();
		res = res + "\n\n";
		res = res + userSeverities.toString();
		return res;
	}
	
}

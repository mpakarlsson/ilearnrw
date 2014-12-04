package ilearnrw.user.profile;

import ilearnrw.textclassification.Word;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDescription;
import ilearnrw.user.profile.clusters.ProblemDescriptionCoordinates;
import ilearnrw.user.profile.clusters.ProfileClusters;
import ilearnrw.utils.LanguageCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserProblems implements Serializable {
	private static final long serialVersionUID = 1L;

	private ProblemDefinitionIndex problems;
	private UserSeverities userSeverities;
	private List<Word> trickyWords;
	
	public UserProblems(){
		problems = null;//new ProblemDefinitionIndex();
		userSeverities = new UserSeverities();
		setTrickyWords(new ArrayList<Word>());
	}
	
	public UserProblems(LanguageCode lc){
		problems = new ProblemDefinitionIndex(lc);
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
		userSeverities = new UserSeverities(theProblems.returnIndexLength());
		
		if(initializeUserSeverities) {
			int idxLen = theProblems.returnIndexLength();
			for (int i=0; i<idxLen; i++){
				userSeverities.constructRow(i, theProblems.getRowLength(i));
			}
		}
	}
	
	public void loadTestGreekProblems(){
		initialize(new ProblemDefinitionIndex(LanguageCode.GR), true);
		Random rand = new Random();
		for (int i=0;i<problems.returnIndexLength(); i++){
			int wi = 0 ;//2*problems.getRowLength(i)/4 + rand.nextInt(1);
			this.setSystemIndex(i, wi);
			this.setTeacherIndex(i, wi);
			for (int j=0; j<userSeverities.getSeverityLength(i); j++){
				/*if (j<wi/2)
					this.setUserSeverity(i, j, 0);// rand.nextInt(2));
				else if(j<wi)
					this.setUserSeverity(i, j, 1);// rand.nextInt(3));
				else if (j<(wi+userSeverities.getSeverityLength(i))/2)
					this.setUserSeverity(i, j, 2);// rand.nextInt(4));
				else */
					this.setUserSeverity(i, j, 1);//  rand.nextInt(3)+1);
			}
		}
	}
	
	public void loadTestEnglishProblems() {
		initialize(new ProblemDefinitionIndex(LanguageCode.EN), true);
		Random rand = new Random();
		for (int i=0;i<problems.returnIndexLength(); i++){
			int wi = 0 ;//2*problems.getRowLength(i)/4 + rand.nextInt(1);
			this.setSystemIndex(i, wi);
			this.setTeacherIndex(i, wi);
			for (int j=0; j<userSeverities.getSeverityLength(i); j++){
				/*if (j<wi/2)
					this.setUserSeverity(i, j, 0);// rand.nextInt(2));
				else if(j<wi)
					this.setUserSeverity(i, j, 1);// rand.nextInt(3));
				else if (j<(wi+userSeverities.getSeverityLength(i))/2)
					this.setUserSeverity(i, j, 2);// rand.nextInt(4));
				else */
					this.setUserSeverity(i, j, 1);//  rand.nextInt(3)+1);
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

	public void setUserSeverity(int i, int j, int value) {
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

	public int getUserSeverity(int i, int j) {
		return userSeverities.getSeverity(i,j);
	}

	public List<Word> getTrickyWords() {
		return trickyWords;
	}

	public void setTrickyWords(List<Word> trickyWords2) {
		this.trickyWords = trickyWords2;
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

	
	public int calculateSystemCluster(){
		
		ProfileClusters cls = new ProfileClusters(this.getProblems());

		ArrayList<Integer> clusters = cls.getClustersNumbers();
		
		int index = -1;
		for(int c : clusters){
			
			ArrayList<ProblemDescriptionCoordinates> problems = cls.getClusterProblems(c);
			
			for(ProblemDescriptionCoordinates coord : problems){//first cluster with a difficulty with severity 2 or 3
				if(this.getUserSeverity(coord.getCategory(),coord.getIndex())>1){
					index = c;
					break;
				}
			}
			
			if (index!=-1)
				break;
		}
		
		if(index==-1)
			index = clusters.get(clusters.size()-1);
		
		return index;
	}
	
	
	
	public int getSystemCluster() {
		return userSeverities.getSystemCluster();
	}
	
	public void updateSystemCluster(){
		 userSeverities.setSystemCluster(calculateSystemCluster());
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

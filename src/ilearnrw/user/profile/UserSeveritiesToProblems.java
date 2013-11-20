package ilearnrw.user.profile;

import ilearnrw.prototype.application.JsonHandler;
import ilearnrw.user.problems.EnglishProblems;
import ilearnrw.user.problems.GreekProblems;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDescription;
import ilearnrw.user.problems.Problems;

import java.io.Serializable;
import java.util.Random;

public class UserSeveritiesToProblems implements Serializable {
	private static final long serialVersionUID = 1L;

	private ProblemDefinitionIndex problems;
	private UserSeverities userSeverities;
	
	public UserSeveritiesToProblems(){
	}
	
	public UserSeveritiesToProblems(ProblemDefinitionIndex theProblems){
		initialize(theProblems);
	}
	
	private void initialize(ProblemDefinitionIndex theProblems){
		this.problems = theProblems;
		userSeverities = new UserSeverities(theProblems.getIndexLength());
		
		int idxLen = theProblems.getIndexLength();
		for (int i=0; i<idxLen; i++){
			userSeverities.constructRow(i, theProblems.getRowLength(i));
		}

	}
	
	// TODO: eliminate these functions and replace with db fetches!!!
	public void loadTestGreekProblems(){
		JsonHandler handler = new JsonHandler("data/problem_definitions_greece.json", true);
		GreekProblems greekProbs = (GreekProblems)handler.fromJson(GreekProblems.class);
		//System.out.println(greekProbs.getAllProblems().toString());
		
		//GreekProblems greekProbs = new GreekProblems();
		initialize(greekProbs.getAllProblems());
		Random rand = new Random();
		for (int i=0;i<problems.getIndexLength(); i++){
			userSeverities.setWorkingIndex(i, rand.nextInt(problems.getRowLength(i)));
			for (int j=0; j<userSeverities.getSeverityLength(i); j++){
				userSeverities.setSeverity(i, j, rand.nextInt(3)+1);
			}
		}
	}
	
	public void loadTestEnglishProblems(){
		JsonHandler handler = new JsonHandler("data/problem_definitions_en.json", true);
		EnglishProblems enProbs = (EnglishProblems)handler.fromJson(EnglishProblems.class);
		//System.out.println(greekProbs.getAllProblems().toString());
		
		//GreekProblems greekProbs = new GreekProblems();
		initialize(enProbs.getAllProblems());
		Random rand = new Random();
		for (int i=0;i<problems.getIndexLength(); i++){
			userSeverities.setWorkingIndex(i, rand.nextInt(problems.getRowLength(i)));
			for (int j=0; j<userSeverities.getSeverityLength(i); j++){
				userSeverities.setSeverity(i, j, rand.nextInt(3)+1);
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

	public int getSeverity(int i, int j) {
		return userSeverities.getSeverity(i,j);
	}

	public int getWorkingIndex(int i) {
		return userSeverities.getWorkingIndex(i);
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

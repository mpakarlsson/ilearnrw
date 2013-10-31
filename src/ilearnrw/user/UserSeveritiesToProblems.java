package ilearnrw.user;

import ilearnrw.games.NumberProblems;
import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.GreekProblems;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDescription;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Random;

public class UserSeveritiesToProblems implements Serializable {
	private static final long serialVersionUID = 1L;

	private ProblemDefinitionIndex theProblems;
	private UserSeverities userSeverities;
	
	public UserSeveritiesToProblems(){
		loadTestProblems();
	}
	
	public UserSeveritiesToProblems(ProblemDefinitionIndex theProblems){
		initialize(theProblems);
	}
	
	private void initialize(ProblemDefinitionIndex theProblems){
		this.theProblems = theProblems;
		userSeverities = new UserSeverities(theProblems.getIndexLength());
		
		int idxLen = theProblems.getIndexLength();
		for (int i=0; i<idxLen; i++){
			userSeverities.constructRow(i, theProblems.getIthRowLength(i));
		}

	}
	
	public void loadTestProblems(){
		NumberProblems numProbs = new NumberProblems();
		initialize(numProbs.getAllProblems());
		Random rand = new Random();
		for (int i=0;i<theProblems.getIndexLength(); i++){
			userSeverities.setIndex(i, rand.nextInt(theProblems.getIthRowLength(i)));
			for (int j=0; j<userSeverities.getSeverityLength(i); j++){
				userSeverities.setSeverity(i, j, rand.nextInt(3)+1);
			}
		}
		
	}
	
	public void loadTestGreekProblems(){
		GreekProblems greekProbs = new GreekProblems();
		initialize(greekProbs.getAllProblems());
		Random rand = new Random();
		for (int i=0;i<theProblems.getIndexLength(); i++){
			userSeverities.setIndex(i, rand.nextInt(theProblems.getIthRowLength(i)));
			for (int j=0; j<userSeverities.getSeverityLength(i); j++){
				userSeverities.setSeverity(i, j, rand.nextInt(3)+1);
			}
		}
		
	}

	public ProblemDefinitionIndex getProblemDefinitionIndex(){
		return theProblems;
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

	public int getIndex(int i) {
		return userSeverities.getIndex(i);
	}

	public ProblemDescription getProblemDefinition(int i, int j) {
		return theProblems.getProblemDescription(i,j);
	}

	public ProblemDefinition getProblemDefinition(int i) {
		return theProblems.getProblemDefinition(i);
	}
	
	@Override
	public String toString(){
		if (theProblems == null || userSeverities == null)
			return "null indices or problems matrix";
		String res = "";
		res = res + theProblems.toString();
		res = res + "\n\n";
		res = res + userSeverities.toString();
		return res;
	}
	
}

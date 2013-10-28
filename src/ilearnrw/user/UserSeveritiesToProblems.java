package ilearnrw.user;

import ilearnrw.games.NumberProblems;
import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinitionIndex;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Random;

public class UserSeveritiesToProblems implements Serializable {
	private static final long serialVersionUID = 1L;

	private ProblemDefinitionIndex theProblems;
	private UserSeverities theSeverities;
	
	public UserSeveritiesToProblems(){
		loadTestProblems();
	}
	
	public UserSeveritiesToProblems(ProblemDefinitionIndex theProblems){
		initialize(theProblems);
	}
	
	private void initialize(ProblemDefinitionIndex theProblems){
		this.theProblems = theProblems;
		theSeverities = new UserSeverities(theProblems.getIndexLength());
		
		int idxLen = theProblems.getIndexLength();
		for (int i=0; i<idxLen; i++){
			theSeverities.constructRow(i, theProblems.getIthRowLength(i));
		}

	}
	
	public void loadTestProblems(){
		NumberProblems numProbs = new NumberProblems();
		initialize(numProbs.getAllProblems());
		Random rand = new Random();
		for (int i=0;i<theProblems.getIndexLength(); i++){
			theSeverities.setIndex(i, rand.nextInt(theProblems.getIthRowLength(i)));
			for (int j=0; j<theSeverities.getSeverityLength(i); j++){
				theSeverities.setSeverity(i, j, rand.nextInt(3)+1);
			}
		}
		
	}

	public int getSeverity(int i, int j) {
		return theSeverities.getSeverity(i,j);
	}

	public int getIndex(int i) {
		return theSeverities.getIndex(i);
	}
	
	@Override
	public String toString(){
		if (theProblems == null || theSeverities == null)
			return "null indices or problems matrix";
		String res = "";
		res = res + theProblems.toString();
		res = res + "\n\n";
		res = res + theSeverities.toString();
		return res;
	}
	
}

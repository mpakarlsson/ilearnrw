package ilearnrw.games;

import java.util.ArrayList;

import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemNode;
import ilearnrw.user.problems.ProblemType;
import ilearnrw.utils.IlearnException;
import ilearnrw.utils.LanguageCode;


public class NumberProblems{ 
	private ProblemDefinitionIndex probsMatrix;

	public NumberProblems() {
		//three problems
		probsMatrix = new ProblemDefinitionIndex(3, LanguageCode.EN);	
		initialize();
	}
	
	public void getProblem(int idx){
		
	}
	
	public ProblemDefinitionIndex getAllProblems(){
		return probsMatrix;
	}

	public void initialize(){	
		
		//1) Problem on adding numbers (one digit, two digits, three digits, four digits)
		probsMatrix.constructProblemRow(0, 4);
		Category cat = new Category("Numbers.Addition");
		ProblemDefinition prob = new ProblemDefinition("1.1", cat);
		probsMatrix.setProblemDefinition(prob, 0);

		probsMatrix.setProblemDescription(ProblemType.X, new String[]{"One Digit"}, 0, 0);
		probsMatrix.setProblemDescription(ProblemType.X, new String[]{"Two Digits"}, 0, 1);
		probsMatrix.setProblemDescription(ProblemType.X, new String[]{"Three Digits"}, 0, 2);
		probsMatrix.setProblemDescription(ProblemType.X, new String[]{"Four Digits"}, 0, 3);
			
	
		//2) Problem on recognizing numbers containing the digit {3} or {4} or { {5} with {7} } 
		probsMatrix.constructProblemRow(1, 3);
		cat = new Category("Numbers.Recognition");
		prob = new ProblemDefinition("1.2", cat);
		probsMatrix.setProblemDefinition(prob, 1);
		probsMatrix.setProblemDescription(ProblemType.X, new String[]{"3"}, 1, 0);
		probsMatrix.setProblemDescription(ProblemType.X, new String[]{"4"}, 1, 1);		
		probsMatrix.setProblemDescription(ProblemType.X, new String[]{"5", "7"}, 1, 2);
	
			
		//3) Problem on recognizing numbers containing pattern xy, where y = x+1
		probsMatrix.constructProblemRow(2, 1);
		prob = new ProblemDefinition("1.3", cat);
		probsMatrix.setProblemDefinition(prob, 2);
		probsMatrix.setProblemDescription(ProblemType.X, new String[]{"Containing xy, y = x+1"}, 2, 0);

	}
	

}

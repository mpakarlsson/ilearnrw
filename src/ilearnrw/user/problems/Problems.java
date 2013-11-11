package ilearnrw.user.problems;

public class Problems {
	protected ProblemDefinitionIndex problemDefinitionIndex;

	public Problems(){}

	public Problems(ProblemDefinitionIndex problemDefinitionIndex) {
		this.problemDefinitionIndex = problemDefinitionIndex;
	}
	
	public void getProblem(int idx){
		
	}
	
	public ProblemDefinitionIndex getAllProblems(){
		return problemDefinitionIndex;
	}

}

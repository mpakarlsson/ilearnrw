package ilearnrw.user.problems;

public class Problems {
	protected ProblemDefinitionIndex problemDefinitionIndex;

	public Problems(){}

	public Problems(ProblemDefinitionIndex problemDefinitionIndex) {
		this.problemDefinitionIndex = problemDefinitionIndex;
	}
	
	public ProblemDefinitionIndex getProblemDefinitionIndex(){
		return problemDefinitionIndex;
	}
	
	public void setProblemDefinitionIndex(
			ProblemDefinitionIndex problemDefinitionIndex) {
		this.problemDefinitionIndex = problemDefinitionIndex;
	}

}

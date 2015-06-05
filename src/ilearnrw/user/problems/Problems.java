package ilearnrw.user.problems;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
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

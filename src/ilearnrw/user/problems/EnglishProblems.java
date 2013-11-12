package ilearnrw.user.problems;

import ilearnrw.prototype.application.JsonHandler;

public class EnglishProblems extends Problems{
	
	public EnglishProblems(){
		JsonHandler handler = new JsonHandler("data/problem_definitions_en.json", true);
		problemDefinitionIndex = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
	}

}

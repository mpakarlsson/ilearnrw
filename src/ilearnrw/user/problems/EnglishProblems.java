package ilearnrw.user.problems;

import ilearnrw.prototype.application.JsonHandler;
import ilearnrw.resource.ResourceLoader.Type;

public class EnglishProblems extends Problems{
	
	public EnglishProblems(){
		JsonHandler handler = new JsonHandler(Type.DATA, "problem_definitions_en.json", true);
		problemDefinitionIndex = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
	}

}

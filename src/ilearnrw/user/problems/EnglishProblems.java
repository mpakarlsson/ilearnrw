package ilearnrw.user.problems;

import ilearnrw.prototype.application.JsonHandler;
import ilearnrw.utils.LanguageCode;

public class EnglishProblems extends Problems{
	
	public EnglishProblems(){
		JsonHandler handler = new JsonHandler("data/problem_definitions_en.json", true);
		probsMatrix = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
	}

}

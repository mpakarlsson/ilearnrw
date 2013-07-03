package ilearn.user.problems;

import ilearnrw.user.LanguageCode;

import java.util.ArrayList;

public interface ProblemDefinitionIndexApi {
	public ArrayList<ProblemDefinition> getProblemsByLanguage(LanguageCode x);
	public ArrayList<ProblemDefinition> getProblemsByCategory(Category x);

}

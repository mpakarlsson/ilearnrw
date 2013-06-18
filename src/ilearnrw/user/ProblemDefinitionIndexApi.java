package ilearnrw.user;

import java.util.ArrayList;

public interface ProblemDefinitionIndexApi {
	public ArrayList<ProblemDefinition> getProblemsByLanguage(LanguageCode x);
	public ArrayList<ProblemDefinition> getProblemsByCategory(Category x);

}

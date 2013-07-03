package ilearnrw.user.mockup;

import java.util.ArrayList;
import java.util.List;

import ilearn.user.problems.Category;
import ilearn.user.problems.ProblemDefinition;
import ilearn.user.problems.ProblemDefinitionIndexApi;
import ilearnrw.user.LanguageCode;

public class ProblemDefinitionIndexMockup implements ProblemDefinitionIndexApi{

	private List<ProblemDefinition> index = new ArrayList<ProblemDefinition>();
	
	public ProblemDefinitionIndexMockup()
	{
		Category category1 = new Category("PHONOLOGY-SUB-WORDLEVEL.Difficultyinrecognizingletters.Auditory-basederrors");
		Category category2 = new Category("PHONOLOGY-SUB-WORDLEVEL.Difficultyinrecognizingletters.Visually-basederrors");

		Category category3 = new Category("WORDLEVEL.Wordrecognition.Visually-basederrors");
		Category category4 = new Category("WORDLEVEL.Wordrecognition.Semantically-basederrors");
		

		ArrayList<LanguageCode> acceptedLanguages = new ArrayList<LanguageCode>();
		acceptedLanguages.add(LanguageCode.GR);
		ProblemDefinition problem111 = new ProblemDefinition("1.1.1", category1, 10, acceptedLanguages);
		ProblemDefinition problem121 = new ProblemDefinition("1.2.1", category2, 10, acceptedLanguages);
		ProblemDefinition problem122 = new ProblemDefinition("1.2.2", category3, 10, acceptedLanguages);
		ProblemDefinition problem123 = new ProblemDefinition("1.2.3", category4, 10, acceptedLanguages);
		ProblemDefinition problem124 = new ProblemDefinition("1.2.4", category4, 10, acceptedLanguages);

		index.add(problem111);
		index.add(problem121);
		index.add(problem122);
		index.add(problem123);
		index.add(problem124);

	}
	@Override
	public ArrayList<ProblemDefinition> getProblemsByLanguage(LanguageCode x) {
		ArrayList<ProblemDefinition> temp = new ArrayList<ProblemDefinition>();
		for(ProblemDefinition problem : index) {
			if (problem.getAvailableLanguages().contains(x)) {
				temp.add(problem);
			}
		}
		return temp;
	}

	@Override
	public ArrayList<ProblemDefinition> getProblemsByCategory(Category x) {
		ArrayList<ProblemDefinition> temp = new ArrayList<ProblemDefinition>();
		for(ProblemDefinition problem : index) {
			if (problem.getType().equals(x)) {
				temp.add(problem);
			}
		}
		return temp;
	}

}

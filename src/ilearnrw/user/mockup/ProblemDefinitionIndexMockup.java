package ilearnrw.user.mockup;

import java.util.ArrayList;
import java.util.List;

import ilearnrw.user.Category;
import ilearnrw.user.LanguageCode;
import ilearnrw.user.ProblemDefinition;
import ilearnrw.user.ProblemDefinitionIndexApi;
import ilearnrw.user.Subcategory;

public class ProblemDefinitionIndexMockup implements ProblemDefinitionIndexApi{

	private List<ProblemDefinition> index = new ArrayList<ProblemDefinition>();
	
	public ProblemDefinitionIndexMockup()
	{
		Category category1 = new Category("PHONOLOGY - SUB-WORD LEVEL: LETTER RECOGNITION PROBLEMS", true);
		Subcategory recognizeLetters11 = new Subcategory("Difficulty in recognizing letters: Auditory-based errors", category1);
		Subcategory recognizeLetters12 = new Subcategory("Difficulty in recognizing letters: Visually-based errors", category1);

		Category category2 = new Category("WORD LEVEL: WORD RECOGNITION PROBLEMS", true);
		Subcategory wordRecognition21 = new Subcategory("Word recognition: Visually-based errors", category2);
		Subcategory wordRecognition22 = new Subcategory("Word recognition: Semantically-based errors", category2);
		

		ArrayList<LanguageCode> acceptedLanguages = new ArrayList<LanguageCode>();
		acceptedLanguages.add(LanguageCode.GR);
		ProblemDefinition problem111 = new ProblemDefinition("1.1.1", recognizeLetters11, 10, acceptedLanguages);
		ProblemDefinition problem121 = new ProblemDefinition("1.2.1", recognizeLetters12, 10, acceptedLanguages);
		ProblemDefinition problem122 = new ProblemDefinition("1.2.2", recognizeLetters12, 10, acceptedLanguages);
		ProblemDefinition problem123 = new ProblemDefinition("1.2.3", recognizeLetters12, 10, acceptedLanguages);
		ProblemDefinition problem124 = new ProblemDefinition("1.2.4", recognizeLetters12, 10, acceptedLanguages);

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
			if (problem.getType().getCategory().equals(x)) {
				temp.add(problem);
			}
		}
		return temp;
	}

}

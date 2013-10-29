package ilearnrw.user.mockup;

import java.util.ArrayList;
import java.util.List;

import ilearnrw.user.IlearnException;
import ilearnrw.user.LanguageCode;
import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDefinitionIndexApi;
import ilearnrw.user.problems.ProblemDescription;
import ilearnrw.user.problems.ProblemType;

public class ProblemDefinitionIndexMockup{

	private ProblemDefinitionIndex index;
	
	public ProblemDefinitionIndexMockup(){
		index = new ProblemDefinitionIndex(5, LanguageCode.EN);
		
		
		Category category1 = new Category("PHONOLOGY-SUB-WORDLEVEL.Difficultyinrecognizingletters.Auditory-basederrors");
		Category category2 = new Category("PHONOLOGY-SUB-WORDLEVEL.Difficultyinrecognizingletters.Visually-basederrors");

		Category category3 = new Category("WORDLEVEL.Wordrecognition.Visually-basederrors");
		Category category4 = new Category("WORDLEVEL.Wordrecognition.Semantically-basederrors");
		
	

		try {
			index.constructProblemRow(0, 2);
			ProblemDefinition problem111 = new ProblemDefinition("suffixing", category1);
			index.setProblemDefinition(problem111, 0);
			index.setProblemDescription(ProblemType.X, new String[]{"-ing"}, 0, 0);
			index.setProblemDescription(ProblemType.X, new String[]{"-es"}, 0, 1);		
	
			index.constructProblemRow(1, 2);
			ProblemDefinition problem121 = new ProblemDefinition("affixing", category2);
			index.setProblemDefinition(problem121, 1);
			index.setProblemDescription(ProblemType.X, new String[]{"un-"}, 1, 0);
			index.setProblemDescription(ProblemType.X, new String[]{"dis-"}, 1, 1);	
	
			index.constructProblemRow(2, 1);
			ProblemDefinition problem122 = new ProblemDefinition("something", category3);
			index.setProblemDefinition(problem122, 1);
			index.setProblemDescription(ProblemType.X, new String[]{"xmmm"}, 2, 0);
	
			index.constructProblemRow(3, 2);
			ProblemDefinition problem123 = new ProblemDefinition("test", category4);
			index.setProblemDefinition(problem123, 1);
			index.setProblemDescription(ProblemType.X, new String[]{"one", "two"}, 3, 0);
			index.setProblemDescription(ProblemType.X, new String[]{"eleven", "twelve"}, 3, 1);
	
			index.constructProblemRow(4, 2);
			ProblemDefinition problem124 = new ProblemDefinition("last", category4);
			index.setProblemDefinition(problem124, 1);
			index.setProblemDescription(ProblemType.X, new String[]{"big  problem"}, 3, 0);
			index.setProblemDescription(ProblemType.X, new String[]{"huge problem"}, 3, 1);	
		} 
		catch (IlearnException e) {
			e.printStackTrace();
		}
			

	}

}

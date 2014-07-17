package ilearnrw.user.mockup;

import java.util.ArrayList;
import java.util.List;

import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDefinitionIndexApi;
import ilearnrw.user.problems.ProblemDescription;
import ilearnrw.user.problems.ProblemType;
import ilearnrw.utils.IlearnException;
import ilearnrw.utils.LanguageCode;

public class ProblemDefinitionIndexMockup{

	private ProblemDefinitionIndex index;
	
	public ProblemDefinitionIndexMockup(){
		index = new ProblemDefinitionIndex(5, LanguageCode.EN);
		
		
		Category category1 = new Category("PHONOLOGY-SUB-WORDLEVEL.Difficultyinrecognizingletters.Auditory-basederrors");
		Category category2 = new Category("PHONOLOGY-SUB-WORDLEVEL.Difficultyinrecognizingletters.Visually-basederrors");

		Category category3 = new Category("WORDLEVEL.Wordrecognition.Visually-basederrors");
		Category category4 = new Category("WORDLEVEL.Wordrecognition.Semantically-basederrors");

		index.constructProblemRow(0, 2);
		ProblemDefinition problem111 = new ProblemDefinition("suffixing", category1);
		index.setProblemDefinition(problem111, 0);
		index.setProblemDescription(ProblemType.X, new String[]{"-ing"}, 
				"-ing problem", 0, 0, 0);
		index.setProblemDescription(ProblemType.X, new String[]{"-es"}, 
				"-es problem", 0, 0, 1);		
	
		index.constructProblemRow(1, 2);
		ProblemDefinition problem121 = new ProblemDefinition("affixing", category2);
		index.setProblemDefinition(problem121, 1);
		index.setProblemDescription(ProblemType.X, new String[]{"un-"}, 
				"un- problem", 0, 1, 0);
		index.setProblemDescription(ProblemType.X, new String[]{"dis-"}, 
				"dis- problem", 1, 1, 1);	
	
		index.constructProblemRow(2, 1);
		ProblemDefinition problem122 = new ProblemDefinition("something", category3);
		index.setProblemDefinition(problem122, 1);
		index.setProblemDescription(ProblemType.X, new String[]{"xmmm"}, 
				"xmmm problem", 0, 2, 0);
	
		index.constructProblemRow(3, 2);
		ProblemDefinition problem123 = new ProblemDefinition("test", category4);
		index.setProblemDefinition(problem123, 1);
		index.setProblemDescription(ProblemType.X, new String[]{"one", "two"}, 
				"x problem", 0, 3, 0);
		index.setProblemDescription(ProblemType.X, new String[]{"eleven", "twelve"}, 
				"x problem", 0, 3, 1);
	
		index.constructProblemRow(4, 2);
		ProblemDefinition problem124 = new ProblemDefinition("last", category4);
		index.setProblemDefinition(problem124, 1);
		index.setProblemDescription(ProblemType.X, new String[]{"big  problem"}, 
				"x problem", 0, 4, 0);
		index.setProblemDescription(ProblemType.X, new String[]{"huge problem"}, 
				"x problem", 0, 4, 1);	

	}

}

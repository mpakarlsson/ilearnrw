package ilearnrw.user;

import java.util.ArrayList;
import java.util.Map;

public class UserProblems {
	private Map<ProblemDefinition, Integer> index;
	
	public void loadProblems(LanguageCode lc){
		//this code is going to be load some kind of csv file or entries from a db
		//depending on the language of the user that calls this method
		ArrayList<LanguageCode> availableLanguages = new ArrayList<LanguageCode>();
		availableLanguages.add(LanguageCode.EN);
		availableLanguages.add(LanguageCode.GR);
		ArrayList<LanguageCode> greek = new ArrayList<LanguageCode>();
		greek.add(LanguageCode.GR);
		ArrayList<LanguageCode> english = new ArrayList<LanguageCode>();
		english.add(LanguageCode.EN);
		ProblemDefinition pr;
		for (int i=0; i<5; i++){
			switch (i%3){
				case 1:
				pr = new ProblemDefinition("URI"+i, "category", 
						10, availableLanguages);
				break;
				case 2:
				pr = new ProblemDefinition("URI"+i, "category", 
						10, greek);
				break;
				default:
				pr = new ProblemDefinition("URI"+i, "category", 
						10, english);
			}
			//here is the logic!
			if (pr.getAvailableLanguages().contains(lc))
				index.put(pr, i);
		}
	}
}

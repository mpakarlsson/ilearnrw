package ilearnrw.user;

import java.util.ArrayList; 

public class UserProblemsList {
	private ArrayList<ProblemDefinition> list;
	
	public UserProblemsList(int userId) {
		this.list = new ArrayList<ProblemDefinition>();
		loadProblems(userId);
	}
	
	public UserProblemsList(LanguageCode lc) {
		this.list = new ArrayList<ProblemDefinition>();
		loadProblems(lc);
	}
	
	public UserProblemsList(ArrayList<ProblemDefinition> list) {
		this.list = new ArrayList<ProblemDefinition>();
		this.list = list;
	}

	//implement methods like:
	//hasProblem(problem name)
	//hasProblemOfCategory(category of problem here)
	//getProblemsOfCategory(category of problem here)
	//hasProblemOfSubCategory(SubCategory of problem here)
	//getProblemsOfSubCategory(SubCategory of problem here)
	//hasProblem(problem name, int threshold)
	//hasProblemOfCategory(category of problem here, int threshold)
	//getProblemsOfCategory(category of problem here, int threshold)
	//hasProblemOfSubCategory(SubCategory of problem here, int threshold)
	//getProblemsOfSubCategory(SubCategory of problem here, int threshold+)
	//getBiggerProblem()
	//getAverageOfScoresOfCategory(category of problem here) *requires that all scores have the same upper bound!
	//getAverageOfScoresOfSubCategory(category of problem here) *requires that all scores have the same upper bound!
	

	public void loadProblems(LanguageCode lc){
		//this code is going to be load some kind of csv file or entries from a db
		//depending on the language of the user that calls this method
		Category reading = new Category("Reading Problem", true);
		Category writting = new Category("Writting Problem", false);
		Subcategory suffixing = new Subcategory("Suffixing", writting);
		Subcategory phonemeGrapheme = 
				new Subcategory("Phoneme Grapheme Correspondance", reading);
		ArrayList<LanguageCode> bothLanguages = new ArrayList<LanguageCode>();
		bothLanguages.add(LanguageCode.EN);
		bothLanguages.add(LanguageCode.GR);
		ArrayList<LanguageCode> greekLanguage = new ArrayList<LanguageCode>();
		greekLanguage.add(LanguageCode.GR);
		ArrayList<LanguageCode> englishLanguage = new ArrayList<LanguageCode>();
		englishLanguage.add(LanguageCode.EN);
		ProblemDefinition pr;
		for (int i=0; i<5; i++){
			switch (i%3){
				case 1:
				pr = new ProblemDefinition("URI"+i, suffixing, 
						10, bothLanguages);
				break;
				case 2:
				pr = new ProblemDefinition("URI"+i, suffixing, 
						10, greekLanguage);
				break;
				default:
				pr = new ProblemDefinition("URI"+i, phonemeGrapheme, 
						10, englishLanguage);
			}
			//the logic has to be here...
			if (pr.getAvailableLanguages().contains(lc))
				list.add(pr);
		}
	}
	public void loadProblems(int userId){
		//this code is going to be load some kind of csv file or entries from a db
		//depending on the language of the user that calls this method
		Category reading = new Category("Reading Problem", true);
		Category writting = new Category("Writting Problem", false);
		Subcategory suffixing = new Subcategory("Suffixing", writting);
		Subcategory phonemeGrapheme = 
				new Subcategory("Phoneme Grapheme Correspondance", reading);
		ArrayList<LanguageCode> bothLanguages = new ArrayList<LanguageCode>();
		bothLanguages.add(LanguageCode.EN);
		bothLanguages.add(LanguageCode.GR);
		ArrayList<LanguageCode> greekLanguage = new ArrayList<LanguageCode>();
		greekLanguage.add(LanguageCode.GR);
		ArrayList<LanguageCode> englishLanguage = new ArrayList<LanguageCode>();
		englishLanguage.add(LanguageCode.EN);
		ProblemDefinition pr;
		for (int i=0; i<5; i++){
			switch (i%3){
				case 1:
				pr = new ProblemDefinition("URI"+i, suffixing, 
						10, bothLanguages);
				break;
				case 2:
				pr = new ProblemDefinition("URI"+i, suffixing, 
						10, greekLanguage);
				break;
				default:
				pr = new ProblemDefinition("URI"+i, phonemeGrapheme, 
						10, englishLanguage);
			}
			//the logic has to be here...
			list.add(pr);
		}
	}

	public ArrayList<ProblemDefinition> getList() {
		return list;
	}

	public void setList(ArrayList<ProblemDefinition> list) {
		this.list = list;
	}
	
	
}

package ilearnrw.user;

import ilearnrw.games.NumberProblems;
import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinitionIndex;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Random;

public class UserProblemsMatrix implements Serializable {
	private static final long serialVersionUID = 1L;

	private ProblemDefinitionIndex theProblems;
	private int indices[];
	private int severities[][];
	
	public UserProblemsMatrix(){
		loadTestProblems();
	}
	
	public UserProblemsMatrix(ProblemDefinitionIndex theProblems){
		initialize(theProblems);
	}
	
	private void initialize(ProblemDefinitionIndex theProblems){
		this.theProblems = theProblems;
		int idxLen = theProblems.getIndexLength();
		indices = new int[idxLen];
		severities = new int[idxLen][];
		for (int i=0; i<idxLen; i++){
			severities[i] = new int[theProblems.getIthRowLength(i)];
		}

	}
	
	/*
	public UserProblemsList(int userId) {
		this.list = new ArrayList<UserProblem>();
		loadProblems(userId);
	}
	
	public UserProblemsList(LanguageCode lc) {
		this.list = new ArrayList<UserProblem>();
		loadProblems(lc);
	}
	
	public UserProblemsList(ArrayList<UserProblem> list) {
		this.list = new ArrayList<UserProblem>();
		this.list = list;
	}*/

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
	

	/*
	public void loadProblems(LanguageCode lc){
		//this code is going to be load some kind of csv file or entries from a db
		//depending on the language of the user that calls this method
		Category reading = new Category("ReadingProblem.Suffixing");
		Category writting = new Category("WrittingProblem");
		Category suffixing = new Category("Suffixing.PhonemeGraphemeCorrespondance");
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
				pr = new ProblemDefinition("URI"+i, writting, 
						10, greekLanguage);
				break;
				default:
				pr = new ProblemDefinition("URI"+i, reading, 
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
		Category reading = new Category("ReadingProblem.Suffixing");
		Category writting = new Category("WrittingProblem");
		Category suffixing = new Category("Suffixing.PhonemeGraphemeCorrespondance");
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
				pr = new ProblemDefinition("URI"+i, reading, 
						10, greekLanguage);
				break;
				default:
				pr = new ProblemDefinition("URI"+i, writting, 
						10, englishLanguage);
			}
			//the logic has to be here...
			list.add(pr);
		}
	}*/
	
	public void loadTestProblems(){
		NumberProblems numProbs = new NumberProblems();
		initialize(numProbs.getAllProblems());
		Random rand = new Random();
		for (int i=0;i<indices.length; i++){
			indices[i] = rand.nextInt(theProblems.getIthRowLength(i));
			for (int j=0; j<severities[i].length; j++){
				severities[i][j] = rand.nextInt(3)+1;
			}
		}
		
	}

	public int getSeverity(int i, int j) {
		return severities[i][j];
	}

	public int getIthIndex(int i) {
		return indices[i];
	}
	
	@Override
	public String toString(){
		if (indices == null)
			return "null indices matrix";
		String res = "[ ";
		for (int i=0; i<indices.length; i++){
			res = res + indices[i] + " ] : |";
			for (int j=0; j<severities[i].length; j++){
				res = res + severities[i][j] + " | ";
			}
			res = res + "\n";
		}
		return res;
	}
	
}

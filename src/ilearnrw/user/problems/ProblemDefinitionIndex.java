package ilearnrw.user.problems;

import java.io.Serializable;

import ilearnrw.utils.LanguageCode;

public class ProblemDefinitionIndex implements Serializable,ProblemDefinitionIndexApi {
    private static final long serialVersionUID = 1L;
	private ProblemDefinition problemsIndex[];
	private ProblemDescription problems[][];

	LanguageCode language;

	public ProblemDefinitionIndex() {
		problemsIndex = null;
		problems = null;
	}

	public ProblemDefinitionIndex(int length, LanguageCode language) {
		problemsIndex = new ProblemDefinition[length];
		problems = new ProblemDescription[length][];
		this.language = language;
	}
	
	public void setProblemDefinition(ProblemDefinition prob, int position){
		problemsIndex[position] = prob;
	}
	
	public ProblemDefinition getProblemDefinition(int idx){
		return problemsIndex[idx];
	}

	public int getCategoryFirstIndex(Category x) {
		for (int i = 0; i < problemsIndex.length; i++) {
			if (problemsIndex[i].getType().equals(x))
				return i;
		}
		return -1;
	}

	public int getCategoryLastIndex(Category x) {
		for (int i = problemsIndex.length-1; i>=0; i--) {
			if (problemsIndex[i].getType().equals(x))
				return i;
		}
		return -1;
	}
	
	public void constructProblemRow(int i, int length){
		problems[i] = new ProblemDescription[length];
	}
	
	public void setProblemDescription(ProblemType problemType, String descriptions[], int i, int j){
			problems[i][j] = new ProblemDescription(problemType, descriptions);
	}
	
	public void setProblemDescription(ProblemDescription description, int i, int j){
		problems[i][j] = description;
	}
	
	public ProblemDescription getProblemDescription(int i, int j){
		return problems[i][j];
	}
	
	public int getIndexLength(){
		return problemsIndex.length;
	}
	
	public void setIndexLength(int length){
		problemsIndex = new ProblemDefinition[length];
		problems = new ProblemDescription[length][];
	}
	
	public int getRowLength(int i){
		return problems[i].length;
	}

	public LanguageCode getLanguage() {
		return language;
	}

	public void setLanguage(LanguageCode language) {
		this.language = language;
	}
	
	public ProblemDescription[][] getProblems() {
		return problems;
	}

	public void setProblems(ProblemDescription[][] problems) {
		this.problems = problems;
	}

	
	public ProblemDefinition[] getProblemsIndex() {
		return problemsIndex;
	}

	public void setProblemsIndex(ProblemDefinition[] problemsIndex) {
		this.problemsIndex = problemsIndex;
	}

	@Override
	public String toString(){
		String res = "[ ";
		for (int i=0; i<problemsIndex.length; i++){
			res = res + problemsIndex[i] + " ] : |";
			for (int j=0; j<problems[i].length; j++){
				res = res + problems[i][j].toString() + " | ";
			}
			res = res + "\n";
		}
		return res;
	}
}

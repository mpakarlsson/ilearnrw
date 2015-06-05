package ilearnrw.user.problems;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.io.Serializable;

import ilearnrw.prototype.application.JsonHandler;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.utils.LanguageCode;

public class ProblemDefinitionIndex implements Serializable, ProblemDefinitionIndexApi {
    private static final long serialVersionUID = 1L;
    // problemsIndex contains the description of each category
	private ProblemDefinition problemsIndex[];
	// the problems matrix contains all possible problems for a language
	private ProblemDescription problems[][];

	private LanguageCode language;

	public ProblemDefinitionIndex() {
		problemsIndex = null;
		problems = null;
	}

	public ProblemDefinitionIndex(int length, LanguageCode language) {
		problemsIndex = new ProblemDefinition[length];
		problems = new ProblemDescription[length][];
		this.language = language;
	}

	public ProblemDefinitionIndex(LanguageCode language) {
		ProblemDefinitionIndex tmp;
		this.language = language;
		if (language == LanguageCode.EN){
			JsonHandler handler = new JsonHandler(Type.DATA, "problem_definitions_en.json", true);
			tmp = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
		}
		else{
			JsonHandler handler = new JsonHandler(Type.DATA, "problem_definitions_greece.json", true);
			tmp = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
		}
		this.problemsIndex = tmp.getProblemsIndex();
		this.problems = tmp.getProblems();
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
	
	public void setProblemDescription(ProblemType problemType, String descriptions[], 
			String humanReadableDescription, int cluster, String character, int i, int j){
			problems[i][j] = new ProblemDescription(problemType, descriptions, 
					humanReadableDescription, cluster, character);
	}
	
	public void setProblemDescription(ProblemDescription description, int i, int j){
		problems[i][j] = description;
	}
	
	public ProblemDescription getProblemDescription(int i, int j){
		return problems[i][j];
	}
	
	public int returnIndexLength(){
		if (problemsIndex != null)
			return problemsIndex.length;
		return 0;
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
		if (problemsIndex==null) return "ProblemDefinitionIndex object is null";
		String res = "[ ";
		for (int i=0; i<problemsIndex.length; i++){
			res = res + problemsIndex[i] + " ] : |";
			for (int j=0; j<problems[i].length; j++){
				if (problems[i][j] != null)
					res = res + problems[i][j].toString() + " | ";
				else
					res = res + "null" + " | ";
			}
			res = res + "\n";
		}
		return res;
	}
}

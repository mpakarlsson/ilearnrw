package ilearnrw.user.problems;

import ilearnrw.user.IlearnException;
import ilearnrw.user.LanguageCode;

import java.util.ArrayList;

public class ProblemDefinitionIndex implements ProblemDefinitionIndexApi {
	private ArrayList<ProblemDefinition> index;

	public ProblemDefinitionIndex() {
		index = new ArrayList<ProblemDefinition>();
	}
	
	public void addProblemDefinition(ProblemDefinition prob){
		index.add(prob);
	}
	
	public ArrayList<ProblemDefinition> getAllProblems(){
		return index;
	}
	
	public ProblemDefinition getProblemDefinition(int idx) throws IlearnException{
		if (index.size()<idx || idx<0) throw new IlearnException("Index out of Bounds");
		return index.get(idx);
	}

	public ArrayList<ProblemDefinition> getProblemsByLanguage(LanguageCode x) {
		ArrayList<ProblemDefinition> res = new ArrayList<ProblemDefinition>();
		if (index == null)
			return res;
		for (int i = 0; i < index.size(); i++) {
			if (index.get(i).getAvailableLanguages().contains(x))
				res.add(index.get(i));
		}
		return res;
	}

	public ArrayList<ProblemDefinition> getProblemsByCategory(Category x) {
		ArrayList<ProblemDefinition> res = new ArrayList<ProblemDefinition>();
		if (index == null)
			return res;
		for (int i = 0; i < index.size(); i++) {
			if (index.get(i).getType().equals(x))
				res.add(index.get(i));
		}
		return res;
	}
}

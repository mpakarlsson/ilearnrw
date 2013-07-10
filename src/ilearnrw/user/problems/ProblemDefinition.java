package ilearnrw.user.problems;

import ilearnrw.user.IlearnException;
import ilearnrw.user.LanguageCode;

import java.io.Serializable;
import java.util.ArrayList;

public class ProblemDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	private String URI;
	private Category type;
	private ArrayList<ProblemNode> problemNodes;

	int scoreUpperBound;
	ArrayList<LanguageCode> availableLanguages;

	public ProblemDefinition(String URI, Category type, int scoreUpperBound,
			ArrayList<LanguageCode> availableLanguages) {
		this.URI = URI;
		this.type = type;
		this.scoreUpperBound = scoreUpperBound;
		this.availableLanguages = availableLanguages;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public Category getType() {
		return type;
	}

	public void setType(Category type) {
		this.type = type;
	}

	public int getScoreUpperBound() {
		return scoreUpperBound;
	}

	public void setScoreUpperBound(int scoreUpperBound) {
		this.scoreUpperBound = scoreUpperBound;
	}

	public ArrayList<LanguageCode> getAvailableLanguages() {
		return availableLanguages;
	}

	public void setAvailableLanguages(ArrayList<LanguageCode> availableLanguages) {
		this.availableLanguages = availableLanguages;
	}

	public ArrayList<ProblemNode> getProblemNodes() {
		return problemNodes;
	}

	public void setProblemNodes(ArrayList<ProblemNode> problemNodes) {
		this.problemNodes = problemNodes;
	}
	
	public void addProblemNode(ProblemNode node){
		problemNodes.add(node);
	}
	
	public ProblemNode getProblemNode(int idx) throws IlearnException{
		if (problemNodes == null) throw new IlearnException("Null Problem Node List");
		if (problemNodes.size()==0) throw new IlearnException("Empty Problem Node List");
		if (idx<0 || idx>scoreUpperBound) throw new IlearnException("Problem Node Out Of Bounds");
		else {
			return problemNodes.get(idx);
		}
	}

	@Override
	public String toString() {
		return "ProblemDefinition [URI=" + URI + ", type=" + type
				+ ",\n scoreUpperBound=" + scoreUpperBound
				+ ",\n availableLanguages=" + availableLanguages + "]\n";
	}

}
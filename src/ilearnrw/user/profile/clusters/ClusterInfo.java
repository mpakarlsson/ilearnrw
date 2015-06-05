package ilearnrw.user.profile.clusters;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.problems.ProblemDescription;

import java.util.ArrayList;

public class ClusterInfo {
	private int clusterNumber;
	ArrayList<ProblemDescriptionCoordinates> relatedProblems;
	
	public ClusterInfo(int clusterNumber, ArrayList<ProblemDescriptionCoordinates> relatedProblems) {
		this.clusterNumber = clusterNumber;
		this.relatedProblems = relatedProblems;
	}
	
	public ClusterInfo(int clusterNumber) {
		this.clusterNumber = clusterNumber;
		this.relatedProblems = new ArrayList<ProblemDescriptionCoordinates>();
	}
	
	public ClusterInfo() {
		this.clusterNumber = -1;
		this.relatedProblems = new ArrayList<ProblemDescriptionCoordinates>();
	}

	public int getClusterNumber() {
		return clusterNumber;
	}

	public void setClusterNumber(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}

	public ArrayList<ProblemDescriptionCoordinates> getRelatedProblems() {
		return relatedProblems;
	}

	public void setRelatedProblems(ArrayList<ProblemDescriptionCoordinates> relatedProblems) {
		this.relatedProblems = relatedProblems;
	}

	public void addRelatedProblem(ProblemDescriptionCoordinates relatedProblem) {
		this.relatedProblems.add(relatedProblem);
	}

	public void addRelatedProblem(int category, int index, ProblemDescription problemDescription) {
		this.relatedProblems.add(new ProblemDescriptionCoordinates(category, index, problemDescription));
	}
}

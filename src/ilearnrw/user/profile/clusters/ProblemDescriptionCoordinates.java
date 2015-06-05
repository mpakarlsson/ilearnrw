package ilearnrw.user.profile.clusters;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.problems.ProblemDescription;

public class ProblemDescriptionCoordinates {
	private int category, index;
	private ProblemDescription problemDescription;

	public ProblemDescriptionCoordinates() {
		this.category = -1;
		this.index = -1;
		this.problemDescription = new ProblemDescription();
	}
	
	public ProblemDescriptionCoordinates(int category, int index, ProblemDescription problemDescription) {
		this.category = category;
		this.index = index;
		this.problemDescription = problemDescription;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ProblemDescription getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDefinition(ProblemDescription problemDescription) {
		this.problemDescription = problemDescription;
	}
}

package ilearnrw.user.problems;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.utils.IlearnException;


public interface ProblemDefinitionIndexApi {
	public int getCategoryFirstIndex(Category x);
	public int getCategoryLastIndex(Category x);
	public void constructProblemRow(int i, int length);
	public void setProblemDescription(ProblemDescription description, int i, int j) throws IlearnException;
	public ProblemDescription getProblemDescription(int i, int j) throws IlearnException;
}

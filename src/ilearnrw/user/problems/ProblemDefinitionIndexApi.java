package ilearnrw.user.problems;

import ilearnrw.user.IlearnException;
import ilearnrw.user.LanguageCode;

import java.util.ArrayList;

public interface ProblemDefinitionIndexApi {
	public int getCategoryFirstIndex(Category x);
	public int getCategoryLastIndex(Category x);
	public void constructProblemRow(int i, int length);
	public void setProblemDescription(ProblemDescription description, int i, int j) throws IlearnException;
	public ProblemDescription getProblemDescription(int i, int j) throws IlearnException;
}

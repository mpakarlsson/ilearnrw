package ilearnrw.utils.screening;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.util.ArrayList;

public interface ScreeningTestIP {
	public ArrayList<TestQuestion> getQuestionsList();
	public ArrayList<ClusterTestQuestions> getQuestions();
	public ArrayList<TestQuestion> getClusterQuestions(int c);
	public void storeTest(String filename);
	public void loadTest(String filename);
}

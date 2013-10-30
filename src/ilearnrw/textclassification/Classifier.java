package ilearnrw.textclassification;

import ilearnrw.user.User;
import ilearnrw.user.UserSeverities;
import ilearnrw.user.UserSeveritiesToProblems;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDescription;
import ilearnrw.user.problems.ProblemType;

public class Classifier {

	private ProblemDefinitionIndex theProblems;
	private UserSeverities theSeverities;
	private Text text;
	
	public Classifier(User user, Text text) {
		this.theProblems = user.getProfile().getSeveritiesToProblemsMatrix().getProblemDefinitionIndex();
		this.theSeverities = user.getProfile().getSeveritiesToProblemsMatrix().getUserSeverities();
	}
	
	public void test(){
		for (Sentence sen : text.getSentences()){
			for (Word w : sen.getWords()){
				checkWordAgainstMatrix(w);
			}
		}
	}
	
	public void checkWordAgainstMatrix(Word w){
		for (int i=0;i<theProblems.getIndexLength(); i++){
			for (int j=0;j<theProblems.getIthRowLength(i); j++){
				if (theSeverities.getSeverity(i, j)>1 && 
						wordMatches(w, theProblems.getProblemDescription(i, j)).getFound()){
					System.out.println(wordMatches(w, theProblems.getProblemDescription(i, j)).toString());
				}
			}
		}
	}

	private WordProblemInfo wordMatches(Word w, ProblemDescription pd){
		switch (pd.getProblemType()){
		case EQUALS:
			return true;
		case CONTAINS:
			return true;
		}
	}
	
	private WordProblemInfo contains(Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo isNounAndEndsWith(Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	//or
	private WordProblemInfo isAdjAndEndsWith(Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
	
	private WordProblemInfo (Word x, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
	}
			 [what, start, end]
			 [what, start, end]
			 [what, start, end]
			 [what, start, end]
			IS_PARTICIPLE_AND_ENDS_WITH [what, start, end]
			IS_NOUN_OR_ADJ_AND_STARTS_WITH [what, start, end]
			IS_ADJ_AND_STARTS_WITH [what, start, end]
			IS_VERB_AND_STARTS_WITH [what, start, end]
			TWO_SYL_WORD_INITIAL_PHONEME [what, start, end]
			TWO_SYL_WORD_INTERNAL_PHONEME [what, start, end]
			THREE_SYL_WORD_INITIAL_PHONEME [what, start, end]
			THREE_SYL_WORD_INTERNAL_PHONEME [what, start, end]
			STARTS_WITH [what, start, end]
			CONTAINS_PATTERN [what, start, end]
			CONTAINS_PHONEME [what, start, end]
			EQUALS

}

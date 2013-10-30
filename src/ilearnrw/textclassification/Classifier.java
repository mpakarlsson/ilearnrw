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
		this.text = text;
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
				if (theSeverities.getSeverity(i, j)>0 && 
						wordMatches(w, i, j).getFound()){
					System.out.println("Problem ("+i+", "+j+")");
					System.out.println(wordMatches(w, i, j).toString());
				}
			}
		}
	}

	private WordProblemInfo wordMatches(Word w, int i, int j){
		WordProblemInfo wpi = new WordProblemInfo();
		//ask for the small strings that describe the problem
		String pd[] = theProblems.getProblemDescription(i, j).getDescriptions();
		//ask the problem type
		ProblemType pt = theProblems.getProblemDescription(i, j).getProblemType();
		
		switch (pt){
			case EQUALS:
				wpi.setProblemInfo(theProblems.getProblemDefinition(i), equals(pd, w), 
						theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, theProblems.getIthRowLength(i));
				break;
			case CONTAINS:
				wpi.setProblemInfo(theProblems.getProblemDefinition(i), contains(pd, w), 
						theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, theProblems.getIthRowLength(i));
				break;
			case IS_NOUN_OR_ADJ_AND_ENDS_WITH:
				if (isNoun(w) || isAdj(w))
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), endsWith(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case IS_NOUN_AND_ENDS_WITH:
				if (isNoun(w))
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), endsWith(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case IS_ADJ_AND_ENDS_WITH:
				if (isAdj(w))
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), endsWith(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case IS_VERB_AND_ENDS_WITH:
				if (isVerb(w))
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), endsWith(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case IS_PARTICIPLE_AND_ENDS_WITH:
				if (isParticiple(w))
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), endsWith(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case IS_NOUN_OR_ADJ_AND_STARTS_WITH:
				if (isNoun(w) || isAdj(w))
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), startsWith(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case IS_ADJ_AND_STARTS_WITH:
				if (isAdj(w))
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), startsWith(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case IS_VERB_AND_STARTS_WITH:
				if (isVerb(w))
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), startsWith(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case TWO_SYL_WORD_INITIAL_PHONEME:
				if (w.getNumberOfSyllables()==2)
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), startsWithPhoneme(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case TWO_SYL_WORD_INTERNAL_PHONEME:
				if (w.getNumberOfSyllables()==2)
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), hasInternalPhoneme(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case THREE_SYL_WORD_INITIAL_PHONEME:
				if (w.getNumberOfSyllables()==3)
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), startsWithPhoneme(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case THREE_SYL_WORD_INTERNAL_PHONEME:
				if (w.getNumberOfSyllables()==3)
					wpi.setProblemInfo(theProblems.getProblemDefinition(i), hasInternalPhoneme(pd, w), 
							theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
							theProblems.getIthRowLength(i));
				break;
			case STARTS_WITH:
				wpi.setProblemInfo(theProblems.getProblemDefinition(i), startsWith(pd, w), 
						theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
						theProblems.getIthRowLength(i));
				break;
			case CONTAINS_PATTERN:
				wpi.setProblemInfo(theProblems.getProblemDefinition(i), containsPattern(pd, w), 
						theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
						theProblems.getIthRowLength(i));
				break;
			case CONTAINS_PHONEME:
				wpi.setProblemInfo(theProblems.getProblemDefinition(i), containsPhoneme(pd, w), 
						theSeverities.getSeverity(i, j), theSeverities.getIndex(i), j, 
						theProblems.getIthRowLength(i));
		}
		return wpi;
	}
	
	private StringMatchesInfo equals(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.equals(str[i])){
				return new StringMatchesInfo(str[i], 0, ws.length());
			}
		}
		return null;
	}
	
	private StringMatchesInfo contains(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}
	
	private StringMatchesInfo endsWith(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.endsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.lastIndexOf(str[i]), ws.lastIndexOf(str[i])+str[i].length());
			}
		}
		return null;
	}
	//or
	private StringMatchesInfo startsWith(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	private StringMatchesInfo containsPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	private StringMatchesInfo hasInternalPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i]) && !ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	private StringMatchesInfo startsWithPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	private StringMatchesInfo containsPattern(String str[], Word w){
		String ws = w.getCVForm();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	private boolean isNoun(Word w){
		return true;
	}
	private boolean isAdj(Word w){
		return true;
	}
	private boolean isVerb(Word w){
		return true;
	}
	private boolean isParticiple(Word w){
		return true;
	}

}

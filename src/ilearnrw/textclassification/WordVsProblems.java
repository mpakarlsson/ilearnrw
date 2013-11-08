package ilearnrw.textclassification;

import java.util.ArrayList;
import java.util.regex.Matcher;

import ilearnrw.languagetools.greek.GreekPartOfSpeech;
import ilearnrw.user.problems.GreekProblems;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemType;
import ilearnrw.user.problems.Problems;
import ilearnrw.utils.LanguageCode;

public class WordVsProblems {

	private ProblemDefinitionIndex theProblems;
	private Word word;
	private ArrayList<WordProblemInfo> matchedProbs;
	private Problems prs;
	private LanguageCode lc;
	
	public WordVsProblems(LanguageCode lc) {
		Problems prs = new Problems();
		this.lc = lc;
				
		switch (lc) {
		case GR:
			prs = new GreekProblems();
			break;
		case EN:
			// TODO prs = new EnglishProblems();
			prs = new GreekProblems();
			this.theProblems = prs.getAllProblems();
			break;
		}
		this.theProblems = prs.getAllProblems();
	}

	
	public void insertWord(Word word) {
		matchedProbs = new ArrayList<WordProblemInfo>();
		this.word = word;
		
		checkWordAgainstMatrix();
	}
	
	public void checkWordAgainstMatrix(){
		for (int i=0;i<theProblems.getIndexLength(); i++){
			for (int j=0;j<theProblems.getRowLength(i); j++){
				if (wordMatches(i, j).getFound()){
					matchedProbs.add(wordMatches(i, j));
				}
			}
		}
	}

	public LanguageCode getLanguageCode() {
		return lc;
	}

	public void setLanguageCode(LanguageCode lc) {
		this.lc = lc;
	}

	public ArrayList<WordProblemInfo> getMatchedProbs() {
		return matchedProbs;
	}

	public boolean containsPosition(int i, int j){
		for (WordProblemInfo wpi : matchedProbs){
			if (wpi.getPosI()==i && wpi.getPosJ()==j)
				return true;
		}
		return false;
	}

	private WordProblemInfo wordMatches(int i, int j){
		WordProblemInfo wpi = new WordProblemInfo(word.getLanguageCode());
		//ask for the small strings that describe the problem
		String pd[] = theProblems.getProblemDescription(i, j).getDescriptions();
		//ask the problem type
		ProblemType pt = theProblems.getProblemDescription(i, j).getProblemType();
		StringMatchesInfo matcher = new StringMatchesInfo();
		
		switch (pt){
			case EQUALS:
				wpi.setProblemInfo(i, j, matcher.equals(pd, word));
				break;
			case CONTAINS:
				wpi.setProblemInfo(i, j, matcher.contains(pd, word));
				break;
			case IS_NOUN_OR_ADJ_AND_ENDS_WITH:
				if (GreekPartOfSpeech.isNoun(word) || GreekPartOfSpeech.isAdj(word))
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_NOUN_AND_ENDS_WITH:
				if (GreekPartOfSpeech.isNoun(word))
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_ADJ_AND_ENDS_WITH:
				if (GreekPartOfSpeech.isAdj(word))
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_VERB_AND_ENDS_WITH:
				if (GreekPartOfSpeech.isVerb(word))
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_PARTICIPLE_AND_ENDS_WITH:
				if (GreekPartOfSpeech.isParticiple(word))
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_NOUN_OR_ADJ_AND_STARTS_WITH:
				if (GreekPartOfSpeech.isNoun(word) || GreekPartOfSpeech.isAdj(word))
					wpi.setProblemInfo(i, j, matcher.startsWith(pd, word));
				break;
			case IS_ADJ_AND_STARTS_WITH:
				if (GreekPartOfSpeech.isAdj(word))
					wpi.setProblemInfo(i, j, matcher.startsWith(pd, word));
				break;
			case IS_VERB_AND_STARTS_WITH:
				if (GreekPartOfSpeech.isVerb(word))
					wpi.setProblemInfo(i, j, matcher.startsWith(pd, word));
				break;
			case TWO_SYL_WORD_INITIAL_PHONEME:
				if (word.getNumberOfSyllables()==2)
					wpi.setProblemInfo(i, j, matcher.startsWithPhoneme(pd, word));
				break;
			case TWO_SYL_WORD_INTERNAL_PHONEME:
				if (word.getNumberOfSyllables()==2)
					wpi.setProblemInfo(i, j, matcher.hasInternalPhoneme(pd, word));
				break;
			case THREE_SYL_WORD_INITIAL_PHONEME:
				if (word.getNumberOfSyllables()==3)
					wpi.setProblemInfo(i, j, matcher.startsWithPhoneme(pd, word));
				break;
			case THREE_SYL_WORD_INTERNAL_PHONEME:
				if (word.getNumberOfSyllables()==3)
					wpi.setProblemInfo(i, j, matcher.hasInternalPhoneme(pd, word));
				break;
			case STARTS_WITH:
				wpi.setProblemInfo(i, j, matcher.startsWith(pd, word));
				break;
			case CONTAINS_PATTERN:
				wpi.setProblemInfo(i, j, matcher.containsPattern(pd, word));
				break;
			case CONTAINS_PHONEME:
				wpi.setProblemInfo(i, j, matcher.containsPhoneme(pd, word));
		default:
			break;
		}
		return wpi;
	}
	
	@Override
	public String toString(){
		if (matchedProbs==null) return "No Matched Problems";
		return matchedProbs.toString();
	}

}

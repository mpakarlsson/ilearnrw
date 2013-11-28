package ilearnrw.textclassification;

import java.util.ArrayList;


import ilearnrw.user.problems.EnglishProblems;
import ilearnrw.languagetools.LanguageAnalyzerAPI;
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
	private LanguageAnalyzerAPI languageAnalyser;
	
	public WordVsProblems(LanguageAnalyzerAPI languageAnalyser) {
		prs = new Problems();
		this.lc = languageAnalyser.getLanguageCode();
		this.languageAnalyser = languageAnalyser;
				
		switch (lc) {
		case GR:
			prs = new GreekProblems();
			break;
		case EN:
			prs = new EnglishProblems();
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
		languageAnalyser.setWord(word);
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
		StringMatchesInfo matcher = new StringMatchesInfo(languageAnalyser);
		switch (pt){
			case EQUALS:
				wpi.setProblemInfo(i, j, matcher.equals(pd, word));
				break;
			case CONTAINS:
				wpi.setProblemInfo(i, j, matcher.contains(pd, word));
				break;
			case IS_NOUN_OR_ADJ_AND_ENDS_WITH:
				if (languageAnalyser.isNoun() || languageAnalyser.isAdj())
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_NOUN_AND_ENDS_WITH:
				if (languageAnalyser.isNoun())
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_ADJ_AND_ENDS_WITH:
				if (languageAnalyser.isAdj())
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_VERB_AND_ENDS_WITH:
				if (languageAnalyser.isVerb())
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_PARTICIPLE_AND_ENDS_WITH:
				if (languageAnalyser.isParticiple())
					wpi.setProblemInfo(i, j, matcher.endsWith(pd, word));
				break;
			case IS_NOUN_OR_ADJ_AND_STARTS_WITH:
				if (languageAnalyser.isNoun() || languageAnalyser.isAdj())
					wpi.setProblemInfo(i, j, matcher.startsWith(pd, word));
				break;
			case IS_ADJ_AND_STARTS_WITH:
				if (languageAnalyser.isAdj())
					wpi.setProblemInfo(i, j, matcher.startsWith(pd, word));
				break;
			case IS_VERB_AND_STARTS_WITH:
				if (languageAnalyser.isVerb())
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
			case SOUND_SIMILARITY:
				wpi.setProblemInfo(i, j, matcher.soundSimilarity(pd, word));
				break;
			case CONTAINS_PHONEME:
				wpi.setProblemInfo(i, j, matcher.containsPhoneme(pd, word));
				break;
				// English language
				// TODO: Fix to be more complex, discuss with language experts
			case SUFFIX_ADD: 
			case SUFFIX_DROP:
			case SUFFIX_CHANGE:
			case SUFFIX_DOUBLE:
			case SUFFIX_STRESS_PATTERN: // FIX THIS TO DO AS THE JSON OBJECT TELLS IT
				wpi.setProblemInfo(i, j, matcher.endsWithSuffix(pd, word));
				break;
				
			// TODO: Fix to be more complex, discuss with language experts
			case PREFIX: 
			case PREFIX_GROUP:
				wpi.setProblemInfo(i, j, matcher.startsWithPrefix(pd, word));
				break;
			
				// TODO: Fix to be more complex, discuss with language experts		
			case LETTER_EQUALS_PHONEME: 
			case DIGRAPH_EQUALS_PHONEME:
			case TRIGRAPH_EQUALS_PHONEME:
				String startType = pt.toString().substring(0, pt.toString().indexOf("_")).toLowerCase();
				wpi.setProblemInfo(i, j, matcher.equalsPhoneme(pd, word, startType));
				break;
			
			// TODO: Fix to be more complex, discuss with language experts
			case PATTERN_EQUALS_PRONUNCIATION_CONTAINS:
			case PATTERN_EQUALS_PRONUNCIATION_BEGINS:
			case PATTERN_EQUALS_PRONUNCIATION_ENDS:
				String endType = pt.toString().substring(pt.toString().lastIndexOf("_")+1, pt.toString().length()).toLowerCase();
				wpi.setProblemInfo(i, j, matcher.patternEqualsPronunciation(pd, word, endType));
				break;
			case SYLLABLE_PATTERN:
			case SUFFIX_PATTERN:
				break;
			case SYLLABLE_COUNT:
				wpi.setProblemInfo(i, j, matcher.syllableCount(pd, word));
				break;
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

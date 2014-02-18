package ilearnrw.user.problems.wordlists;

import ilearnrw.languagetools.DictionaryLoader;
import ilearnrw.languagetools.WordDictionary;
import ilearnrw.languagetools.greek.GreekDictionary;
import ilearnrw.languagetools.greek.GreekGenericDictionaryLoader;
import ilearnrw.user.problems.EnglishProblems;
import ilearnrw.user.problems.GreekProblems;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.utils.LanguageCode;

public class ProblemsWordLists {
	private WordDictionary wordDictionary[][];
	public ProblemsWordLists(LanguageCode lc){
		ProblemDefinitionIndex probs;
		String path;
		switch (lc) {
		case GR:
			probs = (new GreekProblems()).getProblemDefinitionIndex();
			path = "greek_problems/";
			wordDictionary = new WordDictionary[probs.getIndexLength()][];
			for (int i=0;i<wordDictionary.length; i++){
				wordDictionary[i] = new WordDictionary[probs.getRowLength(i)];
				for (int j=0;j<wordDictionary[i].length; j++){
					GreekGenericDictionaryLoader ggl = new GreekGenericDictionaryLoader(path+"words_for_problem_"+i+"_"+j+"_"+"GR.txt");
					wordDictionary[i][j] = new GreekDictionary(ggl.getWords());
				}
			}
			break;
		case EN:
			probs = (new EnglishProblems()).getProblemDefinitionIndex();
			path = "english_problems/";
			wordDictionary = new WordDictionary[probs.getIndexLength()][];
			for (int i=0;i<wordDictionary.length; i++){
				wordDictionary[i] = new WordDictionary[probs.getRowLength(i)];
				for (int j=0;j<wordDictionary[i].length; j++){
					DictionaryLoader ggl = new DictionaryLoader(path+"words_for_problem_"+i+"_"+j+"_"+"GR.txt");
					wordDictionary[i][j] = new GreekDictionary(ggl.getWords());
				}
			}
			break;
		}
	}
	public WordDictionary[][] getWordDictionary() {
		return wordDictionary;
	}
	public void setWordDictionary(WordDictionary[][] wordDictionary) {
		this.wordDictionary = wordDictionary;
	}
	public WordDictionary get(int i, int j){
		if (wordDictionary == null || 
				wordDictionary.length<=i || wordDictionary[i].length<=j)
			return null;
		return wordDictionary[i][j];
	}
}

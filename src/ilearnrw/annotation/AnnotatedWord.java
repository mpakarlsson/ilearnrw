package ilearnrw.annotation;

import java.util.ArrayList;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordProblemInfo;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.utils.LanguageCode;

public class AnnotatedWord extends Word {
	private static final long serialVersionUID = 1L;
	private ArrayList<WordProblemInfo> wordProblems;
	public AnnotatedWord(Word w) {
		super();
		super.word = w.getWord();
		super.wordUnmodified = w.getWordUnmodified();
		super.type = w.getType();
		super.syllables = w.getSyllables();
		super.cvForm = w.getCVForm();
		super.phonetics = w.getPhonetics();
		super.numSyllables = w.getNumberOfSyllables();
		super.languageCode = w.getLanguageCode();
		super.frequency = w.getFrequency();
		super.graphemesPhonemes = w.getGraphemesPhonemes();
		this.wordProblems = getProblems(w);
	}
	public AnnotatedWord(Word w, int i, int j) {
		super();
		super.word = w.getWord();
		super.wordUnmodified = w.getWordUnmodified();
		super.type = w.getType();
		super.syllables = w.getSyllables();
		super.cvForm = w.getCVForm();
		super.phonetics = w.getPhonetics();
		super.numSyllables = w.getNumberOfSyllables();
		super.languageCode = w.getLanguageCode();
		super.frequency = w.getFrequency();
		super.graphemesPhonemes = w.getGraphemesPhonemes();
		this.wordProblems = getProblems(w, i, j);
	}

	public ArrayList<WordProblemInfo> getWordProblems() {
		return wordProblems;
	}
	public void setWordProblems(ArrayList<WordProblemInfo> wordProblems) {
		this.wordProblems = wordProblems;
	}
	
	private ArrayList<WordProblemInfo> getProblems(Word w){
		LanguageAnalyzerAPI lan;
		if (super.getLanguageCode().equals(LanguageCode.GR))
			lan = GreekLanguageAnalyzer.getInstance();
		else 
			lan = EnglishLanguageAnalyzer.getInstance();
		WordVsProblems wp = new WordVsProblems(lan);
		wp.insertWord(w);
		return wp.getMatchedProbs();
	}
	
	private ArrayList<WordProblemInfo> getProblems(Word w, int i, int j){
		LanguageAnalyzerAPI lan;
		if (super.getLanguageCode().equals(LanguageCode.GR))
			lan = GreekLanguageAnalyzer.getInstance();
		else 
			lan = EnglishLanguageAnalyzer.getInstance();
		WordVsProblems wp = new WordVsProblems(lan);
		wp.insertWord(w, i, j);
		return wp.getMatchedProbs();
	}

}

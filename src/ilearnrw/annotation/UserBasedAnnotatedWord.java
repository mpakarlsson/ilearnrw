package ilearnrw.annotation;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.SeverityOnWordProblemInfo;
import ilearnrw.textclassification.WordProblemInfo;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.user.profile.UserProfile;

import java.util.ArrayList;

public class UserBasedAnnotatedWord extends Word {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<SeverityOnWordProblemInfo> userSeveritiesOnWordProblems;
	private WordVsProblems wp;
	public UserBasedAnnotatedWord(Word w) {
		super();

		long start, end, duration;
		start = System.nanoTime();
		LanguageAnalyzerAPI la = null;
		if (languageCode == languageCode.GR)
			la = GreekLanguageAnalyzer.getInstance();
		else
			la = EnglishLanguageAnalyzer.getInstance();
		this.wp = new WordVsProblems(la);
		//la.setWord(w);
		//w = la.getWord();
		wp.insertWord(w);
		w = wp.getWord();
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
		this.userSeveritiesOnWordProblems = getProblems(w, null);
		end = System.nanoTime();
		
		duration = end -start;
		
		double d = (double) duration / 1000000000.0;
		System.out.println("Word: " + word + " - time: " + d);
	}
	public UserBasedAnnotatedWord(Word w, UserProfile userProfile, WordVsProblems wp) {
		super();
		/*LanguageAnalyzerAPI la = null;
		if (languageCode == languageCode.GR)
			la = new GreekLanguageAnalyzer();
		else
			la = new EnglishLanguageAnalyzer();
		this.wp = new WordVsProblems(la);
		la.setWord(w);
		w = la.getWord();*/
		this.wp = wp;
		wp.insertWord(w);
		w = wp.getWord();
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
		this.userSeveritiesOnWordProblems = getProblems(w, userProfile);
	}
	public UserBasedAnnotatedWord(Word w, int i, int j, UserProfile userProfile, WordVsProblems wp) {
		super();
		/*LanguageAnalyzerAPI la = null;
		if (languageCode == languageCode.GR)
			la = new GreekLanguageAnalyzer();
		else
			la = new EnglishLanguageAnalyzer();
		this.wp = new WordVsProblems(la);
		la.setWord(w);
		w = la.getWord();*/
		this.wp = wp;
		wp.insertWord(w);
		w = wp.getWord();
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
		this.userSeveritiesOnWordProblems = getProblems(w, i, j, userProfile);
	}

	public ArrayList<SeverityOnWordProblemInfo> getUserSeveritiesOnWordProblems() {
		return userSeveritiesOnWordProblems;
	}
	public void setWordProblems(ArrayList<SeverityOnWordProblemInfo> userSeveritiesOnWordProblems) {
		this.userSeveritiesOnWordProblems = userSeveritiesOnWordProblems;
	}
	
	private ArrayList<SeverityOnWordProblemInfo> getProblems(Word w, UserProfile userProfile){
		wp.insertWord(w);
		ArrayList<SeverityOnWordProblemInfo> uswp = new ArrayList<SeverityOnWordProblemInfo>();
		for (WordProblemInfo prob : wp.getMatchedProbs()){
			SeverityOnWordProblemInfo n = new SeverityOnWordProblemInfo();
			n.setProblemInfo(prob, userProfile);
			uswp.add(n);
		}
		return uswp;
	}
	
	private ArrayList<SeverityOnWordProblemInfo> getProblems(Word w, int i, int j, 
			UserProfile userProfile){
		wp.insertWord(w, i, j);
		ArrayList<SeverityOnWordProblemInfo> uswp = new ArrayList<SeverityOnWordProblemInfo>();
		for (WordProblemInfo prob : wp.getMatchedProbs()){
			SeverityOnWordProblemInfo n = new SeverityOnWordProblemInfo();
			n.setProblemInfo(prob, userProfile);
			uswp.add(n);
		}
		return uswp;
	}
	@Override
	public boolean equals(Object x) {
		return super.wordUnmodified.equals(((UserBasedAnnotatedWord)x).getWordUnmodified());
	}
	@Override
	public int hashCode() {
		return super.wordUnmodified.hashCode();
	}

}

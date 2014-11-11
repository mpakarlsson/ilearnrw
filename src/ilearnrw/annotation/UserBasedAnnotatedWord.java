package ilearnrw.annotation;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.SeverityOnWordProblemInfo;
import ilearnrw.textclassification.WordProblemInfo;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

import java.util.ArrayList;

public class UserBasedAnnotatedWord extends Word {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<WordProblemInfo> wordProblems;
	//private ArrayList<Integer> severitiesOnWordProblems = new ArrayList<Integer>();
	private WordVsProblems wp;
	public UserBasedAnnotatedWord(Word w) {
		super();
		LanguageAnalyzerAPI la = null;
		if (languageCode == LanguageCode.GR)
			la = GreekLanguageAnalyzer.getInstance();
		else if(languageCode == LanguageCode.EN)
			la = EnglishLanguageAnalyzer.getInstance();
		this.wp = new WordVsProblems(la);
	
		this.wp.insertWord(w);
		w = this.wp.getWord();
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
		this.wordProblems = getProblems(w, null);
		//createSeveritiesList(null);
	}
	public UserBasedAnnotatedWord(Word w, UserProfile userProfile, WordVsProblems wp) {
		super();

		this.wp = wp;
		this.wp.insertWord(w);
		w = this.wp.getWord();
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
		
		this.wordProblems = getProblems(w, userProfile);
		
		//createSeveritiesList(userProfile);
		
	}
	public UserBasedAnnotatedWord(Word w, int i, int j, UserProfile userProfile, WordVsProblems wp) {
		super();

		this.wp = wp;
		this.wp.insertWord(w, i, j);
		w = this.wp.getWord();
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
		this.wordProblems = getProblems(w, i, j, userProfile);
		//createSeveritiesList(userProfile);
	}

	/*private void createSeveritiesList(UserProfile up){
		for(WordProblemInfo p : wp.getMatchedProbs()){
			if(up!=null)
				severitiesOnWordProblems.add(up.getUserProblems().getUserSeverity(p.getCategory(), p.getIndex()));
		}
	}*/
	
	public ArrayList<WordProblemInfo> getWordProblems() {
		return wordProblems;
	}
	public void setWordProblems(ArrayList<WordProblemInfo> wordProblems) {
		this.wordProblems = wordProblems;
	}
	
	private ArrayList<WordProblemInfo> getProblems(Word w, UserProfile userProfile){
		/*ArrayList<SeverityOnWordProblemInfo> uswp = new ArrayList<SeverityOnWordProblemInfo>();
		for (WordProblemInfo prob : wp.getMatchedProbs()){
			SeverityOnWordProblemInfo n = new SeverityOnWordProblemInfo();
			n.setProblemInfo(prob, userProfile);
			uswp.add(n);
		}
		return uswp;*/
		return wp.getMatchedProbs();
	}
	
	private ArrayList<WordProblemInfo> getProblems(Word w, int i, int j, 
			UserProfile userProfile){
		/*ArrayList<SeverityOnWordProblemInfo> uswp = new ArrayList<SeverityOnWordProblemInfo>();
		for (WordProblemInfo prob : wp.getMatchedProbs()){
			SeverityOnWordProblemInfo n = new SeverityOnWordProblemInfo();
			n.setProblemInfo(prob, userProfile);
			uswp.add(n);
		}
		return uswp;*/
		return wp.getMatchedProbs();
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

package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.user.UserTextCounters;
import ilearnrw.user.profile.UserProblems;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserProblemsToWord  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Word word;
	private WordVsProblems wprobs;
	private UserProblems userSeveritiesToProblems;
	private UserTextCounters userCounters;
	private boolean isDifficult, isVeryDifficult;
	private int totalHits, userHits, Wscore;
	
	public UserProblemsToWord(){
		this.userSeveritiesToProblems = null;
		this.userCounters = null;
		this.word = null;
		this.wprobs = null;
		this.Wscore = 0;
		this.isDifficult = false;
		this.isVeryDifficult = false;
		this.totalHits = 0;
		this.userHits = 0;
	}
	
	public UserProblemsToWord(UserProfile userProfile, Word word, LanguageAnalyzerAPI languageAnalyser){
		if (word.getLanguageCode() != userProfile.getLanguage() || 
				languageAnalyser.getLanguageCode() != userProfile.getLanguage() || 
				languageAnalyser.getLanguageCode() != word.getLanguageCode())
			return;
		this.userSeveritiesToProblems = userProfile.getUserProblems();
		this.word = word;
		wprobs = new WordVsProblems(languageAnalyser);
		int n = userSeveritiesToProblems.getNumerOfRows();
		
		userCounters = new UserTextCounters(n);
		
		for (int i=0; i<n; i++){
			userCounters.constructRow(i, userSeveritiesToProblems.getRowLength(i));
		}
		this.initialize();
	}	
	
	public void initialize(){
		this.Wscore = 0;
		this.isDifficult = false;
		this.isVeryDifficult = false;
		this.totalHits = 0;
		this.userHits = 0;
		//find the points of the problem map that the word matches 
		wprobs.insertWord(word);
		ArrayList<WordProblemInfo> probs = wprobs.getMatchedProbs();
		totalHits += probs.size();
		//count the problems for each word
		Wscore = 0;
		for (WordProblemInfo x : probs){
			int t = this.updateValue(x.getCategory(), x.getIndex(), 1);
			Wscore += t;
			if (t>0){
				userHits++;
				isDifficult = true;
			}
		}
		if (Wscore>5)
			isVeryDifficult = true;
	}

	public WordClassificationResults getWordClassificationResults(){
		WordClassificationResults cr = new WordClassificationResults();
		if (userSeveritiesToProblems==null || word==null)
			return null;

		//user problems to word
		cr.setWord(word.toString());
		cr.setLength(word.toString().length());
		cr.setWordInSyllables(word.getWordInToSyllables());
		cr.setType(word.getType());
		cr.setSyllables(word.getSyllables());
		cr.setCvForm(word.getCVForm());
		cr.setPhonetics(word.getPhonetics());
		cr.setNumberOfSyllables(word.getNumberOfSyllables());
		cr.setLanguageCode(word.getLanguageCode());
		cr.setFrequency(word.getFrequency());
		
		//text
		cr.setTotalHits(getTotalHits());
		cr.setUserHits(getUserHits());
		cr.setUserCounters(getUserWordCounters());
		cr.setWprobs(wprobs);
		cr.setDifficult(isDifficult);
		cr.setVeryDifficult(isVeryDifficult);
		cr.setWscore(Wscore);
		return cr;
	}

	
	public String getWordToString() {
		return word.toString();
	}

	public void setWord(String word) {
		this.word = new Word(word);
	}
	
	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public boolean isDifficult() {
		return isDifficult;
	}

	public void setDifficult(boolean isDifficult) {
		this.isDifficult = isDifficult;
	}

	public boolean isVeryDifficult() {
		return isVeryDifficult;
	}

	public void setVeryDifficult(boolean isVeryDifficult) {
		this.isVeryDifficult = isVeryDifficult;
	}

	public double getWscore() {
		return Wscore;
	}

	public void setWscore(int wscore) {
		Wscore = wscore;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public int getUserHits() {
		return userHits;
	}

	public void setUserHits(int userHits) {
		this.userHits = userHits;
	}

	public void setValue(int i, int j, int value) {
		userCounters.setValue(i, j, value);
	}

	public int updateValue(int i, int j, int oneValue) {
		//against severities: 0 --> 0; 1 --> 0.3; 2 --> 0.6; 3 --> 1
		if (userSeveritiesToProblems.getUserSeverity(i, j)>0)
			userCounters.increaseValue(i, j, oneValue);
		/*switch (userSeveritiesToProblems.getSeverity(i, j)) {
		case 3:
			return 1;
		case 2:
			return 0.6;
		case 1:
			return 0.3;
		default:
			return 0;
		}*/
		return userSeveritiesToProblems.getUserSeverity(i, j);
	}
	
	public int getValue(int i, int j) {
		return userCounters.getValue(i, j);
	}

	public UserProblems getUserSeveritiesToProblems(){
		return userSeveritiesToProblems;
	}

	public void setUserSeveritiesToProblems(UserProblems userS2P) {
		this.userSeveritiesToProblems = userS2P;
	}	
	
	public UserTextCounters getUserWordCounters() {
		return userCounters;
	}

	public void setUserWordCounters(UserTextCounters userCounters) {
		this.userCounters = userCounters;
	}

	public WordVsProblems getWprobs() {
		return wprobs;
	}

	public void setWprobs(WordVsProblems wprobs) {
		this.wprobs = wprobs;
	}

	@Override
	public String toString(){
		if (userCounters == null)
			return "null counters or problems matrix";
		String res = "";
		res = res + userCounters.toString();
		return res;
	}
}
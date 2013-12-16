package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.greek.GreekDictionary;
import ilearnrw.user.User;
import ilearnrw.user.UserTextCounters;
import ilearnrw.user.profile.UserProblems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserProblemsToText implements Serializable {
	private static final long serialVersionUID = 1L;

	private Text text;
	private WordVsProblems wprobs;
	private UserProblems userSeveritiesToProblems;
	private UserTextCounters userCounters;
	private HashMap<Word, Integer> wordScores;
	private double SDW, Tscore;
	private int totalHits, userHits, diffWords, veryDiffWords, Wscore;
	private boolean calculateProblematicWords;
	private ProblematicWords problematicWords;

	// TODO fix the threshold to a value that the experts want!
	int threshold = 0;//the threshold for a severity so that the corresponding problem will be counted in the matrix
	
	public UserProblemsToText(){
		this.userSeveritiesToProblems = null;
		this.problematicWords = null;
		this.calculateProblematicWords = true;
		this.userCounters = null;
		this.text = null;
		this.wprobs = null;
		this.wordScores = null;
		SDW = 0;
		totalHits = 0;
		diffWords = 0;
		veryDiffWords = 0;
		Wscore = 0;
		Tscore = 0;
	}
	
	public UserProblemsToText(User user, Text text, LanguageAnalyzerAPI languageAnalyser){
		if (text.getLanguageCode() != user.getDetails().getLanguage() || 
				languageAnalyser.getLanguageCode() != user.getDetails().getLanguage() || 
				languageAnalyser.getLanguageCode() != text.getLanguageCode())
			return;
		this.userSeveritiesToProblems = user.getProfile().getUserProblems();
		this.calculateProblematicWords = true;
		this.problematicWords = new ProblematicWords(userSeveritiesToProblems);
		this.text = text;
		wprobs = new WordVsProblems(languageAnalyser);
		wordScores = new HashMap<Word, Integer>();
		int 
		n = userSeveritiesToProblems.getNumerOfRows();
		
		userCounters = new UserTextCounters(n);
		
		for (int i=0; i<n; i++){
			userCounters.constructRow(i, userSeveritiesToProblems.getRowLength(i));
		}
		this.initialize();
	}	
	
	public void initialize(){

		userHits = 0;
		totalHits = 0;
		SDW = 0;
		diffWords = 0;
		veryDiffWords = 0;
		Wscore = 0;
		Tscore = 0;
		//get all words along with the number times it appeared inside the text
		for (Map.Entry<Word,Integer> entry : text.getWordsFreq().entrySet()) {
			Word w = entry.getKey();
			boolean isDifficult = false;
			int appearences = entry.getValue();
			//find the points of the problem map that the word matches 
			wprobs.insertWord(w);
			ArrayList<WordProblemInfo> probs = wprobs.getMatchedProbs();
			totalHits += probs.size();
			//count the problems for each word
			Wscore = 0;
			for (WordProblemInfo x : probs){
				if (calculateProblematicWords)
					problematicWords.addWord(x.getPosI(), x.getPosJ(), w);
				int t = this.updateValue(x.getPosI(), x.getPosJ(), entry.getValue());
				Wscore += t;
				if (t>0){
					userHits++;
					isDifficult = true;
				}
				if (!wordScores.containsKey(w)){
					//wordScores.put(entry.getKey(), appearences*t - (appearences-1)*0.25);
					SDW += appearences*t;// - (appearences-1)*0.25;
				}
			}
			if (!wordScores.containsKey(w)){
				wordScores.put(entry.getKey(), Wscore);
			}
			Tscore += ((appearences+1)/2.0)*Wscore;
			if (isDifficult)
				diffWords++;
			if (Wscore>5)
				veryDiffWords++;
		}
		//Tscore = Tscore;/text.getNumberOfSentences();
	}

	
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	public double getTscore() {
		return Tscore;
	}

	public void setTscore(double tscore) {
		Tscore = tscore;
	}

	public double getWscore() {
		return Wscore;
	}

	public void setWscore(int wscore) {
		Wscore = wscore;
	}

	public ProblematicWords getProblematicWords() {
		return problematicWords;
	}

	public void setProblematicWords(ProblematicWords problematic) {
		this.problematicWords = problematic;
	}

	public boolean calculateProblematicWords() {
		return calculateProblematicWords;
	}

	public void calculateProblematicWords(boolean calculateProblematicWords) {
		this.calculateProblematicWords = calculateProblematicWords;
	}

	public HashMap<Word, Integer> getWordScores() {
		return wordScores;
	}

	public void setWordScores(HashMap<Word, Integer> wordsWeights) {
		this.wordScores = wordsWeights;
	}

	public int getDiffWords() {
		return diffWords;
	}

	public int getVeryDiffWords() {
		return veryDiffWords;
	}

	public double getSDW() {
		return SDW;
	}

	public void setSDW(double sDW) {
		SDW = sDW;
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
		if (userSeveritiesToProblems.getSeverity(i, j)>threshold)
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
		return userSeveritiesToProblems.getSeverity(i, j);
	}

	public double getwordScore(Word w){
		if (!wordScores.containsKey(w)){
			return wordScores.get(w);
		}
		else return 0;
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
	
	public UserTextCounters getUserCounters() {
		return userCounters;
	}

	public void setUserCounters(UserTextCounters userCounters) {
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

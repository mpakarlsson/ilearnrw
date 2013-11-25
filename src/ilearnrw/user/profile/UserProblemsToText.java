package ilearnrw.user.profile;


import ilearnrw.textclassification.Sentence;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordProblemInfo;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.user.User;
import ilearnrw.user.UserTextCounters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserProblemsToText implements Serializable {
	private static final long serialVersionUID = 1L;

	private Text text;
	private WordVsProblems wprobs;
	private UserSeveritiesToProblems userSeveritiesToProblems;
	private UserTextCounters userCounters;
	private HashMap<Word, Double> wordsWeights;
	private double SDW;
	private int totalHits, userHits;
	
	// TODO fix the threshold to a valua that the experts want!
	int threshold = 0;//the threshold for a severity so that the corresponding problem will be counted in the matrix
	
	public UserProblemsToText(){
		this.userSeveritiesToProblems = null;
		this.userCounters = null;
		this.text = null;
		this.wprobs = null;
		this.wordsWeights = null;
		SDW = 0;
		totalHits = 0;
	}
	
	public UserProblemsToText(User user, Text text){
		if (text.getLanguageCode() != user.getDetails().getLanguage())
			return;
		this.userSeveritiesToProblems = user.getProfile().getUserSeveritiesToProblems();
		this.text = text;
		wprobs = new WordVsProblems(text.getLanguageCode());
		wordsWeights = new HashMap<Word, Double>();
		int n = userSeveritiesToProblems.getNumerOfRows();
		
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
		//get all words along with the number times it appeared inside the text
		for (Map.Entry<Word,Integer> entry : text.getWordsFreq().entrySet()) {
			Word w = entry.getKey();

			int appearences = entry.getValue();
			//find the points of the problem map that the word matches 
			wprobs.insertWord(w);
			ArrayList<WordProblemInfo> probs = wprobs.getMatchedProbs();
			totalHits += probs.size();
			//count the problems for each word
			for (WordProblemInfo x : probs){
				double t = this.updateValue(x.getPosI(), x.getPosJ(), entry.getValue());
				userHits += t>0 ? 1:0;
				if (!wordsWeights.containsKey(w)){
					wordsWeights.put(entry.getKey(), appearences*t - (appearences-1)*0.25);
					SDW += appearences*t;// - (appearences-1)*0.25;
				}
			}
		}
	}

	
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public HashMap<Word, Double> getWordsWeights() {
		return wordsWeights;
	}

	public void setWordsWeights(HashMap<Word, Double> wordsWeights) {
		this.wordsWeights = wordsWeights;
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

	public double updateValue(int i, int j, int oneValue) {
		//against severities: 0 --> 0; 1 --> 0.3; 2 --> 0.6; 3 --> 1
		if (userSeveritiesToProblems.getSeverity(i, j)>threshold)
			userCounters.increaseValue(i, j, oneValue);
		switch (userSeveritiesToProblems.getSeverity(i, j)) {
		case 3:
			return 1;
		case 2:
			return 0.6;
		case 1:
			return 0.3;
		default:
			return 0;
		}
	}

	public double getwordWeight(Word w){
		if (!wordsWeights.containsKey(w)){
			return wordsWeights.get(w);
		}
		else return 0;
	}
	
	public int getValue(int i, int j) {
		return userCounters.getValue(i, j);
	}

	public UserSeveritiesToProblems getUserSeveritiesToProblems(){
		return userSeveritiesToProblems;
	}

	public void setUserSeveritiesToProblems(UserSeveritiesToProblems userS2P) {
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

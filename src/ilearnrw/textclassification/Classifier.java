package ilearnrw.textclassification;

import java.util.ArrayList;

import ilearnrw.user.UserSeveritiesToProblems;
import ilearnrw.user.UserTextCounters;

public class Classifier {

	private UserTextCounters userCnts;
	private Text text;
	private WordVsProblems wprobs;
	
	public Classifier(UserSeveritiesToProblems userSevToProbs, Text text) {
		this.userCnts = new UserTextCounters(userSevToProbs);
		this.text = text;
		wprobs = new WordVsProblems(text.getLanguageCode());
		System.out.println(userSevToProbs.toString());
		
	}
	
	public void test(){
		for (Sentence sen : text.getSentences()){
			for (Word w : sen.getWords()){
				wprobs.insertWord(w);
				ArrayList<WordProblemInfo> probs = wprobs.getMatchedProbs();
				for (WordProblemInfo x : probs){
					userCnts.increaseValue(x.getPosI(), x.getPosJ());
				}
			}
		}
		System.out.println(userCnts.toString());
	}
	
	public int[][] getCounters(){
		return userCnts.getCounters();
	}

}

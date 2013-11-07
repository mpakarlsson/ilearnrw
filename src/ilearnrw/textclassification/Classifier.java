package ilearnrw.textclassification;

import java.util.ArrayList;

import ilearnrw.user.UserProblemsToText;
import ilearnrw.user.UserSeveritiesToProblems;

public class Classifier {

	private UserSeveritiesToProblems userSeveritiesToProblems;
	private UserProblemsToText userProblemsToText;
	private Text text;
	private WordVsProblems wprobs;
	
	public Classifier(){
		
	}
			
	public Classifier(UserSeveritiesToProblems userSeveritiesToProblems, Text text) {
		this.userSeveritiesToProblems = userSeveritiesToProblems;
		this.userProblemsToText = new UserProblemsToText(userSeveritiesToProblems);
		this.text = text;
		wprobs = new WordVsProblems(text.getLanguageCode());
		System.out.println(userProblemsToText.getUserSeveritiesToProblems().toString());
		
	}
	
	public void test(){
		for (Sentence sen : text.getSentences()){
			for (Word w : sen.getWords()){
				wprobs.insertWord(w);
				ArrayList<WordProblemInfo> probs = wprobs.getMatchedProbs();
				for (WordProblemInfo x : probs){
					userProblemsToText.increaseValue(x.getPosI(), x.getPosJ());
				}
			}
		}
		System.out.println(userProblemsToText.toString());
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public WordVsProblems getWprobs() {
		return wprobs;
	}

	public void setWprobs(WordVsProblems wprobs) {
		this.wprobs = wprobs;
	}

	public UserProblemsToText getUserProblemsToText() {
		return userProblemsToText;
	}

	public void setUserPtT(UserProblemsToText userProblemsToText) {
		this.userProblemsToText = userProblemsToText;
	}

	public UserSeveritiesToProblems getUserSeveritiesToProblems() {
		return userSeveritiesToProblems;
	}

	public void setUserSevtP(UserSeveritiesToProblems userSeveritiesToProblems) {
		this.userSeveritiesToProblems = userSeveritiesToProblems;
	}

}

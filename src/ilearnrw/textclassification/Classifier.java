package ilearnrw.textclassification;

import java.util.ArrayList;

import ilearnrw.user.User;
import ilearnrw.user.UserProblemsToText;
import ilearnrw.user.UserSeveritiesToProblems;

public class Classifier {

	private User user;
	private UserProblemsToText userProblemsToText;
	private Text text;
	private WordVsProblems wprobs;
	
	public Classifier(){
		
	}
			
	public Classifier(User user, Text text) {
		this.user = user;
		this.userProblemsToText = new UserProblemsToText(user);
		this.text = text;
		wprobs = new WordVsProblems(text.getLanguageCode());
		
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

	public void setUserProblemsToText(UserProblemsToText userProblemsToText) {
		this.userProblemsToText = userProblemsToText;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

package ilearnrw.languagetools.extras;

import java.util.ArrayList;
import java.util.Random;

public class DoubleWordList {
	private ArrayList<String> easyWords, hardWords;

	public DoubleWordList() {
		this.easyWords = new ArrayList<String>();
		this.hardWords = new ArrayList<String>();
	}
	
	public DoubleWordList(ArrayList<String> words) {
		this.easyWords = new ArrayList<String>();
		this.hardWords = new ArrayList<String>();
		setWords(words);
	}

	public ArrayList<String> getWords() {
		ArrayList<String> ws = new ArrayList<String>();
		for (String x:easyWords)
			ws.add(x);
		for (String x:hardWords)
			ws.add(x);
		return ws;
	}
	
	public ArrayList<String> getEasyWords() {
		return easyWords;
	}
	
	public ArrayList<String> getHardWords() {
		return hardWords;
	}

	public void setWords(ArrayList<String> words) {
		boolean easy = true;
		for (String x:words){
			if (x.startsWith("###")){
				easy = false;
				continue;
			}
			if (easy)
				this.easyWords.add(x);
			else
				this.hardWords.add(x);
		}
	}
	
	public void addEasy(String w){
		easyWords.add(w);
	}
	
	public void addHard(String w){
		hardWords.add(w);
	}
	
	public String getEasy(int i){
		return easyWords.get(i);
	}
	
	public String getHard(int i){
		return hardWords.get(i);
	}
	
	public ArrayList<String> getRandomWords(int count, int level){
		//levels: easy, hard
		ArrayList<String> result = new ArrayList<String>();
		if (this.easyWords.size()+this.easyWords.size() <= count){
			return getWords();
		}
		ArrayList<String> set;
		Random rand = new Random();
		if (level==1)
			set = hardWords;
		else
			set = easyWords;

		while (result.size()<count && set.size()>0)
			result.add(set.remove(rand.nextInt(set.size())));
		
		return result;
	}
}

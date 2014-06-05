package ilearnrw.languagetools.extras;

import java.util.ArrayList;
import java.util.Random;

public class EasyHardList {
	private ArrayList<String> easy, hard;

	public EasyHardList() {
		this.easy = new ArrayList<String>();
		this.hard = new ArrayList<String>();
	}
	
	public EasyHardList(ArrayList<String> words) {
		this.easy = new ArrayList<String>();
		this.hard = new ArrayList<String>();
		setWords(words);
	}

	public ArrayList<String> getAll() {
		ArrayList<String> ws = new ArrayList<String>();
		for (String x:easy)
			ws.add(x);
		for (String x:hard)
			ws.add(x);
		return ws;
	}
	
	public ArrayList<String> getEasy() {
		return easy;
	}
	
	public ArrayList<String> getHard() {
		return hard;
	}

	private void setWords(ArrayList<String> words) {
		boolean easy = true;
		for (String x:words){
			if (x.isEmpty())
				continue;
			if (x.startsWith("###")){
				easy = false;
				continue;
			}
			if (easy)
				this.easy.add(x);
			else
				this.hard.add(x);
		}
	}
	
	public void addEasy(String w){
		easy.add(w);
	}
	
	public void addHard(String w){
		hard.add(w);
	}
	
	public String getEasy(int i){
		return easy.get(i);
	}
	
	public String getHard(int i){
		return hard.get(i);
	}
	
	public ArrayList<String> getRandom(int count, int level){
		//levels: easy, hard
		ArrayList<String> result = new ArrayList<String>();
		if (this.easy.size()+this.easy.size() <= count){
			return getAll();
		}
		ArrayList<String> set;
		Random rand = new Random();
		if (level==1)
			set = hard;
		else
			set = easy;

		while (result.size()<count && set.size()>0)
			result.add(set.remove(rand.nextInt(set.size())));
		
		return result;
	}
}

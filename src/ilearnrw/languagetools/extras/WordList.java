package ilearnrw.languagetools.extras;

import java.util.ArrayList;
import java.util.Random;

public class WordList {
	private ArrayList<String> words;

	public WordList() {
		this.words = new ArrayList<String>();
	}
	
	public WordList(ArrayList<String> words) {
		this.words = words;
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	
	public void add(String w){
		words.add(w);
	}
	
	public String get(int i){
		return words.get(i);
	}
	
	public ArrayList<String> getRandomWords(int count, int level){
		//levels: easy, hard
		ArrayList<String> result = new ArrayList<String>();
		int start = 0, end = this.words.size();
		if (level == 1)
			start = end/2;
		else 
			end = end/2;
		
		if (this.words.size() <= count){
			return this.words;
		}
		if (end-start < count){
			int cnt = 0;
			if (start == 0){
				for (int i=0;i<this.words.size();i++){
					result.add(this.words.get(i));
					if (++cnt>=count)
						return result;
				}
			}
			else{
				for (int i=this.words.size()-1;i>=0;i--){
					result.add(this.words.get(i));
					if (++cnt>=count)
						return result;
				}
			}
			return result;
		}
		else {
			Random rand = new Random();
			ArrayList<Integer> nums = new ArrayList<Integer>();
			for (int i=0;i<end-start;i++){
				nums.add(i+start);
			}
			int cnt = 0;
			while (cnt<count){
				int r = nums.remove(rand.nextInt(nums.size()));
				result.add(this.words.get(r));
				cnt++;
			}
			System.err.println(result.toString());
			return result;
		}
	}
}

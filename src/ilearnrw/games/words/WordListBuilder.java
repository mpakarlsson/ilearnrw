package ilearnrw.games.words;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import ilearnrw.languagetools.english.EnglishDictionary;
import ilearnrw.languagetools.extras.WordList;
import ilearnrw.languagetools.extras.WordListLoader;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;

public class WordListBuilder {
	public static void main(String args[]){
		ArrayList<String> t = new ArrayList<String>();
		t.add(ENdictionaryToString());
		try {
			save("data/game_words_EN/test.txt", t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String ENdictionaryToString(){
		System.out.println("Starting loading words...");
		EnglishDictionary ed = EnglishDictionary.getInstance();
		ed = EnglishDictionary.getInstance();
		if(ed.getDictionary().isEmpty())
			ed.loadDictionary("data/dictionary_english.csv");
		Map<String, EnglishWord> ws = ed.getDictionary();
		String res = "";
		for (String x : ws.keySet()){
			res = res + (x+" ");
		}
		return res;
	}
	
	private static void greekList(int category, int index){
		String path = "greek_collection_for_problems/cat"+category;
		WordListLoader wll = new WordListLoader();
		try {
			wll.load("greek_collection_for_problems/test");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		WordList wl = wll.getWordList();
		ArrayList<String> base = wl.getWords();
		wll = new WordListLoader();
		try {
			wll.load(path+"/words_for_problem_"+category+"_"+index+"_GR.txt");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		wl = wll.getWordList();
		ArrayList<String> more = wl.getWords();
		System.err.println(base);
		System.err.println(more);
		listUnion(more, base);
		System.out.println(more);
		try {
			save("data/"+path+"/words_"+category+"_"+index+"_GR.txt", more);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void listUnion(ArrayList<String> base, ArrayList<String> more){
		base.add("###");
		int wordsToAppend = 500 - base.size() - 1;

		while (base.size() >= 200){
			base.remove(0);
		}
		for (String x:more){
			if (base.contains(x))
				continue;
			GreekWord w = new GreekWord(x);
			//if (w.getNumberOfSyllables()>=3)
				base.add(x);
			if (base.size() == wordsToAppend)
				return;
		}
	}
	
	public static void save(String fileName, ArrayList<String> base) throws IOException {
		FileWriter writer = new FileWriter(fileName);
		for(int i=0;i<base.size()-1;i++) {
		  writer.write(base.get(i)+"\n");
		}
		writer.write(base.get(base.size()-1)+"\n");
		writer.close();
	}
}

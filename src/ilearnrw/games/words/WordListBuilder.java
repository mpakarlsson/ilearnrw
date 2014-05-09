package ilearnrw.games.words;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ilearnrw.languagetools.extras.WordList;
import ilearnrw.languagetools.extras.WordListLoader;
import ilearnrw.textclassification.greek.GreekWord;

public class WordListBuilder {
	private static String path = "greek_collection_for_problems/cat6";
	private static int category = 6, index = 26;
	public static void main(String args[]){
		WordListLoader wll = new WordListLoader("greek_collection_for_problems/test");
		WordList wl = wll.getWordList();
		ArrayList<String> base = wl.getWords();
		wll = new WordListLoader(path+"/words_for_problem_"+category+"_"+index+"_GR.txt");
		wl = wll.getWordList();
		ArrayList<String> more = wl.getWords();
		System.err.println(base);
		System.err.println(more);
		listUnion(base, more);
		System.out.println(base);
		try {
			save("data/"+path+"/words_"+category+"_"+index+"_GR.txt", base);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void listUnion(ArrayList<String> base, ArrayList<String> more){
		base.add("###");
		int wordsToAppend = 500 - base.size() - 1;
		for (String x:more){
			if (base.contains(x))
				continue;
			GreekWord w = new GreekWord(x);
			if (w.getNumberOfSyllables()>=3)
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

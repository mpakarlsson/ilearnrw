package ilearnrw.languagetools.greek;

import ilearnrw.languagetools.WordDictionary;
import ilearnrw.structs.sets.SortedTreeSet;

public class GreekDictionary extends WordDictionary{

	public GreekDictionary() {
		super();
		loadWords();
	}

	public GreekDictionary(SortedTreeSet gw) {
		super(gw);
	}

	
	public void loadWords(){
		GreekDictionaryLoader gl = new GreekDictionaryLoader("greekDictionary");
		words = gl.getEntries();
		System.err.println("hi i have "+words.size()+" words!");
	}	
}

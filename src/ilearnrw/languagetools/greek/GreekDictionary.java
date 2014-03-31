package ilearnrw.languagetools.greek;

import ilearnrw.languagetools.WordDictionary;
import ilearnrw.structs.sets.SortedTreeSet;

public class GreekDictionary extends WordDictionary{

	public GreekDictionary() {
		super();
		loadWords();
	}

	public GreekDictionary(String filename) {
		super();
		loadWords(filename);
	}

	public GreekDictionary(SortedTreeSet gw) {
		super(gw);
	}

	
	public void loadWords(){
		GreekDictionaryLoader gl = new GreekDictionaryLoader("greekDictionary");
		words = gl.getEntries();
	}	

	
	public void loadWords(String filename){
		GreekDictionaryLoader gl = new GreekDictionaryLoader(filename);
		words = gl.getEntries();
	}	
}

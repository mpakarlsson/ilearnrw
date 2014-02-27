package ilearnrw.languagetools.greek;

import ilearnrw.languagetools.WordDictionary;
import ilearnrw.structs.sets.SortedTreeSet;

public class GreekDictionary extends WordDictionary{

	public GreekDictionary() {
		super();
	}

	public GreekDictionary(SortedTreeSet gw) {
		super(gw);
	}

	
	public void loadWords(){
		GreekGenericDictionaryLoader gl = new GreekGenericDictionaryLoader("greek_dictionary.txt");
		words = gl.getEntries();
	}	
}

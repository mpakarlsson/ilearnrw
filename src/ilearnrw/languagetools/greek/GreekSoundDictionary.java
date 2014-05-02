package ilearnrw.languagetools.greek;

import ilearnrw.structs.sets.SortedTreeSet;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.greek.GreekWord;

public class GreekSoundDictionary {
	private GreekWord words[];
	public GreekSoundDictionary() {
		loadWords();
	}

	public GreekSoundDictionary(String filename) {
		super();
		loadWords(filename);
	}

	public GreekSoundDictionary(SortedTreeSet sts) {
		words = new GreekWord[sts.size()];
		int i = 0;
		for (Word w : sts){
			words[i++] = (GreekWord)w;
		}
	}

	
	public void loadWords(){
		GreekDictionaryLoader gl = new GreekDictionaryLoader("greekDictionary");
		SortedTreeSet sts = gl.getEntries();
		words = new GreekWord[sts.size()];
		int i = 0;
		for (Word w : sts){
			words[i++] = (GreekWord)w;
		}
	}	

	
	public void loadWords(String filename){
		GreekDictionaryLoader gl = new GreekDictionaryLoader(filename);
		SortedTreeSet sts = gl.getEntries();
		words = new GreekWord[sts.size()];
		int i = 0;
		for (Word w : sts){
			words[i++] = (GreekWord)w;
		}
	}
	
	public GreekWord[] getWords(){
		return this.words;
	}
}

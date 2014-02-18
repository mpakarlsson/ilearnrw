package ilearnrw.languagetools;

import ilearnrw.languagetools.greek.DictionaryEntry;
import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.structs.sets.SortedTreeSet;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;

public class DictionaryLoader {

	protected Dictionary<String, DictionaryEntry> entries;
	protected SortedTreeSet words;
	protected InputStream dictionary;
	protected String filename;
	protected int i=0;

	public DictionaryLoader(String filename) {
		this.filename = filename;
		this.entries = new Hashtable<String, DictionaryEntry>();
		words = new SortedTreeSet();
		loadDictionary();
		readDic();
	}
	
	private void loadDictionary() {
		dictionary = ResourceLoader.getInstance().getInputStream(Type.DATA, this.filename);
	}

	public Dictionary<String, DictionaryEntry> getEntries() {
		return entries;
	}

	public void setEntries(Dictionary<String, DictionaryEntry> entries) {
		this.entries = entries;
	}

	public SortedTreeSet getWords() {
		return words;
	}

	public void setWords(SortedTreeSet greekWords) {
		this.words = greekWords;
	}

	public DictionaryEntry getValue(String word){
		return entries.get(word);
	}

	public void add(String word, DictionaryEntry entry){
		if (entry.isActive() && word!=null && !word.isEmpty()){
			if (entry.getLemma()==null || entry.getLemma().isEmpty()){
				entry.setLemma(word);
			}
			entries.put(word, entry);
		}
	}
	
	public void readDic(){}

}

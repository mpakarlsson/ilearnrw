package ilearnrw.languagetools;

import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.structs.sets.SortedTreeSet;
import java.io.InputStream;

public class DictionaryLoader {
	protected SortedTreeSet entries;
	protected InputStream dictionary;
	protected String filename;
	protected int i=0;

	public DictionaryLoader(String filename) {
		this.filename = filename;
		entries = new SortedTreeSet();
		loadDictionary();
		readDic();
	}
	
	private void loadDictionary() {
		dictionary = ResourceLoader.getInstance().getInputStream(Type.DATA, this.filename);
	}

	public SortedTreeSet getEntries() {
		return entries;
	}

	public void setEntries(SortedTreeSet greekWords) {
		this.entries = greekWords;
	}
	
	public void readDic(){}

}

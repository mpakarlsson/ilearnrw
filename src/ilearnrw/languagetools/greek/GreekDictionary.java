package ilearnrw.languagetools.greek;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class GreekDictionary {
	private final String path = "data/";
	private Dictionary<String, DictionaryEntry> entries;

	public GreekDictionary() {
		this.entries = new Hashtable<String, DictionaryEntry>();
		readDic();
	}
	
	public Dictionary<String, DictionaryEntry> getEntries() {
		return entries;
	}

	public void setEntries(Dictionary<String, DictionaryEntry> entries) {
		this.entries = entries;
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
	
	private void readDic(){
		// Open the file
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(path+"greek_dictionary.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				String test = strLine;

		        String[] result = test.split("\\\t");
		        if (result.length<4)
		        	continue;

		    	String word, lemma, partOfSpeech;
		    	word = result[0].toLowerCase().trim();
		    	lemma = result[1].toLowerCase().trim();
		    	partOfSpeech = result[2].toLowerCase().trim();
		    	result = result[3].split("\\,");
		    	ArrayList<String> extras = new ArrayList<String>();
		        for(String s : result){
		        	extras.add(s.toLowerCase().trim());
		        }
		        DictionaryEntry entry = new DictionaryEntry(lemma, partOfSpeech, extras);
		        add(word, entry);
				// Print the content on the console
				//System.out.println (strLine);
			}

			//Close the input stream
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}

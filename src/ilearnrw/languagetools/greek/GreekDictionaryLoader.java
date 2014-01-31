package ilearnrw.languagetools.greek;

import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.utils.LanguageCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

public class GreekDictionaryLoader {
	private Dictionary<String, DictionaryEntry> entries;
	ArrayList<GreekWord> greekWords;
	ArrayList<GreekWord> similarSoundGreekWords;
	InputStream greekDictionary;
	InputStream greekSoundDictionary;
	int i=0;

	public GreekDictionaryLoader() {
		this.entries = new Hashtable<String, DictionaryEntry>();
		greekWords = new ArrayList<GreekWord>();
		similarSoundGreekWords = new ArrayList<GreekWord>();
		loadDictionaries();
		readDic();
		readSoundDic();
	}
	
	private void loadDictionaries() {
		greekDictionary = ResourceLoader.getInstance().getInputStream(Type.DATA, "greek_dictionary.txt");
		greekSoundDictionary = ResourceLoader.getInstance().getInputStream(Type.DATA, "greek_sound_similarity.txt");
	}

	public Dictionary<String, DictionaryEntry> getEntries() {
		return entries;
	}

	public void setEntries(Dictionary<String, DictionaryEntry> entries) {
		this.entries = entries;
	}

	public ArrayList<GreekWord> getSimilarSoundGreekWords() {
		return similarSoundGreekWords;
	}

	public void setSimilarSoundGreekWords(
			ArrayList<GreekWord> similarSoundGreekWords) {
		this.similarSoundGreekWords = similarSoundGreekWords;
	}

	public ArrayList<GreekWord> getGreekWords() {
		return greekWords;
	}

	public void setGreekWords(ArrayList<GreekWord> greekWords) {
		this.greekWords = greekWords;
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
			
			BufferedReader br = new BufferedReader(new InputStreamReader(greekDictionary, "UTF-8"));

			String strLine;

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				String test = strLine;

		        String[] result = test.split("\\\t");
		        /*if (result.length<3 && result[0]!=null && !result[0].isEmpty()){
		        	System.out.println("Greek Dictionary"+ ++i+" "+result[0]);
		    	}*/
		        if (result.length<3)
		        	continue;

		    	String word, lemma, partOfSpeech;
		    	word = result[0].toLowerCase().trim();
		    	lemma = result[1].toLowerCase().trim();
		    	partOfSpeech = result[2].toLowerCase().trim();
	        	ArrayList<String> extras = new ArrayList<String>();
		        if (result.length>=4){
		        	result = result[3].split("\\,");
		        	for(String s : result){
		        		extras.add(s.toLowerCase().trim());
		        	}
		        }
		        DictionaryEntry entry = new DictionaryEntry(lemma, partOfSpeech, extras);
				if (entry.isActive() && word!=null && !word.isEmpty())
					greekWords.add(new GreekWord(word, partOfSpeech(partOfSpeech, extras)));
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
		//int i=0;
		//for (Word w:greekWords)
			//System.out.println((++i)+" "+w.toString()+" "+w.getPhonetics()+" "+w.getType());
	}
	
	private void readSoundDic(){
		String text = "";
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		text = new Scanner(greekSoundDictionary, "UTF-8").useDelimiter("\\A").next();
		Text txt = new Text(text, LanguageCode.GR);
		HashMap<Word, Integer> tmp = txt.getWordsFreq();
		Object obj[] = tmp.keySet().toArray();
		for (Object x : obj){
			String w = ((Word) x).toString();
			words.put(w,0);
		}
		Object obj2[] = words.keySet().toArray();
		for (Object x2 : obj2){
			GreekWord gw = new GreekWord((String)x2);
			//System.out.println("sound "+gw.toString());
			similarSoundGreekWords.add(gw);
		}
	}
	
	private WordType partOfSpeech(String pos, ArrayList<String> ext){
		if (pos.trim().equals("ουσιαστικό"))
			return WordType.Noun;
		if (pos.trim().equals("επίθετο"))
			return WordType.Adjective;
		if (pos.trim().equals("ρήμα"))
			return WordType.Verb;
		if (ext.contains("μετοχή"))
			return WordType.Participle;
		return WordType.Unknown;
	}

}

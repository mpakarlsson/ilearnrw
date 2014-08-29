package ilearnrw.languagetools.greek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.greek.GreekWord;

public class GreekDictionary {
	private Map<String, GreekWord> dictionary;

	private static GreekDictionary instance = null;

	protected GreekDictionary() {
		super();
		readDic();
	}

	public GreekWord getWord(String w) {
		if (dictionary.containsKey(w)) {
			return dictionary.get(w);
		}

		return null;
	}
	
	/*protected GreekDictionary(String filename) {
		super();
		loadWords(filename);
	}

	protected GreekDictionary(SortedTreeSet gw) {
		super(gw);
	}*/
	public static GreekDictionary getInstance() {
	      if(instance == null) {
	         instance = new GreekDictionary();
	      }
	      return instance;
	}
	/*public static GreekDictionary getInstance(String filename) {
	      if(instance == null) {
	         instance = new GreekDictionary(filename);
	      }
	      return instance;
	}
	public static GreekDictionary getInstance(SortedTreeSet gw) {
	      if(instance == null) {
	         instance = new GreekDictionary(gw);
	      }
	      return instance;
	}
	
	public void loadWords(){
		GreekDictionaryLoader gl = new GreekDictionaryLoader("greekDictionary");
		words = gl.getEntries();
	}	

	
	public void loadWords(String filename){
		GreekDictionaryLoader gl = new GreekDictionaryLoader(filename);
		words = gl.getEntries();
	}*/
	
	private void readDic(){
		try {
			dictionary = new HashMap<String, GreekWord>();
			InputStream input = ResourceLoader.getInstance().getInputStream(Type.DATA, "greekDictionary");
			InputStreamReader in = new InputStreamReader(input, "UTF-8");
			BufferedReader buf = new BufferedReader(in);
			String line = null;
			while((line=buf.readLine())!=null) {
				String[] row = line.split(" ");
				if (row.length == 1 && row[0]!=null && !row[0].isEmpty()){
					dictionary.put(row[0], new GreekWord(row[0], WordType.Unknown));
				}
				if (row.length == 3){
					GreekWord w = new GreekWord(row[0], partOfSpeech(row[1]));
					w.setStem(row[2].trim());
					dictionary.put(row[0], w);
				}
				if (row.length<3)
					continue;
			}
			buf.close();
			in.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private WordType partOfSpeech(String pos){
		if (pos.trim().equals("ουσιαστικό"))
			return WordType.Noun;
		if (pos.trim().equals("επίθετο"))
			return WordType.Adjective;
		if (pos.trim().equals("ρήμα"))
			return WordType.Verb;
		if (pos.trim().equals("μετοχή"))
			return WordType.Participle;
		return WordType.Unknown;
	}
	
}

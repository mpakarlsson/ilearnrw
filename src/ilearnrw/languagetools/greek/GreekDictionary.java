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
import ilearnrw.textclassification.english.EnglishWord;
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

		return new GreekWord(w);
	}

	public Map<String, GreekWord> getDictionary() {
		return dictionary;
	}

	public static GreekDictionary getInstance() {
	      if(instance == null) {
	         instance = new GreekDictionary();
	      }
	      return instance;
	}
	
	private void readDic(){
		try {
			dictionary = new HashMap<String, GreekWord>();
			InputStream input = ResourceLoader.getInstance().getInputStream(Type.DATA, "greekSmallDict.txt");
			InputStreamReader in = new InputStreamReader(input, "UTF-8");
			BufferedReader buf = new BufferedReader(in);
			String line = null;
			while((line=buf.readLine())!=null) {
				String[] row = line.split(" ");
				if (row.length == 1 && row[0]!=null && !row[0].isEmpty()){
					dictionary.put(row[0], new GreekWord(row[0], WordType.Unknown));
				}
				if (row.length == 4){
					GreekWord w = new GreekWord(row[0], partOfSpeech(row[1]), row[2].trim(), Integer.parseInt(row[3]));
					dictionary.put(row[0], w);
				}
				if (row.length<4)
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
		if (pos.trim().equals("επίρρημα"))
			return WordType.Adverb;
		if (pos.trim().equals("επιφώνημα"))
			return WordType.Exclamation;
		if (pos.trim().equals("αντωνυμία"))
			return WordType.Pronoun;
		if (pos.trim().equals("πρόθεση"))
			return WordType.Preposition;
		if (pos.trim().equals("μόριο"))
			return WordType.Particle;
		if (pos.trim().equals("άρθρο"))
			return WordType.Article;
		if (pos.trim().equals("σύνδεσμος"))
			return WordType.Conjunction;
		return WordType.Unknown;
	}
}

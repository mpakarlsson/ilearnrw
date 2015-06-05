package ilearnrw.languagetools.greek;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.greek.GreekWord;

public class GreekSoundDictionary {
	private HashMap<String, ArrayList<String>> collection = new HashMap<String, ArrayList<String>>();
	private static GreekSoundDictionary instance = null;
	private GreekSoundDictionary() {
		loadCollection();
	}

	public static GreekSoundDictionary getInstance(){
		if(instance==null)
			instance = new GreekSoundDictionary();
		return instance;
	}

	private void loadCollection(){
		try {
			InputStream input = ResourceLoader.getInstance().getInputStream(Type.DATA, "soundSimilarityList.txt");
			InputStreamReader in = new InputStreamReader(input, "UTF-8");
			BufferedReader buf = new BufferedReader(in);
			String line = null;
			while((line=buf.readLine())!=null) {
				String[] row = line.split("\t");
				if (row.length > 1 && row[0]!=null && !row[0].isEmpty()){
					ArrayList<String> similar = new ArrayList<String>();
					for (int i = 1; i<row.length; i++){
						if (row[i]!=null && !row[i].isEmpty())
							similar.add(row[i]);
					}
					if (!similar.isEmpty())
						collection.put(row[0], similar);
				}
			}
			buf.close();
			in.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, ArrayList<String>> getCollection(){
		return collection;
	}
	
	/*private void loadWords(){
		try {
			words = new ArrayList<GreekWord>();
			InputStream input = ResourceLoader.getInstance().getInputStream(Type.DATA, "greekSoundSimilarityList.txt");
			InputStreamReader in = new InputStreamReader(input, "UTF-8");
			BufferedReader buf = new BufferedReader(in);
			String line = null;
			while((line=buf.readLine())!=null) {
				String[] row = line.split(" ");
				if (row.length == 1 && row[0]!=null && !row[0].isEmpty()){
					words.add(new GreekWord(row[0], WordType.Unknown));
				}
				if (row.length == 4){
					GreekWord w = new GreekWord(row[0], partOfSpeech(row[1]), row[2].trim(), Integer.parseInt(row[3]));

					words.add(w);
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
	
	public ArrayList<GreekWord> getWords(){
		return words;
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
	}*/
}

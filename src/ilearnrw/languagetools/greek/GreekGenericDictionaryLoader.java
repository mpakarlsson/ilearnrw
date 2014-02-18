package ilearnrw.languagetools.greek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import ilearnrw.languagetools.DictionaryLoader;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.greek.GreekWord;

public class GreekGenericDictionaryLoader extends DictionaryLoader {
	public GreekGenericDictionaryLoader(String filename) {
		super(filename);
	}
	
	public void readDic(){
		// Open the file
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(dictionary, "UTF-8"));

			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String test = strLine;
				if ((test.contains("#"))){
					continue;
				}

		        String[] result = test.split("\\\t");
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
					words.add(new GreekWord(word, partOfSpeech(partOfSpeech, extras)));
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

package ilearnrw.languagetools.english;

import ilearnrw.textclassification.GraphemePhonemePair;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.english.EnglishWord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnglishDictionary {

	private Map<String, EnglishWord> dictionary;
	
	public EnglishDictionary(String fileName){
		dictionary = new HashMap<String, EnglishWord>();
		
		loadDictionary(fileName);
	}
	
	public Map<String, EnglishWord> getDictionary(){
		return dictionary;
	}
	
	public EnglishWord getWord(String w){
		if(dictionary.containsKey(w)){
			return dictionary.get(w);
		}
		
		return null;
	}
	
	private void loadDictionary(String fileName){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data/" + fileName), "UTF-8"));

			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {				
				String[] results = strLine.split("\t");
				
				String word, stem, phonetic;
				ArrayList<GraphemePhonemePair> phoneticList;
				int numChars, numPhons, numSyllables;
				double frequency;
				// Add this when it's possible
				// syllables
				// partOfSpeech
				
				
				word = results[0];
				stem = results[1];
				phonetic = results[2];
				
				phoneticList = new ArrayList<GraphemePhonemePair>();
				numPhons = 0;
				
				if(!results[2].contains("<")){
					String[] phoneticMatches = results[3].split(",");
					
					for(String s : phoneticMatches){
						String[] values = s.split("-", -1);
						
						if(values.length>2){
							for(int i=1; i<values.length-1; i++){
								values[0] += values[i];
								
								if(values[i].isEmpty())
									values[0] += "-";
							}
							values[1] = values[values.length-1];
						}
						
						GraphemePhonemePair pair = new GraphemePhonemePair(values[0], values[1]);
						
						phoneticList.add(pair);
					}
					
					numPhons = Integer.parseInt(results[5]);
				}
				
				numChars = Integer.parseInt(results[4]);
				numSyllables = Integer.parseInt(results[6]);
				frequency = Double.parseDouble(results[7]);
				
				EnglishWord w = new EnglishWord(word,phonetic, phoneticList, numSyllables, frequency, WordType.Unknown);
				
				if(!dictionary.containsKey(word)){
					dictionary.put(word, w);
				}
			}

			//Close the input stream
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

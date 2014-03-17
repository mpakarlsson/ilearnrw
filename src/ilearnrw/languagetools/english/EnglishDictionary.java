package ilearnrw.languagetools.english;

import ilearnrw.textclassification.GraphemePhonemePair;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.english.EnglishWord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
				ArrayList<String> graphemeSyllables = new ArrayList<String>();
				ArrayList<String> phoneticSyllables = new ArrayList<String>();
				int numChars, numPhons, numSyllables;
				double frequency;
				String suffixType, suffix;
				// Add this when it's possible
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
					
					numPhons = Integer.parseInt(results[9]);
				}
				
				suffixType = results[4];
				suffix = results[5];
				graphemeSyllables = new ArrayList<String>(Arrays.asList(results[6].split(",")));
				phoneticSyllables = new ArrayList<String>(Arrays.asList(results[7].split(",")));
				
				numChars = Integer.parseInt(results[8]);
				numSyllables = Integer.parseInt(results[10]);
				frequency = Double.parseDouble(results[11]);
				
				EnglishWord w = new EnglishWord(word, phonetic, stem, phoneticList, suffix, suffixType, numSyllables, frequency, WordType.Unknown);
				
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

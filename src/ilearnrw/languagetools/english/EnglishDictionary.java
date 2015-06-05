package ilearnrw.languagetools.english;

import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.textclassification.GraphemePhonemePair;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.english.EnglishWord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
public class EnglishDictionary {

	private Map<String, EnglishWord> dictionary;

	protected EnglishDictionary() {
		dictionary = new HashMap<String, EnglishWord>();
	}

	public Map<String, EnglishWord> getDictionary() {
		return dictionary;
	}

	public EnglishWord getWord(String w) {
		if (dictionary.containsKey(w)) {
			return dictionary.get(w);
		}

		return null;
	}

	public void loadDictionary(String fileName) {
		try {
			BufferedReader br = ResourceLoader.getInstance().getBufferedReaderUTF8(Type.DATA, fileName);

			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String[] results = strLine.split("\t");

				String word, stem, phonetic;
				ArrayList<GraphemePhonemePair> phoneticList;
				String[] graphemeSyllables;
				int numChars, numPhons, numSyllables;
				int frequency;
				String suffixType, suffix, prefixType, prefix;
				String valid;
				// Add this when it's possible
				// partOfSpeech

				word = results[0];
				stem = results[1];
				phonetic = results[2];

				phoneticList = new ArrayList<GraphemePhonemePair>();
				numPhons = 0;

				if (!results[2].contains("<")) {
					String[] phoneticMatches = results[3].split(",");

					for (String s : phoneticMatches) {
						String[] values = s.split("-", -1);

						if (values.length > 2) {
							for (int i = 1; i < values.length - 1; i++) {
								values[0] += values[i];

								if (values[i].isEmpty())
									values[0] += "-";
							}
							values[1] = values[values.length - 1];
						}

						GraphemePhonemePair pair = new GraphemePhonemePair(
								values[0], values[1]);

						phoneticList.add(pair);
					}

					numPhons = Integer.parseInt(results[9]);
				}

				suffixType = results[4];
				suffix = results[5];

				graphemeSyllables = results[6].split("\\.");
				numChars = Integer.parseInt(results[7]);
				numSyllables = Integer.parseInt(results[9]);
				frequency = Integer.parseInt(results[10]);

				prefixType = results[11];
				prefix = results[12];
				
				valid =  results[13];
				
				EnglishWord w = new EnglishWord(word, phonetic, stem,
						phoneticList, graphemeSyllables, 
						suffix, suffixType,
						prefix, prefixType,
						numSyllables, frequency, WordType.Unknown);

				if(valid.equals("NO_LIST"))
					w.setListAsGameWord(false);
				
				String lowerWord = word.toLowerCase();
				if (!dictionary.containsKey(lowerWord)) {
					dictionary.put(lowerWord, w);
				}
			}

			// Close the input stream
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

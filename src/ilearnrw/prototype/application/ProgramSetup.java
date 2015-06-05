package ilearnrw.prototype.application;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramSetup {
	public static Map<String, ArrayList<String>> LoadDictionary(){
		/**
		 * Dictionary layout
		 * Param1: Word 
		 * Param2: Root word 
		 * Param3: Number of syllables
		 * Param4: Frequency
		 * Param5: Phonetics (IPA)
		 * 
		 * e.g. 
		 * 	absorbed|absorb|2|9762|əb.ˈzɔːbd 
		 */
		
		Map<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();		
			try {
				BufferedReader reader = ResourceLoader.getInstance().getBufferedReaderUTF8(Type.DATA, "dictionary_english.csv");
				String line = null;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split("\\|");
					
					ArrayList<String> temp = new ArrayList<String>();
					for(int i=1; i<parts.length; i++){							
						temp.add(parts[i]);
					}
					
					dict.put(parts[0], temp);
				}
				reader.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return dict;
	}
	
	public static List<String> LoadDaleChallList(){
		try {
			List<String> words = ResourceLoader.getInstance().readAllLinesAsListUTF8(Type.DATA, "dale-chall_word_list.txt");
			return words;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	public static Map<String, ArrayList<String>> SetupSoundToSpelling(){
		Map<String, ArrayList<String>> ipa = new HashMap<String, ArrayList<String>>();
		
		// Found at: http://en.wikipedia.org/wiki/English_orthography#Spelling_patterns
		// some entries have been left out after discussing with language experts (e.g. "in some dialects"-items)		
		
		// Consonants
		ipa.put("p", new ArrayList<String>(Arrays.asList(new String[] {"p", "pp", "gh"})));
		ipa.put("b", new ArrayList<String>(Arrays.asList(new String[] {"b", "bb" })));
		ipa.put("t", new ArrayList<String>(Arrays.asList(new String[] {"t", "tt", "ed", "pt", "th", "ct"})));
		ipa.put("d", new ArrayList<String>(Arrays.asList(new String[] {"d", "dd", "ed", "dh"})));
		ipa.put("ɡ", new ArrayList<String>(Arrays.asList(new String[] {"g", "gg", "gue", "gh"})));
		ipa.put("k", new ArrayList<String>(Arrays.asList(new String[] {"c", "k", "ck", "ch", "cc", "qu", "cqu", "cu", "que", 
																			"kk", "kh", "q", "x"})));
		ipa.put("m", new ArrayList<String>(Arrays.asList(new String[] {"m", "mm", "mb", "mn", "mh", "gm", "chm"})));
		ipa.put("n", new ArrayList<String>(Arrays.asList(new String[] {"n", "nn", "kn", "gn", "pn", "nh", "cn", "mn"})));
		ipa.put("ŋ", new ArrayList<String>(Arrays.asList(new String[] {"ng", "n", "ngue"})));
		ipa.put("r", new ArrayList<String>(Arrays.asList(new String[] {"r", "rr", "wr", "rh", "rrh"})));
		ipa.put("f", new ArrayList<String>(Arrays.asList(new String[] {"f", "ph", "ff", "gh", "pph", "u"})));
		ipa.put("v", new ArrayList<String>(Arrays.asList(new String[] {"v", "vv", "f", "ph", "w"})));
		ipa.put("θ", new ArrayList<String>(Arrays.asList(new String[] {"th", "chth", "phth", "tth"})));
		ipa.put("ð", new ArrayList<String>(Arrays.asList(new String[] {"th", "the"})));
		ipa.put("s", new ArrayList<String>(Arrays.asList(new String[] {"s", "c", "ss", "sc", "st", "ps", "cc", "se", "ce"})));
		ipa.put("z", new ArrayList<String>(Arrays.asList(new String[] {"s", "z", "x", "zz", "ss", "ze"})));
		ipa.put("ʃ", new ArrayList<String>(Arrays.asList(new String[] {"sh", "ti", "ci", "ssi", "si", "ss", "ch", "s", "sci", "ce", 
																			"sch", "sc"})));
		ipa.put("ʒ", new ArrayList<String>(Arrays.asList(new String[] {"si", "s", "g", "z", "j", "ti"})));
		ipa.put("tʃ", new ArrayList<String>(Arrays.asList(new String[] {"ch", "t", "tch", "ti", "c", "cc", "tsch", "cz"})));
		ipa.put("dʒ", new ArrayList<String>(Arrays.asList(new String[] {"g", "j", "dg", "dge", "d", "di", "gi", "ge", "gg"})));
		ipa.put("h", new ArrayList<String>(Arrays.asList(new String[] {"h", "wh", "j", "ch"})));
		ipa.put("j", new ArrayList<String>(Arrays.asList(new String[] {"y", "i", "j", "ll", "e"})));
		ipa.put("l", new ArrayList<String>(Arrays.asList(new String[] {"l", "ll", "lh"})));
		ipa.put("w", new ArrayList<String>(Arrays.asList(new String[] {"w", "u", "o", "ou"})));
		
		
		// Vowels
		/*ipa.put("iː", new ArrayList<String>(Arrays.asList(new String[] {"e", "ea", "ee", "e...e", "ae", "ei", "i...e", "ie", "eo", "ie...e", 
																			"ay", "ey", "i", "y", "oi", "ue", "ey", "a"})));
		ipa.put("ɪ", new ArrayList<String>(Arrays.asList(new String[] {"i", "y", "ui", "e", "ee", "ie", "o", "u", "a", "ei", 
																			"ee", "ia", "ea", "i...e", "ai", "ii", "oe"})));
		ipa.put("uː", new ArrayList<String>(Arrays.asList(new String[] {"oo", "u", "o", "u...e", "ou", "ew", "ue", "o...e", "ui", "eu", "oeu", 
																			"oe", "ough", "wo", "ioux", "ieu", "oup", "w", "u"})));
		ipa.put("ʊ", new ArrayList<String>(Arrays.asList(new String[] {"oo", "u", "o", "oo...e", "or", "ou", "oul"})));
		ipa.put("eɪ", new ArrayList<String>(Arrays.asList(new String[] {"a", "a...e", "aa", "ae", "ai", "ai...e", "aig", "aigh", "al", "ao", 
																			"au", "ay", "e", "é", "e...e", "ae", "eg", "ei", "ei...e", "eig", 
																			"eigh", "ee", "ée", "eh", "er", "es", "et", "ey", "ez", "ie", 
																			"oeh", "ue", "uet"})));
		ipa.put("ə", new ArrayList<String>(Arrays.asList(new String[] {"a", "e", "o", "u", "ai", "ou", "eig", "y", "ah", "ough", 
																			"ae", "oi"})));
		ipa.put("oʊ", new ArrayList<String>(Arrays.asList(new String[] {"o", "o...e", "oa", "ow", "ou", "oe", "oo", "eau", "oh", "ew", 
																			"au", "aoh", "ough", "eo"})));
		ipa.put("ɛ", new ArrayList<String>(Arrays.asList(new String[] {"e", "ea", "a", "ae", "ai", "ay", "ea...e", "ei", "eo", "ie", 
																			"ieu", "u", "ue", "oe"})));
		ipa.put("æ", new ArrayList<String>(Arrays.asList(new String[] { "a", "ai", "al", "au", "i"})));
		ipa.put("ʌ", new ArrayList<String>(Arrays.asList(new String[] {"u", "o", "o...e", "eo", "ou", "oo", "wo"})));
		ipa.put("ɔː", new ArrayList<String>(Arrays.asList(new String[] {"a", "au", "aw", "ough", "augh", "o", "oa", "oo", "al", "uo", 
																			"u", "ao"})));
		ipa.put("ɒ", new ArrayList<String>(Arrays.asList(new String[] {"o", "a", "eau", "ach", "au", "ou"})));
		ipa.put("ɑː", new ArrayList<String>(Arrays.asList(new String[] {"a", "ah", "aa", "i"})));
		ipa.put("aɪ", new ArrayList<String>(Arrays.asList(new String[] {"ae", "ai", "aie", "aille", "ais", "ay", "aye", "ei", "eigh", "ey", 
																			"eye", "i", "i...e", "ia", "ie", "ic", "ig", "igh", "is", "oi",
																			"ui", "uy", "uye", "y", "y...e", "ye"})));
		ipa.put("ɔɪ", new ArrayList<String>(Arrays.asList(new String[] {"oi", "oy", "awy", "uoy", "oy...e", "eu"})));
		ipa.put("aʊ", new ArrayList<String>(Arrays.asList(new String[] {"ou", "ow", "ough", "au", "ao"})));
		ipa.put("ɑr", new ArrayList<String>(Arrays.asList(new String[] {"aar", "ar", "are", "arre", "ear", "er", "our", "uar", "arrh"})));
		ipa.put("ɛər", new ArrayList<String>(Arrays.asList(new String[] {"aar", "aer", "air", "aire", "ar", "are", "ayer", "ayor", "ear", "eir", 
																			"er", "ere", "err", "erre", "ey're", "e'er"})));
		ipa.put("ɪər", new ArrayList<String>(Arrays.asList(new String[] {"ear", "eer", "eir", "eor", "ere", "ers", "e're", "ier", "iere", "ir" })));
		ipa.put("ɜr", new ArrayList<String>(Arrays.asList(new String[] {"er", "or", "ur", "ir", "yr", "our", "ear", "err", "eur", "yrrh", 
																			"ar", "oeu", "olo"})));
		ipa.put("juː", new ArrayList<String>(Arrays.asList(new String[] {"u", "u...e", "eu", "ue", "iew", "eau", "ieu", "ueue", "ui", "ewe", 	
																			"ew"})));
		*/
		
		ipa.put("iː", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɪ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("uː", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ʊ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("eɪ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ə", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("oʊ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɛ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("æ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ʌ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɔː", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɒ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɑː", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("aɪ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɔɪ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("aʊ", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɑr", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɛər", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɪər", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("ɜr", new ArrayList<String>(Arrays.asList(new String[] {})));
		ipa.put("juː", new ArrayList<String>(Arrays.asList(new String[]{})));
		return ipa;
	}
}

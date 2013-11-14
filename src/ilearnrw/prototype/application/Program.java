package ilearnrw.prototype.application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.user.problems.EnglishProblems;

import ilearnrw.datalogger.IProfileAccessUpdater;
import ilearnrw.datalogger.ILoginProvider;
import ilearnrw.datalogger.IDataLogger;
import ilearnrw.datalogger.DataLogger;

public class Program {

	private static DataLogger sDataLogger = new DataLogger();
	private static final Map<String, ArrayList<String>> sDictionary = LoadDictionary();
	private static final ArrayList<String> sDaleChallList = LoadDaleChallList();
	private static final Map<String, ArrayList<String>> sSoundToSpelling = SetupSoundToSpelling();
	
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
				BufferedReader reader = new BufferedReader(new FileReader("data/dictionary.txt"));
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
	
	public static ArrayList<String> LoadDaleChallList(){
		ArrayList<String> words = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/dale-chall_word_list.txt"));
		
			String line = null;
			while ((line = reader.readLine()) != null) {
				words.add(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
	}
	
	public static Map<String, ArrayList<String>> SetupSoundToSpelling(){
		Map<String, ArrayList<String>> ipa = new HashMap<String, ArrayList<String>>();
		
		// Found at: http://en.wikipedia.org/wiki/English_orthography#Spelling_patterns
		// some entries have been left out after discussing with language experts (e.g. "in some dialects"-items)
		
		ipa.put("e", new ArrayList<String>(Arrays.asList(new String[] {"e", "ea"})));
		
		
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
		ipa.put("iː", new ArrayList<String>(Arrays.asList(new String[] {"e", "ea", "ee", "e...e", "ae", "ei", "i...e", "ie", "eo", "ie...e", 
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
		
		return ipa;
	}
	
	public static Map<String, ArrayList<String>> getSoundToSpellingList(){
		return sSoundToSpelling;
	}
	
	public static Map<String, ArrayList<String>> getDictionary(){
		return sDictionary;
	}
	
	public static ArrayList<String> getDaleChallList(){
		return sDaleChallList;
	}
	
	public static IProfileAccessUpdater getProfileAccessUpdater() {
		return sDataLogger;
	}
	public static ILoginProvider getLoginProvider() {
		return sDataLogger;
	}
	public static IDataLogger getDataLogger() {
		return sDataLogger;
	}

	private static String sWelcomeMessage = "Welcome to the iLearnRW prototype application\n"
											+ "\n"
											+ "Developer notes:\n"
											+ "\tUsernames are not unique.\n"
											+ "\tUsernames cannot contain spaces.\n"
											+ "\n";

	public static String getStringArg(String name, String[] args) throws Exception
	{
		boolean found = false;
		for(String arg : args) {
			if( found == true )
				return arg;
			else if( arg.equals(name) )
				found = true;
		}
		throw new Exception("Argument " + name + " not found.");
	}
	
	/**
	 * @param args	Commandline arguments.
	 * 
	 * Commandline arguments:
	 * 
	 * --db <filePath>		Database file to use (required)
	 */
	
	
	public static void main(String[] args) {
		try {
			System.out.print(sWelcomeMessage);
			String databaseFile = getStringArg("--db", args);
			System.out.println("Using database file: " + databaseFile);
			
			// Initialize the DataLogger object
			sDataLogger.loadUserStore(databaseFile);
			sDataLogger.loadUserActions(databaseFile);
			
//			ArrayList<EnglishWord> ew = new ArrayList<EnglishWord>();
//			Iterator it = sDictionary.entrySet().iterator();
//			while(it.hasNext()){
//				Map.Entry pairs = (Map.Entry<String, ArrayList<String>>)it.next();
//				ArrayList<String> list = (ArrayList<String>)pairs.getValue();
//				
//				ew.add(new EnglishWord(list.get(0)));
//			}
			
			ConsoleMenu mnu = new ConsoleMenu(System.out, System.in, "iLearnRW - Main menu", 
					new IConsoleMenuAction[] {
						new DatabaseManager("Manage datalogger database"),
						new UserLogin("Login user"),
						new ConsoleMenuAction("Exit") {
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								return EConsoleMenuActionResult.menuBreak;
								
							}
						}
					}
			);
			mnu.doModalMenu();
		} catch (Exception ex) {
			System.out.println("Unhandled exception");
			System.out.println("-------------------");
			System.out.print(ex.toString());
		}

		

	}
	


}
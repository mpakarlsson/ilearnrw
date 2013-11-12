package ilearnrw.prototype.application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.user.problems.EnglishProblems;
import ilearnrw.user.problems.GreekProblems;

import ilearnrw.datalogger.IProfileAccessUpdater;
import ilearnrw.datalogger.ILoginProvider;
import ilearnrw.datalogger.IDataLogger;
import ilearnrw.datalogger.DataLogger;

public class Program {

	private static DataLogger sDataLogger = new DataLogger();
	private static final Map<String, ArrayList<String>> sDictionary = LoadDictionary();
	private static final ArrayList<String> sDaleChallList = LoadDaleChallList();
	private static final Map<String, ArrayList<String>> sSoundToSpellingConsonant = SetupConsonantSoundToSpelling();
	
	public static Map<String, ArrayList<String>> LoadDictionary(){
		/**
		 * Dictionary layout
		 * Param1: Word 
		 * Param2: Root word 
		 * Param3: Number of syllables
		 * Param4: Frequency
		 * 
		 * e.g. 
		 * 	absorbed absorb 2 9762
		 */
		
		Map<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();		
			try {
				BufferedReader reader = new BufferedReader(new FileReader("data/dictionary.txt"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split("\\s");

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
	
	public static Map<String, ArrayList<String>> SetupConsonantSoundToSpelling(){
		Map<String, ArrayList<String>> consonantMap = new HashMap<String, ArrayList<String>>();
		// Found at: http://en.wikipedia.org/wiki/English_orthography#Spelling_patterns
		// some entries have been left out after discussing with language experts (e.g. "in some dialects"-items)
		consonantMap.put("p", new ArrayList<String>(Arrays.asList(new String[] {"p", "pp", "gh"})));
		consonantMap.put("b", new ArrayList<String>(Arrays.asList(new String[] {"b", "bb" })));
		consonantMap.put("t", new ArrayList<String>(Arrays.asList(new String[] {"t", "tt", "ed", "pt", "th", "ct"})));
		consonantMap.put("d", new ArrayList<String>(Arrays.asList(new String[] {"d", "dd", "ed", "dh"})));
		consonantMap.put("ɡ", new ArrayList<String>(Arrays.asList(new String[] {"g", "gg", "gue", "gh"})));
		consonantMap.put("k", new ArrayList<String>(Arrays.asList(new String[] {"c", "k", "ck", "ch", "cc", "qu", "cqu", "cu", "que", "kk", "kh", "q", "x"})));
		consonantMap.put("m", new ArrayList<String>(Arrays.asList(new String[] {"m", "mm", "mb", "mn", "mh", "gm", "chm"})));
		consonantMap.put("n", new ArrayList<String>(Arrays.asList(new String[] {"n", "nn", "kn", "gn", "pn", "nh", "cn", "mn"})));
		consonantMap.put("ŋ", new ArrayList<String>(Arrays.asList(new String[] {"ng", "n", "ngue"})));
		consonantMap.put("r", new ArrayList<String>(Arrays.asList(new String[] {"r", "rr", "wr", "rh", "rrh"})));
		consonantMap.put("f", new ArrayList<String>(Arrays.asList(new String[] {"f", "ph", "ff", "gh", "pph", "u"})));
		consonantMap.put("v", new ArrayList<String>(Arrays.asList(new String[] {"v", "vv", "f", "ph", "w"})));
		consonantMap.put("θ", new ArrayList<String>(Arrays.asList(new String[] {"th", "chth", "phth", "tth"})));
		consonantMap.put("ð", new ArrayList<String>(Arrays.asList(new String[] {"th", "the"})));
		consonantMap.put("s", new ArrayList<String>(Arrays.asList(new String[] {"s", "c", "ss", "sc", "st", "ps", "cc", "se", "ce"})));
		consonantMap.put("z", new ArrayList<String>(Arrays.asList(new String[] {"s", "z", "x", "zz", "ss", "ze"})));
		consonantMap.put("ʃ", new ArrayList<String>(Arrays.asList(new String[] {"sh", "ti", "ci", "ssi", "si", "ss", "ch", "s", "sci", "ce", "sch", "sc"})));
		consonantMap.put("ʒ", new ArrayList<String>(Arrays.asList(new String[] {"si", "s", "g", "z", "j", "ti"})));
		consonantMap.put("tʃ", new ArrayList<String>(Arrays.asList(new String[] {"ch", "t", "tch", "ti", "c", "cc", "tsch", "cz"})));
		consonantMap.put("dʒ", new ArrayList<String>(Arrays.asList(new String[] {"g", "j", "dg", "dge", "d", "di", "gi", "ge", "gg"})));
		consonantMap.put("h", new ArrayList<String>(Arrays.asList(new String[] {"h", "wh", "j", "ch"})));
		consonantMap.put("j", new ArrayList<String>(Arrays.asList(new String[] {"y", "i", "j", "ll", "e"})));
		consonantMap.put("l", new ArrayList<String>(Arrays.asList(new String[] {"l", "ll", "lh"})));
		consonantMap.put("w", new ArrayList<String>(Arrays.asList(new String[] {"w", "u", "o", "ou"})));
		
		return consonantMap;
	}
	
	public static Map<String, ArrayList<String>> getSoundToSpellingList(){
		return sSoundToSpellingConsonant;
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
			GreekWord a = new GreekWord("Ακούσατε");
			EnglishWord b = new EnglishWord("defend");
			
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
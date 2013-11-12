package ilearnrw.prototype.application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;
import ilearnrw.user.problems.EnglishProblems;
import ilearnrw.user.problems.GreekProblems;

import ilearnrw.datalogger.IProfileAccessUpdater;
import ilearnrw.datalogger.ILoginProvider;
import ilearnrw.datalogger.IDataLogger;
import ilearnrw.datalogger.DataLogger;

public class Program {

	private static DataLogger sDataLogger = new DataLogger();
	private static final Map<String, Vector<String>> sDictionary = LoadDictionary();
	private static final ArrayList<String> sDaleChallList = LoadDaleChallList();
	
	public static Map<String, Vector<String>> LoadDictionary(){
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
		
		Map<String, Vector<String>> dict = new HashMap<String, Vector<String>>();		
			try {
				BufferedReader reader = new BufferedReader(new FileReader("data/dictionary.txt"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split("\\s");

					Vector<String> temp = new Vector<String>();
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
	
	public static Map<String, Vector<String>> getDictionary(){
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
package ilearnrw.prototype.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.datalogger.IProfileAccessUpdater;
import ilearnrw.datalogger.ILoginProvider;
import ilearnrw.datalogger.IDataLogger;
import ilearnrw.datalogger.DataLogger;

public class Program {

	private static DataLogger sDataLogger = new DataLogger();
	private static final Map<String, ArrayList<String>> sDictionary = ProgramSetup.LoadDictionary();
	private static final List<String> sDaleChallList = ProgramSetup.LoadDaleChallList();
	private static final Map<String, ArrayList<String>> sSoundToSpelling = ProgramSetup.SetupSoundToSpelling();
	
	
	public static Map<String, ArrayList<String>> getSoundToSpellingList(){
		return sSoundToSpelling;
	}
	
	public static Map<String, ArrayList<String>> getDictionary(){
		return sDictionary;
	}
	
	public static List<String> getDaleChallList(){
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
			
			
			EnglishWord st = new EnglishWord("stegosaurus");
			EnglishWord st1 = new EnglishWord("lemma");
			EnglishWord st2 = new EnglishWord("astrological");
			
			
			EnglishWord w = new EnglishWord("excommunication");
			
			//EnglishWord w1 = new EnglishWord("hedge");
			//EnglishWord w2 = new EnglishWord("deck");
			EnglishWord w3 = new EnglishWord("youth");
			EnglishWord w4 = new EnglishWord("competitor");
			EnglishWord w5 = new EnglishWord("gnome");
			
			ArrayList<EnglishWord> ew = new ArrayList<EnglishWord>();
			Iterator it = sDictionary.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry<String, ArrayList<String>>)it.next();
				ArrayList<String> list = (ArrayList<String>)pairs.getValue();
				
				ew.add(new EnglishWord(pairs.getKey().toString()));
			}
			
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
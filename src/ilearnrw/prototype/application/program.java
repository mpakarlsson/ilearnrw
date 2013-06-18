package ilearnrw.prototype.application;

import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;

import ilearnrw.datalogger.LoginDetails;
import ilearnrw.datalogger.DataLogger;

import java.util.ArrayList;
import java.util.Scanner;


public class Program {

	public static DataLogger gDataLogger = new DataLogger();

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
	
	public static LoginDetails getLoginDetails() {
		LoginDetails ret = new LoginDetails();
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter username: ");
		ret.Username = scanner.nextLine();
		System.out.print("Enter password: ");
		ret.Password = scanner.nextLine();
		scanner.close();
		return ret;
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
			System.out.println("Hello iLearnRW");
			String databaseFile = getStringArg("--db", args);
			System.out.println("Using database file: " + databaseFile);
			gDataLogger.loadUserStore(databaseFile);
			
			ConsoleMenu mnu = new ConsoleMenu(System.out, System.in, "iLearnRW - Main menu", 
					new IConsoleMenuAction[] {
						new DatabaseManager("Manage datalogger database"),
						new ConsoleMenuAction("Manage user") {
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								System.out.println("Not implemented yet.");
								return EConsoleMenuActionResult.showThisMenuAgain;
								
							}
						},
						new ConsoleMenuAction("exit") {
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

package ilearnrw.prototype.application;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class ConsoleMenu {
	
	public enum EConsoleMenuActionResult
	{
		showThisMenuAgain,
		menuBreak
	};
	public interface IConsoleMenuAction {
		EConsoleMenuActionResult onSelected(ConsoleMenu menu);
		String getText();
	};

	
	PrintStream mOut;
	InputStream mIn;
	Scanner mScanner;
	String mMenuTitle;
	IConsoleMenuAction[] mMenuActions;

	ConsoleMenu(PrintStream outStream, InputStream inStream, String menuTitle, IConsoleMenuAction[] menuActions) {
		mOut = outStream;
		mIn = inStream;
		mScanner = new Scanner(mIn);
		mMenuTitle = menuTitle;
		mMenuActions = menuActions;
	}
	
	public void doModalMenu(){
		EConsoleMenuActionResult result = EConsoleMenuActionResult.showThisMenuAgain;
		while(result != EConsoleMenuActionResult.menuBreak)
			result = showMenu();
	}
	
	public EConsoleMenuActionResult showMenu()
	{
		mOut.println();
		mOut.println(mMenuTitle);
		mOut.println(mMenuTitle.replaceAll(".", "*"));
		
		for(int i = 0; i < mMenuActions.length; i++)
			mOut.println(Integer.toString(i + 1) + ": " + mMenuActions[i].getText());
		
		/* Do not close scanner, seems to close the stream as well.
		 * */
		
		mOut.print("Enter [1-" + Integer.toString(mMenuActions.length) + "]: ");
		int selectedMenuItem = mScanner.nextInt();

		if( selectedMenuItem > 0 && selectedMenuItem <= mMenuActions.length)
		{
			return mMenuActions[selectedMenuItem-1].onSelected(this);
		}
		return EConsoleMenuActionResult.showThisMenuAgain;
	}
	
	
	public PrintStream out() {
		return mOut;
	}
	public Scanner in() {
		return mScanner;
	}
	public ConsoleMenu subMenu(String menuTitle, IConsoleMenuAction[] menuActions) {
		return new ConsoleMenu(mOut, mIn, menuTitle, menuActions);
	}

}

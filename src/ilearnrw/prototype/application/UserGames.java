package ilearnrw.prototype.application;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.games.GameAddTwoNumbers;
import ilearnrw.games.NumbersToText;
import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;
import ilearnrw.user.User;

public class UserGames extends ConsoleMenuAction {

	User mUser;

	public UserGames(String text, User user) {
		super(text);
		mUser = user;
	}

	@Override
	public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {

		menu.subMenu(
				"User: " + mUser.getDetails().getUsername(),
				new IConsoleMenuAction[] { 
					new ConsoleMenuAction("'Add Two Numbers' Game") {
						@Override
						public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
							menu.out().println("This is not implemented yet");
							new GameAddTwoNumbers(mUser, Program.getDataLogger());
							return EConsoleMenuActionResult.showThisMenuAgain;
						}
					},
					new NumbersToText("'Numbers to Text' Game", mUser, Program.getDataLogger()),
					new ConsoleMenuAction("Back") {
						@Override
						public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
							return EConsoleMenuActionResult.menuBreak;
						}
					},
				
				}).doModalMenu();
		return super.onSelected(menu);
	}

}

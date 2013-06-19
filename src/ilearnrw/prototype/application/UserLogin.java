package ilearnrw.prototype.application;

import ilearnrw.datalogger.UserStore;
import ilearnrw.prototype.application.ConsoleMenu.*;
import ilearnrw.prototype.application.ConsoleMenuAction.*;
import ilearnrw.user.User;
import ilearnrw.user.UserSession;

/** @brief Main menu of a logged in user.
 *
 */
public class UserLogin extends ConsoleMenuAction {

	public UserLogin(String text) {
		super(text);
	}

	@Override
	public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
		
		/* Get username and password from user.
		 */
		menu.out().println("Enter username: ");
		String username = menu.in().next();
		menu.out().println("Enter password: ");
		String password = menu.in().next();
		
		/* Get the User from the UserStore based
		 * on the username and password.
		 */
		final UserStore userStore = Program.gDataLogger.getUserStore();
		final User user = userStore.getUser(username, password);
		if( user == null )
		{
			menu.out().println("Wrong username or password");
			return EConsoleMenuActionResult.showThisMenuAgain;
		}
		
		menu.out().println("User successfully logged in: " + user.getDetails().getUsername());

		menu.subMenu("User: " + user.getDetails().getUsername(), new IConsoleMenuAction[] {
				new ConsoleMenuAction("List Sessions") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						menu.out().println("Not implemented yet.");
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new UserProblems("Problem definitions", user),
				new ConsoleMenuAction("Log out") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						return EConsoleMenuActionResult.menuBreak;
					}
				}
		}).doModalMenu();
		return EConsoleMenuActionResult.showThisMenuAgain;
	}
	

}

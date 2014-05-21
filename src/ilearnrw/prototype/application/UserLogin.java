package ilearnrw.prototype.application;

import ilearnrw.annotation.HtmlGenerator;
import ilearnrw.datalogger.ILoginProvider;
import ilearnrw.datalogger.IProfileAccessUpdater.PendingChangesAvailable;
import ilearnrw.datalogger.IProfileAccessUpdater.PermissionException;
import ilearnrw.prototype.application.ConsoleMenu.*;
import ilearnrw.user.User;

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
		final ILoginProvider loginProvider = Program.getLoginProvider();
		final User user = loginProvider.getUser(username, password);
		if( user == null ){
			menu.out().println("Wrong username or password");
			return EConsoleMenuActionResult.showThisMenuAgain;
		}
		
		
		
		try {			
			Program.getProfileAccessUpdater().setCurrentUser(user, user);
		} catch (PermissionException e) {
			menu.out().println("Does not have permission to set current user.");
			e.printStackTrace();
		} catch (PendingChangesAvailable e) {
			menu.out().println("Could not set current user due to pending changes");
			e.printStackTrace();
		}
		
		
		menu.out().println("User successfully logged in: " + user.getDetails().getUsername());
		
		String problemsMatrix = user.getProfile().getUserProblems().toString();

		menu.out().println(problemsMatrix);
		
		String str = HtmlGenerator.loadTemplate("pg76.txt");
		
		HtmlGenerator gen = new HtmlGenerator(str, user.getProfile(), user.getProfile().getLanguage(), "html/template.html");
		
		
		
		menu.subMenu("User: " + user.getDetails().getUsername(), new IConsoleMenuAction[] {
				new ConsoleMenuAction("List Sessions") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						menu.out().println("Not implemented yet.");
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new UserProblems("Problem definitions", user),
				new UserGames("Play Games", user),
				new ConsoleMenuAction("Log out") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						if(Program.getProfileAccessUpdater().hasPendingChanges()){
							menu.out().println("User has pending changes");
							menu.out().println("1. Apply changes");
							menu.out().println("2. Discard changes");
							int value = Integer.parseInt(menu.in().next());
							switch (value) {
							case 1:
								Program.getProfileAccessUpdater().writePendingChanges();
								menu.out().println("Pending changes has been applied");
								break;
							case 2:
								Program.getProfileAccessUpdater().discardPendingChanges();
								menu.out().println("Pending changes has been discarded");
								break;
							default:
								menu.out().println("Please choose an alternative");
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						}
						
						return EConsoleMenuActionResult.menuBreak;
					}
				}
		}).doModalMenu();
		return EConsoleMenuActionResult.showThisMenuAgain;
	}
}

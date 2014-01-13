package ilearnrw.prototype.application;

import ilearnrw.datalogger.IUserAdministration;
import ilearnrw.datalogger.IUserAdministration.AuthenticationException;
import ilearnrw.prototype.application.ConsoleMenu.*;
import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenuAction;
import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;

public class DatabaseManager extends ConsoleMenuAction {

	public DatabaseManager(String text) {
		super(text);
	}
	
	private interface ISelectUser {
		User selectUser(ConsoleMenu menu) throws AuthenticationException;
	}

	@Override
	public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {

		final IUserAdministration userAdmin = Program.getLoginProvider().getUserAdministration();

		if( !userAdmin.isAuthenticated() )
		{
			menu.out().println("Enter administration password: ");
			String adminPassword = menu.in().next();
			try {
				userAdmin.authenticateAdmin(adminPassword);
			} catch (AuthenticationException ex)
			{
				menu.out().println("Wrong password.");
				return EConsoleMenuActionResult.showThisMenuAgain;
			}
		}

		final ISelectUser selectUser = new ISelectUser() {
			@Override
			public User selectUser(ConsoleMenu menu) throws AuthenticationException {
				menu.out().println("Index: User id\t User name");
				int index = 1;
				for( User u : userAdmin.getAllUsers() )
				{
					menu.out().println(Integer.toString(index) + ": " + Integer.toString(u.getUserId())
							+ "\t" + u.getDetails().getUsername());
					index++;
				}
				int indexToChange = menu.in().nextInt();

				index = 1;
				for( User u : userAdmin.getAllUsers() )
				{
					if( index == indexToChange ) {
						return u;
					}
					index++;
				}
				return null;
			}
		};

		menu.subMenu("Database Manager", new IConsoleMenuAction[] {
				new ConsoleMenuAction("List users") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						try{
							menu.out().println("User id\t User name\t Password set");
							for( User u : userAdmin.getAllUsers() )
							{
								menu.out().print(Integer.toString(u.getUserId()) + "\t");
								menu.out().print(u.getDetails().getUsername() + "\t");
								menu.out().print(Boolean.toString(u.getDetails().hasPassword()) + "\t");
								menu.out().print(u.getProfile().getLanguage().toString() + "\t");
								menu.out().println();
							}
						}catch(AuthenticationException ex){}
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Add user") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {

						try{
							menu.out().print("Enter username: ");
							String userName = menu.in().next();
							
							menu.out().print("Choose a language: ");
							String lang = "";

							menu.out().println("Select language: ");
							menu.out().println("1. English");
							menu.out().println("2. Greek");
							lang = menu.in().next();
							
							LanguageCode lc =  lang.equals("1") ? LanguageCode.EN : LanguageCode.GR;
							userAdmin.createUser(userName, "defaultPassword", lc);
							
						}catch(AuthenticationException ex){}
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Change username") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						try{
							menu.out().println("Enter index of user to change name of: ");
							User u = selectUser.selectUser(menu);
							if( u != null ) {
								menu.out().print("Enter new username: ");
								String username = menu.in().next();
								userAdmin.changeUsername(u.getDetails().getUsername(), username);
							} 
						}catch(AuthenticationException ex){}
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Change password") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						try{
							menu.out().println("Enter index of user to change name of: ");
							User u = selectUser.selectUser(menu);
							if( u != null ) {
								menu.out().print("Enter new password: ");
								String plaintextPassword = menu.in().next();
								userAdmin.updatePassword(u.getDetails().getUsername(), plaintextPassword);
							}
						}catch(AuthenticationException ex){}
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Delete user") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						try{
							menu.out().println("Enter index of user to delete: ");
							User u = selectUser.selectUser(menu);
							if( u != null ) {
								userAdmin.deleteUser(u.getDetails().getUsername());
							}
						}catch(AuthenticationException ex){}
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new UserActionsLog("User Actions Log"),
				new ConsoleMenuAction("Back") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						return EConsoleMenuActionResult.menuBreak;
					}
				}
		}).doModalMenu();
		return EConsoleMenuActionResult.showThisMenuAgain;
	}

	

}

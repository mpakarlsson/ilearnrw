package ilearnrw.prototype.application;

import java.lang.annotation.IncompleteAnnotationException;

import ilearnrw.datalogger.UserStore;
import ilearnrw.prototype.application.ConsoleMenu.*;
import ilearnrw.prototype.application.ConsoleMenuAction;
import ilearnrw.user.User;

public class DatabaseManager extends ConsoleMenuAction {

	public DatabaseManager(String text) {
		super(text);
	}
	
	private interface ISelectUser {
		User selectUser(ConsoleMenu menu);
	}

	@Override
	public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
		
		final UserStore userStore = Program.gDataLogger.getUserStore();
		
		final ISelectUser selectUser = new ISelectUser() {
			@Override
			public User selectUser(ConsoleMenu menu) {
				menu.out().println("Index: User id\t User name");
				int index = 1;
				for( Integer i : userStore.getUserIds() )
				{
					User u = userStore.read(i);
					menu.out().println(Integer.toString(index) + ": " + Integer.toString(i)
							+ "\t" + u.getDetails().getUsername());
					index++;
				}
				int indexToChange = menu.in().nextInt();
			
				index = 1;
				for( Integer i : userStore.getUserIds() )
				{
					if( index == indexToChange ) {
						return userStore.read(i);
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
								menu.out().println("User id\t User name");
								for( Integer i : userStore.getUserIds() )
								{
									User u = userStore.read(i);
									menu.out().println(Integer.toString(i) + "\t" + u.getDetails().getUsername());
								}
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						},
						new ConsoleMenuAction("Add user") {
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {

								menu.out().print("Enter username: ");
								String userName = menu.in().next();
								
								User newUser = userStore.create();
								newUser.getDetails().setUsername(userName);
								userStore.update(newUser);
								
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						},
						new ConsoleMenuAction("Change username") {
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								menu.out().println("Enter index of user to change name of: ");
								User u = selectUser.selectUser(menu);
								if( u != null ) {
									menu.out().print("Enter new username: ");
									String userName = menu.in().next();
									u.getDetails().setUsername(userName);
									userStore.update(u);
								}
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						},
						new ConsoleMenuAction("Delete user") {
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								menu.out().println("Enter index of user to delete: ");
								User u = selectUser.selectUser(menu);
								if( u != null ) {
									userStore.delete(u.getUserId());
								}
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						},
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

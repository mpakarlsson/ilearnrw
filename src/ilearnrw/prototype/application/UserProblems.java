package ilearnrw.prototype.application;

import ilearnrw.prototype.application.ConsoleMenu.*;
import ilearnrw.prototype.application.ConsoleMenuAction.*;
import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.user.ProblemDefinition;
import ilearnrw.user.User;

public class UserProblems extends ConsoleMenuAction {

	User mUser = null;
	public UserProblems(String text, User user) {
		super(text);
		mUser = user;
	}
	/**
	 *  Should display a menu where you can:
	 *  * List problem definitions
	 *  * Add problem definitions to a profile from the ProblemDefinitionIndex.
	 *  * Remove problem definitions
	 */
	@Override
	public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
		


			menu.subMenu("User: " + mUser.getDetails().getUsername(), new IConsoleMenuAction[] {
				new ConsoleMenuAction("List problem definitions") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						for( ProblemDefinition problem : mUser.getProfile().getProblemsList().getList() )
						{
							menu.out().print(problem.getURI() + "\t");
							menu.out().println(Integer.toString(problem.getScoreUpperBound()));
						}
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Add problem definition") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						menu.out().println("Not implemented yet.");
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Remove problem definition") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						menu.out().println("Not implemented yet.");
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

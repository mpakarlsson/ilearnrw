package ilearnrw.prototype.application;

import java.util.ArrayList;

import ilearnrw.prototype.application.ConsoleMenu.*;
import ilearnrw.user.LanguageCode;
import ilearnrw.user.User;
import ilearnrw.user.mockup.ProblemDefinitionIndexMockup;
import ilearnrw.user.problems.ProblemDefinition;

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
						menu.out().print(mUser.getProfile().getSeveritiesToProblemsMatrix().toString());

						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				/*new ConsoleMenuAction("Set problem index / severity") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						
						menu.out().println("Get problems by ");
						menu.out().println("1. Language");
						menu.out().println("2. Category");
						menu.out().println("3. Back");
						
						switch (Integer.parseInt(menu.in().next())) {
						case 1:
							addProblemDefinitionByLanguage(menu);
							break;
						case 2:
							addProblemDefinitionByCategory(menu);
							break;

						default:
							return EConsoleMenuActionResult.showThisMenuAgain;
						}
						
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},*/				
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

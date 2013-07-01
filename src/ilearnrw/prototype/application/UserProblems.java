package ilearnrw.prototype.application;

import java.util.ArrayList;

import ilearnrw.prototype.application.ConsoleMenu.*;
import ilearnrw.user.LanguageCode;
import ilearnrw.user.ProblemDefinition;
import ilearnrw.user.User;
import ilearnrw.user.mockup.ProblemDefinitionIndexMockup;

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
				},
				new ConsoleMenuAction("Remove problem definition") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						menu.out().println("Remove a problem");
						int i = 1;
						ArrayList<ProblemDefinition> problemList = mUser.getProfile().getProblemsList().getList();
						for(ProblemDefinition problem : problemList){
							menu.out().println((i++) + ". " + problem.getURI());
						}
						menu.out().println(i + ". Back");
						String value = menu.in().next();
						
						if(Integer.parseInt(value) == i)
							return EConsoleMenuActionResult.showThisMenuAgain;
						
						ProblemDefinition problemDefinition = problemList.get(Integer.parseInt(value)-1);
						Program.getProfileAccessUpdater().removeProblemDefinition(problemDefinition);
						
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
	
	
	
	private void addProblemDefinitionByLanguage(ConsoleMenu menu){
		menu.out().println("Choose a Language");
		
		// temporary
		ProblemDefinitionIndexMockup mockUp = new ProblemDefinitionIndexMockup();
		ArrayList<ProblemDefinition> availableProblems;
		
		int i = 1;
		for( LanguageCode language : LanguageCode.values() ){
			menu.out().println(i++ + ". " + language);
		}
		
		String languageValue = menu.in().next();
		LanguageCode code;
		code = Integer.parseInt(languageValue) == 1 ? LanguageCode.EN : LanguageCode.GR;
		availableProblems = mockUp.getProblemsByLanguage(code);
		
		if(availableProblems.isEmpty()){
			menu.out().println("No available problems for language " + code);
			return;
		}
		
		menu.out().println("Choose an URI");
		i = 1;
		for(ProblemDefinition problem : availableProblems){
			menu.out().println(i++ + ". " + problem.getURI());
		}
		
		int uriValue = Integer.parseInt(menu.in().next()) - 1;
		
		// temporary, this feels odd
		ArrayList<LanguageCode> languages = new ArrayList<LanguageCode>();
		languages.add(code);
		
		ProblemDefinition problem = new ProblemDefinition(
				availableProblems.get(uriValue).getURI(),
				availableProblems.get(uriValue).getType(),
				availableProblems.get(uriValue).getScoreUpperBound(), 
				languages);
		
		if( Program.getProfileAccessUpdater().addProblemDefinition(problem) )
			menu.out().println("Added a problem definition to user");
		else
			menu.out().println("Could not add a problem definition to user");			
	}
	
	private void addProblemDefinitionByCategory(ConsoleMenu menu){
		menu.out().println("Not implemented yet.");

	}
	
	

}

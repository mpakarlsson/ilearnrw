package ilearnrw.prototype.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;
import ilearnrw.user.UserAction;

public class UserActionsLog extends ConsoleMenuAction {	
	public UserActionsLog(String text) {
		super(text);
	}

	@Override
	public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {

		menu.subMenu("Log", new IConsoleMenuAction[] {
				new ConsoleMenuAction("List all actions"){
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						Calendar calendar = Calendar.getInstance();
						List<UserAction> actions = Program.getDataLogger().getUserActions().getActions();
						if(actions == null){
							menu.out().println("There are no user actions to log");
							return EConsoleMenuActionResult.showThisMenuAgain;
						}
						
						for( UserAction action : actions){
							calendar.setTime(action.getTimeStamp());
							int hours = calendar.get(Calendar.HOUR_OF_DAY);
							int minutes = calendar.get(Calendar.MINUTE);
							int seconds = calendar.get(Calendar.SECOND);
							
							menu.out().printf("%s %02d:%02d:%02d", "TIME:", hours, minutes, seconds);
							menu.out().print(", APPID: " + action.getApplicationId());
							menu.out().print(", USERID: " + action.getUserId());
							menu.out().print(", TAG: " + action.getTag());
							menu.out().println(", TEXT: " + action.getText());
						}
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Add user action"){
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						menu.out().print("Tag: ");
						String tag = menu.in().next();
						menu.out().print("Text: ");
						String text = menu.in().next();
						menu.out().print("ApplicationId: ");
						String appId = menu.in().next();
						menu.out().print("UserId: ");
						int userId = Integer.parseInt(menu.in().next());
						
						UserAction action = new UserAction(tag, text, appId, userId);
						Program.getDataLogger().logAction(action);
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Add test user action"){
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						Random rand = new Random(System.currentTimeMillis());
						
						UserAction action = new UserAction("Test", "Test Value", "No App", rand.nextInt(200));
						Program.getDataLogger().getUserActions().addUserActionTest(action);
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Clear Log"){
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						menu.out().println("Are you sure you wish to clear the log? (Y/N)");
						menu.out().println("The data will be irrecoverable.");
						String answer = menu.in().next();

						if(answer.startsWith("Y") || answer.startsWith("y")){
							menu.out().println("Log is cleared!");
							Program.getDataLogger().getUserActions().clearLog();
						}
						else
							menu.out().println("Log is NOT cleared!");
						
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Save"){
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						Program.getDataLogger().getUserActions().save();
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("Back"){
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						return EConsoleMenuActionResult.menuBreak;
					}
				}
		}).doModalMenu();
		
		return super.onSelected(menu);
	}

	
}

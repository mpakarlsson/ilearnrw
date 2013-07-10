package ilearnrw.prototype.application;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ilearnrw.application.ApplicationId;
import ilearnrw.datalogger.UserActionFilter;
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
						List<UserAction> actions = Program.getDataLogger().getActions(null);
						if(actions == null){
							menu.out().println("There are no user actions to log");
							return EConsoleMenuActionResult.showThisMenuAgain;
						}
						
						printOutActions(actions, menu.out());
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				},
				new ConsoleMenuAction("List actions"){

					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						menu.out().println("Listing actions, preparing filter");
						menu.out().println("Enter an userid. (Enter '-1' to skip)");
						int id = Integer.parseInt(menu.in().next());
						
						menu.out().println("Enter an applicationid. (Enter 'n' to skip)");
						String appIdValue = menu.in().next();
						ApplicationId appId;
						if(appIdValue.equals("n"))
							appId = null;
						else
							appId = new ApplicationId(appIdValue);
						
						int yearStart = -1, monthStart = -1, dayStart = -1;
						menu.out().println("Do you wish to enter a starttime. (y/n)");
						if(menu.in().next().equals("y")){
							menu.out().print("Year:");
							yearStart = Integer.parseInt(menu.in().next());
							menu.out().print("Month:");
							monthStart = Integer.parseInt(menu.in().next());
							menu.out().print("Day:");
							dayStart = Integer.parseInt(menu.in().next());
						}
						
						int yearEnd = -1, monthEnd = -1, dayEnd = -1;
						menu.out().println("Do you wish to enter an endtime. (y/n)");
						if(menu.in().next().equals("y")){
							menu.out().print("Year:");
							yearEnd = Integer.parseInt(menu.in().next());
							menu.out().print("Month:");
							monthEnd = Integer.parseInt(menu.in().next());
							menu.out().print("Day:");
							dayEnd = Integer.parseInt(menu.in().next());
						}
						menu.out().println("Enter tags:");
						List<String> tags = new ArrayList<String>();
						String tag = "";
						
						do{
							menu.out().println("Tag: (enter 'n' to stop entering tags)");
							tag = menu.in().next();
							if(tag.equals("n"))
								break;
							
							tags.add(tag);
							
						}while(true);
						
						
						Calendar cal = Calendar.getInstance();
						Date startTime = null;
						if(yearStart != -1){
							cal.set(yearStart, monthStart, dayStart);
							startTime = cal.getTime();
						}
						
						Date endTime = null;
						if(yearEnd != -1){
							cal.set(yearEnd, monthEnd, dayEnd);
							endTime = cal.getTime();
						}
						
						
						UserActionFilter filter = new UserActionFilter(id, appId, startTime, endTime, tags);
						
						List<UserAction> actions = Program.getDataLogger().getActions(filter);
						if(actions == null){
							menu.out().println("There are no user actions to log");
							return EConsoleMenuActionResult.showThisMenuAgain;
						}
						
						printOutActions(actions, menu.out());
						
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
						
						UserAction action = new UserAction(tag, text, new ApplicationId(appId), userId);
						Program.getDataLogger().logAction(action);
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

	private void printOutActions(List<UserAction> actions, PrintStream out){
		for( UserAction action : actions){							
			SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' HH:mm:ss");
			out.print(ft.format(action.getTimeStamp()));
			
			out.print(", APPID: " + action.getApplicationId().getId());
			out.print(", USERID: " + action.getUserId());
			out.print(", TAG: " + action.getTag());
			out.println(", TEXT: " + action.getText());
		}
	}
}

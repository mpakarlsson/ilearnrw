package ilearnrw.games;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ilearnrw.application.ApplicationId;
import ilearnrw.datalogger.IDataLogger;
import ilearnrw.datalogger.UserActionFilter;
import ilearnrw.prototype.application.ConsoleMenu;
import ilearnrw.prototype.application.ConsoleMenuAction;
import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;
import ilearnrw.user.User;
import ilearnrw.user.UserAction;

public class GameAddTwoNumbers {

	private IDataLogger mLogger;
	private User mUser;
	private ApplicationId mApplicationId = new ApplicationId("AddTwoNumbers");
	private int mUserId;
	private String mWelcomeMessage = "Welcome, you are now playing 'AddTwoNumbers'";
	private List<UserAction> mUserActions = null;
	
	public GameAddTwoNumbers(User user, IDataLogger logger){
		mUser = user;
		mUserId = mUser.getUserId();
		mLogger = logger;
	
		mUserActions = new ArrayList<UserAction>();
		
		update();
	}
	
	public void update(){
		System.out.println(mWelcomeMessage);
		try{
			ConsoleMenu mnu = new ConsoleMenu(System.out, System.in, "iLearnRW - " + mApplicationId.getId(), 
					new IConsoleMenuAction[] {
						new ConsoleMenuAction("Start Game") {
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								// @TODO: Check the log for what problems the user has and apply it to the program
								// something();
								mUserActions.clear();
								int counter = 0;
								while(true){
									
									if(counter>=5)
										break;
									
									counter++;
									
									menu.out().println("\nUser: " + mUser.getDetails().getUsername());
									menu.out().println("+ Add Two Numbers +");
									menu.out().println("***********\n");
									
									Random rand = new Random(System.currentTimeMillis());
									int number1 = rand.nextInt(10);
									int number2 = rand.nextInt(10);
									
									menu.out().print("How much is " + number1 + " + " + number2 + "? ");
									String answer = menu.in().next();
									
									int number;
									try {
										number = Integer.parseInt(answer);
									} catch (Exception e) {
										UserAction action = new UserAction("InputError", "User did not enter a number", mApplicationId, mUserId);
										mLogger.logAction(action);
										mUserActions.add(action);
										number = -1;
									}
									
									if(number != -1){
										if(number == (number1+number2)){
											UserAction action = new UserAction("Answer", "Correct Answer: " + number1 + " + " + number2 + " = " + number, mApplicationId, mUserId);
											mLogger.logAction(action);
											mUserActions.add(action);
										} else {
											UserAction action = new UserAction("Answer", "Incorrect Answer: " + number1 + " + " + number2 + " != " + number + ", it is " + (number1 + number2), mApplicationId, mUserId);
											mLogger.logAction(action);
											mUserActions.add(action);
										}
									}									
								}
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						},
						new ConsoleMenuAction("Show Previous Results"){
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								printOutActions(mUserActions, menu.out());
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						},
						new ConsoleMenuAction("Show User Results"){
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								UserActionFilter filter = new UserActionFilter(mUserId, mApplicationId, null, null, null);
								printOutActions(mLogger.getActions(filter), menu.out());
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						},
						new ConsoleMenuAction("Show Log"){
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								printOutActions(mLogger.getActions(null), menu.out());
								return EConsoleMenuActionResult.showThisMenuAgain;
							}
						},
						new ConsoleMenuAction("Exit Game") {
							@Override
							public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
								return EConsoleMenuActionResult.menuBreak;
							}
						}
					}
				);
			mnu.doModalMenu();
		} catch (Exception ex) {
			System.out.println("Unhandled exception");
			System.out.println("-------------------");
			System.out.print(ex.toString());
		}
	}
	
	
	
	private void printOutActions(List<UserAction> actions, PrintStream out){
		
		if(actions.size() == 0){
			out.println("\nNo actions in list!");
			return;
		}
		
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

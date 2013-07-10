package ilearnrw.games;

import java.text.SimpleDateFormat;
import java.util.List;
import java.io.PrintStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ilearnrw.application.ApplicationId;
import ilearnrw.datalogger.IDataLogger;
import ilearnrw.datalogger.UserActionFilter;
import ilearnrw.games.NumbersToTextHelper.AbstractProcessor;
import ilearnrw.games.NumbersToTextHelper.DefaultProcessor;
import ilearnrw.prototype.application.ConsoleMenu;
import ilearnrw.prototype.application.ConsoleMenuAction;
import ilearnrw.prototype.application.ConsoleMenu.EConsoleMenuActionResult;
import ilearnrw.prototype.application.ConsoleMenu.IConsoleMenuAction;
import ilearnrw.user.User;
import ilearnrw.user.UserAction;

public class NumbersToText extends ConsoleMenuAction {

	private final ApplicationId APPID = new ApplicationId("NUMBERSTOTEXT");
	private final String FAILED_TAG = "FAILED";
	private final String OK_TAG = "OK";
	private User mUser;
	private IDataLogger mDataLogger;

	public NumbersToText(String text, User user, IDataLogger dataLogger) {
		super(text);
		mUser = user;
		mDataLogger = dataLogger;
	}

	@Override
	public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {

		menu.subMenu("You're playing NumbersToText",
				new IConsoleMenuAction[] { new ConsoleMenuAction("Start Game") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {

						String inputString = "";
						List<String> tags = new ArrayList<String>();
						tags.add(OK_TAG);
						tags.add(FAILED_TAG);
						List<UserAction> history = mDataLogger
								.getActions(new UserActionFilter(mUser
										.getUserId(), APPID, new Date(
										Long.MIN_VALUE), new Date(
										Long.MAX_VALUE), tags));

						if (history != null) {
							menu.out()
									.println(
											String.format(
													"We have found %d actions in history.",
													history.size()));
						}

						Map<Integer, Integer> countOk = new HashMap<Integer, Integer>();

						if (history != null) {
							for (UserAction userAction : history) {
								if (userAction.getTag().compareTo(OK_TAG) == 0) {
									int currentValue = 0;
									if (countOk.containsKey(userAction
											.getText().length())) {
										currentValue = countOk.get(userAction
												.getText().length());
									}

									currentValue++;
									countOk.put(userAction.getText().length(),
											currentValue);
								}

							}
						}

						int digitsToUse = 1;
						for (int i = 1; i < 3; i++) {
							if (countOk.containsKey(i) && countOk.get(i) > 5) {
								digitsToUse++;
							}
						}
						menu.out()
								.println(
										String.format(
												"For this session we will use %d digits",
												digitsToUse));

						do {
							int random = (new Random(System.currentTimeMillis()))
									.nextInt((int) Math.pow(10, digitsToUse));

							menu.out()
									.println(
											String.format(
													"Write down the number: %d (or type 'quit' to leave game)",
													random));

							inputString = menu.in().next();

							if (inputString.compareTo("quit") == 0) {
								break;
							}
							AbstractProcessor processor = new DefaultProcessor();
							String nrToText = processor.getName(random);
							if (inputString.compareToIgnoreCase(nrToText) == 0) {
								menu.out().println("That's correct!\n");
								mDataLogger.logAction(new UserAction("OK",
										String.valueOf(random), APPID, mUser
												.getUserId()));
							} else {
								menu.out()
										.println(
												String.format(
														"Not correct! I expected: %s\n",
														nrToText));
								mDataLogger.logAction(new UserAction("FAILED",
										String.format("%d => %s", random,
												inputString), APPID, mUser
												.getUserId()));
							}
						} while (inputString.compareTo("quit") != 0);

						/**
						 * @todo: it's the responsability of the datalogger to
						 *        save the actions
						 */
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				}, new ConsoleMenuAction("Show User Results") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						UserActionFilter filter = new UserActionFilter(mUser
								.getUserId(), APPID, null, null, null);
						printOutActions(mDataLogger.getActions(filter),
								menu.out());
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				}, new ConsoleMenuAction("Show Log") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						printOutActions(mDataLogger.getActions(null),
								menu.out());
						return EConsoleMenuActionResult.showThisMenuAgain;
					}
				}, new ConsoleMenuAction("Exit Game") {
					@Override
					public EConsoleMenuActionResult onSelected(ConsoleMenu menu) {
						return EConsoleMenuActionResult.menuBreak;
					}
				}

				}

		).doModalMenu();

		return EConsoleMenuActionResult.showThisMenuAgain;
	}

	private void printOutActions(List<UserAction> actions, PrintStream out) {

		if (actions.size() == 0) {
			out.println("\nNo actions in list!");
			return;
		}

		for (UserAction action : actions) {
			SimpleDateFormat ft = new SimpleDateFormat(
					"E yyyy.MM.dd 'at' HH:mm:ss");
			out.print(ft.format(action.getTimeStamp()));

			out.print(", APPID: " + action.getApplicationId().getId());
			out.print(", USERID: " + action.getUserId());
			out.print(", TAG: " + action.getTag());
			out.println(", TEXT: " + action.getText());
		}
	}
}

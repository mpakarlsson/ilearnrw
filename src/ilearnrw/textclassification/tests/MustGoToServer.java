package ilearnrw.textclassification.tests;

import ilearnrw.datalogger.IUserAdministration.AuthenticationException;
import ilearnrw.datalogger.UserStore;
import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.rest.AuthenticatedRestClient;
import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MustGoToServer {
	private static String filename = "texts/deyteraDim2.txt";
	private static UserStore mUserStore = null;
	private static User user = null;
	private static LanguageCode lan = LanguageCode.GR;

	public static void main(String args[]) {

//		// TODO users must stored server side, not locally!
//		try {
//			/* Load the user database. */
//			String databaseFile = "the_users";// Program.getStringArg("--db",
//												// args);
//			mUserStore = new UserStore(databaseFile);
//			/* We have to auth as admin to access the database. */
//			mUserStore.authenticateAdmin("ilearn");
//		} catch (Exception e) {
//			/* Could not load any users, no point to continue. */
//			e.printStackTrace();
//			return;
//		}
		AuthenticatedRestClient restClient = new AuthenticatedRestClient();
		List<User> users = null;
		try {
			restClient.authenticate("admin", "admin");
			users = restClient.getAllUsers();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		//for (User u : mUserStore.getAllUsers()) {
		for (User u : users) {
			if (u.getDetails().getLanguage() == lan)
			{
				user = u;
				System.out.println(user.getUserId());
			}
		}

//		// read the file locally
//		String text = "";
//		try {
//			text = new Scanner(new File(filename), "UTF-8").useDelimiter("\\A").next();
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
//
//		// TODO next four lines must go server side!
//		LanguageAnalyzerAPI languageAnalyzer = new GreekLanguageAnalyzer();
//		Text t = new Text(text, lan);
//		Classifier cls = new Classifier(user, t, languageAnalyzer);
//		cls.calculateProblematicWords(false);
//
//		// use the results locally!
//		int numberOfWords = t.getNumberOfWords();
//		double textScore = cls.getUserProblemsToText().getTscore();
//		System.out.println("Number Of Words:" + numberOfWords);
//		System.out.println("Text Score:" + textScore);

	}

}

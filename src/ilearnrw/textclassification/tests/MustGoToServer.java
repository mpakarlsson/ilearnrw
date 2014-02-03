package ilearnrw.textclassification.tests;

import ilearnrw.datalogger.IUserAdministration.AuthenticationException;
import ilearnrw.datalogger.UserStore;
import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.rest.AuthenticatedRestClient;
import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.TextClassificationResults;
import ilearnrw.textclassification.UserProblemsToText;
import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

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
		restClient.authenticate("admin", "admin");
		users = restClient.getAllUsers();
		// TODO again, server side calls to select a user!
		for (User u : users) {
			System.out.println("id: " + u.getUserId() + ", language: " + u.getProfile().getLanguage());
			if (u.getProfile().getLanguage() == lan)
				user = u;
		}

		Gson gson = new Gson();
		System.out.println("User:" + gson.toJson(user));
		
		//user.getProfile().getUserProblems().loadTestGreekProblems();
		// read the file locally
		String text = "";
		try {
			text = new Scanner(new File(filename), "UTF-8").useDelimiter("\\A").next();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// TODO next four lines must go server side!
//		LanguageAnalyzerAPI languageAnalyzer = new GreekLanguageAnalyzer();
//		UserProblemsToText upt = new UserProblemsToText(user.getProfile(), new Text(text, lan), languageAnalyzer);
//		upt.calculateProblematicWords(false);
//		TextClassificationResults cr = upt.getTextClassificationResults();
		System.out.println("Text:" + text);
		TextClassificationResults cr = null;
		cr = restClient.classifyText(user, text);

		// use the results locally!
		int numberOfWords = cr.getNumberOfTotalWords();
		double textScore = cr.getTscore();
		System.out.println("Number Of Words:" + numberOfWords);
		System.out.println("Text Score:" + textScore);
		System.out.println("Coleman Liau:" + cr.getColemanLiau());
		System.out.println("Flesch Kincaid:" + cr.getFleschKincaid());
		
		
		//english text
		text = "classify this please thank you";
		System.out.println("Text:" + text);
		cr = restClient.classifyText(user, text);
		System.out.println("Number Of Words:" + cr.getNumberOfTotalWords());
		System.out.println("Text Score:" + cr.getTscore());
		System.out.println("Coleman Liau:" + cr.getColemanLiau());
		System.out.println("Flesch Kincaid:" + cr.getFleschKincaid());

	}

}

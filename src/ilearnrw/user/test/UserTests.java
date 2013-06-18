package ilearnrw.user.test;

import java.util.ArrayList;

import junit.framework.Assert;
import ilearnrw.user.Category;
import ilearnrw.user.LanguageCode;
import ilearnrw.user.ProblemDefinition;
import ilearnrw.user.Subcategory;
import ilearnrw.user.UserProfile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

public class UserTests {

	@Test
	public void createUserProfile() {
		UserProfile profile = new UserProfile(1);
		Assert.assertNotNull(profile);
	}
	
	@Test
	public void createProblems() {
		Category category1 = new Category("PHONOLOGY - SUB-WORD LEVEL: LETTER RECOGNITION PROBLEMS", true);
		Subcategory recognizeLetters11 = new Subcategory("Difficulty in recognizing letters: Auditory-based errors", category1);
		Subcategory recognizeLetters12 = new Subcategory("Difficulty in recognizing letters: Visually-based errors", category1);

		Category category2 = new Category("WORD LEVEL: WORD RECOGNITION PROBLEMS", true);
		Subcategory wordRecognition21 = new Subcategory("Word recognition: Visually-based errors", category2);
		Subcategory wordRecognition22 = new Subcategory("Word recognition: Semantically-based errors", category2);
		

		ArrayList<LanguageCode> acceptedLanguages = new ArrayList<LanguageCode>();
		acceptedLanguages.add(LanguageCode.GR);
		ProblemDefinition problem111 = new ProblemDefinition("1.1.1", recognizeLetters11, 10, acceptedLanguages);
		ProblemDefinition problem121 = new ProblemDefinition("1.2.1", recognizeLetters12, 10, acceptedLanguages);
		

		Assert.assertEquals("We have added only one language", problem111.getAvailableLanguages().size(), 1);
	}
}

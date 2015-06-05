package ilearnrw.user.test;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.util.ArrayList;

import junit.framework.Assert;
import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

import org.junit.Test;

public class UserTests {

	@Test
	public void createUserProfile() {
		UserProfile profile = new UserProfile(1);
		Assert.assertNotNull(profile);
	}
	
	@Test
	public void createProblems() {
		Category category1 = new Category("PHONOLOGY-SUB-WORDLEVEL.Difficultyinrecognizingletters.Auditory-basederrors");
		//Category category2 = new Category("PHONOLOGY-SUB-WORDLEVEL.Difficultyinrecognizingletters.Visually-basederrors");

		//Category category3 = new Category("WORDLEVEL.Wordrecognition.Visually-basederrors");
		//Category category4 = new Category("WORDLEVEL.Wordrecognition.Semantically-basederrors");
		

		ArrayList<LanguageCode> acceptedLanguages = new ArrayList<LanguageCode>();
		acceptedLanguages.add(LanguageCode.GR);
		//ProblemDefinition problem111 = new ProblemDefinition("1.1.1", category1, 10, acceptedLanguages);
		//ProblemDefinition problem121 = new ProblemDefinition("1.2.1", category2, 10, acceptedLanguages);
		

		//Assert.assertEquals("We have added only one language", problem111.getAvailableLanguages().size(), 1);
	}
}

package ilearnrw.user.test;

import java.util.GregorianCalendar;

import ilearnrw.user.LanguageCode;
import ilearnrw.user.ProblemDefinitionIndexApi;
import ilearnrw.user.mockup.ProblemDefinitionIndexMockup;

import org.junit.Assert;
import org.junit.Test;

public class MockupTests {

	@Test
	public void testIndex() {
		ProblemDefinitionIndexApi index = new ProblemDefinitionIndexMockup();
		Assert.assertEquals("at the moment no problems for English", index.getProblemsByLanguage(LanguageCode.EN).size(), 0);
		Assert.assertTrue("we should have some mockup data for Greek language", index.getProblemsByLanguage(LanguageCode.GR).size() > 0);
	}

}

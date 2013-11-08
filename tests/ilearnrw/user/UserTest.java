package ilearnrw.user;

import static org.junit.Assert.*;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {
	/*
	 * You need to add the JUnit to the Java Build Path -> Libraries in project properties
	 * It asks you if you want to add it automatically if you create a new 'JUnit test case'
	 * Just make sure that when you create a new test case that you browse to another sourcefolder
	 * 
	 * The to run either choose 'Run As' -> 'JUnit Test' or press 'ALT' + 'SHIFT' + 'X'
	 * and pick 'Run JUnit Test from the popup box that appears in the lower right corner
	 * 
	 * You can use the method above to run tests by 'folder', 'package', 'class' or 'method'
	 * 
	 * */
	private static User user = null;
	
	@BeforeClass
	public static void runOnceBeforeAnything(){
		UserProfile profile = new UserProfile(LanguageCode.EN, null, null);
		user = new User(1, profile, null, null);
	}
	
	@AfterClass
	public static void runOnceAfterEverything(){
		user = null;
	}
	
	@Before
	public void runBeforeEveryTest(){
		user.setDetails(new UserDetails("Karl", 2, LanguageCode.EN));
	}
	
	@Test
	public void testGetDetails(){
		UserDetails details = user.getDetails();
		assertEquals("Karl", details.getUsername());
		assertEquals(LanguageCode.EN, details.getLanguage());
	}
	
	@Test
	public void testSetDetails(){
		UserDetails userDetails = user.getDetails();
		
		userDetails.setUsername("Eric");
		userDetails.setLanguage(LanguageCode.EN);
		
		assertEquals("Eric", userDetails.getUsername());
		assertEquals(LanguageCode.EN, userDetails.getLanguage());
	}
}

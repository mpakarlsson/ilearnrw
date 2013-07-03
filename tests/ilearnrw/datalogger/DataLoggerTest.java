package ilearnrw.datalogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import ilearn.user.problems.ProblemDefinition;
import ilearnrw.datalogger.IProfileAccessUpdater.PendingChangesAvailable;
import ilearnrw.datalogger.IProfileAccessUpdater.PermissionException;
import ilearnrw.datalogger.IUserAdministration.AuthenticationException;
import ilearnrw.prototype.application.UserActions;
import ilearnrw.user.LanguageCode;
import ilearnrw.user.User;
import ilearnrw.user.UserAction;
import ilearnrw.user.UserDetails;
import ilearnrw.user.UserPreferences;
import ilearnrw.user.UserSession;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

public class DataLoggerTest {

	private static DataLogger dataLogger = null;
	private static UserStore userStore = null;
	private static UserActions userActions = null;
	private static File tempFile = null;
	private static User user = null;
	private static String filePath = null;
	private static String filePathLog = null;
	
	@BeforeClass
	public static void runOnceBeforeAnything(){
		try {
			tempFile = File.createTempFile("ilearn", "");
		} catch (IOException e) {
			System.out.println("Could not create file!");
		}

		filePath = tempFile.getAbsolutePath()+"ilearn.db";
		filePathLog = new StringBuilder(filePath).insert(filePath.length()-3, "_LOG").toString();
				
		userStore = new UserStore(filePath);
		userActions = new UserActions(filePath);

		try {
			userStore.authenticateAdmin("ilearn");
			userStore.createUser("User1", "test");
			userStore.createUser("User2", "test");
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		}
		
		UserAction action = new UserAction("Tag", "Text", "Test", 1);
		UserAction action2 = new UserAction("Tag2", "Text2", "Test2", 2);
		UserAction action3 = new UserAction("Tag3", "Text3", "Test3", 3);
		
		userActions.addUserAction(action);
		userActions.addUserAction(action2);
		userActions.addUserAction(action3);
		userActions.save();
		
		userStore = null;
		userActions = null;
		
	}
	
	@AfterClass
	public static void runOnceAfterAnything(){
		dataLogger = null;
		tempFile.delete();
		tempFile = null;
		
		try {
			Files.delete(Paths.get(filePath));
			Files.delete(Paths.get(filePathLog));
		} catch (IOException e) {
			System.out.print("Could not delete temporary files");
		}
		
		
	}
	
	@Before
	public void runBeforeEveryTest(){
		dataLogger = new DataLogger();
		dataLogger.loadUserStore(filePath);
		dataLogger.loadUserActions(filePath);
		user = dataLogger.getUser("User1", "test");
		
		try { dataLogger.setCurrentUser(user, user);
		} catch (PermissionException e) {e.printStackTrace();
		} catch (PendingChangesAvailable e) {e.printStackTrace();}
	}
	
	@After
	public void runAfterEveryTest(){
		
		dataLogger.discardPendingChanges();
		try {dataLogger.setCurrentUser(null, null);
		} catch (PermissionException e) {e.printStackTrace();
		} catch (PendingChangesAvailable e) {e.printStackTrace();}
		
		dataLogger = null;
	}
	
//	@Test
//	public void testLoadUserStore(){
//		//assertTrue(dataLogger.loadUserStore(tempFile.getAbsolutePath()+"ilearn.db"));
//	}
	
//	@Test
//	public void testLoadUserActions(){
//		assertTrue(dataLogger.loadUserActions(tempFile.getAbsolutePath()+"ilearn.db"));
//	}
	
	@Test
	public void testGetUserActions(){		
		UserActions actions = dataLogger.getUserActions();
		assertNotNull(actions);
	}
	
	@Test
	public void testLogAction(){
		UserAction actionLog = new UserAction("Tag", "Text", "TestId", -1);

		
		dataLogger.logAction(actionLog);
		
		UserActions actions2 = dataLogger.getUserActions();
		
		assertEquals("Tag", actions2.getAction(actions2.getActions(null).size()-1).getTag());
		assertEquals("Text", actions2.getAction(actions2.getActions(null).size()-1).getText());
		assertEquals("TestId", actions2.getAction(actions2.getActions(null).size()-1).getApplicationId());
		assertEquals(-1, actions2.getAction(actions2.getActions(null).size()-1).getUserId());
	}
	
	@Test
	public void testGetUser(){
		assertEquals("User1", user.getDetails().getUsername());
	}
	
	@Test
	public void testRegisterUserSession(){
		UserSession session = new UserSession();
		
		assertTrue(dataLogger.registerUserSession(session));
		session = null;
		assertFalse(dataLogger.registerUserSession(session));
	}
	
	@Test
	public void testMoveToOldSessions(){
		UserSession session2 = new UserSession();
		UserSession session3 = new UserSession();
			
		dataLogger.registerUserSession(session2);
		dataLogger.registerUserSession(session3);
		assertEquals(0, user.getOldSessions().size());
		
		dataLogger.moveToOldSessions();
		assertEquals(2, user.getOldSessions().size());
	}
	
	@Test
	public void testSetPreference(){		
		assertFalse(dataLogger.setPreference(null));
		
		UserPreferences pref = new UserPreferences(12);
		assertTrue(dataLogger.setPreference(pref));
		
		assertEquals(12, user.getProfile().getPreferences().getFontSize());
	}
	
	@Test
	public void testSetUserDetail(){
		UserDetails details = null;
		assertFalse(dataLogger.setUserDetail(details));
		assertEquals("User1", user.getDetails().getUsername());
		details = new UserDetails("1resU", 2, LanguageCode.GR);
		assertTrue(dataLogger.setUserDetail(details));
		assertEquals("1resU", user.getDetails().getUsername());
	}
	
	@Test
	public void testAddProblemDefinition(){
		ProblemDefinition problem1 = new ProblemDefinition("URI200", null, 10, new ArrayList<LanguageCode>());
		
		assertTrue(dataLogger.addProblemDefinition(problem1));
		assertFalse(dataLogger.addProblemDefinition(problem1));
	}
	
	@Test
	public void testRemoveProblemDefinition(){
		ProblemDefinition problem1 = new ProblemDefinition("URI100", null, 10, new ArrayList<LanguageCode>());
		ProblemDefinition problem2 = new ProblemDefinition("URI101", null, 10, new ArrayList<LanguageCode>());
		
		dataLogger.addProblemDefinition(problem1);
		
		assertFalse(dataLogger.removeProblemDefinition(problem2));
		assertTrue(dataLogger.removeProblemDefinition(problem1));
		assertFalse(dataLogger.removeProblemDefinition(problem1));
	}
	
	@Test
	public void testSetUserLanguage(){
		LanguageCode code = LanguageCode.EN;
		
		assertTrue(dataLogger.setUserLanguage(code));
		assertFalse(dataLogger.setUserLanguage(code));
	}
	
	@Test
	public void testWritePendingChanges(){
		ProblemDefinition problem1 = new ProblemDefinition("URI100", null, 10, new ArrayList<LanguageCode>());
		int origSize = user.getProfile().getProblemsList().getList().size();
		
		dataLogger.addProblemDefinition(problem1);
		dataLogger.writePendingChanges();
		dataLogger = null;
		
		dataLogger = new DataLogger();
		dataLogger.loadUserStore(filePath);
		dataLogger.loadUserActions(filePath);
		user = dataLogger.getUser("User1", "test");
		
		try { dataLogger.setCurrentUser(user, user);
		} catch (PermissionException e) {e.printStackTrace();
		} catch (PendingChangesAvailable e) {e.printStackTrace();}
		
		ProblemDefinition definition = user.getProfile().getProblemsList().getList().get(user.getProfile().getProblemsList().getList().size()-1);
		
		// Has written pending changes, the definition has been added
		assertEquals(definition.getURI(), problem1.getURI());
		assertEquals(origSize+1, user.getProfile().getProblemsList().getList().size());
	}
	
	@Test
	public void testHasPendingChanges(){
		assertFalse(dataLogger.hasPendingChanges());
		
		ProblemDefinition problem2 = new ProblemDefinition("URI300", null, 10, new ArrayList<LanguageCode>());		
		dataLogger.addProblemDefinition(problem2);
		assertTrue(dataLogger.hasPendingChanges());
	}
}

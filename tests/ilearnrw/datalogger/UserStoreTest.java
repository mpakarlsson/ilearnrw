package ilearnrw.datalogger;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import ilearnrw.datalogger.IUserAdministration.AuthenticationException;
import ilearnrw.user.User;
import ilearnrw.user.UserDetails;
import ilearnrw.utils.LanguageCode;

import java.io.File;
import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.rules.ExpectedException;

public class UserStoreTest {
	private static UserStore userStore = null;
	private static File tempFile = null;
	private static String storePath = null;
	private static User user = null;
	private static final String PASSWORD = "ilearn";
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void startUp(){
		try { tempFile = File.createTempFile("ilearnUserStore", ".db");
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
	@AfterClass
	public static void tearDown(){
		tempFile.delete();
		
	}
	
	
	@Before
	public void preMethodSetup(){
		storePath = new StringBuilder(tempFile.getAbsolutePath()).insert(tempFile.getAbsolutePath().length()-3, "_userstore").toString();
		userStore = new UserStore(storePath);
		user = userStore.create();
	}
	
	@After
	public void postMethodTearDowns(){
		/*try { Files.delete(Paths.get(storePath));
		} catch (IOException e) {
			System.out.println("Could not delete userStore file due to " + e.getMessage());
			e.printStackTrace();}
		userStore = null;
		storePath = null;
		user = null;*/
	}
		
	@Test
	public void testCreate(){
		User tempUser = userStore.create();
		assertNotNull(tempUser);
	}
	
	@Test
	public void testRead(){
		User tempUser2 = userStore.read(user.getUserId());
		assertNotNull(tempUser2);
		
		int id = 0;
		List<User> users = null;
		try {
		userStore.authenticateAdmin(PASSWORD);
		users = userStore.getAllUsers();
		} catch (AuthenticationException e) {e.printStackTrace();}
		
		for(int i = 0; i < users.size(); i++){
			if(id == users.get(i).getUserId()){
				id++;
				i = -1;
			}
		}
		assertNull(userStore.read(id));
	}
	
	@Test
	public void testUpdate(){
		User testUser = null;
		try { 
			userStore.authenticateAdmin(PASSWORD);
			testUser = userStore.createUser("albin", "rawr", LanguageCode.EN);
		} catch (AuthenticationException e) {e.printStackTrace();}
		String name = testUser.getDetails().getUsername();
		testUser.setDetails(new UserDetails("sture", 2, user.getDetails().getLanguage()));
		
		User tempUser3 = userStore.update(testUser);
		assertThat(tempUser3.getDetails().getUsername(), not(name));
	}
	
	@Test 
	public void testDelete(){
		User tempUser4 = userStore.create();
		int id = 0, size2 = 0;
		List<User> users = null;
		
		try {
			userStore.authenticateAdmin(PASSWORD);
			users = userStore.getAllUsers();
		
			for(int i = 0; i < users.size(); i++){
				if(id == users.get(i).getUserId()){
					id++; i = -1;
				}
			}
			
			assertFalse(userStore.delete(id));
			
			assertTrue(userStore.delete(tempUser4.getUserId()));
			size2 = userStore.getAllUsers().size();
		} catch (AuthenticationException e) {e.printStackTrace();}	
		assertThat(size2, not(users.size()));
	}
	
	@Test
	public void testGetUserIds(){
		LinkedList<Integer> userIds = userStore.getUserIds();
		assertNotNull(userIds);
	}
	
	@Test
	public void testGetUser(){
		int id = 0;
		try {
			userStore.authenticateAdmin(PASSWORD);
			id = userStore.createUser("daniel", "test", LanguageCode.EN).getUserId();
		} catch (AuthenticationException e) {e.printStackTrace();}
		
		User tempUser5 = userStore.getUser("daniel", "test");
		assertEquals(tempUser5.getUserId(), id);
	}
	
	
	@Test
	public void testAuthenticateAdmin() throws AuthenticationException {
		exception.expect(AuthenticationException.class);
		userStore.authenticateAdmin("thisisthewrongpassword");
		assertFalse(userStore.isAuthenticated());
		
		userStore.authenticateAdmin(PASSWORD);
		assertTrue(userStore.isAuthenticated());
	}
	
	@Test
	public void testCreateUser() throws AuthenticationException{
		
		exception.expect(AuthenticationException.class);
		userStore.createUser("Peter", "tolo", LanguageCode.EN);
		
		userStore.authenticateAdmin(PASSWORD);
		User tempUser6 = userStore.createUser("Peter", "tolo", LanguageCode.EN);
		assertNotNull(tempUser6);
		assertThat(tempUser6.getDetails().getUsername(), is("Peter"));
		
	}
	
	@Test
	public void testUpdatePassword(){
		try {
			userStore.authenticateAdmin(PASSWORD);
			userStore.createUser("mattias", "test", LanguageCode.EN);
			
			assertNull(userStore.updatePassword("qwerty", "ytrewq"));
			
			userStore.updatePassword("mattias", "tset");
			
			assertNull(userStore.getUser("mattias", "test"));
			assertNotNull(userStore.getUser("mattias", "tset"));
		} catch (AuthenticationException e) {e.printStackTrace();}
	}
	
	@Test
	public void testChangeUserName(){
		
		try {
			userStore.authenticateAdmin(PASSWORD);
			userStore.createUser("richard", "inkognito", LanguageCode.EN);
			
			assertNull(userStore.changeUsername("qwerty", "ytrewq"));
			
			userStore.changeUsername("richard", "fredrik");
			
			assertNull(userStore.getUser("richard", "inkognito"));
			assertNotNull(userStore.getUser("fredrik", "inkognito"));
		} catch (AuthenticationException e) {e.printStackTrace();}
		
	}
	
	@Test 
	public void testDeleteUser(){
		try {
			userStore.authenticateAdmin(PASSWORD);
			userStore.createUser("theodore", "best", LanguageCode.EN);
			
			assertFalse(userStore.deleteUser("qwerty"));
			assertTrue(userStore.deleteUser("theodore"));
		} catch (AuthenticationException e) {e.printStackTrace();}
	}
	
	@Test
	public void testGetUserAdministration(){
		assertThat(userStore.getUserAdministration(), is(IUserAdministration.class));
		assertNotNull(userStore.getUserAdministration());
	}
	
	@Test
	public void testGetAllUsers(){
		try {
			userStore.authenticateAdmin(PASSWORD);
			userStore.createUser("william", "fireemblem", LanguageCode.EN);
			
			List<User> userList = userStore.getAllUsers();
			
			assertNotNull(userList);
			assertThat(userList.isEmpty(), is(false));
		} catch (AuthenticationException e) {e.printStackTrace();}
	}
}

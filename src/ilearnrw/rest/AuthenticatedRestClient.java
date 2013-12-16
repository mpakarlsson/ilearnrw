package ilearnrw.rest;

import ilearnrw.user.User;
import ilearnrw.user.UserDetails;
import ilearnrw.user.UserPreferences;
import ilearnrw.user.UserSession;
import ilearnrw.user.profile.UserProblems;
import ilearnrw.user.profile.UserProfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AuthenticatedRestClient {
	HttpURLConnection connection;

	String baseURL = "http://localhost:8080/test";
	Authentication authentication = null;
	
	String request(String urlString) throws IOException
	{
		URL url = new URL(baseURL + urlString);
		connection = (HttpURLConnection) url.openConnection();
		//encoding of api:api
		String basicAuth = "Basic YXBpOmFwaQ==";
		connection.setRequestProperty ("Authorization", basicAuth);
		connection.connect();
		int responseCode = connection.getResponseCode();
		System.out.println("Response code: " + responseCode);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		return bReader.readLine();
	}
	
	public <T extends Object> T requestObj(String urlString, Class<T> type) throws IOException
	{
		Gson gson = new Gson();
		String result = request(urlString);
		return gson.fromJson(result, type);
	}
	
	private List<String> requestListOfStrings(String urlString) throws IOException {
		Gson gson = new Gson();
		String result = request(urlString);
		Type typeOfListOfStrings =  new TypeToken<List<String>>() {}.getType();
		return gson.fromJson(result, typeOfListOfStrings);
	}
	
	public void authenticate(String username, String pass) throws IOException
	{
		String authURL = String.format("/user/auth?username=%s&pass=%s" , username, pass);
		Authentication obj = requestObj(authURL, Authentication.class);
		authentication = obj;
	}
	
	public List<User> getAllUsers() throws IOException
	{
		List<User> users = new ArrayList<User>();
		List<String> userIds = requestListOfStrings("/profile/list");
		for (String id : userIds)
		{
			UserProblems problems = requestObj(String.format("/profile/problems?userId=%s", id), UserProblems.class);
			UserPreferences preferences = requestObj(String.format("/profile/preferences?userId=%s", id), UserPreferences.class);
			UserProfile profile = new UserProfile(problems, preferences);
			UserDetails details = requestObj(String.format("/profile/details?userId=%s", id), UserDetails.class);
			User user = new User(Integer.parseInt(id), profile, details, new ArrayList<UserSession>());
			users.add(user);
		}
		return users;
	}
	
	public static void main(String[] args) throws IOException {
		AuthenticatedRestClient rClient = new AuthenticatedRestClient();
		rClient.authenticate("admin", "admin");
		System.out.println(rClient.authentication.auth);
	}
}

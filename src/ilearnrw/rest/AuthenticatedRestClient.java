package ilearnrw.rest;

import ilearnrw.textclassification.TextClassificationResults;
import ilearnrw.textclassification.Word;
import ilearnrw.user.User;
import ilearnrw.user.UserDetails;
import ilearnrw.user.UserPreferences;
import ilearnrw.user.UserSession;
import ilearnrw.user.profile.UserProblems;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class AuthenticatedRestClient {
	HttpURLConnection connection;

	String baseURL = "http://localhost:8080/test";
	Authentication authentication = null;
	
	String request(String urlString, boolean post, String postArgs) throws IOException
	{
		URL url = new URL(baseURL + urlString);
		connection = (HttpURLConnection) url.openConnection();
		//encoding of api:api
		String basicAuth = "Basic YXBpOmFwaQ==";
		connection.setRequestProperty ("Authorization", basicAuth);
		if (post)
		{
	        connection.setRequestMethod("POST");        
	        connection.setRequestProperty("Content-Type","text/plain");
	        connection.setRequestProperty("Content-Length",String.valueOf(postArgs.toString().getBytes("UTF-8").length)); 
	        //connection.setRequestProperty("User-Agent","Profile/MIDP-2.0 Configuration/CLDC-1.1");
	        //connection.setRequestProperty("Accept-Charset","UTF-8;q=0.7,*;q=0.7");
	        //connection.setRequestProperty("Accept-Encoding","gzip, deflate");
	        //connection.setRequestProperty("Accept","text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");

			//connection.setDoInput(true);
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			out.write(postArgs);
            out.close();
		}
		else
			connection.connect();
		int responseCode = connection.getResponseCode();
		System.out.println("Response code: " + responseCode);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		return bReader.readLine();
	}
	
	public <T extends Object> T requestObj(String urlString, Class<T> type, boolean post, String postArgs) throws IOException
	{
		Gson gson = new Gson();
		String result = request(urlString, post, postArgs);
		System.out.println(result);
		return gson.fromJson(result, type);
	}
	
	public <T extends Object> T requestObj(String urlString, Class<T> type) throws IOException
	{
		Gson gson = new Gson();
		String result = request(urlString, false, null);
		return gson.fromJson(result, type);
	}
	
	private List<String> requestListOfStrings(String urlString) throws IOException {
		Gson gson = new Gson();
		String result = request(urlString, false, null);
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
//			UserProblems problems = requestObj(String.format("/profile/problems?userId=%s", id), UserProblems.class);
//			UserPreferences preferences = requestObj(String.format("/profile/preferences?userId=%s", id), UserPreferences.class);
//			UserProfile profile = new UserProfile(language, problems, preferences);
			UserProfile profile = requestObj(String.format("/profile?userId=%s", id), UserProfile.class);
			UserDetails details = requestObj(String.format("/profile/details?userId=%s", id), UserDetails.class);
			User user = new User(Integer.parseInt(id), profile, details, new ArrayList<UserSession>());
			users.add(user);
		}
		return users;
	}
	
	public TextClassificationResults classifyText(User user, String text) throws IOException
	{
		String urlString = String.format("/text/classify/%s", String.valueOf(user.getUserId()));
		Gson gson = new GsonBuilder().registerTypeAdapter(Word.class, new WordDeserializer()).create();
		String result = request(urlString, true, text);
		System.out.println(result);
		return gson.fromJson(result, TextClassificationResults.class);
	}
	
	public static void main(String[] args) throws IOException {
		AuthenticatedRestClient rClient = new AuthenticatedRestClient();
		rClient.authenticate("admin", "admin");
		System.out.println(rClient.authentication.auth);
	}
}

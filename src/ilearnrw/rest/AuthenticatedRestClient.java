package ilearnrw.rest;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.textclassification.TextClassificationResults;
import ilearnrw.textclassification.Word;
import ilearnrw.user.User;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.profile.UserProfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class AuthenticatedRestClient {
	HttpURLConnection connection;

	String baseURL = "http://localhost:8080/test";
	String authenticationUrl = "/user/auth";
	String problemDefinitionUrl = "/profile/problemDefinitions";
	String listUserUrl = "/user/list";
	String userProfileUrl = "/profile";
	String classifyTextUrl = "/text/classify";
	Authentication authentication = null;
	
	String request(Request request)
	{
		String response = null;
		try {
			URL url = new URL(baseURL + request.getUrl());
			connection = (HttpURLConnection) url.openConnection();
			//encoding of api:api
			String basicAuth = "Basic YXBpOmFwaQ==";
			connection.setRequestProperty ("Authorization", basicAuth);
			if (request.usesPost())
			{
			    connection.setRequestMethod("POST");
			    connection.setRequestProperty("Content-Type","text/plain;charset=UTF-8");
			    connection.setRequestProperty("Content-Length",String.valueOf(request.getPostBody().getBytes("UTF-8").length));
				connection.setDoOutput(true);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
				out.write(request.getPostBody());
			    out.close();
			}
			else
				connection.connect();
			int responseCode = connection.getResponseCode();
			System.out.println("Response code: " + responseCode);
			response = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")).readLine();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			try {
				String errorString = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8")).readLine();
				try {
					JsonElement jelement = new JsonParser().parse(errorString);
					String errorMessage = jelement.getAsJsonObject().get("developerMessage").getAsString();
					System.out.println(errorMessage + " for URL " + request.getUrl());
				} catch (Exception e1) {
					System.out.println(errorString);
					e1.printStackTrace();
				}
			} catch (Exception e2) {
				e.printStackTrace();
			}
		}
		return response;
	}
	
	public <T extends Object> T requestObj(Request request, Class<T> type)
	{
		Gson gson = new Gson();
		String result = request(request);
		return gson.fromJson(result, type);
	}
	
	public void authenticate(String username, String pass)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("username", username);
		argMap.put("pass", pass);
		Request request = new Request(authenticationUrl, argMap);
		Authentication obj = requestObj(request, Authentication.class);
		authentication = obj;
	}
	
	public void fetchUserProblems(User user)
	{
		if (user.getProfile() == null)
			return;
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("userId", String.valueOf(user.getUserId()));
		Request request = new Request(problemDefinitionUrl, authentication, argMap);
		ProblemDefinitionIndex problems = requestObj(request, ProblemDefinitionIndex.class);
		user.getProfile().getUserProblems().setProblems(problems);
	}
	
	public void fetchUserProfile(User user)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("userId", String.valueOf(user.getUserId()));
		Request request = new Request(userProfileUrl , authentication, argMap);
		UserProfile userProfile = requestObj(request, UserProfile.class);
		user.setProfile(userProfile);
	}
	
	public List<User> getAllUsers()
	{
		Request request = new Request(listUserUrl, authentication);
		Type collectionType = new TypeToken<List<User>>(){}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
		Gson gson = gsonBuilder.create();
		String result = request(request);
		List<User> users = gson.fromJson(result, collectionType);
		for (User user : users)
		{
			Gson gson2 = new Gson();
			System.out.println(gson2.toJson(user));
			fetchUserProfile(user);
			System.out.println(gson2.toJson(user));
			fetchUserProblems(user);
			System.out.println(gson2.toJson(user));
		}
		return users;
	}

	public TextClassificationResults classifyText(User user, String text)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("userId", String.valueOf(user.getUserId()));
		Request request = new Request(classifyTextUrl, authentication, argMap, text);
		Gson gson = new GsonBuilder().registerTypeAdapter(Word.class, new WordDeserializer()).create();
		String result = request(request);
		System.out.println(result);
		return gson.fromJson(result, TextClassificationResults.class);
	}
	
	public static void main(String[] args) {
		AuthenticatedRestClient rClient = new AuthenticatedRestClient();
		rClient.authenticate("admin", "admin");
		System.out.println(rClient.authentication.auth);
		List<User> users = rClient.getAllUsers();
	}
}

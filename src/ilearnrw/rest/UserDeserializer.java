package ilearnrw.rest;

import java.lang.reflect.Type;

import ilearnrw.user.User;
import ilearnrw.user.UserDetails;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserDeserializer implements JsonDeserializer<User> {

	@Override
	public User deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jobject = (JsonObject) json;
		int id = jobject.get("id").getAsInt(); //User
		String username = jobject.get("username").getAsString(); //UserDetails
		String language = jobject.get("language").getAsString(); //UserProfile
		LanguageCode lc;
		if (language.equals("EN"))
			lc = LanguageCode.EN;
		else
			lc = LanguageCode.GR;
	    return new User(id, new UserProfile(lc, null, null), new UserDetails(username, 0, null), null);
	}

}

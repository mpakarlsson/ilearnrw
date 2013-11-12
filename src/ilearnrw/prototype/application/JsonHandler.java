package ilearnrw.prototype.application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class JsonHandler {
	private Gson gson;
	private String path;
	
	public JsonHandler(String filename, boolean prettyPrint){
		if(prettyPrint)
			gson = new GsonBuilder().setPrettyPrinting().create();
		else 
			gson = new Gson();
		
		path = filename;
	}
	
	public boolean toJson(Object obj){
		try {
			FileWriter fw = new FileWriter(path);
			gson.toJson(obj, fw);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Object fromJson(Type type){
		
		Object obj = null;
		try {
			JsonReader reader = new JsonReader(new FileReader(path));
			obj = gson.fromJson(reader, type);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return obj;
		}
		
		return obj;
	}
}

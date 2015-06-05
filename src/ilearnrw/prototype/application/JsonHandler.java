package ilearnrw.prototype.application;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class JsonHandler {
	private Gson gson;
	private String path;
	private Type type;

	public JsonHandler(Type type, String filename, boolean prettyPrint) {
		if (prettyPrint)
			gson = new GsonBuilder().setPrettyPrinting().create();
		else
			gson = new Gson();

		this.type = type;
		path = filename;
	}

	public boolean toJson(Object obj) {
		try {
			switch (type) {
			case DATA:
				path = "data/" + path;
				break;
			}
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

	public Object fromJson(java.lang.reflect.Type type) {

		Object obj = null;
		try {
			InputStream inputStream = ResourceLoader.getInstance()
					.getInputStream(this.type, path);
			JsonReader reader = new JsonReader(new InputStreamReader(
					inputStream,"UTF-8"));
			obj = gson.fromJson(reader, type);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return obj;
		}

		return obj;
	}
}

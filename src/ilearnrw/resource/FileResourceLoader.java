package ilearnrw.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileResourceLoader extends ResourceLoader {

	@Override
	public InputStream getInputStream(Type type, String resource) {
		switch (type) {
		case DATA:
			resource = "data/" + resource;
			break;
		}

		try {
			return new FileInputStream(resource);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}

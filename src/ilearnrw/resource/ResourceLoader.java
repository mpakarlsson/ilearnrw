package ilearnrw.resource;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class ResourceLoader {

	static ResourceLoader resourceLoader = null;

	protected ResourceLoader() {
	}

	public static ResourceLoader getInstance() {
		if (resourceLoader == null)
			resourceLoader = new FileResourceLoader();
		return resourceLoader;
	}

	public abstract InputStream getInputStream(Type type, String resource);

	public abstract FileOutputStream getOutputStream(Type type, String resource);

	public static void setResourceLoaderInstance(ResourceLoader resourceLoader) {
		ResourceLoader.resourceLoader = resourceLoader;
	}

	public BufferedReader getBufferedReaderUTF8(Type type, String resource)
			throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream(type,
				resource), "UTF-8"));
	}

	public String readAllLinesAsStringUTF8(Type type, String resource)
			throws IOException {
		return readAllLinesAsStringUTF8(type, resource, "\n");
	}

	public String readAllLinesAsStringUTF8(Type type, String resource,
			String separator) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader buf = getBufferedReaderUTF8(type, resource);
		String line = null;
		while ((line = buf.readLine()) != null) {
			stringBuilder.append(line).append(separator);
		}
		buf.close();
		return stringBuilder.toString();
	}

	public List<String> readAllLinesAsListUTF8(Type type, String resource)
			throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader buf = getBufferedReaderUTF8(type, resource);
		String line = null;
		while ((line = buf.readLine()) != null) {
			lines.add(line);
		}
		buf.close();
		return lines;
	}

	public enum Type {
		DATA, LOCAL;
	}
}

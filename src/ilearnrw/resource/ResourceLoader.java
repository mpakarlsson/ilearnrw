package ilearnrw.resource;

import java.io.InputStream;

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
	
	public static void setResourceLoaderInstance(ResourceLoader resourceLoader)
	{
		ResourceLoader.resourceLoader = resourceLoader; 
	}
	
	public enum Type {
		DATA,LOCAL;
	}
}

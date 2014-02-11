package ilearnrw.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Request {
	Authentication tokenAuthentication;
	String postBody;
	Map<String, String> arguments;
	String url;

	public Request(String url, Authentication tokenAuthentication) {
		this(url, tokenAuthentication, null);
	}
	
	public Request(String url, Map<String, String> arguments) {
		this(url, null, arguments, null);
	}

	public Request(String url, Authentication tokenAuthentication,
			Map<String, String> arguments) {
		this(url, tokenAuthentication, arguments, null);
	}

	public Request(String url, Authentication tokenAuthentication,
			Map<String, String> arguments, String postBody) {
		this.url = url;
		this.tokenAuthentication = tokenAuthentication;
		this.arguments = arguments;
		this.postBody = postBody;
		useTokenAuthentication();
		formatUrl();
	}
	
	public boolean usesPost()
	{
		return (postBody != null);
	}
	
	public boolean usesTokenAuthentication()
	{
		return (tokenAuthentication != null);
	}
	
	public boolean hasArguments()
	{
		return (arguments != null);
	}
	
	public String getPostBody() {
		return postBody;
	}
	
	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}

	public Map<String, String> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, String> arguments) {
		this.arguments = arguments;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	private void useTokenAuthentication()
	{
		if (!usesTokenAuthentication())
			return;
		if (!hasArguments())
			arguments = new HashMap<String, String>();
		arguments.put("token", tokenAuthentication.auth);
	}

	private void formatUrl() {
		if (!hasArguments())
			return;
		Iterator<Entry<String, String>> iterator = arguments.entrySet()
				.iterator();
		if (iterator.hasNext())
			url += "?";
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			url += entry.getKey() + "=" + entry.getValue();
			if (iterator.hasNext())
				url += "&";
		}
	}
}

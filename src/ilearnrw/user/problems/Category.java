package ilearnrw.user.problems;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.io.Serializable;

public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	private String url;//always to lowercase
	
	public Category() {
		//for JSON deserialization
	}
	
	public Category(String url) {
		this.url = url.toLowerCase().trim();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url.toLowerCase().trim();
	}

	@Override
	public boolean equals(Object obj) {
		Category c = (Category)obj;
		return c.getUrl().trim().equalsIgnoreCase(this.url.trim());
	}
	@Override
	public String toString() {
		return "Category :" + url + "\n";
	}
	
	public boolean isSubCategory(Category c){
		return url.startsWith(c.getUrl()) && !(c.getUrl()).startsWith(url);
	}
	
	
}

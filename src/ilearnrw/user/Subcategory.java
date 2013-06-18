package ilearnrw.user;

import java.io.Serializable;

public class Subcategory implements Serializable {
	private static final long serialVersionUID = 1L;

	Category category;
	private String subcategoryTitle;

	public Subcategory(String subcategoryTitle, Category category) {
		this.category = category;
		this.subcategoryTitle = subcategoryTitle;
	}
	
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}



	public String getSubcategoryTitle() {
		return subcategoryTitle;
	}

	public void setSubcategoryTitle(String subcategoryTitle) {
		this.subcategoryTitle = subcategoryTitle;
	}

	@Override
	public boolean equals(Object obj) {
		Subcategory s = (Subcategory)obj;
		return s.getCategory().getTitle().equals(category.getTitle()) &&
				s.getSubcategoryTitle().equals(this.subcategoryTitle);
	}


	@Override
	public String toString() {
		return "Subcategory [category=" + category + ", subcategoryTitle="
				+ subcategoryTitle + "]";
	}
	
		
	
}

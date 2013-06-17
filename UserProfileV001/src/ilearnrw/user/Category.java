package ilearnrw.user;

public class Category {
	private String title;
	private boolean isReadingProblem;
	public Category(String title, boolean isReadingProblem) {
		this.title = title;
		this.isReadingProblem = isReadingProblem;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isReadingProblem() {
		return isReadingProblem;
	}
	public void setReadingProblem(boolean isReadingProblem) {
		this.isReadingProblem = isReadingProblem;
	}
	@Override
	public boolean equals(Object obj) {
		Category c = (Category)obj;
		return c.getTitle().equals(this.title);
	}
	@Override
	public String toString() {
		return "Category [title=" + title + ", isReadingProblem="
				+ isReadingProblem + "]";
	}
	
	
	
}

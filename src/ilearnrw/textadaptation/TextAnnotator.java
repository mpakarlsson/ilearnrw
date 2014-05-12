package ilearnrw.textadaptation;

import ilearnrw.user.profile.UserProfile;

import java.util.Map;
import java.util.List;
import java.awt.Font;
import java.awt.Color;

public interface TextAnnotator {
	
	void setTextFile(String textFile);
	
	String getTextFile ();
	
	void setProfile(UserProfile profile);
	
	UserProfile getProfile ();
	
	void setJSONFile(String JSONFileName);
	
	String getJSONFile();

	void setInputHTMLFile(String HTMLFile);

	String getInputHTMLFile();
	
	void annotateText();

	//WordClassifierInfo parseJSONFile()

	Map<Integer, List<String>> splitInPages();
	
	String readNextWord();
	
	void setWordColor(String wordId, Color color, int start, int end);
	
	void removeWordColor(String wordId, int start, int end);
	
	void setWordHighlighting(String wordId, Color color, int start, int end);
	
	void removeWordHighlighting(String wordId, int start, int end);

	void setHighlightingSpeed(String wordId, double value);

	List<String> decoratedWordsPerPage (int p);

	void setThreshold(double d);

	double getThreshold();

	void filterPage(int p);

	void setTextFontFamily(Font font);

	Font getTextFontFamily();

	void setTextFontSize(double v);

	double getDoubleTextFontSize();

	void setTextFontSize(int v);

	int getTextFontSize();

	void setDoubleLineSpacing(double v);

	double getDoubleLineSpacing ();

	void setIntLineSpacing(int v);

	int getIntLineSpacing ();

	void setDoubleMargin(double v);

	double getDoubleMargin();

	void setMargin(int v);

	int getMargin();

	void setBackgroundColor(Color color);

	Color getBackgroundColor();

	void setForegroundColor(Color color);

	Color getForegroundColor();

	void updatePageStyle(String CSSProperty, String value);

}

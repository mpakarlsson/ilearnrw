package ilearnrw.textadaptation;

import ilearnrw.annotation.UserBasedAnnotatedWord;
import ilearnrw.textclassification.*;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.textadaptation.utils.*;
import ilearnrw.annotation.*;
import ilearnrw.utils.ServerHelperClass;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
//import java.awt.Font;
//import java.awt.Color;
//import java.awt.geom.AffineTransform;
//import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Attribute;

public class TextAnnotationModule implements TextAnnotator, Serializable {

	private String textFile;
	private UserProfile profile;
	private String JSONFileName;
	private String inputHTMLFile;

	private String annotatedHTMLFile;
	private File annotatedFile;
	private File inputFile;

	private Document doc;

	private UserBasedAnnotatedWordsSet jsonObject;

	// Page properties
	private double threshold;
	private String font;
	private double dFontSize;
	private int fontSize;
	private double dLineSpacing;
	private int lineSpacing;
	private double dMargin;
	private int margin;
	private int backgroundColor;
	private int foregroundColor;

	private int screenWidth;
	private int screenHeight;

	private boolean activatePagination;

	private static final long serialVersionUID = 1L;

	private boolean normalUser;

	private PresentationRulesModule presRules;

	public TextAnnotationModule(String textFile) {
		this.textFile = textFile;

		// Default page values
		this.threshold = 0.10;
		this.font = "Tahoma";
		this.dFontSize = 1.0;
		this.fontSize = 14;
		this.dLineSpacing = 1.25;
		this.dMargin = 0.0;
		this.margin = 20;
		this.backgroundColor = 0xFFFFFF; //color white
		this.foregroundColor =  0xffff0000; //color black
		this.jsonObject = null;
		this.activatePagination = false;
		this.normalUser = true;
		this.screenWidth = 200;
		this.screenHeight = 200;
	}

	public TextAnnotationModule() {
		this.threshold = 0.10;
		this.font = "Tahoma";
		this.dFontSize = 1.0;
		this.fontSize = 14;
		this.dLineSpacing = 1.25;
		this.dMargin = 0.0;
		this.margin = 20;
		this.backgroundColor = 0xFFFFFF; //color white
		this.foregroundColor =  0xffff0000; //color black
		this.jsonObject = null;
		this.activatePagination = false;
		this.normalUser = true;
		this.screenWidth = 200;
		this.screenHeight = 200;
	}
	
	public void initializePresentationModuleFromServer(String token, String userID)
	{	
		String profileAsJSON = null;
		try {
			profileAsJSON = ServerHelperClass.sendGet("profile?userId="+userID+"&token=" + token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (profileAsJSON != null){
			Gson gson = new Gson();
			this.profile = gson.fromJson(profileAsJSON, UserProfile.class);
		}
		this.presRules = new PresentationRulesModule(profile);
	}
	
	public void initializePresentationModule(UserProfile profile)
	{
		this.profile = profile;
		
		if (this.presRules == null)
			this.presRules = new PresentationRulesModule(profile);
	}
	
	public void initializePresentationModule()
	{
		if (this.presRules == null)
			this.presRules = new PresentationRulesModule(profile);
	}
	

	public PresentationRulesModule getPresentationRulesModule() {
		return this.presRules;
	}

	/*public String sendPostToServer(String token, String input, String text)
	{
		String tokenParams;
		String resp = "No resp";
		int language;
		if (profile.getLanguage() == ilearnrw.utils.LanguageCode.EN)
		{
			tokenParams = "&lc=EN&token=";
			language = 1;
			
		}
		else
		{
			tokenParams = "&lc=GR&token=";
			language = 5;
		}
		try
		{
			this.textFile = text;
			resp = ServerHelperClass.sendPost(input+token, this.textFile);
			//PrintWriter printWriter = new PrintWriter("response.json");
			//printWriter.println(resp);
			//printWriter.close();
			this.JSONFileName = resp;
		}
		catch (Exception e)
		{
			resp = "Nothing";
		}
		return resp;
		
	}*/
	
	/**
	 * Sets a text file to the TextAnnotator object
	 */
	public void setTextFile(String textFile) {
		this.textFile = textFile;
	}

	/**
	 * Returns the text file of the TextAnnotator object
	 */
	public String getTextFile() {
		return this.textFile;
	}

	/**
	 * Sets a user’s profile to the TextAnnotator object.
	 */
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	/**
	 * Returns the current user’s profile of the TextAnnotator object.
	 */
	public UserProfile getProfile() {
		return this.profile;
	}

	/**
	 * Sets a JSON file to the TextAnnotator object.
	 */
	public void setJSONFile(String JSONFileName) {
		this.JSONFileName = JSONFileName;
	}

	/**
	 * Returns the JSON file of the TextAnnotator.
	 */
	public String getJSONFile() {
		return this.JSONFileName;
	}

	/**
	 * Sets an HTML file to the TextAnnotator object.
	 */
	public void setInputHTMLFile(String inputHTMLFile) {
		this.inputHTMLFile = inputHTMLFile;
	}

	/**
	 * Returns the input HTML file of the TextAnnotator.
	 */
	public String getInputHTMLFile() {
		return inputHTMLFile;
	}

	/**
	 * Sets an HTML file to the TextAnnotator object.
	 */
	public void setAnnotatedHTMLFile(String annotatedHTMLFile) {
		this.annotatedHTMLFile = annotatedHTMLFile;
	}

	/**
	 * Returns the input HTML file of the TextAnnotator.
	 */
	public String getAnnotatedHTMLFile() {
		return annotatedHTMLFile;
	}

	/**
	 * Retrieves the java object from the JSON file.
	 */
	public void retrieveJSonObject() {
		Gson gson = new GsonBuilder().create();
		//try {
			//JsonReader reader = new JsonReader(new java.io.BufferedReader(new java.io.FileReader(this.getJSONFile())));
			System.out.println("I am the jSonfile: " +this.getJSONFile());
			//JsonReader reader = new JsonReader(new java.io.StringReader(this.getJSONFile()));
			this.jsonObject = gson.fromJson(JSONFileName, ilearnrw.annotation.UserBasedAnnotatedWordsSet.class);
			
			
			
			//reader.close();
		//} catch (IOException e) {
			//System.err.println("Unable to write to file");
		//}
	}

	public void readInputHTML() {
		Charset.forName("UTF-8").newEncoder();
		//this.inputHTMLFile = this.jsonObject.getHtml();
		doc = Jsoup.parse(this.inputHTMLFile);
	}

	public void writeAnnotatedHTML() {
		this.annotatedHTMLFile = doc.html().toString();
		removeExtraWhiteSpaces();
	}

	public void removeExtraWhiteSpaces() {
		this.annotatedHTMLFile = this.annotatedHTMLFile.replaceAll("\\s+<span", "<span");
		this.annotatedHTMLFile = this.annotatedHTMLFile.replaceAll("\\s+</w", "</w");
	}
	
	
	public void processText() {
		// Reads words one by one and processes them according to the
		// presentation rules.
		Elements selectedElements = doc.select("w");
		
		for (Element selElem : selectedElements) {
			FinalAnnotation f = processWord(selElem.attr("id").replace("w", ""));

			selElem.text(selElem.text().replace(" ", ""));
			String word = selElem.text();
			
			if (f != null) {
				System.out.println(word + " I have color: " + f.getRule().getHighlightingColor());
				if (f.getRule().getPresentationStyle() == Rule.HIGHLIGHT_WHOLE_WORD) {
					this.setWordHighlighting(selElem.attr("id"), f.getRule()
							.getHighlightingColor(), 0, word.length() - 1);
				} else if (f.getRule().getPresentationStyle() == Rule.HIGHLIGHT_PROBLEMATIC_PARTS) {
					this.setWordHighlighting(selElem.attr("id"), f.getRule()
							.getHighlightingColor(), f.getStringMatchesInfo()
							.getStart(), f.getStringMatchesInfo().getEnd());
				} else if (f.getRule().getPresentationStyle() == Rule.PAINT_WHOLE_WORD) {
					this.setWordColor(selElem.attr("id"), f.getRule()
							.getTextColor(), 0, word.length() - 1);
				} else if (f.getRule().getPresentationStyle() == Rule.PAINT_PROBLEMATIC_PARTS) {
					this.setWordColor(selElem.attr("id"), f.getRule()
							.getTextColor(), f.getStringMatchesInfo()
							.getStart(), f.getStringMatchesInfo().getEnd());
				}
			}
		}
	}
	
	

	/**
	 * Annotates the HTML file associated with the TextAnnotator object.
	 */
	public void annotateText() {
		
		// Retrieve the JSON Oject
		if (this.jsonObject == null) {
			this.retrieveJSonObject();
		}

		// Take the input HTML from JSON
		this.readInputHTML();

		// If activated, do pagination;
		//if (this.activatePagination) {
			//this.splitInPages();
		//}

		// Process the words of the text
		this.processText();

		if (this.activatePagination) {
			this.filterPage(0); // To change
		}

		// Write to the annotated html file
		this.writeAnnotatedHTML();
	}

	/**
	 * Determines how will the word will be eventually annotated if more than
	 * one rules apply in the same word.
	 * 
	 * @param wordID
	 * @return
	 */
	private FinalAnnotation processWord(String wordID) {
		
		FinalAnnotation f = null;
		Integer pos = this.jsonObject.getIdCorrespondance()
				.get(Integer.parseInt(wordID.replace("w", "")));
		
		System.out.println(this.jsonObject.toString());
		
		if (pos != null) {
			UserBasedAnnotatedWord word = this.jsonObject
					.getWords().get(pos);
			

			if (word != null) {
				ArrayList<SeverityOnWordProblemInfo> wordProblems = word
						.getUserSeveritiesOnWordProblems();
				

				int category;
				int index;
				int userSeverity;

				if (wordProblems.size() == 1) {
					category = wordProblems.get(0).getCategory();
					index = wordProblems.get(0).getIndex();

					userSeverity = wordProblems.get(0).getUserSeverity();

					if ((this.normalUser && userSeverity != 0)
							|| !this.normalUser) {
						if (presRules.getRulesTable()[category][index]
								.getActivated()) {
							f = new FinalAnnotation(
									wordProblems.get(wordProblems.size() - 1),
									wordProblems.get(wordProblems.size() - 1)
											.getMatched().get(0),
									presRules.getRulesTable()[category][index]);
						}
					}
				} else if (wordProblems.size() > 1) {
					category = wordProblems.get(wordProblems.size() - 1)
							.getCategory();
					index = wordProblems.get(wordProblems.size() - 1)
							.getIndex();

					userSeverity = wordProblems.get(wordProblems.size() - 1)
							.getUserSeverity();

					if ((this.normalUser && userSeverity != 0)
							|| !this.normalUser) {
						if (presRules.getRulesTable()[category][index]
								.getActivated()) {
							f = new FinalAnnotation(wordProblems.get(0),
									wordProblems.get(0).getMatched().get(0),
									presRules.getRulesTable()[category][index]);
						}
					}
				} else {
					return null;
				}
			}
		}
		return f;
	}

	/**
	 * Returns a Map object maintaining a list of words of each page (including
	 * multiple occurrences of each word in the same page).
	 */
	/*public Map<Integer, List<String>> splitInPages() {
		// Add <br> to define lines.
		StringTokenizer st;
		double totalWidth = 0;
		double wordWidth = 0;
		double maxHeight = 0;
		double totalMaxHeight = 0;
		String token;

		AffineTransform af = new AffineTransform();
		FontRenderContext fr = new FontRenderContext(af, true, true);
		StringBuffer sb;

		Font f = new Font(font, Font.BOLD, this.fontSize);

		int lineNumber = 1;
		int parLineNumber = 1;

		double lineHeight = 0;
		int pageLimit = 200;
		double currentHeight = 0;

		ArrayList<Integer> linesPerParagraph = new ArrayList<Integer>();
		Elements words;
		Element word;
		Element previousWord = null;

		for (Element e : doc.body().select("p")) {
			totalWidth = 0;

			for (Element sen : e.select("sen")) {
				words = sen.select("w");
				for (int i = 0; i < words.size(); i++) {
					word = words.get(i);
					if (i > 0) {
						previousWord = words.get(i - 1);
					}
					wordWidth = f.getStringBounds(word.text(), fr).getWidth();
					maxHeight = Math.max(f.getStringBounds(word.text(), fr)
							.getHeight(), maxHeight);

					if (totalWidth + wordWidth + this.margin + 20 > 600) {
						totalWidth = wordWidth;
						lineNumber++;
						parLineNumber++;
						word.wrap("<br>");
						currentHeight += maxHeight;

						if (currentHeight > pageLimit) {
							sen.wrap("<strong>");
							currentHeight = maxHeight;
						}
					} else {
						totalWidth += wordWidth;
					}
				}
			}
			linesPerParagraph.add(new Integer(lineNumber));
		}
		doc.html(doc.html().replace("</br>", ""));

		/*
		 * double lineHeight = 0; int pageLimit = 600;
		 * java.util.ArrayList<Integer> lastParagraphInPage = new
		 * java.util.ArrayList<Integer>();
		 * 
		 * double currentHeight = 0;
		 * 
		 * for (int i = 0; i < linesPerParagraph.size(); i++) { currentHeight =
		 * linesPerParagraph.get(i).intValue() * maxHeight;
		 * 
		 * if (currentHeight + lineHeight > pageLimit && currentHeight <
		 * pageLimit) { lastParagraphInPage.add(i); lineHeight = currentHeight;
		 * } else if (lineHeight > pageLimit &&
		 * linesPerParagraph.get(i).intValue() > pageLimit) {
		 * //System.out.println("I call"); // First paragraph does not fit in
		 * page. Have to split in sentences } else if (currentHeight +
		 * lineHeight < pageLimit && linesPerParagraph.size() == 1) {
		 * lastParagraphInPage.add(i); } else { lineHeight += currentHeight; } }
		 * 
		 * lastParagraphInPage.add(linesPerParagraph.size());
		 * 
		 * int lastParagraph = 0; int startParagraph = 0;
		 */
		// System.out.println(doc.html());

		/*
		 * double lineHeight = 0; int pageLimit = 200; double currentHeight = 0;
		 * boolean firstSentenceOfPageFound = false;
		 * 
		 * Elements sentences = doc.select("sen"); Element e; Element previous =
		 * null;
		 * 
		 * int page = 0;
		 * 
		 * sentences.get(0).wrap("<strong>");
		 * sentences.get(0).html(sentences.get(0).html().replace("</strong>",
		 * ""));
		 * 
		 * for (int i = 0; i < sentences.size(); i++) { e = sentences.get(i);
		 * 
		 * if (i > 0) { previous = sentences.get(i-1); }
		 * 
		 * String sentence = e.html(); String k = "<br />";
		 * 
		 * 
		 * java.util.StringTokenizer tokenizer = new
		 * java.util.StringTokenizer(sentence, k);
		 * 
		 * 
		 * String[] pieces = sentence.split("<br />");
		 * 
		 * for (int j = 0; j < pieces.length;j++) { System.out.println("j: " +
		 * pieces[j]); }
		 * 
		 * /*while (tokenizer.hasMoreTokens()) {
		 * System.out.println(tokenizer.nextToken()); }
		 * 
		 * if (e.html().contains("br")) { System.out.println("E-text(): "
		 * +e.text() + currentHeight); currentHeight += maxHeight;
		 * 
		 * if (currentHeight > pageLimit) { currentHeight = maxHeight;
		 * firstSentenceOfPageFound = false; previous.wrap("<instrong>");
		 * previous.html(previous.html().replace("<instrong>", ""));
		 * previous.html(previous.html().replace("</instrong>", "</strong>"));
		 * e.wrap("<strong>"); e.html(e.html().replace("</strong>", ""));
		 * page++; } else { firstSentenceOfPageFound = true; } }
		 * 
		 * }
		 */

		/*
		 * for (int i = 0; i < lastParagraphInPage.size(); i++) { lastParagraph
		 * = lastParagraphInPage.get(i).intValue(); //String startHtml =
		 * htmlPerParagraph.get(startParagraph); //String lastHtml =
		 * htmlPerParagraph.get(lastParagraph-1);
		 * 
		 * 
		 * for (Element e : doc.body().select("sen")); {
		 * 
		 * } //System.out.println(.get(startParagraph));
		 * //System.out.println(doc.body().select("p").get(lastParagraph-1));
		 * 
		 * //startHtml = "<page>"+startHtml;
		 * 
		 * 
		 * //doc.html(doc.html().replace(startHtml, "<page>"+startHtml));
		 * //doc.html(doc.html().replace(lastHtml, lastHtml + "</page>"));
		 * //lastHtml = lastHtml + "</page>";
		 * 
		 * startParagraph = lastParagraph; }
		 */

		/*
		 * java.util.StringTokenizer st = new
		 * java.util.StringTokenizer(body.text()); StringBuffer sb = new
		 * StringBuffer("<br>");
		 * 
		 * String token;
		 * 
		 * double totalWidth = 0; double wordWidth = 0; double maxHeight = 0;
		 * double totalMaxHeight = 0;
		 * 
		 * java.awt.geom.AffineTransform af = new
		 * java.awt.geom.AffineTransform(); java.awt.font.FontRenderContext fr =
		 * new java.awt.font.FontRenderContext(af,true,true);
		 * 
		 * int lineNumber = 0; while (st.hasMoreTokens()) { token =
		 * st.nextToken(); wordWidth = font.getStringBounds(token,
		 * fr).getWidth(); maxHeight = Math.max(font.getStringBounds(token,
		 * fr).getHeight(), maxHeight);
		 * 
		 * if (totalWidth + wordWidth > 200) { sb.append("</br><br>" + token);
		 * totalWidth = wordWidth; lineNumber++; } else { totalWidth +=
		 * wordWidth; sb.append(token + " "); } } sb.append("</br>");
		 * 
		 * 
		 * totalMaxHeight = lineNumber * maxHeight;
		 * System.out.println(totalMaxHeight);
		 * 
		 * if (totalMaxHeight > 100) { int lineHeight = 0;
		 * 
		 * st = new java.util.StringTokenizer(sb.toString(),"</br>"); sb = new
		 * StringBuffer("<html><page>");
		 * 
		 * while (st.hasMoreTokens()) { token = st.nextToken();
		 * 
		 * if (lineHeight + maxHeight > 100 && !token.startsWith("html")) {
		 * sb.append("</page><page>"); lineHeight = 0; }
		 * sb.append("<br>"+token+"</br>"); lineHeight+=maxHeight; }
		 * sb.append("</page>"); }
		 * 
		 * sb.append("</html>"); System.out.println(sb.toString());
		 */

		//return null;
	//}

	/**
	 * Returns the next word that has to be processed in the HTML file.
	 */
	public String readNextWord() {
		java.util.ArrayList<String> wordsList = new java.util.ArrayList<String>();

		java.util.StringTokenizer st = new java.util.StringTokenizer(doc.text()
				.trim(), " . ! ; ? >> <<");

		while (st.hasMoreTokens()) {
			String nextToken = st.nextToken();
			if (!nextToken.equals(",")) {
				wordsList.add(nextToken.replace(",", "").trim());
			}
		}
		return null;
	}

	public void activatePagination(boolean activatePagination) {
		this.activatePagination = activatePagination;
	}

	/**
	 * The method applies the CSS “color” property to the word with id=w1,
	 * starting from index start and finishing to index end and annotates
	 * property the HTML file.
	 */
	public void setWordColor(String wordID, int color, int start, int end) {
		Element element = doc.getElementById(wordID);
		this.setAttributeToWord(element, wordID, "color", String.format("#%06X", (0xFFFFFF & color))+"", start, end);
	}

	/**
	 * Removes the colour of a word by removing the CSS “color” property and
	 * annotates property the HTML file.
	 */
	public void removeWordColor(String wordID, int start, int end) {
		Element element = doc.getElementById(wordID);

		this.removeAttributeFromWord(element, wordID, "color", start, end);
	}

	/**
	 * The method applies the CSS “mark” property to the word with id=w1,
	 * starting from index start and finishing to index end and annotates
	 * property the HTML file.
	 */
	public void setWordHighlighting(String wordID, int color, int start,
			int end) {
		Element element = doc.getElementById(wordID);

		this.setAttributeToWord(element, wordID, "background-color", String.format("#%06X", (0xFFFFFF & color)), start, end);

	}

	public void setAttributesToWord(String wordID,
			java.util.Map<String, String> attributes, int start, int end) {
		Element element = doc.getElementById(wordID);

		String attribute = "";

		for (java.util.Iterator<String> it = attributes.keySet().iterator(); it
				.hasNext();) {
			attribute = it.next();
			this.setAttributeToWord(element, wordID, attribute,
					(String) attributes.get(attribute), start, end);
		}

	}

	public void setAttributeToWord(Element element, String wordID,
			String attribute, String value, int start, int end) {
		String word = element.text().trim();

		if (end == word.length() - 1 && start == 0) {
			element.attr("style", element.attr("style") + " " + attribute + ":"
					+ value + ";");
		} else {
			// Checks if the base element contains span
			if (element.outerHtml().contains("span")) {
				boolean found = false;
				for (Element e : element.select("span")) {

					// Checks if the desired word contains span
					if (e.text().equals(
							element.text().substring(start, end + 1))) {
						e.attr("style", e.attr("style") + " " + attribute + ":"
								+ value + ";");
						found = true;
					}
				}

				// If the desired word is not within a span
				if (!found) {
					for (int i = 0; i < element.childNodes().size(); i++) {
						Node child = element.childNodes().get(i);

						if (child instanceof TextNode) {
							String text = ((TextNode) child).text();

							if (text.equals(element.text().substring(start,
									end + 1))) {
								child.remove();
								element.prependElement("span")
										.attr("style",
												attribute + ":" + value + ";")
										.text(text);
							}
						}
					}
				}
			} else // The element had no span
			{
				String result[] = setElementsText(element.text(), start, end);

				for (int i = 0; i < element.childNodes().size(); i++) {
					Node child = element.childNodes().get(i);
					child.remove();
				}

				if (!result[0].equals("")) {
					element.appendChild(new TextNode(result[0], ""));
				}
				if (!result[1].equals("")) {
					element.appendElement("span")
							.attr("style", attribute + ":" + value + ";")
							.text(result[1]);
				}
				if (!result[2].equals("")) {
					element.appendChild(new TextNode(result[2], ""));
				}
			}
		}
	}

	public void removeAttributeFromWord(Element element, String wordID,
			String attribute, int start, int end) {
		String word = element.text();

		if (end == word.length() - 1 && start == 0) {
			Attributes styleAtt = element.attributes();

			if (!styleAtt.asList().isEmpty()) {
				Attribute a = styleAtt.asList().get(1);

				if (a.getKey().equals("style")) {
					String[] items = a.getValue().trim().split(";");

					String newValue = "";

					if (items.length == 1) {
						element.removeAttr("style");
					}

					else {
						for (String item : items) {
							if (!item.contains(attribute)) {
								newValue = newValue.concat(item).trim();
							}
						}
						a.setValue(newValue);
					}
				}
			}
		} else {
			for (int i = 0; i < element.select("span").size(); i++) {
				Element e = element.select("span").get(i);

				if (e.text().equals(element.text().substring(start, end + 1))) {
					Attributes styleAtt = e.attributes();

					if (!styleAtt.asList().isEmpty()) {
						Attribute a = styleAtt.asList().get(0);

						if (a.getKey().equals("style")) {
							String[] items = a.getValue().trim().split(";");

							String newValue = "";

							if (items.length == 1) {
								Element clone = element.clone();
								e.remove();
								element.text(clone.text());
							} else {
								for (String item : items) {
									if (!item.contains(attribute)) {
										newValue = newValue.concat(item).trim();
									}
								}
								a.setValue(newValue);
							}
						}
					}
				}
			}

		}
	}

	/**
	 * Removes the highlight colour of a word by removing the CSS “mark”
	 * property and annotates property the HTML file.
	 */
	public void removeWordHighlighting(String wordID, int start, int end) {
		Element element = doc.getElementById(wordID);

		this.removeAttributeFromWord(element, wordID, "color", start, end);
	}

	/**
	 * The method adds a tag to the word with the specified id value that
	 * determines the speed of the highlighting when it will display the word
	 * (if highlighting operation is activated) and annotates property the HTML
	 * file.
	 */
	public void setHighlightingSpeed(String wordId, double value) {

	}

	/**
	 * Returns a List object containing the decorated words of page p.
	 */
	public List<String> decoratedWordsPerPage(int p) {
		return null;
	}

	/**
	 * A fraction p between 0 and 1 indicating that the decorated words will be
	 * no more than d% of the words within the same page.
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	/**
	 * Returns a fraction between 0 and 1 indicating that only the d% of the
	 * words of a page will be decorated.
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * Filters the page such that only the d% of the words of a page will be
	 * decorated.
	 */
	public void filterPage(int p) {

	}

	/**
	 * Modifies the font face of the HTML file by applying the CSS “font-family”
	 * property.
	 */
	public void setTextFontFamily(String font, String tag) {
		this.font = font;
		this.updatePageStyle("font-family", font, tag);
	}

	/**
	 * Modifies the CSS values of the text
	 * 
	 * @param attribute
	 *            - the attribute to be modified
	 * @param value
	 *            - the value that attribute is assigned
	 * @param tag
	 *            - the tag that will be affected
	 */
	public void updatePageStyle(String attribute, String value, String tag) {
		StringBuffer sb = new StringBuffer();

		for (Element e : doc.select("style")) {
			String styleRules = e.getAllElements().get(0).data();

			java.util.StringTokenizer st = new java.util.StringTokenizer(
					styleRules, "{}");
			java.util.StringTokenizer inner;
			String innerNextToken;
			String stringToReplace;

			while (st.countTokens() > 1) {
				String selectors = st.nextToken();
				String properties = st.nextToken();
				sb.append(selectors + "{");

				if (selectors.contains(tag)) {
					inner = new java.util.StringTokenizer(properties);

					if (properties.contains(attribute)) {
						while (inner.hasMoreTokens()) {
							innerNextToken = inner.nextToken();

							if (innerNextToken.contains(attribute)) {
								int start = innerNextToken.indexOf(attribute)
										+ attribute.length() + 1;
								int end = innerNextToken.indexOf(";", start);

								if (!attribute.equals("color")) {
									stringToReplace = innerNextToken.substring(
											start, end);
									innerNextToken = innerNextToken.replace(
											stringToReplace, value);
								} else {
									if (innerNextToken.indexOf(attribute) > 1) {
										if (innerNextToken
												.charAt(innerNextToken
														.indexOf(attribute) - 1) == '-') {
											sb.append(innerNextToken);
											continue;
										} else if (innerNextToken
												.charAt(innerNextToken
														.indexOf(attribute) - 1) != '-') {
											stringToReplace = innerNextToken
													.substring(start, end);
											innerNextToken = innerNextToken
													.replace(stringToReplace,
															value);
										}
									} else {

										stringToReplace = innerNextToken
												.substring(start, end);
										innerNextToken = innerNextToken
												.replace(stringToReplace, value);
									}
								}
							}
							sb.append(innerNextToken);
						}

					} else {
						properties = properties.substring(0,
								properties.length())
								+ " " + attribute + ":" + value + ";";
						sb.append(properties);

					}
					sb.append("}");
				} else {
					sb.append(properties + "}");
				}
			}
		}

		for (Element e : doc.select("style")) {
			doc.html(doc.html().replace(e.getAllElements().get(0).data(),
					sb.toString() + "\n"));
			System.out.println(doc.html());
		}
	}

	/**
	 * Returns the current font family of the HTML file.
	 */
	public String getTextFontFamily() {
		return this.font;
	}

	/**
	 * Modifies by v% the font size of the HTML file by applying the CSS
	 * “font-size” property and annotates property the HTML file.
	 */
	public void setTextFontSize(double dFontSize, String tag) {
		this.dFontSize = dFontSize;
		this.updatePageStyle("font-size", dFontSize + "%", tag);
	}

	/**
	 * Returns the current font-size value (as percentage) of the HTML file.
	 */
	public double getDoubleTextFontSize() {
		return this.dFontSize;
	}

	/**
	 * Sets to the font size of the HTML file, value v (in pixels) by applying
	 * the CSS “font-size” property and annotates property the HTML file.
	 */
	public void setTextFontSize(int fontSize, String tag) {
		this.fontSize = fontSize;
		this.updatePageStyle("font-size", dFontSize + "px", tag);
	}

	/**
	 * Returns the current font-size value (in pixels) of the HTML file.
	 * 
	 * @return
	 */
	public int getTextFontSize() {
		return this.fontSize;
	}

	/**
	 * Modifies by v% the line spacing size of the HTML file by applying the CSS
	 * “line-height” property and annotates property the HTML file
	 */
	public void setDoubleLineSpacing(double dLineSpacing, String tag) {
		this.dLineSpacing = dLineSpacing;
		this.updatePageStyle("line-height", dFontSize + "px", tag);
	}

	/**
	 * Returns the current value of line spacing (as percentage) of the HTML
	 * file.
	 */
	public double getDoubleLineSpacing() {
		return this.dLineSpacing;
	}

	/**
	 * Sets to the line spacing size of the HTML file value v (in pixel) by
	 * applying the CSS “line-height” property and annotates property the HTML
	 * file.
	 */
	public void setIntLineSpacing(int lineSpacing, String tag) {
		this.lineSpacing = lineSpacing;
		this.updatePageStyle("line-height", dLineSpacing + "px", tag);
	}

	/**
	 * Returns the current value of line spacing (in pixel) of the HTML file.
	 */
	public int getIntLineSpacing() {
		return this.lineSpacing;
	}

	/**
	 * Modifies by v% the margins of the HTML file by applying the CSS “margin”
	 * property and annotates property the HTML file.
	 */
	public void setDoubleMargin(double dMargin, String tag) {
		this.dMargin = dMargin;
		this.updatePageStyle("margin", dMargin + "%", tag);
	}

	/**
	 * Returns the current value of margins (as percentage) of the HTML file.
	 */
	public double getDoubleMargin() {
		return this.dMargin;
	}

	/**
	 * Sets to the margins of the HTML file value v (in pixels) by applying the
	 * CSS “margin” property and annotates property the HTML file.
	 */
	public void setMargin(int margin) {
		this.margin = margin;
	}

	/**
	 * Returns the current value of margins (in pixels) of the HTML file.
	 */
	public int getMargin() {
		return this.margin;
	}

	/**
	 * Sets the background colour of the HTML file by applying the
	 * “background-color” property and annotates property the HTML file.
	 */
	public void setBackgroundColor(int backgroundColor, String tag) {
		this.backgroundColor = backgroundColor;
		this.updatePageStyle("background-color", String.format("#%06X", (0xFFFFFF & backgroundColor))+"",tag);
	}

	/**
	 * Returns the current background colour of the HTML file.
	 */
	public int getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * Sets the foreground colour of the HTML file by applying the “color”
	 * property and annotates property the HTML file.
	 */
	public void setForegroundColor(int foregroundColor, String tag) {
		this.foregroundColor = foregroundColor;
		this.updatePageStyle("color", String.format("#%06X", (0xFFFFFF & foregroundColor))+"", tag);
	}

	/**
	 * Returns the current foreground colour of the HTML file.
	 */
	public int getForegroundColor() {
		return this.foregroundColor;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	/**
	 * Splits the word in three parts to determine the part that will be
	 * visualized independently.
	 * 
	 * @param elementText
	 * @param start
	 * @param end
	 * @return
	 */
	private static String[] setElementsText(String elementText, int start,
			int end) {
		String beforeText = "";
		String middleText = "";
		String endText = "";

		if (start == 0 && end != elementText.length() - 2) {
			middleText = elementText.substring(0, end);
			endText = elementText.substring(end, elementText.length());
		} else if (start != 0 && end != elementText.length() - 2) {
			beforeText = elementText.substring(0, start);
			middleText = elementText.substring(start, end - 1);
			endText = elementText.substring(end - 1, elementText.length());
		} else if (start != 0 && end == elementText.length() - 2) {
			beforeText = elementText.substring(0, start);
			middleText = elementText.substring(start, end);
		}

		String[] result = new String[3];
		result[0] = beforeText;
		result[1] = middleText;
		result[2] = endText;

		return result;
	}
	
	public String toString()
	{
		return "this is me";
	}

}

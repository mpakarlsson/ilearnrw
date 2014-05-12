package ilearnrw.textadaptation;

import ilearnrw.user.profile.UserProfile;

import java.util.Map;
import java.util.List;
import java.awt.Font;
import java.awt.Color;
import java.io.Serializable;

class TextAnnotationModule implements TextAnnotator, Serializable{
	
	private String textFile;
	private UserProfile profile;
	private String JSONFileName;
	private String inputHTMLFile;
	
	private String annotatedHTMLFile;
	private java.io.File annotatedFile;
	private java.io.File inputFile;
	
	private org.jsoup.nodes.Document doc;
	
	// Page properties
	private double threshold;
	private Font font;
	private double dFontSize;
	private int fontSize;
	private double dLineSpacing;
	private int lineSpacing;
	private double dMargin;
	private int margin;
	private Color backgroundColor;
	private Color foregroundColor;
	
	private int screenWidth;
	private int screenHeight;
	
	private static final long serialVersionUID = 1L;
	
	public TextAnnotationModule(String textFile, UserProfile profile)
	{
		this.textFile = textFile;
		this.profile = profile;
		
		// Default page values
		this.threshold = 0.10;
		this.font = new Font("Tahoma", Font.PLAIN, 14);
		this.dFontSize = 1.0;
		this.dLineSpacing = 1.25;
		this.dMargin = 0.0;
		this.backgroundColor = Color.WHITE;
		this.foregroundColor = Color.BLACK;
	}
		
	public TextAnnotationModule()
	{
		this.threshold = 0.10;
		this.font = new Font("Tahoma", Font.PLAIN, 14);
		this.dFontSize = 1.0;
		this.dLineSpacing = 1.25;
		this.dMargin = 0.0;
		this.backgroundColor = Color.WHITE;
		this.foregroundColor = Color.BLACK;
	}
	
	/**
	 * Sets a text file to the TextAnnotator object	
	 */
	public void setTextFile(String textFile)
	{
		this.textFile = textFile;
	}
		
	/**
	 * Returns the text file of the TextAnnotator object
	 */
	public String getTextFile ()
	{
		return this.textFile;
	}
		
	/**
	 * Sets a user’s profile to the TextAnnotator object.
	 */
	public void setProfile(UserProfile profile)
	{
		this.profile = profile;
	}
		
	/**
	 * Returns the current user’s profile of the TextAnnotator object.
	 */
	public UserProfile getProfile()
	{
		return this.profile;
	}
		
	/**
	 * Sets a JSON file to the TextAnnotator object.
	 */
	public void setJSONFile(String JSONFileName)
	{
		this.JSONFileName = JSONFileName;
	}
		
	/**
	 * Returns the JSON file of the TextAnnotator.
	 */
	public String getJSONFile()
	{
		return this.JSONFileName;
	}

	/**
	 * Sets an HTML file to the TextAnnotator object.
	 */
	public	void setInputHTMLFile(String inputHTMLFile)
	{
		this.inputHTMLFile = inputHTMLFile;
	}

	/**
	 * Returns the input HTML file of the TextAnnotator.
	 */
	public String getInputHTMLFile()
	{
		return inputHTMLFile;
	}
	

	/**
	 * Sets an HTML file to the TextAnnotator object.
	 */
	public	void setAnnotatedHTMLFile(String annotatedHTMLFile)
	{
		this.annotatedHTMLFile = annotatedHTMLFile;
	}

	/**
	 * Returns the input HTML file of the TextAnnotator.
	 */
	public String getAnnotatedHTMLFile()
	{
		return annotatedHTMLFile;
	}
		
	/**
	 * Annotates the HTML file associated with the TextAnnotator object.
	 */
	public void annotateText()
	{
		try
		{
			this.annotatedHTMLFile = "C:\\Users\\Fouli\\Desktop\\OutputTest.html";
			java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(new java.io.File(this.inputHTMLFile)));
			
			java.nio.charset.Charset.forName("UTF-8").newEncoder() ;
			
			String strLine;
			
			StringBuffer text=new StringBuffer();
			
			// Extracts the text from the HTML file
			while ((strLine = br.readLine()) != null)
			{
				text.append(strLine+"\n");
			}
			
			doc = org.jsoup.Jsoup.parse(text.toString());
			
			
			// Adds the appropriate attributes to the words
			/*for (org.jsoup.nodes.Element element : doc.select("style") )
			{
				System.out.println(element.childNodes().get(0).attr("h1"));
				
				//element.html(element.html().replaceAll("font-family:Tahoma, Geneva, sans-serif;", "font-family:Arial;"));
				//System.out.println(doc.select("style").first().getElementsByTag("h1"));
				
				//String word = element.ownText();
			    //String wordID = element.id();
			    
			    //element.attr("style", "color:pink;");
		        //element.appendElement("style");
		        
		        
		        //element.attr("font-family", "font-family:Arial;");
		        //child.attr("h1", element.attr("h1") +" " +"background:yellow;");
				//System.out.println(element.attr("h1"));
				//System.out.println(element);
			}
			*/
			for (org.jsoup.nodes.Element e : doc.select("style")) {
	            String styleRules = e.getAllElements().get(0).data().replaceAll("\n", "").trim(), delims =
	                    "{}";
	           java.util.StringTokenizer st = new java.util.StringTokenizer(styleRules, delims);
	            while (st.countTokens() > 1) 
	            {
	                String selector = st.nextToken(), properties = st.nextToken();
	                System.out.println(selector);
	                // Process selectors such as "a:hover"
	                if (selector.indexOf(":") > 0) 
	                {
	                    selector = selector.substring(0, selector.indexOf(":"));
	                }
	            }
			}
	                
			
			
			java.io.PrintWriter bw = new java.io.PrintWriter(new java.io.File(this.annotatedHTMLFile),"UTF-8");
			bw.write(doc.html()) ;
			bw.flush();
			
			br.close();
			bw.close();
		}
		
		catch (java.io.IOException e)
		{
			System.err.println("Unable to write to file");
			System.exit(-1);
		}
		
	}
	

	//WordClassifierInfo parseJSONFile()

	/**
	 * Returns a Map object maintaining a list of words of each page (including multiple occurrences of each word in the same page).
	 */
	public Map<Integer, List<String>> splitInPages()
	{
		return null;
	}
		
	/**
	 * Returns the next word that has to be processed in the HTML file.
	 */
	public String readNextWord()
	{
		return null;
	}
	
	/**
	 * The method applies the CSS “color” property to the word with id=w1, starting from index start and finishing to index end and annotates property the HTML file.
	 */
	public void setWordColor(String wordID, Color color, int start, int end)
	{
		org.jsoup.nodes.Element element = doc.getElementById(wordID);
		
		setAttributeToWord(element, wordID, "color", color.toString(), start, end);
	}
	
		
	/**
	 * Removes the colour of a word by removing the CSS “color” property and annotates property the HTML file.
	 */
	public void removeWordColor(String wordID, int start, int end)
	{
		org.jsoup.nodes.Element element = doc.getElementById(wordID);
		
		removeAttributeFromWord(element, wordID, "color", start, end);
	}
		
	/**
	 * The method applies the CSS “mark” property to the word with id=w1, starting from index start and finishing to index end and annotates property the HTML file.
	 */
	public void setWordHighlighting(String wordID, Color color, int start, int end)
	{
		org.jsoup.nodes.Element element = doc.getElementById(wordID);
		setAttributeToWord(element, wordID, "background", color.toString(), start, end);
	}
	
	
	public void setAttributesToWord(String wordID, java.util.Map<String, String> attributes, int start, int end)
	{
		org.jsoup.nodes.Element element = doc.getElementById(wordID);
		
		String attribute = "";
		
		for (java.util.Iterator<String> it = attributes.keySet().iterator(); it.hasNext();)
		{
			attribute = it.next();
			setAttributeToWord(element, wordID, attribute, (String) attributes.get(attribute), start, end);
		}
		
	}
	
	public void setAttributeToWord(org.jsoup.nodes.Element element, String wordID, String attribute, String value, int start, int end)
	{
		String word = element.text();

		if (end == word.length()-1 && start == 0)
		{
			element.attr("style", element.attr("style") +" " +attribute+";");
		}
		else
		{
			// Checks if the base element contains span 
			if (element.outerHtml().contains("span"))
			{
				boolean found = false;
				for (org.jsoup.nodes.Element e : element.select("span") )
				{
					
					// Checks if the desired word contains span 
					if (e.text().equals(element.text().substring(start, end+1)))
					{
						e.attr("style", e.attr("style") +" " +attribute+":"+value+";");
						found = true;
					}
				}
				
				// If the desired word is not within a span 
				if (!found)
				{
					for (int i = 0; i < element.childNodes().size(); i++) 
					{
						org.jsoup.nodes.Node child = element.childNodes().get(i);
						
						if (child instanceof org.jsoup.nodes.TextNode) 
					    {
							String text = ((org.jsoup.nodes.TextNode) child).text();
							
							if (text.equals(element.text().substring(start, end+1)))
							{
								child.remove();
								element.prependElement("span").attr("style",attribute+":"+value+";").text(text);
							}
					    }
					}
				}
			}
			else // The element had no span 
			{
				String result[] = setElementsText(element.text(), start, end);
				
				for (int i = 0; i < element.childNodes().size(); i++) 
				{
					org.jsoup.nodes.Node child = element.childNodes().get(i);
					child.remove();
				}
				
				if (!result[0].equals(""))
				{
					element.appendChild(new org.jsoup.nodes.TextNode(result[0], ""));
				}
				if (!result[1].equals(""))
				{
					element.appendElement("span").attr("style",attribute+":"+value+";").text(result[1]);
				}
				if (!result[2].equals(""))
				{
					element.appendChild(new org.jsoup.nodes.TextNode(result[2], ""));
				}
			}
		}
	}
	
	public void removeAttributeFromWord(org.jsoup.nodes.Element element, String wordID, String attribute, int start, int end)
	{
		String word = element.text();

		if (end == word.length()-1 && start == 0)
		{
			org.jsoup.nodes.Attributes styleAtt = element.attributes();

			if (!styleAtt.asList().isEmpty())
			{
				org.jsoup.nodes.Attribute a = styleAtt.asList().get(1);
			
				if (a.getKey().equals("style"))
				{
					String[] items = a.getValue().trim().split(";");
					
					String newValue = "";
					
					if (items.length == 1)
					{
						element.removeAttr("style");
					}
					
					else
					{
						for (String item: items)
						{
							if (!item.contains(attribute))
							{
								newValue = newValue.concat(item).trim();
							}
						}
						a.setValue(newValue);
					}    
				}
			}
		}
		else
		{
			for (int i = 0; i < element.select("span").size(); i++ )
			{
				org.jsoup.nodes.Element e = element.select("span").get(i);
				
				if (e.text().equals(element.text().substring(start, end+1)))
				{
					org.jsoup.nodes.Attributes styleAtt = e.attributes();

					if (!styleAtt.asList().isEmpty())
					{
						org.jsoup.nodes.Attribute a = styleAtt.asList().get(0);
						
						if (a.getKey().equals("style"))
						{
							String[] items = a.getValue().trim().split(";");
							
							String newValue = "";
							
							if (items.length == 1)
							{
								org.jsoup.nodes.Element clone = element.clone();
								e.remove();
								element.text(clone.text());
							}
							else
							{
								for (String item: items)
								{
									if (!item.contains(attribute))
									{
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
	 * Removes the highlight colour of a word by removing the CSS “mark” property and annotates property the HTML file.
	 */
	public void removeWordHighlighting(String wordID, int start, int end)
	{
		org.jsoup.nodes.Element element = doc.getElementById(wordID);
		
		removeAttributeFromWord(element, wordID, "color", start, end);
	}

	/**
	 * The method adds a tag to the word with the specified id value that determines the speed of the highlighting when it will display the word (if highlighting operation is activated) and annotates property the HTML file.
	 */
	public void setHighlightingSpeed(String wordId, double value)
	{
		
	}

	/**
	 * Returns a List object containing the decorated words of page p.
	 */
	public List<String> decoratedWordsPerPage (int p)
	{
		return null;
	}

	/**
	 * A fraction p between 0 and 1 indicating that the decorated words will be no more than d% of the words within the same page.
	 */
	public void setThreshold(double threshold)
	{
		this.threshold = threshold;
	}
	
	/**
	 * Returns a fraction between 0 and 1 indicating that only the d% of the words of a page will be decorated.
	 */
	public double getThreshold()
	{
		return threshold;
	}

	/**
	 * Filters the page such that only the d% of the words of a page will be decorated.
	 */
	public void filterPage(int p)
	{
		
	}

	/**
	 * Modifies the font face of the HTML file by applying the CSS “font-family” property.
	 */
	public void setTextFontFamily(Font font)
	{
		this.font = font;
	}
	

	/**
	 * Returns the current font family of the HTML file.
	 */
	public Font getTextFontFamily()
	{
		return this.font;
	}

	/**
	 * Modifies by v% the font size of the HTML file by applying the CSS “font-size” property and annotates property the HTML file.
	 */
	public void setTextFontSize(double dFontSize)
	{
		this.dFontSize = dFontSize;
	}

	/**
	 * Returns the current font-size value (as percentage) of the HTML file.
	 */
	public double getDoubleTextFontSize()
	{
		return this.dFontSize;
	}

	/**
	 * Sets to the font size of the HTML file, value v (in pixels) by applying the CSS “font-size” property and annotates property the HTML file.
	 */
	public void setTextFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}

	/**
	 * Returns the current font-size value (in pixels) of the HTML file.
	 * @return
	 */
	public int getTextFontSize()
	{
		return this.fontSize;
	}

	/**
	 * Modifies by v% the line spacing size of the HTML file by applying the CSS “line-height” property and annotates property the HTML file
	 */
	public void setDoubleLineSpacing(double dLineSpacing)
	{
		this.dLineSpacing = dLineSpacing;
	}

	/**
	 * Returns the current value of line spacing (as percentage) of the HTML file.
	 */
	public double getDoubleLineSpacing ()
	{
		return this.dLineSpacing;
	}

	/**
	 * Sets to the line spacing size of the HTML file value v (in pixel) by applying the CSS “line-height” property and annotates property the HTML file.
	 */
	public void setIntLineSpacing(int lineSpacing)
	{
		this.lineSpacing = lineSpacing;
	}

	/**
	 * Returns the current value of line spacing (in pixel) of the HTML file.
	 */
	public int getIntLineSpacing ()
	{
		return this.lineSpacing;
	}
	
	/**
	 * Modifies by v% the margins of the HTML file by applying the CSS “margin” property and annotates property the HTML file.
	 */
	public void setDoubleMargin(double dMargin)
	{
		this.dMargin = dMargin;
	}

	/**
	 * Returns the current value of margins (as percentage) of the HTML file.
	 */
	public double getDoubleMargin()
	{
		return this.dMargin;
	}
	
	/**
	 * Sets to the margins of the HTML file value v (in pixels) by applying the CSS “margin” property and annotates property the HTML file.
	 */
	public void setMargin(int margin)
	{
		this.margin = margin;
	}

	/**
	 * Returns the current value of margins (in pixels) of the HTML file.
	 */
	public int getMargin()
	{
		return this.margin;
	}

	/**
	 * Sets the background colour of the HTML file by applying the “background-color” property and annotates property the HTML file.
	 */
	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Returns the current background colour of the HTML file.
	 */
	public Color getBackgroundColor()
	{
		return this.backgroundColor;
	}
	
	
	/**
	 * Sets the foreground colour of the HTML file by applying the “color” property and annotates property the HTML file.
	 */
	public void setForegroundColor(Color foregroundColor)
	{
		this.foregroundColor = foregroundColor;
	}

	/**
	 * Returns the current foreground colour of the HTML file.
	 */
	public	Color getForegroundColor()
	{
		return this.foregroundColor;
	}

	/**
	 * Applies the CCS style to the HTML document
	 */
	public void updatePageStyle(String CSSProperty, String value)
	{
		
	}
	
	public void setScreenWidth(int screenWidth)
	{
		this.screenWidth = screenWidth;
	}
	
	public void setScreenHeight(int screenHeight)
	{
		this.screenHeight = screenHeight;
	}

	
	/** 
	 * Splits the word in three parts to determine the part that will be visualized independently.
	 * @param elementText
	 * @param start
	 * @param end
	 * @return
	 */
	private static String[] setElementsText(String elementText, int start, int end)
	{
		String beforeText = "";
        String middleText = "";
        String endText = "";
        
        if (start == 0 && end != elementText.length()-1)
        {
        	middleText = elementText.substring(0,end+1);
        	endText = elementText.substring(end+1, elementText.length());
        }
        else if (start != 0 && end != elementText.length()-1)
        {
        	beforeText = elementText.substring(0,start);
        	middleText = elementText.substring(start,end);
        	endText = elementText.substring(end, elementText.length());
        }
        else if (start != 0 && end == elementText.length()-1)
        {
        	beforeText = elementText.substring(0,start);
        	middleText = elementText.substring(start, end+1);
        }
		
        String [] result = new String[3];
        result[0] = beforeText;
        result[1] = middleText;
        result[2] = endText;
		
		return result;
		
	}
	
	private void setAttributeToDocument(String attribute, String value)
	{
		
	}
	
}
	


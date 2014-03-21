package ilearnrw.annotation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

public class HtmlGenerator {
	private String text;
	private String html = "";
	private UserProfile userProfile;
	private LanguageCode lc;
	
	public HtmlGenerator(String text, UserProfile userProfile, LanguageCode lc) {
		super();
		this.text = text;
		this.html = html;
		this.userProfile = userProfile;
		this.lc = lc;
		createAll();
	}
	
	private void createAll(){
		HtmlPartsGenerator tp = new HtmlPartsGenerator(text);
		String template = "";
		int pn = tp.getParagraphsNumber();
		int pId = 0, sId = 0, wId = 0;
		this.html = "";
		for (int i=0; i<pn; i++){
			this.html = this.html+"<p id = p"+(pId++)+">";
			HtmlSentence s[] = tp.getParagraph(i);
			for (int j=0;j<s.length; j++){
				this.html = this.html+"<span id = s"+(sId++)+">";
				for (int k=0; k<s[j].getNumberOfWords(); k++){
					if (s[j].isWord(k)){
						this.html = this.html+"<span id = w"+(wId++)+">";
						this.html = this.html+s[j].getWord(k);
						this.html = this.html+"</span>";
					}
					else 
						this.html = this.html+s[j].getWord(k);
					//s[j].isSymbols(k);
				}
				this.html = this.html+"</span>";
			}
			this.html = this.html+"</p>";
		}
		try {
			template = new Scanner(new File("data/html/template.html"), "UTF-8").useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.html = template.replace("???", this.html);
		//this.html = template;
	}

	public String getHtml() {
		return html;
	}
	
}

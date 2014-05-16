package ilearnrw.annotation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;

public class HtmlGenerator {
	private String text;
	private String html = "";
	private UserBasedAnnotatedWordsSet wordSet;
	private UserProfile userProfile;
	private LanguageCode lc;
	
	public HtmlGenerator(String text, UserProfile userProfile, LanguageCode lc, String templateFilename) {
		super();
		this.text = text;
		this.html = "";
		this.userProfile = userProfile;
		this.lc = lc;
		createAll(templateFilename);
	}
	
	private void createAll(String templateFilename){
		LanguageAnalyzerAPI la = GreekLanguageAnalyzer.getInstance();
		if (lc == LanguageCode.EN)
			la = EnglishLanguageAnalyzer.getInstance();
		WordVsProblems wp = new WordVsProblems(la);
		wordSet = new UserBasedAnnotatedWordsSet();
		HtmlPartsGenerator tp = new HtmlPartsGenerator(text);
		String template = "";
		int pn = tp.getParagraphsNumber();
		int pId = 0, sId = 0, wId = 0;
		this.html = "";
		for (int i=0; i<pn; i++){
			this.html = this.html+"<p id = p"+(pId++)+">";
			HtmlSentence s[] = tp.getParagraph(i);
			for (int j=0;j<s.length; j++){
				this.html = this.html+"<sen id = s"+(sId++)+">";
				for (int k=0; k<s[j].getNumberOfWords(); k++){
					if (s[j].isWord(k)){
						if (lc == LanguageCode.EN){
							UserBasedAnnotatedWord t = new UserBasedAnnotatedWord(
									new EnglishWord(s[j].getWord(k)), userProfile, wp);
							wordSet.addWord(t, wId);
						}
						else{
							UserBasedAnnotatedWord t = new UserBasedAnnotatedWord(
									new GreekWord(s[j].getWord(k)), userProfile, wp);
							wordSet.addWord(t, wId);
						}
						this.html = this.html+"<w id = w"+(wId++)+">";
						this.html = this.html+s[j].getWord(k);
						this.html = this.html+"</w>";
					}
					else 
						this.html = this.html+s[j].getWord(k);
				}
				this.html = this.html+"</sen>";
			}
			this.html = this.html+"</p>";
		}
		template = loadTemplate(templateFilename);
		this.html = template.replace("???", this.html);
		//this.html = template;
	}
	
	private String loadTemplate(String filename){
		InputStream templ;
		templ = ResourceLoader.getInstance().getInputStream(Type.DATA, filename);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(templ, "UTF-8"));
			String s, result = "";
			while ((s=br.readLine())!=null) {
	            result = result +s+"\n";
			}
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getHtml() {
		return html;
	}
	public UserBasedAnnotatedWordsSet getWordSet(){
		return wordSet;
	}
	public List<Word> getTrickyWordsList(){
		return userProfile.getUserProblems().getTrickyWords();
	}
}

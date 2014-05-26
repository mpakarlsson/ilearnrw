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
import java.util.concurrent.TimeUnit;

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
		LanguageAnalyzerAPI la  = null;
		if(lc == LanguageCode.GR)
			la = GreekLanguageAnalyzer.getInstance();
		else if (lc == LanguageCode.EN)
			la = EnglishLanguageAnalyzer.getInstance();
		WordVsProblems wp = new WordVsProblems(la);
		wordSet = new UserBasedAnnotatedWordsSet();
		HtmlPartsGenerator tp = new HtmlPartsGenerator(text);
		String template = "";
		int pn = tp.getParagraphsNumber();
		int pId = 0, sId = 0, wId = 0;
		this.html = "";
		StringBuilder builder = new StringBuilder();		
		for (int i=0; i<pn; i++){
			builder.append("<p id = p"+(pId++)+">");
			HtmlSentence s[] = tp.getParagraph(i);
			for (int j=0;j<s.length; j++){
				builder.append("<sen id = s"+(sId++)+">");
				for (int k=0; k<s[j].getNumberOfWords(); k++){
					String word = s[j].getWord(k);
					if (s[j].isWord(k)){
						if (lc == LanguageCode.EN){
							UserBasedAnnotatedWord t = new UserBasedAnnotatedWord(
									new EnglishWord(word), userProfile, wp);
							wordSet.addWord(t, wId);
						}
						else{
							UserBasedAnnotatedWord t = new UserBasedAnnotatedWord(
									new GreekWord(word), userProfile, wp);
							wordSet.addWord(t, wId);
						}
						builder.append("<w id = w"+(wId++)+">");
						builder.append(word);
						builder.append("</w>");
					}
					else 
						builder.append(word);
				}
				builder.append("</sen>");
			}
			builder.append("</p>");
		}
		
		template = loadTemplate(templateFilename);
		
		int index = template.indexOf("???");
		StringBuilder templateBuilder = new StringBuilder(template);
		templateBuilder.insert(index+3 ,builder.toString());
		templateBuilder.replace(index, index+3, "");
		this.html = templateBuilder.toString();
	}
	
	private String loadTemplate(String filename){
		InputStream templ;
		templ = ResourceLoader.getInstance().getInputStream(Type.DATA, filename);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(templ, "UTF-8"));
			String line = "";
			StringBuilder sb = new StringBuilder();
			
			while((line = br.readLine()) != null){
				sb.append(line + "\n");
			}
			br.close();
			return sb.toString();
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

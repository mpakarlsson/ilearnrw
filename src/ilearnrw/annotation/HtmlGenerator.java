package ilearnrw.annotation;

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
		TextPartsGenerator tp = new TextPartsGenerator(text);
		int pn = tp.getParagraphsNumber();
		for (int i=0; i<pn; i++){
			DetailedSentence s[] = tp.getParagraph(i);
			for (int j=0;j<s.length; j++){
				System.out.println(s[0].toString());
			}
			/*Word ws[] = sentences[i][j].getWords();
			for (int k=0;k<ws.length;k++){
				UserBasedAnnotatedWord uw = new UserBasedAnnotatedWord(ws[k]);
				if (!uniqueWords.contains(uw))
					uniqueWords.add(new UserBasedAnnotatedWord(ws[k], userProfile, wp));
			}*/
		}
	}
	
}

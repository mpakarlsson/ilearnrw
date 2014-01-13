package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.user.User;
import ilearnrw.user.profile.UserProfile;

public class Classifier {

	private UserProfile userProfile;
	private UserProblemsToText userProblemsToText;
	private Text text;
	private LanguageAnalyzerAPI languageAnalyzer;
	
	public Classifier(){
		
	}
			
	public Classifier(UserProfile userProfile, Text text, LanguageAnalyzerAPI languageAnalyzer) {
		this.userProfile = userProfile;
		this.text = text;
		this.languageAnalyzer = languageAnalyzer;
		this.userProblemsToText = new UserProblemsToText(userProfile, text, languageAnalyzer);
	}
	
	//formula :: ((top of Age range + bottom of Age Range)/2)/100 + SDW / NW + LSI
	public double getDifficulty(){
		return this.getAgeFK()/100+userProblemsToText.getSDW()/text.getNumberOfWords()+this.getLSI();
	}
	public String getDifficultyToString(){
		return ""+this.getAgeFK()+"/"+100+"+"+
				String.format("%.2f",userProblemsToText.getSDW())+"/"+text.getNumberOfWords()+
				"+"+String.format("%.2f",this.getLSI());
	}
	
	/*
	 * Flesch–Kincaid
	 * 0 - 29	Very difficult	College Graduates	5%
	 * 30 - 49	Difficult	College	30%
	 * 50 - 59	Fairly difficult	Senior High School, A-level	50%
	 * 60 - 69	Plain English	13 to 15 year-olds	80%
	 * 70 - 79	Fairly easy	12 year-olds	90%
	 * 80 - 89	Easy	11 year-olds.
	 * 90 - 100	Very easy	10 year-olds
	 */
	
	private double getAgeFK(){
		double fk = text.fleschKincaid();
		if (fk>100)
			return 0;
		else if (90 <= fk)
			return (10/2);
		else if (80 <= fk)
			return (11/2);
		else if (70 <= fk)
			return (12/2);
		else if (60 <= fk)
			return (14/2);
		else if (50 <= fk)
			return (16/2);
		else if (30 <= fk)
			return (18/2);
		else 
			return (20/2);
	}
	
	//LSI
	/*
    LSI (Length and Structure Coefficient) = LI - SC

    LI (Length index)
        * 20 words = 0
        * 100 words = 0.1
        * 500 words = 0.2
        * 1500 words = 0.5
        * 5000+ words = 1


    NW (Number of Words)
    NP (Number of Paragraphs)

    SC (Structure Coefficient) [needs to be refined by number of headings]
		= (NP / NW) * 10
    */
	
	private double getLSI(){
		return this.getLI()-this.getSC();
	}
	
	private double getLI(){
		int length = text.getNumberOfWords();
		if (length<=0)
			return 0;
		else if (length<=100)
			return 0.1;
		else if (length<=100)
			return 0.2;
		else if (length<=100)
			return 0.5;
		else 
			return 1;
	}
	
	private double getSC(){
		return ((double)text.getNumberOfParagraphs() / text.getNumberOfWords()) * 10;
	}
	
	
	/*
	SDW (Sum of Difficult Words)

    DW (difficult word - any word with any difficulty of value = 0 >= 1 reduced by RC)

[for unreliable profile]

        3 syllables or more

        Frequency of 1,500 or more

        DILP TP 80 or more 

OR

    [for reliable profile]

        Word with profile patterns not known or having problems (against severities: 0 = 0; 1 = 0.3; 2 = 0.6; 3 = 1)
        Words in individual profile marked as 'tricky'
	 */

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public UserProblemsToText getUserProblemsToText() {
		return userProblemsToText;
	}

	public void setUserProblemsToText(UserProblemsToText userProblemsToText) {
		this.userProblemsToText = userProblemsToText;
	}

	public boolean calculateProblematicWords() {
		return this.userProblemsToText.calculateProblematicWords();
	}

	public void calculateProblematicWords(boolean calculateProblematicWords) {
		this.userProblemsToText.calculateProblematicWords(calculateProblematicWords);
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUser(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public int getUserHits() {
		return this.userProblemsToText.getUserHits();
	}

	public int getDiffWords() {
		return this.userProblemsToText.getDiffWords();
	}

	public int getVeryDiffWords() {
		return this.userProblemsToText.getVeryDiffWords();
	}

}

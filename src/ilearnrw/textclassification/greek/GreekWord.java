package ilearnrw.textclassification.greek;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.speller.GreekSpeller;
import ilearnrw.textclassification.speller.Speller;
import ilearnrw.utils.LanguageCode;

public class GreekWord extends Word{
	
	public GreekWord(){
	}
	
	public GreekWord(String word){
		super(word);
		super.lc = LanguageCode.GR;
		checkType();
		syllabism();
		createCVForm();
		//System.out.println(syllables.toString());
	}

	protected void checkType(){
		//if (isVerb(word))...
		//...
		type = WordType.Unknown;
	}
	
	protected void syllabism(){
		Speller s = new GreekSpeller();
		s.setStringToSpell(word);
		s.performSpelling();
		syllables = s.getTokensArray();
		numSyllables = syllables.length;
	}
	
	protected char upperCharToCV(char x){
		switch (x){
			case 'Β':
			case 'Γ':
			case 'Δ':
			case 'Ζ':
			case 'Θ':
			case 'Κ':
			case 'Λ':
			case 'Μ':
			case 'Ν':
			case 'Ξ':
			case 'Π':
			case 'Ρ':
			case 'Σ':
			case 'Τ':
			case 'Φ':
			case 'Χ':
			case 'Ψ':
				return 'c';
			default :
				return 'v';
		}
	}
	
	protected void createCVForm(){
		cvForm = "-";
		for (int i=0;i<syllables.length;i++){
			String tmp = syllables[i].toUpperCase();
			for (int j=0;j<tmp.length();j++){
				cvForm = cvForm+upperCharToCV(tmp.charAt(j));
			}
			cvForm = cvForm+"-";
		}
	}
	
	private void createPhonetics(){
		//read the word, and calculate its phonetic transcription
	}
	
}

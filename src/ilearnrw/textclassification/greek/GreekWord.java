package ilearnrw.textclassification.greek;
import ilearnrw.languagetools.greek.GreekPhonetics;
import ilearnrw.languagetools.greek.GreekSpeller;
import ilearnrw.languagetools.greek.Speller;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.utils.LanguageCode;

public class GreekWord extends Word{
	
	public GreekWord(){
	}
	
	public GreekWord(String word){
		super(word);
		super.lc = LanguageCode.GR;
		syllabism();
		createCVForm();
		createPhonetics();
		//System.out.println(word);
	}
	
	public GreekWord(String word, WordType wt){
		super(word, wt);
		super.lc = LanguageCode.GR;
		syllabism();
		createCVForm();
		createPhonetics();
		//System.out.println(word);
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
		GreekPhonetics gp = new GreekPhonetics(word);
		int k=0;
		char ph[] = gp.getCVphonetics();
		for (String x : syllables){
			int len = x.trim().length();
			for (int i=k; i<len+k; i++){
				if (ph[i] != '*')
					cvForm += ph[i];
			}
			cvForm += "-";
			k = len+k;
		}
	}
	
	private void createPhonetics(){
		GreekPhonetics gp = new GreekPhonetics(this.word);
		phonetics = gp.getResult();
	}
	
}

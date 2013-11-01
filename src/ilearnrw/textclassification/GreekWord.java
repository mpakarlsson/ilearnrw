package ilearnrw.textclassification;
import ilearnrw.textclassification.speller.GreekSpeller;
import ilearnrw.textclassification.speller.Speller;

public class GreekWord extends Word{
	
	public GreekWord(){
	}
	
	public GreekWord(String word){
		super(word);
		checkType();
		syllabism();
		createCVForm();
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
		System.out.println(numSyllables);
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
	
	
}

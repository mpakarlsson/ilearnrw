package ilearnrw.textclassification.greek;
import java.util.ArrayList;

import ilearnrw.languagetools.greek.GreekPhonetics;
import ilearnrw.languagetools.greek.GreekSpeller;
import ilearnrw.languagetools.greek.Speller;
import ilearnrw.textclassification.GraphemePhonemePair;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.utils.LanguageCode;

public class GreekWord extends Word{
	private static final long serialVersionUID = 1L;

	public GreekWord(){
	}
	
	public GreekWord(String word){
		super(word);
		super.languageCode = LanguageCode.GR;
		syllabism();
		createCVForm();
		createPhonetics();
		createGraphemePhonemePairs();
		setStem("");
		//System.out.println(word);
	}
	
	public GreekWord(String word, WordType wt){
		super(word, wt);
		super.languageCode = LanguageCode.GR;
		syllabism();
		createCVForm();
		createPhonetics();
		createGraphemePhonemePairs();
		setStem("");
		//System.out.println(word);
	}
	
	public GreekWord(String word, WordType wt, String stem, int frequency){
		super(word, wt);
		super.languageCode = LanguageCode.GR;
		syllabism();
		createCVForm();
		createPhonetics();
		createGraphemePhonemePairs();
		setStem(stem);
		super.frequency = frequency;
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
		cvForm += "-";
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
	
	private void createGraphemePhonemePairs(){
		GreekPhonetics gp = new GreekPhonetics(this.word);
		String correspondance[] = gp.getCorrespondanceMatrix();
		graphemesPhonemes = new ArrayList<GraphemePhonemePair>();
		String gr = "", ph = "";
		int i=0;
		while (i<this.word.length()){
			gr = "" + word.charAt(i);
			ph = correspondance[i];
			while (++i<this.word.length() && correspondance[i] == "*"){
				gr = gr + word.charAt(i);
			}
			if (ph != "" && gr != "")
				graphemesPhonemes.add(new GraphemePhonemePair(gr, ph));
		}
	}
	
}

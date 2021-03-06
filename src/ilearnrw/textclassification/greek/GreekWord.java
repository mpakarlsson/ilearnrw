package ilearnrw.textclassification.greek;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.util.ArrayList;

import ilearnrw.languagetools.greek.GreekPhonetics;
import ilearnrw.languagetools.greek.GreekSpeller;
import ilearnrw.languagetools.greek.GreekSyllabification;
import ilearnrw.languagetools.greek.Speller;
import ilearnrw.languagetools.greek.WordTypeInfo;
import ilearnrw.textclassification.GraphemePhonemePair;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.utils.LanguageCode;

public class GreekWord extends Word{
	private static final long serialVersionUID = 1L;
	
	private WordTypeInfo wordTypeInfo;

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
		wordTypeInfo = new WordTypeInfo();
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
		wordTypeInfo = new WordTypeInfo();
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
		wordTypeInfo = new WordTypeInfo();
	}
	
	public GreekWord(String word, WordType wt, String stem, int frequency, String jsonWTI){
		super(word, wt);
		super.languageCode = LanguageCode.GR;
		syllabism();
		createCVForm();
		createPhonetics();
		createGraphemePhonemePairs();
		setStem(stem);
		super.frequency = frequency;
		wordTypeInfo = new WordTypeInfo(jsonWTI);
	}
	
	protected void syllabism(){
		Speller s = new GreekSyllabification();
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

	public WordTypeInfo getWordTypeInfo() {
		return wordTypeInfo;
	}

	public void setWordTypeInfo(WordTypeInfo wordTypeInfo) {
		this.wordTypeInfo = wordTypeInfo;
	}
	
}

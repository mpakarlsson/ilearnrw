package ilearnrw.languagetools.english;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.utils.LanguageCode;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
public class EnglishLanguageAnalyzer implements LanguageAnalyzerAPI{
	
	private Word word;
	private static EnglishLanguageAnalyzer instance = null;
	private EnglishDictionary dictionary 			= null;
	
	protected EnglishLanguageAnalyzer() {
		if(dictionary==null){
			dictionary = new EnglishDictionary();
			if(dictionary.getDictionary().isEmpty())
				dictionary.loadDictionary("dictionary_english.csv");
		}
	}
	
	public static EnglishLanguageAnalyzer getInstance(){
		if(instance==null) {
			instance = new EnglishLanguageAnalyzer();
		}
		
		return instance;
	}

	@Override
	public void setWord(Word w) {
		this.word = (EnglishWord)w;
		if (dictionary.getDictionary().containsKey(word.getWord())){
			word = dictionary.getDictionary().get(word.getWord());
			//word.setType((dictionary.getDictionary().get(word.getWord())).getType());
		}
		else word.setType(WordType.Unknown);
	}

	public EnglishDictionary getDictionary(){
		return dictionary;
	}
	
	@Override
	public boolean isNoun() {
		return this.word.getType() == WordType.Noun;
	}

	@Override
	public boolean isAdj() {
		return this.word.getType() == WordType.Adjective;
	}

	@Override
	public boolean isVerb() {
		return this.word.getType() == WordType.Verb;
	}

	@Override
	public boolean isParticiple() {
		return this.word.getType() == WordType.Participle;
	}

	@Override
	public LanguageCode getLanguageCode() {
		return LanguageCode.EN;
	}
	
	@Override
	public Word getWord() {
		return this.word;
	}

	@Override
	public Word getSimilarSoundWord(String phA, String phB) {
		// TODO Auto-generated method stub
		return null;
	}

}

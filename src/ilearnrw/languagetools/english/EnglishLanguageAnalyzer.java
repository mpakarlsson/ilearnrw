package ilearnrw.languagetools.english;

import java.util.HashMap;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.textclassification.Word;
import ilearnrw.utils.LanguageCode;

public class EnglishLanguageAnalyzer implements LanguageAnalyzerAPI{

	public EnglishLanguageAnalyzer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setWord(Word w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNoun() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAdj() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isVerb() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isParticiple() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public LanguageCode getLanguageCode() {
		return LanguageCode.EN;
	}

	@Override
	public HashMap<String, Integer> getUnknownWords() {
		// TODO Auto-generated method stub
		return null;
	}

}

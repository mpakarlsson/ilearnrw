package ilearnrw.languagetools;

import java.util.HashMap;

import ilearnrw.textclassification.Word;
import ilearnrw.utils.LanguageCode;

public interface LanguageAnalyzerAPI {
	public void setWord(Word w);
	public boolean isNoun();
	public boolean isAdj();
	public boolean isVerb();
	public boolean isParticiple();
	public LanguageCode getLanguageCode();
	public HashMap<String, Integer> getUnknownWords();

}

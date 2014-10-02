package ilearnrw.languagetools;

import ilearnrw.textclassification.Word;
import ilearnrw.utils.LanguageCode;

public interface LanguageAnalyzerAPI {
	public void setWord(Word w);
	public Word getWord();
	public boolean isNoun();
	public boolean isAdj();
	public boolean isVerb();
	public boolean isParticiple();
	public LanguageCode getLanguageCode();
	public Word getSimilarSoundWord(String phA, String phB);
	//public HashMap<String, Integer> getUnknownWords();

}

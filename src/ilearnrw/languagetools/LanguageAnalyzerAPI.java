package ilearnrw.languagetools;

import ilearnrw.textclassification.Word;

public interface LanguageAnalyzerAPI {
	public void setWord(Word w);
	public boolean isNoun();
	public boolean isAdj();
	public boolean isVerb();
	public boolean isParticiple();

}

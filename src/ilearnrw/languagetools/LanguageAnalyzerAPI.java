package ilearnrw.languagetools;

import ilearnrw.textclassification.Word;
import ilearnrw.utils.LanguageCode;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
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

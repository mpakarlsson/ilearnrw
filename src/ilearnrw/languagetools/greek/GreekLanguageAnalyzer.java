package ilearnrw.languagetools.greek;


import java.util.HashMap;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.textclassification.Word;
import ilearnrw.utils.LanguageCode;

public class GreekLanguageAnalyzer implements LanguageAnalyzerAPI{
	private GreekDictionary dictionary;
	private boolean isNoun, isAdj, isVerb, isParticiple;
	private HashMap<String, Integer> unknownWords;


	public GreekLanguageAnalyzer() {
		dictionary = new GreekDictionary();
		unknownWords = new HashMap<String, Integer>();
	}
	

	@Override
	public void setWord(Word w) {
		DictionaryEntry de = this.dictionary.getValue(w.toString());
		if (de == null){
			isNoun = false;
			isAdj = false;
			isVerb = false;
			isParticiple = false;
			if (w!=null && !w.toString().isEmpty())
				this.unknownWords.put(w.toString(), unknownWords.containsKey(w.toString())?
						unknownWords.get(w.toString())+1:1);
			
		}
		else{
			isNoun = de.getPartOfSpeech().equals("ουσιαστικό");
			isAdj = de.getPartOfSpeech().equals("επίθετο");
			isVerb = de.getPartOfSpeech().equals("ρήμα");
			isParticiple = de.getExtras().contains("μετοχή");
		}
		
	}

	@Override
	public HashMap<String, Integer> getUnknownWords() {
		return unknownWords;
	}


	public void setUnknownWords(HashMap<String, Integer> unknownWords) {
		this.unknownWords = unknownWords;
	}

	@Override
	public boolean isNoun() {
		return isNoun;
	}

	@Override
	public boolean isAdj() {
		return isAdj;
	}

	@Override
	public boolean isVerb() {
		return isVerb;
	}

	@Override
	public boolean isParticiple() {
		return isParticiple;
	}


	@Override
	public LanguageCode getLanguageCode() {
		return LanguageCode.GR;
	}


}

package ilearnrw.languagetools.greek;


import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.textclassification.Word;

public class GreekLanguageAnalyzer implements LanguageAnalyzerAPI{
	private GreekDictionary dictionary;
	private boolean isNoun, isAdj, isVerb, isParticiple; 
	
	public GreekLanguageAnalyzer() {
		dictionary = new GreekDictionary();
	}
	

	@Override
	public void setWord(Word w) {
		DictionaryEntry de = this.dictionary.getValue(w.toString());
		if (de == null){
			isNoun = false;
			isAdj = false;
			isVerb = false;
			isParticiple = false;
			
		}
		else{
			isNoun = de.getPartOfSpeech().equals("ουσιαστικό");
			isAdj = de.getPartOfSpeech().equals("επίθετο");
			isVerb = de.getPartOfSpeech().equals("ρήμα");
			isParticiple = de.getExtras().contains("μετοχή");
		}
		
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


}

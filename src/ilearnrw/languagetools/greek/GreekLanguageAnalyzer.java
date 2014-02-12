package ilearnrw.languagetools.greek;


import java.io.InputStream;
import java.util.HashMap;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.utils.LanguageCode;

public class GreekLanguageAnalyzer implements LanguageAnalyzerAPI{
	//private GreekDictionaryLoader dictionary;
	private GreekDictionary dictionary;
	private GreekDictionary soundsSimilarDictionary;
	private Word word;
	//private HashMap<String, Integer> unknownWords;


	public GreekLanguageAnalyzer() {
		GreekDictionaryLoader gl = new GreekDictionaryLoader();
		dictionary = new GreekDictionary(gl.getWords());
		soundsSimilarDictionary = new GreekDictionary(gl.getSimilarSoundWords());
		//dictionary = new GreekDictionaryLoader();
		//unknownWords = new HashMap<String, Integer>();
	}

	@Override
	public void setWord(Word w) {
		this.word = (GreekWord)w;
		/*DictionaryEntry de = this.dictionary.getValue(w.toString());
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
		}*/
		if (dictionary.contains(word))
			word.setType((dictionary.get(word)).getType());
		else word.setType(WordType.Unknown);
	}

	/*
	@Override
	public HashMap<String, Integer> getUnknownWords() {
		return unknownWords;
	}


	public void setUnknownWords(HashMap<String, Integer> unknownWords) {
		this.unknownWords = unknownWords;
	}*/
	
	//returns the word that has the same phonetic transcription as the this.word 
	//except the parts phA  phB which may be replaced in the same position of the two words

	@Override
	public Word getSimilarSoundWord(String phA, String phB){
		String phonems = this.word.getPhonetics();
		if (!phonems.contains(phA) && !phonems.contains(phB))
			return null;
		else {
			String target = phonems.replaceAll(phA, "*");
			target = target.replaceAll(phB, "*");
			
			for (Word x : soundsSimilarDictionary.getWords()){
				if (x.equals(this.word) || (x.getPhonetics()).equals(this.word.getPhonetics()))
					continue;
				String temp = x.getPhonetics().replaceAll(phA, "*");
				temp = temp.replaceAll(phB, "*");
				if (temp.equals(target)){
					//System.out.println(x.toString() +" -- " + this.word.toString());
					//System.out.println(temp +" -- " + target);
					return x;
				}
			}
			return null;
		}
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
		return LanguageCode.GR;
	}


}

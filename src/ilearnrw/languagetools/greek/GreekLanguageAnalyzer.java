package ilearnrw.languagetools.greek;

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
		dictionary = new GreekDictionary();
		//GreekLineByLineDictionaryLoader glld = new GreekLineByLineDictionaryLoader("greek_sound_similarity.txt");
		//soundsSimilarDictionary = new GreekDictionary(glld.getEntries());
		soundsSimilarDictionary = new GreekDictionary("similarSoundWords.txt");
	}


	public GreekLanguageAnalyzer(GreekDictionary dictionary, GreekDictionary soundsSimilarDictionary) {
		if (dictionary != null)
			this.dictionary = dictionary;
		else 
			this.dictionary = new GreekDictionary();
		if (soundsSimilarDictionary != null)
			this.soundsSimilarDictionary = soundsSimilarDictionary;
		else{
			//GreekLineByLineDictionaryLoader glld = new GreekLineByLineDictionaryLoader("greek_sound_similarity.txt");
			//this.soundsSimilarDictionary = new GreekDictionary(glld.getEntries());
			soundsSimilarDictionary = new GreekDictionary("similarSoundWords.txt");
		}
	}

	@Override
	public void setWord(Word w) {
		this.word = (GreekWord)w;
		if (dictionary.contains(word))
			word.setType((dictionary.get(word)).getType());
		else word.setType(WordType.Unknown);
	}
	
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
				if (temp.length() != target.length())
					continue;
				int dist = 0;
				for (int i=0;i<temp.length(); i++){
					if (temp.charAt(i) != target.charAt(i))
						dist++;
				}
				//if (temp.equals(target)){
				if (dist<1){
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
	public Word getWord() {
		return this.word;
	}

	@Override
	public LanguageCode getLanguageCode() {
		return LanguageCode.GR;
	}


}

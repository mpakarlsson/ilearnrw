package ilearnrw.languagetools.greek;

import java.util.ArrayList;
import java.util.HashMap;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.utils.LanguageCode;

public class GreekLanguageAnalyzer implements LanguageAnalyzerAPI{
	//private GreekDictionaryLoader dictionary;
	private GreekDictionary dictionary;
	private GreekSoundDictionary soundsSimilarDictionary;
	private Word word;
	private static GreekLanguageAnalyzer instance = null;
	//public static HashMap<String, ArrayList<String>> collection = new HashMap<String, ArrayList<String>>();


	protected GreekLanguageAnalyzer() {
		dictionary = GreekDictionary.getInstance();
		soundsSimilarDictionary = null;//GreekSoundDictionary.getInstance();
	}
	
	public static GreekLanguageAnalyzer getInstance(){
		if(instance==null)
			instance = new GreekLanguageAnalyzer();
		
		return instance;
	}

	@Override
	public void setWord(Word w) {
		this.word = (GreekWord)w;
		GreekWord gr = dictionary.getWord(word.toString());
		if (gr != null){
			word.setType(gr.getType());
			word.setFrequency(gr.getFrequency());
			word.setStem(gr.getStem());
		}
		else word.setType(WordType.Unknown);
	}

	public GreekDictionary getDictionary(){
		return dictionary;
	}
	
	@Override
	public Word getSimilarSoundWord(String phA, String phB){
		String phonems = this.word.getPhonetics();
		if (!phonems.contains(phA) && !phonems.contains(phB))
			return null;
		else {
			String target = phonems;
			target = target.replaceAll(phA, "*");
			target = target.replaceAll(phB, "*");
			
			if (soundsSimilarDictionary.getCollection().containsKey(this.word.getWord())){
				for (String x : soundsSimilarDictionary.getCollection().get(this.word.getWord())){
					GreekWord w = new GreekWord(x);
					if (w.getWord().equalsIgnoreCase(this.word.getWord()))
						continue;
					String temp = w.getPhonetics();
					temp = temp.replaceAll(phA, "*");
					temp = temp.replaceAll(phB, "*");
					if (temp.length() != target.length())
						continue;
					int altDist = 0;
					for (int i=0;i<phonems.length() && i<w.getPhonetics().length(); i++){
						if (phonems.charAt(i) != w.getPhonetics().charAt(i))
							altDist++;
					}
					int dist = 0;
					for (int i=0;i<temp.length() && i<target.length(); i++){
						if (temp.charAt(i) != target.charAt(i))
							dist++;
					}
					if (altDist > dist && (dist < 3 && this.word.getLength()>3 || dist < 2 && this.word.getLength()>2)){
						return w;
					}
				}
			}
		}
		return null;
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

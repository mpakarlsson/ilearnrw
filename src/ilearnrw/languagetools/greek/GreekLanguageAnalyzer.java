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
		soundsSimilarDictionary = GreekSoundDictionary.getInstance();
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
			
			for (Word x : soundsSimilarDictionary.getWords()){
				if (x.getWord().equalsIgnoreCase(this.word.getWord()))
					continue;
				String temp = x.getPhonetics();
				temp = temp.replaceAll(phA, "*");
				temp = temp.replaceAll(phB, "*");
				if (temp.length() != target.length())
					continue;
				int altDist = 0;
				for (int i=0;i<phonems.length() && i<x.getPhonetics().length(); i++){
					if (phonems.charAt(i) != x.getPhonetics().charAt(i))
						altDist++;
				}
				int dist = 0;
				for (int i=0;i<temp.length() && i<target.length(); i++){
					if (temp.charAt(i) != target.charAt(i))
						dist++;
				}
				//if (temp.equals(target)){
				//ArrayList<String> p = new ArrayList<String>();
				if (altDist > dist && (dist < 3 && this.word.getLength()>3 || dist < 2 && this.word.getLength()>2)){
					//p.add(x.getWord());
					/*if (this.word.getFrequency()>0 && !test.contains(this.word.getWord()+" "+this.word.getType()+" "+
							this.word.getStem()+" "+this.word.getFrequency())){
						test.add(this.word.getWord()+" "+this.word.getType()+" "+
							this.word.getStem()+" "+this.word.getFrequency());
					}
					if (x.getFrequency()>0 && !test.contains(x.getWord()+" "+x.getType()+" "+x.getStem()+" "+x.getFrequency())){
						test.add(x.getWord()+" "+x.getType()+" "+x.getStem()+" "+x.getFrequency());
					}*/
					return x;
				}
				/*if (p.size()>0){
					if (collection.containsKey(this.word.getWord())){
						ArrayList<String> k = collection.get(this.word.getWord());
						if (!k.contains(x.getWord())){
							k.add(x.getWord());
							collection.put(this.word.getWord(), k);
						}
					}
					else{
						ArrayList<String> k = new ArrayList<String>();
						k.add(x.getWord());
						collection.put(this.word.getWord(), k);
					}
				}*/
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

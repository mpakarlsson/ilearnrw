package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class StringMatchesInfo {

	private LanguageAnalyzerAPI languageAnalyser;
	
	private String what;
	private int start, end;
	
	public StringMatchesInfo(LanguageAnalyzerAPI languageAnalyser) {
		this.languageAnalyser = languageAnalyser;
		what = null;
		start = -1;
		end = -1;
	}

	public StringMatchesInfo(String what, int start, int end) {
		this.what = what;
		this.start = start;
		this.end = end;
	}

	public boolean isMatched(){
		return what != null && !what.isEmpty();
	}
	
	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "StringMatchesInfo [what=" + what + ", start=" + start
				+ ", end=" + end + "]";
	}

	
	public StringMatchesInfo equals(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.equals(str[i])){
				return new StringMatchesInfo(str[i], 0, ws.length());
			}
		}
		return null;
	}
	
	public StringMatchesInfo contains(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}
	
	public StringMatchesInfo containsLettersOnConsequtiveSyllables(String str[], Word w){
		String syl[] = w.getSyllables();
		for (int i=0;i<str.length;i++){
			String fp = ""+str[i].charAt(0);
			String sp = str[i].substring(1);
			int start = syl[0].length();
			for (int j=0;j<syl.length-1;j++){
				if (syl[j].endsWith(fp) && syl[j+1].startsWith(sp)){
					return new StringMatchesInfo(str[i], start-1, start-1+sp.length());
				}
				start += syl[j].length();
			}
		}
		return null;
	}
	
	public StringMatchesInfo containsLettersOnSameSyllable(String str[], Word w){
		String ws = w.getWord();
		String syl[] = w.getSyllables();
		for (int i=0;i<str.length;i++){
			int start = syl[0].length();
			for (int j=0;j<syl.length-1;j++){
				if (syl[j].contains(str[i])){
					return new StringMatchesInfo(str[i], 
							ws.indexOf(str[i], start-1), ws.indexOf(str[i], start-1)+str[i].length());
				}
				start += syl[j].length();
			}
		}
		return null;
	}
	
	public StringMatchesInfo endsWith(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.endsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.lastIndexOf(str[i]), ws.lastIndexOf(str[i])+str[i].length());
			}
		}
		return null;
	}
	//or
	public StringMatchesInfo startsWith(String str[], Word w){
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo containsPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo hasInternalPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i]) && !ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo startsWithPhoneme(String str[], Word w){
		String ws = w.getWordInToPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo containsPattern(String str[], Word w){
		String ws = w.getCVForm();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		return null;
	}

	public StringMatchesInfo containsPatternOrEndsWithExtraConsonant(String str[], Word w){
		String ws = w.getCVForm();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				return new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length());
			}
		}
		for (int i=0;i<str.length;i++){
			String newStr = "";
			for (int k=0; k<str[i].length()-1;k++)
				newStr = newStr +str[i].charAt(k);
			newStr = newStr + "c-";
			if (ws.endsWith(newStr)){
				return new StringMatchesInfo(newStr, ws.lastIndexOf(newStr), ws.lastIndexOf(newStr)+newStr.length());
			}
		}
		return null;
	}

	public StringMatchesInfo soundSimilarity(String str[], Word w){
		languageAnalyser.setWord(w);
		for (int i=0;i<str.length;i++){
			String[] parts = str[i].split("-");
			GreekWord r = (GreekWord)languageAnalyser.getSimilarSoundWord(parts[0], parts[1]);
			if (r!=null){
				return new StringMatchesInfo(str[i], 0, w.toString().length());
			}
		}
		return null;
	}
	
	
	public StringMatchesInfo endsWithSuffix(String str[], Word w){
		String phonetics = w.getPhonetics();
		
		for(String s : str){
			int pos = s.indexOf("-");
			if(pos != -1){
				String[] values = s.split("-");
				
				// TODO: fix this temporary solution -> if no phonetics but word ends with suffix -> difficult word
				if(phonetics == null){
					if(values[0].endsWith(s))
						return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
				} else { 
					if(values[0].endsWith(s) && phonetics.endsWith(values[1]))
						return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
				}
			} else {
				
				if(w.getWord().endsWith(s))
					return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
			}
			
		}
		return null;
	}
	
	public StringMatchesInfo startsWithPrefix(String str[], Word w){
		for(String s : str){
			if(w.getWord().startsWith(s))
				return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
		}
		return null;
	}
	
	public StringMatchesInfo equalsPhoneme(String str[], Word w, String type){
		String ipa = w.getPhonetics();
		
		// TODO: Fix -> ignore phonetics if word is not in the dictionary
		if(ipa==null)
			for(String s : str)
				if(w.getWord().contains(s.split("-")[0]))
					return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
				else 
					return null;
		
	
		for(String s : str){
			String[] values = s.split("-");
			String difficulty = values[0];
			String transcription = values[1];
			
			
			if(w.getWord().contains(difficulty) && w.getPhonetics().contains(transcription)){
				//boolean isValid = checkEqualsPhoneme(w.getWord(), w.getPhonetics(), difficulty, transcription);
				return new StringMatchesInfo(difficulty, w.getWord().indexOf(difficulty), w.getWord().indexOf(difficulty)+difficulty.length());
			}
		}
		// type = letter, digraph, trigraph
		
		return null;
	}
	
	public StringMatchesInfo syllableCount(String str[], Word w){
		if(w.getSyllables().length>=3)
			return new StringMatchesInfo(w.getWord(), 0, w.getWord().length());
		return null;
	}
	
	public StringMatchesInfo patternEqualsPronunciation(String str[], Word w, String type){
		//_C_ _V_
		
		String ipa = w.getPhonetics();
		
		for(String s: str){
			String[] sarr = s.split("-");
			if(sarr.length==1)
				return null;
		}
		
		// TODO: Fix -> ignore phonetics if word is not in the dictionary
		if(ipa==null)
			for(String s : str)
				if(w.getWord().contains(s.split("-")[0]))
					return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
				else 
					return null;
		
		for(String s : str){
			String[] values = s.split("-");
			String difficulty = values[0];
			String transcription = values[1];
			
			if(type.equals("contains")){
				if(w.getWord().contains(difficulty) && w.getPhonetics().contains(transcription))
					return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
			} else if(type.equals("ends")){
				if(w.getWord().endsWith(difficulty) && w.getPhonetics().endsWith(transcription))
					return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
			} else if(type.equals("start")){
				if(w.getWord().startsWith(difficulty) && w.getPhonetics().startsWith(transcription))
					return new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length());
			} 
		}
		return null;
	}
	
	private boolean checkEqualsPhoneme(String word, String phonetics, String difficulty, String phoneme){
		
	
		// Remove syllables dividers, primary and secondary stress.
		String tempPhon = phonetics.replace(".", "").replace("\u02C8", "").replace("\u02CC", "").trim();
		Map<String, ArrayList<String>> ssl = Program.getSoundToSpellingList();
		ArrayList<ArrayList<String>> soundOptions = new ArrayList<ArrayList<String>>();

		for(int i=0; i<tempPhon.length(); i++){
			String cP = Character.toString(tempPhon.charAt(i));
			
			ArrayList<String> soundMeanings = ssl.get(cP);
			if(soundMeanings==null || soundMeanings.isEmpty()){
				soundOptions.add(new ArrayList<String>());
				continue;
			}
			
			for(String s : soundMeanings){
				if(!word.contains(s))
					soundMeanings.remove(s);
			}
			soundOptions.add(soundMeanings);
		}
		
		int availableSize = word.length();
		int prevTaken = -1;
		String buildWord = "";
		int buildPos = 0;
		int numberOfChars = 4;
		for(int i=0; i<soundOptions.size(); i++){
			ArrayList<String> options = soundOptions.get(i);
			
			int longest = 0;
			String value = "";
			for(int k=0; k<options.size(); k++){
				String temp = options.get(k);
				if(temp.length()>longest){
					prevTaken = k;
					longest = temp.length();
					value = temp;
				}
			}
			
			boolean isEmpty = false;
			if(value.isEmpty()){
				if(numberOfChars+buildPos > word.length())
					numberOfChars = word.length()- buildPos;
				
				isEmpty = true;
				value = word.substring(buildPos, buildPos + numberOfChars);
			}
			
			int j=0;
			boolean isRemoved = false;
			while((j=(word.indexOf(value, j)+1)) > 0){
				if(j-1 > buildPos && buildPos+1-1 != j){
					String subitem = word.substring(buildPos, j);
					buildWord = buildWord.concat(value);
					buildPos = buildWord.length();
				} else {
					int test = availableSize - value.length() - soundOptions.size() + i;
					
					
					//if(availableSize-(word.length()+j-1)-(options.size() ) >=0){
					if(availableSize - value.length() + j - 1 - soundOptions.size()+j >= 0){
						buildWord = buildWord.concat(value);
						buildPos = buildWord.length();
						availableSize -= value.length();
					} else {

						if(!isEmpty){
							options.remove(prevTaken);
							prevTaken = -1;
							soundOptions.set(i, options);
						}
						
						if(isEmpty)
							numberOfChars--;
						
						i--;
						isRemoved=true;
						continue;
					}
					
				}
			}
			if(isRemoved) continue;
			
			
		}
		
		
//			if(soundMeanings==null || soundMeanings.isEmpty()){
//				buildWord = buildWord.concat("*");
//				continue;
//			}
			/*
				if(!word.substring(buildPos, buildPos + cP.length()).equals(cP)){
					if(buildWord.substring(buildWord.length()-cP.length(), buildWord.length()).equals(cP)){
						int lastOption = soundOptions.size()-1;
						ArrayList<String> data = soundOptions.get(lastOption);
						String invalid = data.remove(prevTaken);
						
						int longest = 0;
						String value = "";
						for(int j=0; j<data.size(); j++){
							String temp = data.get(j);
							if(temp.length()>longest){
								prevTaken = j;
								longest = temp.length();
								value = temp;
							}
						}
						
						buildWord = buildWord.substring(0, buildWord.length() - invalid.length());
						buildWord = buildWord.concat(value);
						buildPos = buildWord.length();
						i--;
						
						soundOptions.set(lastOption, data);
					}
				} else {
					buildWord = buildWord.concat("*");
					buildPos = buildWord.length();
				}
				continue;
				*/

			
//			
//			int longest = 0;
//			String value = "";
//			for(int j=0; j<soundMeanings.size(); j++){
//				String temp = soundMeanings.get(j);
//				if(temp.length()>longest){
//					prevTaken = j;
//					longest = temp.length();
//					value = temp;
//				}
//			}
//				
//				
//				
//			
//			
//
//			if(!word.substring(buildPos, buildPos + value.length()).equals(value)){
//				int j=0;
//				while((j=(word.indexOf(value, j)+1)) > 0){
//					if(j>buildPos){
//						buildWord = buildWord.concat(word.substring(buildPos, j-1)).concat(value);
//						buildPos = buildWord.length();
//					}
//				}
//			} else {
//				buildWord = buildWord.concat(value);
//				buildPos = buildWord.length();
//			}
//		}
//		
		return false;
	}
	
	
	
	private char charToCV(char x){
		// y = consonant at beginning of syllable, vowel everywhere else
        switch (x){
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                case 'y':
                    return 'v';
                case '-':
                	return '-';
                default :                        	
                    return 'c';
        }
    }

}

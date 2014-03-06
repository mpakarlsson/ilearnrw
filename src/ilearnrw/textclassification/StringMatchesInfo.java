package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.textclassification.greek.GreekWord;
import java.util.ArrayList;
import java.util.Iterator;

public class StringMatchesInfo {

	private static LanguageAnalyzerAPI languageAnalyser;
	
	private String matchedPart;
	private int start, end;
	
	public StringMatchesInfo(LanguageAnalyzerAPI la) {
		languageAnalyser = la;
		matchedPart = null;
		start = -1;
		end = -1;
	}

	public StringMatchesInfo(String what, int start, int end) {
		this.matchedPart = what;
		this.start = start;
		this.end = end;
	}

	public boolean isMatched(){
		return matchedPart != null && !matchedPart.isEmpty();
	}
	
	public String getMatchedPart() {
		return matchedPart;
	}

	public void setMatchedPart(String what) {
		this.matchedPart = what;
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
		return "StringMatchesInfo [what=" + matchedPart + ", start=" + start
				+ ", end=" + end + "]";
	}

	
	public static ArrayList<StringMatchesInfo> equals(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.equals(str[i])){
			    result.add(new StringMatchesInfo(str[i], 0, ws.length()));
				return result;
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> contains(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> visualSimilarity(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWordUnmodified();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> containsLettersOnConsequtiveSyllables(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String syl[] = w.getSyllables();
		for (int i=0;i<str.length;i++){
			String fp = ""+str[i].charAt(0);
			String sp = str[i].substring(1);
			int start = syl[0].length();
			for (int j=0;j<syl.length-1;j++){
				if (syl[j].endsWith(fp) && syl[j+1].startsWith(sp)){
				    result.add(new StringMatchesInfo(str[i], start-1, start-1+sp.length()));
					return result;
				}
				start += syl[j].length();
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> containsLettersOnSameSyllable(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		String syl[] = w.getSyllables();
		for (int i=0;i<str.length;i++){
			int start = syl[0].length();
			for (int j=0;j<syl.length-1;j++){
				if (syl[j].contains(str[i])){
				    result.add(new StringMatchesInfo(str[i], 
						ws.indexOf(str[i], start-1), ws.indexOf(str[i], start-1)+str[i].length()));
					return result;
				}
				start += syl[j].length();
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> endsWith(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.endsWith(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.lastIndexOf(str[i]), ws.lastIndexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}
	//or
	public static ArrayList<StringMatchesInfo> startsWith(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}

	public static ArrayList<StringMatchesInfo> containsPhoneme(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getPhonetics();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}

	public static ArrayList<StringMatchesInfo> hasInternalPhoneme(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getPhonetics();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i]) && !ws.startsWith(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}

	public static ArrayList<StringMatchesInfo> startsWithPhoneme(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getPhonetics();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}

	public static ArrayList<StringMatchesInfo> containsPattern(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getCVForm();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}

	public static ArrayList<StringMatchesInfo> containsPatternOrEndsWithExtraConsonant(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getCVForm();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		for (int i=0;i<str.length;i++){
			String newStr = "";
			for (int k=0; k<str[i].length()-1;k++)
				newStr = newStr +str[i].charAt(k);
			newStr = newStr + "c-";
			if (ws.endsWith(newStr)){
			    result.add(new StringMatchesInfo(newStr, ws.lastIndexOf(newStr), ws.lastIndexOf(newStr)+newStr.length()));
				return result;
			}
		}
		return null;
	}

	public static ArrayList<StringMatchesInfo> soundSimilarity(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		languageAnalyser.setWord(w);
		for (int i=0;i<str.length;i++){
			String[] parts = str[i].split("-");
			GreekWord r = (GreekWord)languageAnalyser.getSimilarSoundWord(parts[0], parts[1]);
			if (r!=null){
			    result.add(new StringMatchesInfo(str[i], 0, w.toString().length()));
				return result;
			}
		}
		return null;
	}
	
	
	public static ArrayList<StringMatchesInfo> endsWithSuffix(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String phonetics = w.getPhonetics();

		
		for(String s : str){
			int pos = s.indexOf("-");
			
			
			if(pos != -1){
				String[] values = s.split("-");
				
				// TODO: fix this temporary solution -> if no phonetics but word ends with suffix -> difficult word
				if(phonetics == null){
					if(w.getWord().endsWith(values[0])){
						result.add(new StringMatchesInfo(s, w.getWord().indexOf(values[0]), w.getWord().lastIndexOf(values[0])+values[0].length()));
						return result;
					}
				} else {
					String tempPhon = w.getPhonetics();
					// remove stress, syllable dividers and vertical lines
					tempPhon = tempPhon.replace(".", "").replace("\u02C8", "").replace("\u02CC", "").replace("\u0329", "").replace("\u0027", "");
					
					if(w.getWord().endsWith(values[0]) && tempPhon.endsWith(values[1])){
						result.add(new StringMatchesInfo(s, w.getWord().indexOf(values[0]), w.getWord().lastIndexOf(values[0])+values[0].length()));
						return result;
					}
				}
			} else {
				if(w.getWord().endsWith(s)){
					result.add(new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length()));
					return result;
				}
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> startsWithPrefix(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		for(String s : str){
			if(w.getWord().startsWith(s)){
			    result.add(new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length()));
				return result;
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> equalsPhoneme(String str[], Word w, String type){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ipa = w.getPhonetics();
		
		// TODO: Fix -> ignore phonetics if word is not in the dictionary
		if(ipa==null)
			for(String s : str)
				if(w.getWord().contains(s.split("-")[0])){
				    result.add(new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length()));
				    return result;
				}
				else 
					return null;
		
	
		for(String s : str){
			String[] values = s.split("-");
			String difficulty = values[0];
			String transcription = values[1];
			
			String tempPhon = w.getPhonetics();
			// remove stress, syllable dividers and vertical lines
			tempPhon = tempPhon.replace(".", "").replace("\u02C8", "").replace("\u02CC", "").replace("\u0329", "").replace("\u0027", "");
			
			if(w.getWord().contains(difficulty) && tempPhon.contains(transcription)){			
				int pos = wordContainsGraphemePhoneme(w, difficulty, transcription);
				
				if(pos != -1){
					result.add(new StringMatchesInfo(difficulty, pos, pos+difficulty.length()));
					return result;
				}
			}
		}
		
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> syllableCount(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		if(w.getSyllables().length>=3){
		    result.add(new StringMatchesInfo(w.getWord(), 0, w.getWord().length()));
		    return result;
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> patternEqualsPronunciation(String str[], Word w, String type){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		//_C_ _V_
		
		String ipa = w.getPhonetics();
		
		// Problem doesn't contain an IPA? 
		// al_C_ special case
		for(String s: str){
			String[] sarr = s.split("-");
			if(sarr.length==1){
				String word = w.getWord();
		
				String shortStr = s.substring(0,s.indexOf("_C_"));				
				
				if(word.contains(shortStr)){
					int ind = -1;
					
					while((ind=(word.indexOf(shortStr, ind)+1)) > 0){
						if(ind+shortStr.length()-1<word.length())
							if(!isVowel(word.charAt(ind+shortStr.length()-1))){
								result.add(new StringMatchesInfo(s, word.indexOf(shortStr), ind+shortStr.length()));
							}
					}
					
					if(result.size()>0)
						return result;
					
				}
				return null;
			}
		}

		// TODO: Fix -> ignore phonetics if word is not in the dictionary
		if(ipa==null)
			for(String s : str)
				if(w.getWord().contains(s.split("-")[0])){
				    result.add(new StringMatchesInfo(s, w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length()));
					return result;
				}
				else 
					return null;		
		
		for(String s : str){
			String[] values = s.split("-");
			String difficulty = values[0];
			String transcription = values[1];
			
			if(type.equals("contains")){
				int pos = wordContainsGraphemePhoneme(w, difficulty, transcription);
				if(pos != -1){
					if(difficulty.contains("_C_") || difficulty.contains("_V_")){
						result.add(new StringMatchesInfo(s, pos, pos+difficulty.length()-2));
						return result;					
					}
					result.add(new StringMatchesInfo(s, pos, pos+difficulty.length()));
					return result;
				}
			} else if(type.equals("ends")){
				String tempPhon = w.getPhonetics();
				// remove stress, syllable dividers and vertical lines
				tempPhon = tempPhon.replace(".", "").replace("\u02C8", "").replace("\u02CC", "").replace("\u0329", "").replace("\u0027", "");
				
				if(tempPhon.endsWith(transcription)){
					String sVal = "";
					if(difficulty.contains("_C_")){
						sVal = difficulty.substring(3);
						if(w.getWord().endsWith(sVal) && !isVowel(w.getWord().charAt(w.getWord().length()-sVal.length()-1))){
							result.add(new StringMatchesInfo(s, w.getWord().length()-sVal.length()-1, w.getWord().length()));
							return result;
						} else 
							return null;
						
						
					}else if(difficulty.contains("_V_")){
						sVal = difficulty.substring(3);
						if(w.getWord().endsWith(sVal) && isVowel(w.getWord().charAt(w.getWord().length()-sVal.length()-1))){
							result.add(new StringMatchesInfo(s, w.getWord().length()-sVal.length()-1, w.getWord().length()));
							return result;
						} else 
							return null;
					}
					
					result.add(new StringMatchesInfo(s, w.getWord().lastIndexOf(values[0]), w.getWord().lastIndexOf(values[0])+difficulty.length()));
					return result;
				}
			} else if(type.equals("start")){
				String tempPhon = w.getPhonetics();
				// remove stress, syllable dividers and vertical lines
				tempPhon = tempPhon.replace(".", "").replace("\u02C8", "").replace("\u02CC", "").replace("\u0329", "").replace("\u0027", "");
				
				if(w.getWord().startsWith(difficulty) && tempPhon.startsWith(transcription)){
					result.add(new StringMatchesInfo(s, w.getWord().indexOf(values[0]), w.getWord().indexOf(values[0])+difficulty.length()));
					return result;
				}
			} 
		}
		return null;
	}
	
	private static int wordContainsGraphemePhoneme(Word word, String difficulty, String ipa){
		ArrayList<GraphemePhonemePair> gpList = word.getGraphemesPhonemes();
		
		if(gpList == null)
			return -1;

		String value = "";
		int specialDiff = 0;
		
		// If character contains unknown consonant or vowel character
		if(difficulty.contains("_C_")){
			specialDiff = 1;
			value = difficulty.replace("_C_", ":");
		}
		else if(difficulty.contains("_V_")){
			specialDiff = 2;
			value = difficulty.replace("_V_", ":");
		} else
			value = difficulty;
		
		// take out firstChar in the problem pattern
		String firstChar = Character.toString(value.charAt(0));		
		int retPos = 0;
		
		// iterate over grapheme-phoneme list and try to find the pattern start
		for (int i = 0; i < gpList.size(); i++) {
			String grapheme = gpList.get(i).getGrapheme();
			String phoneme = gpList.get(i).getPhoneme();
			
			boolean firstVowelSkip = false;
			
			if(firstChar.equals(":")){
				if(isVowel(grapheme.charAt(0)))
					firstVowelSkip = true;
			}
			
			if(firstVowelSkip || (grapheme.startsWith(firstChar))){
				int cnt = 0, rememberCnt = -1;
				String bGr = firstChar, bPh = phoneme;
				int patternPos = 1;
				boolean isWrong = false;
				String secondValue = "";
				
				// found pattern start, iterate over 
				while(i+cnt<gpList.size()){
					String gr = gpList.get(i+cnt).getGrapheme();
					int j = cnt==0 ? 1 : 0;
					
					if(cnt!=0)
						bPh += gpList.get(i+cnt).getPhoneme();
					
					
					
					if(gr.contains(".")){
						secondValue = gr.substring(gr.indexOf(".")+1);
						gr = gr.substring(0, gr.indexOf("."));
						rememberCnt = cnt + 1;
					}
					
					// iterate over grapheme characters to continue looking for pattern
					for(;j<gr.length();j++){
						String c = Character.toString(gr.charAt(j));
						
						if(patternPos>=value.length()){
							isWrong = true;
							break;
						}
						
						boolean isConsonant = true;
						Character chAtPP = value.charAt(patternPos);
						if(chAtPP==':'){
							if(specialDiff==1){
								if(isVowel(gr.charAt(j)))
									isConsonant = false;
							}
						}

						if(c.equals(Character.toString(chAtPP)) || (specialDiff==1 && isConsonant && chAtPP == ':') || (specialDiff==2 && !isConsonant && chAtPP == ':')) {
							bGr += gr.charAt(j);
							patternPos++;
							
						} else{
							isWrong = true;
							break;
						}
					}
					
					if(!secondValue.isEmpty() && rememberCnt != -1 && cnt != rememberCnt-1 && !isWrong){
						bGr += secondValue;
						rememberCnt = -1;
						secondValue = "";
					}
					
					if(isWrong) break;

					if(value.contains(":") && secondValue.isEmpty() && bPh.startsWith(ipa)){
						int ind = value.indexOf(":");
						
						if(ind >= bGr.length()){
							isWrong = true;
							break;
						}
						
						if(specialDiff == 1){
							if(!isVowel(bGr.charAt(ind))){
								String tempValue = value.replace(':', bGr.charAt(ind));
								if(tempValue.equals(bGr))
									return retPos;
							}
						}
					}
					else if(value.equals(bGr) && secondValue.isEmpty() && ipa.startsWith(bPh))
						return retPos;
					
					cnt++;
				}
			}
			
			retPos += grapheme.contains(".") ? grapheme.length()-1 : grapheme.length();
		}
		
		return -1;
	}	

	
	private static boolean isVowel(char c){
		return "AEIOUYaeiouy".indexOf(c) != -1;
	}

}

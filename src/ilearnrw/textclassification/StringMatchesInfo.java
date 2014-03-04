package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.greek.GreekWord;
import java.util.ArrayList;
import java.util.Map;

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
					if(w.getWord().endsWith(values[0]) && w.getPhonetics().endsWith(values[1])){
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
			
			if(w.getWord().contains(difficulty) && w.getPhonetics().contains(transcription)){			
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
		
		for(String s: str){
			String[] sarr = s.split("-");
			if(sarr.length==1)
				return null;
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
				if(w.getPhonetics().endsWith(transcription)){
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
				if(w.getWord().startsWith(difficulty) && w.getPhonetics().startsWith(transcription)){
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
		
		String bW = "", bP = "", takenCharacters = "";
		String charactersLeft = word.getWord();
		String[] values = {"", ""};
		
		ArrayList<String> dotList = new ArrayList<String>();
		int pos = 0, tempPos = 0;
		
		ArrayList<String> dInfo = new ArrayList<String>();
		if(difficulty.contains("_C_"))
			findDifficulty(word.getWord(), difficulty, dInfo);

		int wPos = 0;
		for(int i=0; i<gpList.size(); i++){
			GraphemePhonemePair pair = gpList.get(i);
			String grapheme = pair.getGrapheme();
			String phoneme = pair.getPhoneme();
			

			if(pair.getGrapheme().contains(".")){
				values = pair.getGrapheme().split("\\.");
				dotList.add(grapheme);
				dotList.add(phoneme);
			} else {
				values[0] = pair.getGrapheme();
				
				if(!dotList.isEmpty()){
					dotList.add(grapheme);
					dotList.add(phoneme);
				}
			}
			
			bW += values[0];
			bP += pair.getPhoneme();
			
			if(!dInfo.isEmpty()){
				int dPos = Integer.parseInt(dInfo.get(1));
				if(wPos == dPos){
					if(phoneme.equals(ipa)){
						return dPos;
					}
				}
			}
			
			if(!difficulty.startsWith(bW) || !ipa.startsWith(bP)){
				bW = "";
				bP = "";
				pos += values[0].length() + tempPos;
				tempPos = 0;
			} else 
				tempPos += values[0].length();
			
			if(bW.equals(difficulty) && bP.equals(ipa))
				return pos;
			
			takenCharacters = charactersLeft.substring(0, charactersLeft.indexOf(values[0])+values[0].length());
			int index = takenCharacters.indexOf(values[0]);
			takenCharacters = index==0 ? takenCharacters.substring(values[0].length()) : takenCharacters.substring(0, takenCharacters.indexOf(values[0]));
			
			// Handling dot values (a.e)
			if(!values[1].isEmpty() && takenCharacters.contains(values[1])){
				String graphStr = "", phonStr = "";
				for(int j=0; j<dotList.size()-2; j+=2){
					graphStr += j==0 ? dotList.get(j).substring(0, dotList.get(j).indexOf(".")) : dotList.get(j);
					phonStr += dotList.get(j+1);
				}
				
				graphStr += values[1];
				
				if(graphStr.equals(difficulty) && phonStr.equals(ipa)){
					pos = pos -  graphStr.length();
					return pos;
				}
				
				values[1] = "";
				dotList.clear();
			}
			charactersLeft = charactersLeft.substring(charactersLeft.indexOf(values[0])+values[0].length());
			wPos += values[0].length();
		}

		return -1;
	}
	
	private static void findDifficulty(String word, String difficulty, ArrayList<String> dInfo){
		String[] difficultValues = {"", ""};
		String tempCharsLeft = word;
		int index1 = 0, index2= 0;
		
		difficultValues = difficulty.split("_C_",-1);

		while(index1!=-1){
			if(difficultValues[0].isEmpty() && difficultValues[1].isEmpty())
				break;
			
			index1 = tempCharsLeft.indexOf(difficultValues[0]);
			if(index1==-1) break;
			
			tempCharsLeft = tempCharsLeft.substring(index1+1);
			
			index2 = tempCharsLeft.indexOf(difficultValues[1]);
			if(index2==-1) break;
			
			int cnt = 0;
			for(Character c : tempCharsLeft.toCharArray()){
				boolean isVowel = isVowel(c);
				
				if(isVowel && !Character.toString(c).equals(difficultValues[1])){
					tempCharsLeft = tempCharsLeft.substring(cnt+1);
					break;
				} else if(isVowel && Character.toString(c).equals(difficultValues[1])){
					
					int wPos = word.length();
					wPos = wPos - tempCharsLeft.length() - 1;
					dInfo.add(word.substring(wPos));
					dInfo.add(Integer.toString(wPos));
					return;
				}
				cnt++;
			}
		}
		
		return;
	}
	
	private static boolean isVowel(char c){
		return "AEIOUYaeiouy".indexOf(c) != -1;
	}

}

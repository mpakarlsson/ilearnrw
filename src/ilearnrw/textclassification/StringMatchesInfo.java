package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.user.problems.ProblemType;
import ilearnrw.utils.LanguageCode;

import java.util.ArrayList;

public class StringMatchesInfo {

	private static LanguageAnalyzerAPI languageAnalyser;
	
	private int start, end;
	
	public StringMatchesInfo(LanguageAnalyzerAPI la) {
		languageAnalyser = la;
		start = -1;
		end = -1;
	}

	public StringMatchesInfo(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public boolean isMatched(){
		return start != -1 && end != -1;
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
		return "StringMatchesInfo [start=" + start
				+ ", end=" + end + "]";
	}

	
	public static ArrayList<StringMatchesInfo> equals(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.equals(str[i])){
			    result.add(new StringMatchesInfo(0, ws.length()));
				return result;
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> contains(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			for (int k=0;k<ws.length();k++){
				if ((ws.substring(k)).startsWith(str[i])){
				    result.add(new StringMatchesInfo(k, k+str[i].length()));
				}
			}
		}
		if (result.size()>0)
			return result;
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> visualSimilarity(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWordUnmodified();
		for (int i=0;i<str.length;i++){
			for (int k=0;k<ws.length();k++){
				if ((ws.substring(k)).startsWith(str[i])){
				    result.add(new StringMatchesInfo(k, k+str[i].length()));
				}
			}
		}
		if (result.size()>0)
			return result;
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
				    result.add(new StringMatchesInfo(start-1, start-1+sp.length()));
				}
				start += syl[j].length();
			}
		}
		if (result.size()>0)
			return result;
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
				    result.add(new StringMatchesInfo(
				    		ws.indexOf(str[i], start-1), ws.indexOf(str[i], start-1)+str[i].length()));
				}
				start += syl[j].length();
			}
		}
		if (result.size()>0)
			return result;
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> endsWith(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.endsWith(str[i])){
			    result.add(new StringMatchesInfo(ws.lastIndexOf(str[i]), ws.lastIndexOf(str[i])+str[i].length()));
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
			    result.add(new StringMatchesInfo(ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
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
			    result.add(new StringMatchesInfo(ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
			}
		}
		if (result.size()>0)
			return result;
		return null;
	}

	public static ArrayList<StringMatchesInfo> hasInternalPhoneme(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getPhonetics();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i]) && !ws.startsWith(str[i])){
			    result.add(new StringMatchesInfo(ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
			}
		}
		if (result.size()>0)
			return result;
		return null;
	}

	public static ArrayList<StringMatchesInfo> startsWithPhoneme(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getPhonetics();
		ArrayList<GraphemePhonemePair> gp = w.getGraphemesPhonemes();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i])){
				int k = 1, length = 0;
				String match = gp.get(0).getPhoneme();
				length = gp.get(0).getGrapheme().length();
				while (match.length() < str[i].length()){
					match = match+gp.get(k).getPhoneme();
					length += gp.get(k).getGrapheme().length();
					k++;
				}
				if (match.equals(str[i])){
					result.add(new StringMatchesInfo(0,length));
			    	return result;
				}
			}
		}
		return null;
	}

	public static ArrayList<StringMatchesInfo> containsPattern(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getCVForm();
		if (ws.length()<1)
			return null;
		String cvf[] = (ws.substring(1, ws.length()-1)).split("-");
		String syl[] = w.getSyllables();
		for (int i=0;i<str.length;i++){
			String pat[] = (str[i].substring(1, str[i].length()-1)).split("-");
			for (int j=0;j<=cvf.length-pat.length;j++){
				int length = 0;
				for (int k=0;k<pat.length;k++){
					if (!pat[k].equalsIgnoreCase(cvf[j+k])){
						length = 0;
						break;
					}
					length += syl[j+k].length();
				}
				if (length>0){
					int start = 0;
					for (int k=0;k<str.length;k++){
						if (k>=j)
							break;
						start += syl[i].length();
					}
				    result.add(new StringMatchesInfo(start, start+length));
				}
			}
			/*if (ws.contains(str[i])){
			    result.add(new StringMatchesInfo(str[i], ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}*/
		}
		if (result.size()>0)
			return result;
		return null;
	}

	public static ArrayList<StringMatchesInfo> containsPatternOrEndsWithExtraConsonant(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getCVForm();
		if (ws.length()<1)
			return null;
		String cvf[] = (ws.substring(1, ws.length()-1)).split("-");
		String syl[] = w.getSyllables();
		for (int i=0;i<str.length;i++){
			String pat[] = (str[i].substring(1, str[i].length()-1)).split("-");
			for (int j=0;j<=cvf.length-pat.length;j++){
				int length = 0;
				for (int k=0;k<pat.length;k++){
					if (j+k == syl.length-1 && (pat[k]+"c").equalsIgnoreCase(cvf[j+k])){
						length += syl[j+k].length();
						break;
					}
					if (!pat[k].equalsIgnoreCase(cvf[j+k])){
						length = 0;
						break;
					}
					length += syl[j+k].length();
				}
				if (length>0){
					int start = 0;
					for (int k=0;k<str.length;k++){
						if (k>=j)
							break;
						start += syl[i].length();
					}
				    result.add(new StringMatchesInfo(start, start+length));
				}
			}
		}
		if (result.size()>0)
			return result;
		return null;
	}

	public static ArrayList<StringMatchesInfo> soundSimilarity(String str[], Word w){
	    //ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		languageAnalyser.setWord(w);
		for (int i=0;i<str.length;i++){
			String[] parts = str[i].split("-");
			GreekWord r = (GreekWord)languageAnalyser.getSimilarSoundWord(parts[0], parts[1]);
			if (r!=null){
			    //result.add(new StringMatchesInfo(str[i], 0, w.toString().length()));
				//return result;
				return containsPhoneme(parts, w);
			}
		}
		return null;
	}
	
	
	public static void setLanguageAnalyser(LanguageAnalyzerAPI languageAnalyser) {
		StringMatchesInfo.languageAnalyser = languageAnalyser;
	}

	public static ArrayList<StringMatchesInfo> endsWithSuffix(String str[], Word w, ProblemType pt){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String phonetics = w.getPhonetics();
		
		if(phonetics==null || phonetics.isEmpty())
			return null;
		
		if(w.getLanguageCode().equals(LanguageCode.EN)){
		for(String s : str){
				int pos = s.indexOf("-");

				if(pos != -1){
					String[] values = s.split("-");
					
					// TODO: fix this temporary solution -> if no phonetics but word ends with suffix -> difficult word
					String tempPhon = w.getPhonetics();
					// remove stress, syllable dividers and vertical lines
					tempPhon = tempPhon.replace(".", "").replace("\u02C8", "").replace("\u02CC", "").replace("\u0329", "").replace("\u0027", "");
					
					if(w.getWord().endsWith(values[0]) && tempPhon.endsWith(values[1])){
						int pos2 = w.getWord().lastIndexOf(values[0]);
						result.add(new StringMatchesInfo(pos2, pos2 + values[0].length()));
						return result;
					}
					
				} else {
					if(((EnglishWord)w).getSuffix().equals(s) && pt.toString().equals(((EnglishWord)w).getSuffixType())){
						//int pos2 = w.getWord().lastIndexOf(s);
						result.add(new StringMatchesInfo(w.getLength()-s.length(), w.getLength()));
						return result;
					}
				}
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> suffixStress(String[] pd, Word w, ProblemType pt){
		ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String phonetics = w.getPhonetics();
		
		if(phonetics==null || phonetics.isEmpty())
			return null;
		
		if(w.getNumberOfSyllables()<=1)
			return null;
		
		String[] args = pd[0].split(",");
		String word = w.getWord();
		
		if(word.endsWith(args[0])){
			String[] syllables = phonetics.split("\\.");
			
			Character ch = syllables[syllables.length-2].charAt(0);
			if((ch=='\u02C8' || ch=='\u02CC') && syllables.length-2>0){
				((EnglishWord)w).setSuffixType("SUFFIX_DOUBLE");
				return StringMatchesInfo.endsWithSuffix(new String[]{args[0]}, w, ProblemType.SUFFIX_DOUBLE);
			}
			
			ch = syllables[0].charAt(0);
			if(ch=='\u02C8' || ch=='\u02CC'){
				((EnglishWord)w).setSuffixType("SUFFIX_ADD");
				return StringMatchesInfo.endsWithSuffix(new String[]{args[0]}, w, ProblemType.SUFFIX_ADD);
			}
		}
		
		
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> startsWithPrefix(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		for(String s : str){
			if(w.getWord().startsWith(s)){
			    result.add(new StringMatchesInfo(w.getWord().indexOf(s), w.getWord().indexOf(s)+s.length()));
				return result;
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> equalsPhoneme(String str[], Word w, String type){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ipa = w.getPhonetics();
		
		// TODO: Fix -> ignore phonetics if word is not in the dictionary
		if(ipa==null || ipa.isEmpty() || w.getGraphemesPhonemes()==null)
			return null;		
	
		
		
		for(String s : str){
			String[] values = s.split("-");
			String difficulty = values[0];
			String transcription = values[1];
			
			String tempPhon = w.getPhonetics();
			// remove stress, syllable dividers and vertical lines
			tempPhon = tempPhon.replace(".", "").replace("\u02C8", "").replace("\u02CC", "").replace("\u0329", "").replace("\u0027", "");
			String word = w.getWord();
			ArrayList<GraphemePhonemePair> graphemesPhonemes = new ArrayList<GraphemePhonemePair>();
			
			for(int i=0; i<w.getGraphemesPhonemes().size(); i++){
				graphemesPhonemes.add(w.getGraphemesPhonemes().get(i));
			}
			
			int savePos = 0;
			while(word.contains(difficulty) && tempPhon.contains(transcription)){				
				int pos = wordContainsGraphemePhoneme(graphemesPhonemes, difficulty, transcription);
				
				
				
				if(pos != -1){					
					result.add(new StringMatchesInfo(pos+savePos, pos+savePos+difficulty.length()));
					word = word.substring(pos+difficulty.length());
					tempPhon = tempPhon.substring(tempPhon.indexOf(transcription)+transcription.length());
					savePos = pos+difficulty.length();
					if(pos+1<graphemesPhonemes.size()){
						for(int j=0; j<=pos; j++){
							graphemesPhonemes.remove(0);
						}
					} else 
						break;
					
				} else 
					break;
			}
		}
		
		if(!result.isEmpty())
			return result;
		else
			return null;
	}
	
	public static ArrayList<StringMatchesInfo> syllablePattern(String str[], Word w){
		ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		for(String s : str){
			if(!s.contains("/")){
				
				if(s.equals("1 syllable"))
					if(w.getSyllables().length == 1){
						result.add(new StringMatchesInfo(0, w.getWord().length()));
						return result;
					}
				
				if(s.equals("Closed syllables"))
					if(w.getSyllables().length == 2 && !isVowel(w.getSyllables()[0].charAt(w.getSyllables()[0].length()-1))){
						result.add(new StringMatchesInfo(0, w.getWord().length()));
						return result;
					}
				
				if(s.equals("Open syllables"))
					if(w.getSyllables().length == 2 && isVowel(w.getSyllables()[0].charAt(w.getSyllables()[0].length()-1))){
						result.add(new StringMatchesInfo(0, w.getWord().length()));
						return result;
					}
				
				if(s.equals("Closed and Open syllables"))
					if(ContainsOpenClosedSyllables(w)){
						result.add(new StringMatchesInfo(0, w.getWord().length()));
						return result;
					}
				
				break;
			}
			String lookup = "", problem = "";
			
			if(s.contains(":")){
				String[] splitValues = s.split(":");
				lookup = splitValues[0];
				problem = splitValues[1];
			} else
				problem = s;
			
			problem = problem.replace("/", "-");
			String word = w.getWord();
			String cv = w.getCVForm();
			
			if(countCharacter(cv, '-') != 3)
				continue;
			
			if(cv.contains(problem)){
				if(!lookup.isEmpty()){
					if(!word.contains(lookup))
						continue;

					if(lookup.equals("qu")){
						int index = word.indexOf("qu");
						
						for(int i=0; i<index+1; i++){
							if(cv.charAt(i) == '-')
								index++;
						}
						
						cv = new StringBuilder(cv).replace(index+1, index+2, "").toString();
						
						if(!cv.contains(problem)) continue;
					}
				}
				
				int index = cv.indexOf(problem);
				String temp = cv.substring(0, index);
				index = index - countCharacter(temp, '-');
				result.add(new StringMatchesInfo(index, index+problem.length()-1));
			}
		}

		if(!result.isEmpty())
			return result;
		else
			return null;
	}
	
	public static ArrayList<StringMatchesInfo> syllableCount(String str[], Word w){
		String t = str[0].substring(0, str[0].indexOf(' ')>0?str[0].indexOf(' '):str[0].length());
		int syllables = Integer.parseInt(t.trim());
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		if (str[0].contains("or more")){
			if(w.getSyllables().length >= syllables){
			    result.add(new StringMatchesInfo(0, w.getWord().length()));
			    return result;
			}
		}
		else{
			if(w.getSyllables().length == syllables){
			    result.add(new StringMatchesInfo(0, w.getWord().length()));
			    return result;
			}
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> letterPair(String str[], Word w){
		ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		
		if(w.getLanguageCode() == LanguageCode.EN){
			//String l1 = Character.toString(str[0].charAt(0));
			//String l2 = Character.toString(str[0].charAt(2));
			//if (w.getWord().indexOf(str[0].charAt(0)) == -1)
			//	return null;
			//String tempW = w.getWord().replace(str[0].charAt(0), str[0].charAt(2));
			/*tempW = tempW.substring(1);
			
			if(w.getWord().startsWith(l1))
				tempW = l2 + tempW;
			else if(w.getWord().startsWith(l2))
				tempW = l1 + tempW;
			else
				return null;
			*/
			int start = w.getWord().indexOf(str[0].charAt(0), 0);
			while (start != -1) {
			//(w.getWord().indexOf(str[0].charAt(0), start) > -1){
				String tempW = w.getWord();
				tempW = tempW.substring(0,start)+str[0].charAt(2)+tempW.substring(start+1);
				if(EnglishLanguageAnalyzer.getInstance().getDictionary().getDictionary().containsKey(tempW))
					result.add(new StringMatchesInfo(start, start+1));
				start = w.getWord().indexOf(str[0].charAt(0), start+1);
			}
			start = w.getWord().indexOf(str[0].charAt(2), 0);
			while (start != -1) {
				String tempW = w.getWord();
				tempW = tempW.substring(0,start)+str[0].charAt(0)+tempW.substring(start+1);
				if(EnglishLanguageAnalyzer.getInstance().getDictionary().getDictionary().containsKey(tempW))
					result.add(new StringMatchesInfo(start, start+1));
				start = w.getWord().indexOf(str[0].charAt(2), start+1);
			}
			/*if (w.getWord().indexOf(str[0].charAt(2)) > -1){
				String tempW = w.getWord();
				tempW = tempW.replace(str[0].charAt(2), str[0].charAt(0));
				if(EnglishLanguageAnalyzer.getInstance().getDictionary().getDictionary().containsKey(tempW)){
					result.add(new StringMatchesInfo(0, w.getWord().length()));
				}
			}*/
		}
		if (!result.isEmpty())
			return result;		
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> patternEqualsPronunciation(String str[], Word w, String type){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
	    
		String ipa = w.getPhonetics();
		if(ipa==null || ipa.isEmpty())
			return null;		
		
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
								result.add(new StringMatchesInfo(word.indexOf(shortStr), ind+shortStr.length()));
							}
					}
					
					if(result.size()>0)
						return result;
					
				}
				return null;
			}
		}

		
		
		for(String s : str){
			String[] values = s.split("-");
			String difficulty = values[0];
			String transcription = values[1];
			
			if(type.equals("contains")){
				int pos = wordContainsGraphemePhoneme(w.getGraphemesPhonemes(), difficulty, transcription);
				if(pos != -1){
					if(difficulty.contains("_C_") || difficulty.contains("_V_")){
						result.add(new StringMatchesInfo(pos, pos+difficulty.length()-2));
						return result;					
					}
					result.add(new StringMatchesInfo(pos, pos+difficulty.length()));
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
							result.add(new StringMatchesInfo(w.getWord().length()-sVal.length()-1, w.getWord().length()));
							return result;
						} else 
							return null;
						
						
					}else if(difficulty.contains("_V_")){
						sVal = difficulty.substring(3);
						if(w.getWord().endsWith(sVal) && isVowel(w.getWord().charAt(w.getWord().length()-sVal.length()-1))){
							result.add(new StringMatchesInfo(w.getWord().length()-sVal.length()-1, w.getWord().length()));
							return result;
						} else 
							return null;
					}
					if(w.getWord().endsWith(difficulty)){
						result.add(new StringMatchesInfo(w.getWord().lastIndexOf(values[0]), w.getWord().lastIndexOf(values[0])+difficulty.length()));
						return result;
					}
				}
			} else if(type.equals("begins")){
				String tempPhon = w.getPhonetics();
				// remove stress, syllable dividers and vertical lines
				tempPhon = tempPhon.replace(".", "").replace("\u02C8", "").replace("\u02CC", "").replace("\u0329", "").replace("\u0027", "");
				
				if(w.getWord().startsWith(difficulty) && tempPhon.startsWith(transcription)){
					result.add(new StringMatchesInfo(w.getWord().indexOf(values[0]), w.getWord().indexOf(values[0])+difficulty.length()));
					return result;
				}
			} 
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> patternEqualsMixedPronunciation(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
	    String[] sarr = str[0].split("-");
		String ipa = w.getPhonetics();
		if(ipa==null || ipa.isEmpty() || sarr == null || sarr.length != 2 || sarr[0].length()<=2)
			return null;
		//contains 'variable' C that denotes consonants
		if (sarr[0].charAt(1) == 'C'){
			String target = "";
			int idx = -1;
			for (int i=0;i<w.getGraphemesPhonemes().size()-1; i++){
				if (w.getGraphemesPhonemes().get(i).getGrapheme().equals(sarr[0].charAt(0)+"."+sarr[0].charAt(2))){
					target = w.getGraphemesPhonemes().get(i).getPhoneme();
					idx = i;
					break;
				}
			}
			if (idx != -1 && w.getGraphemesPhonemes().get(idx+1).getGrapheme().length() == 1)
				target = target + (isConsonant(w.getGraphemesPhonemes().get(idx+1).getGrapheme().charAt(0))?'C':'X');
			if (target.equals(sarr[1])){
				result.add(new StringMatchesInfo(w.getWord().indexOf(sarr[0]), w.getWord().indexOf(sarr[0])+sarr[0].length()));
				return result;
			}
		}
		else if (w.getWord().contains(sarr[0])){
			String target = "";
			int idx = -1;
			for (int i=0;i<w.getGraphemesPhonemes().size()-1; i++){
				if (w.getGraphemesPhonemes().get(i).getGrapheme().equals(sarr[0].charAt(0)+"."+sarr[0].charAt(2))){
					target = w.getGraphemesPhonemes().get(i).getPhoneme();
					idx = i;
					break;
				}
			}
			if (idx != -1)
				target = target + w.getGraphemesPhonemes().get(idx+1).getPhoneme();
			if (target.equals(sarr[1])){
				result.add(new StringMatchesInfo(w.getWord().indexOf(sarr[0]), w.getWord().indexOf(sarr[0])+sarr[0].length()));
				return result;
			}
		}
		
		return null;
	}
	
	private static int wordContainsGraphemePhoneme(ArrayList<GraphemePhonemePair> graphsPhons, String difficulty, String ipa){
		ArrayList<GraphemePhonemePair> gpList = graphsPhons;
		
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

	
	private static Boolean ContainsOpenClosedSyllables(Word w){
		if(w.getNumberOfSyllables()<2)
			return false;
		else { 
			String[] syllables = w.getSyllables();
			int numOpen = 0, numClosed = 0;
			for(int i=0; i<syllables.length; i++){
				if(isVowel(syllables[i].charAt(syllables[i].length()-1)))
					numOpen++;
				else
					numClosed++;
			}
			
			if(numOpen>0 && numClosed>0)
				return true;
		}
		return false;
	}
	
	private static boolean isVowel(char c){
		return "AEIOUYaeiouy".indexOf(c) != -1;
	}
	
	private static boolean isConsonant(char c){
		return "BCDFGHJKLMNPQRSTVXZbcdfghjklmnpqrstvwxyz".indexOf(c) != -1;
	}
	
	private static int countCharacter(String word, Character lookup){
		int cnt = 0;
		for(Character ch : word.toCharArray()){
			if(ch == lookup)
				cnt++;
		}
		return cnt;
	}

}

package ilearnrw.textclassification;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.user.problems.ProblemType;
import ilearnrw.utils.LanguageCode;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	public static ArrayList<StringMatchesInfo> containsButExcludePreviousLetters(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			for (int k=0;k<ws.length();k++){
				String p[] = str[i].split("-");
				String in = p[0], out = p[1]+p[0];
				if ((ws.substring(k)).startsWith(in) && (k-p[1].length()<0 || !ws.substring(k-p[1].length()).startsWith(out))){
				    result.add(new StringMatchesInfo(k, k+in.length()));
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
	
	//must be wrong, but not used at the moment!
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
		String syl[] = w.getSyllables();
		for (int i=0;i<str.length;i++){
			int start = 0;
			for (int j=0;j<syl.length-1;j++){
				if (syl[j].contains(str[i])){
				    result.add(new StringMatchesInfo(
				    		start+syl[j].indexOf(str[i]), start+syl[j].indexOf(str[i])+str[i].length()));
				}
				start += syl[j].length();
			}
		}
		if (result.size()>0)
			return result;
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> endsWithSuffixAndSatisfiesFilter(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		GreekWord t = GreekLanguageAnalyzer.getInstance().getDictionary().getWord(w.getWord());
		if (t == null)
			return null;
		
		for (int i=0;i<str.length;i++){
			String p[] = str[i].split("-");
			if (p.length == 2){
				if (ws.endsWith(p[1]) && GreekWordTypeFilterChecker.check(t, p[0])){
				    result.add(new StringMatchesInfo(ws.lastIndexOf(p[1]), ws.lastIndexOf(p[1])+p[1].length()));
					return result;
				}
			}
			else { // no filters!
				if (ws.endsWith(str[i])){
				    result.add(new StringMatchesInfo(ws.lastIndexOf(str[i]), ws.lastIndexOf(str[i])+str[i].length()));
					return result;
				}
			}
		}
		return null;
	}
	//or
	public static ArrayList<StringMatchesInfo> startsWith(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getWord();
		for (int i=0;i<str.length;i++){
			if (ws.startsWith(str[i]) && w.getWord().length()>str[i].length()+1){
			    result.add(new StringMatchesInfo(ws.indexOf(str[i]), ws.indexOf(str[i])+str[i].length()));
				return result;
			}
		}
		return null;
	}

	/*public static ArrayList<StringMatchesInfo> containsPhoneme(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getPhonetics();
		for (int i=0;i<str.length;i++){
			if (ws.contains(str[i])){
				for (GraphemePhonemePair gp : w.getGraphemesPhonemes()){
					
				}
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
	}*/
	
	public static ArrayList<StringMatchesInfo> containsSimilarPhonemeAdaptor(String str[], Word w){
		ArrayList<String> t = new ArrayList<String>();
		for (int i=0;i<str.length; i++){
			String k[] = str[i].split("-");
			if (!t.contains(k[0]))
				t.add(k[0]);
			if (!t.contains(k[1]))
				t.add(k[1]);
		}
		String p[] = new String[t.size()];
		for (int i=0;i<p.length;i++)
			p[i] = t.get(i);
		return containsPhoneme(p, w);
	}
	
	public static ArrayList<StringMatchesInfo> containsPhoneme(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getPhonetics();
		ArrayList<GraphemePhonemePair> gp = w.getGraphemesPhonemes();
		for (int i=0;i<str.length;i++){
			if (!ws.contains(str[i]))
				continue;
			int start = 0;
			for (int j=0;j<gp.size();j++){
				int length = 0, k=j;
				String phonemes = "";
				while (str[i].startsWith(phonemes) && k<gp.size()){
					phonemes = phonemes + gp.get(k).getPhoneme();
					length += gp.get(k).getGrapheme().length();
					if (str[i].equals(phonemes) && length>0 && start>=0 && 
							start+length<=w.getWord().length()){
					    result.add(new StringMatchesInfo(start, start+length));
					    break;
					}
					k++;
				}
				start += gp.get(j).getGrapheme().length();
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
			if (!ws.startsWith(str[i]))
				continue;
			int start = 0;
			int j=0, length = 0, k=j;
			String phonemes = gp.get(k).getPhoneme();
			while (str[i].startsWith(phonemes) && k+1<gp.size()){
				length += gp.get(k).getGrapheme().length();
				if (str[i].equals(phonemes) && length>0 && start>=0 && 
						start+length<=w.getWord().length()){
				    result.add(new StringMatchesInfo(start, start+length));
				    break;
				}
				k++;
				phonemes = phonemes + gp.get(k).getPhoneme();
			}
			start += gp.get(j).getGrapheme().length();
		}
		if (result.size()>0)
			return result;
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> hasInternalPhoneme(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		String ws = w.getPhonetics();
		ArrayList<GraphemePhonemePair> gp = w.getGraphemesPhonemes();
		for (int i=0;i<str.length;i++){
			if (!ws.contains(str[i]))
				continue;
			int start = 0;
			for (int j=0;j<gp.size();j++){
				int length = 0, k=j;
				String phonemes = "";
				while (str[i].startsWith(phonemes) && k<gp.size()){
					phonemes = phonemes + gp.get(k).getPhoneme();
					length += gp.get(k).getGrapheme().length();
					if (str[i].equals(phonemes) && length>0 && start>0 && 
							start+length<=w.getWord().length()){
					    result.add(new StringMatchesInfo(start, start+length));
					}
					k++;
				}
				start += gp.get(j).getGrapheme().length();
			}
		}
		if (result.size()>0)
			return result;
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
			int start = 0;
			for (int j=0;j<=cvf.length-pat.length;j++){
				int length = 0;
				for (int k=0;k<pat.length;k++){
					if (!pat[k].equalsIgnoreCase(cvf[j+k])){
						length = 0;
						break;
					}
					length += syl[j+k].length();
				}
				if (length>0 && start>=0 && start+length<=w.getWord().length())
				    result.add(new StringMatchesInfo(start, start+length));
				start += syl[j].length();
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
			int start = 0;
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
				if (length>0 && start>=0 && start+length<=w.getWord().length())
				    result.add(new StringMatchesInfo(start, start+length));
				start += syl[j].length();
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
		String phonetics = w.getPhonetics();
		if(phonetics==null || phonetics.isEmpty())
			return null;
		
		String wordStem = w.getStem();
		if(wordStem == null || wordStem.isEmpty())
			return null;
		
		EnglishWord stem = EnglishLanguageAnalyzer.getInstance().getDictionary().getDictionary().get(wordStem);
		if(stem == null)
			return null;
		
		EnglishWord word = (EnglishWord) w;
		if(word.getNumberOfSyllables() < 2 || stem.getNumberOfSyllables() < 2)
			return null;
		
		ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();		
		String[] syllables = stem.getPhonetics().split("\\.");
		
		String syllable = syllables[0];
		if(word.getSuffixType().equals("SUFFIX_ADD") && syllable.charAt(0) == '\u02C8' ){
			result.add(new StringMatchesInfo(word.getLength()-word.getSuffix().length(), word.getLength()));
			return result;
		}
		
		syllable = syllables[stem.getSyllables().length-1];
		if(word.getSuffixType().equals("SUFFIX_DOUBLE") && syllable.charAt(0) == '\u02C8'){
			result.add(new StringMatchesInfo(word.getLength()-word.getSuffix().length(), word.getLength()));
			return result;
		}
		
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> startsWithPrefix(String str[], Word w){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
		for(String s : str){
			EnglishWord ew =  (EnglishWord)(w);
			
			if(ew.getPrefix().equals(s)){
				result.add(new StringMatchesInfo(0, ew.getPrefix().length()));
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
		
				String shortStr = s.substring(0,s.indexOf("C"));				
				
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
					if(difficulty.contains("C") || difficulty.contains("V")){
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
					if(difficulty.contains("C")){
						sVal = difficulty.substring(3);
						if(w.getWord().endsWith(sVal) && !isVowel(w.getWord().charAt(w.getWord().length()-sVal.length()-1))){
							result.add(new StringMatchesInfo(w.getWord().length()-sVal.length()-1, w.getWord().length()));
							return result;
						} else 
							return null;
						
						
					}else if(difficulty.contains("V")){
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
			int idx = -1, startsFrom = 0;
			for (int i=0;i<w.getGraphemesPhonemes().size()-1; i++){
				if (w.getGraphemesPhonemes().get(i).getGrapheme().equals(sarr[0].charAt(0)+"."+sarr[0].charAt(2))){
					target = w.getGraphemesPhonemes().get(i).getPhoneme();
					idx = i;
					break;
				}
				startsFrom += (w.getGraphemesPhonemes().get(i).getGrapheme()).replace("ː", "").replace(".", "").length();
			}
			if (idx != -1 && w.getGraphemesPhonemes().get(idx+1).getGrapheme().length() == 1)
				target = target + (isConsonant(w.getGraphemesPhonemes().get(idx+1).getGrapheme().charAt(0))?'C':'X');
			if (target.equals(sarr[1])){
				result.add(new StringMatchesInfo(startsFrom, startsFrom+sarr[0].length()));
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
	
	public static ArrayList<StringMatchesInfo> equalsPronunciation(String str[], Word w, String type){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
	    
		String ipa = w.getPhonetics();
		if(ipa==null || ipa.isEmpty())
			return null;				
		
		for(String s : str){
			String[] values = s.split("-");
			String difficulty = values[0];
			String transcription = values[1];
			
			if (!w.getWord().contains(difficulty))
				continue;
			int totalStartsFrom = 0;
			for (int i=0; i<w.getGraphemesPhonemes().size(); i++){
				String phon = "", graph = "";
				int j = i;
				int endsTo = totalStartsFrom;
				while (difficulty.startsWith(graph) && transcription.startsWith(phon)){
					GraphemePhonemePair pair = w.getGraphemesPhonemes().get(j);
					phon = phon + pair.getPhoneme();
					graph = graph + pair.getGrapheme().trim();
					endsTo = totalStartsFrom+graph.length();
					if (phon.equals(transcription) && graph.equals(difficulty)){
						if (type.equals("ends") && j == w.getGraphemesPhonemes().size()-1){
							result.add(new StringMatchesInfo(w.getWord().length() - graph.length(), w.getWord().length()));
							return result;
						}
						else if (type.equals("begins") && i == 0){
							result.add(new StringMatchesInfo(0, graph.length()));
							return result;
						}
						else if (type.equals("contains")){
							result.add(new StringMatchesInfo(endsTo - graph.length(), endsTo));
							return result;
						}
					}
					j++;
					if (j>=w.getGraphemesPhonemes().size())
						break;
				}
				String grapheme = w.getGraphemesPhonemes().get(i).getGrapheme();
				
				if(grapheme.contains(".")){
					String[] vals = grapheme.split("\\.");
					totalStartsFrom += vals[0].length();
				} else 
					totalStartsFrom += grapheme.length();
			}
			
			/*if(type.equals("contains")){
				if (!w.getWord().contains(difficulty))
					continue;
				for (int i=0; i<w.getGraphemesPhonemes().size(); i++){
					String phon = "", graph = "";
					int j = i;
					while (difficulty.startsWith(graph) && transcription.startsWith(phon)){
						GraphemePhonemePair pair = w.getGraphemesPhonemes().get(j);
						phon = phon + pair.getPhoneme();
						graph = graph + pair.getGrapheme().trim();
						if (phon.equals(transcription) && graph.equals(difficulty)){
							result.add(new StringMatchesInfo(0, graph.length()));
							return result;
						}
						j++;
						if (j>=w.getGraphemesPhonemes().size())
							break;
					}
				}
			} 
			else if(type.equals("ends")){
				if (!w.getWord().endsWith(difficulty))
					continue;
				String phon = "", graph = "";
				for (int i=w.getGraphemesPhonemes().size()-1; i>=0; i--){
					GraphemePhonemePair pair = w.getGraphemesPhonemes().get(i);
					phon = pair.getPhoneme() + phon;
					graph = pair.getGrapheme() + graph;
					if (phon.equals(transcription) && graph.equals(difficulty)){
						result.add(new StringMatchesInfo(0, graph.length()));
						return result;
					}
				}
			} 
			else if(type.equals("begins")){
				if (!w.getWord().startsWith(difficulty))
					continue;
				String phon = "", graph = "";
				for (GraphemePhonemePair pair : w.getGraphemesPhonemes()){
					phon = phon + pair.getPhoneme();
					graph = graph + pair.getGrapheme();
					if (phon.equals(transcription) && graph.equals(difficulty)){
						result.add(new StringMatchesInfo(0, graph.length()));
						return result;
					}
				}
			} */
			/*
			else if(type.equals("ends")){
			String ph = ipa.replace(".", "").replace("ˈ", "");
			if (w.getWord().endsWith(difficulty) && ph.endsWith(transcription)){
				result.add(new StringMatchesInfo(w.getWord().length() - difficulty.length(), w.getWord().length()));
				return result;
			}
			}*/
			/*
			else if(type.equals("begins")){String ph = ipa.replace(".", "").replace("ˈ", "");
			if (w.getWord().startsWith(difficulty) && ph.startsWith(transcription)){
				result.add(new StringMatchesInfo(0, difficulty.length()));
				return result;
			}
			}*/
		}
		return null;
	}
	
	public static ArrayList<StringMatchesInfo> equalsPronunciationPattern(String str[], Word w, String type){
	    ArrayList<StringMatchesInfo> result = new ArrayList<StringMatchesInfo>();
	    
		String ipa = w.getPhonetics();
		if(ipa==null || ipa.isEmpty())
			return null;
		
		for(String s : str){
			String[] values = s.split("-");
			String difficulty = values[0];
			String transcription = values[1];
			
			if (difficulty.length() > w.getWord().length() || transcription.length() > ipa.length())
				continue;
			
			int totalStartsFrom = 0;
			for (int i=0; i<w.getGraphemesPhonemes().size(); i++){
				String phon = "", graph = "";
				int j = i;
				int endsTo = totalStartsFrom;
				String savedValue = "";
				int savedPos = -2;
				
				while (j<w.getGraphemesPhonemes().size()){
					GraphemePhonemePair pair = w.getGraphemesPhonemes().get(j);
					
					String nextPhon = pair.getPhoneme();
					String nextGraph =  pair.getGrapheme().trim();
					
					if(nextGraph.contains(".")){
						String[] vals	= nextGraph.split("\\.");
						nextGraph 		= vals[0];
						savedValue 		= vals[1];
						savedPos 		= j;
					}
					
					if(j==savedPos+1){
						nextGraph 	= nextGraph + savedValue;
						savedValue 	= "";
						savedPos 	= -2;
					}
					
					
					phon = phon + nextPhon;
					graph = graph + nextGraph;
					endsTo = totalStartsFrom + graph.length();
					
					String phonCV = replaceWithCV(transcription, phon, true);
					String diffCV = replaceWithCV(difficulty, graph, false);
					
					if (phonCV.equals(transcription) && 
							diffCV.equals(difficulty)){
						if (type.equals("ends") && j == w.getGraphemesPhonemes().size()-1){
							result.add(new StringMatchesInfo(w.getWord().length() - graph.length(), w.getWord().length()));
							return result;
						}
						else if (type.equals("begins") && i == 0){
							result.add(new StringMatchesInfo(0, graph.length()));
							return result;
						}
						else if (type.equals("contains")){
							result.add(new StringMatchesInfo(endsTo - graph.length(), endsTo));
							return result;
						}
					}
					j++;
					if (j>=w.getGraphemesPhonemes().size())
						break;
				}
				String grapheme = w.getGraphemesPhonemes().get(i).getGrapheme();
				
				if(grapheme.contains(".")){
					String[] vals = grapheme.split("\\.");
					totalStartsFrom += vals[0].length();
				} else 
					totalStartsFrom += grapheme.length();
			}
			/*if(type.equals("contains")){
				for (int i=0; i<w.getGraphemesPhonemes().size(); i++){
					String phon = "", graph = "";
					int j = i;
					while (j<w.getGraphemesPhonemes().size()){
						GraphemePhonemePair pair = w.getGraphemesPhonemes().get(j);
						phon = phon + pair.getPhoneme();
						graph = graph + pair.getGrapheme().trim();
						if (replaceWithCV(transcription, phon).equals(transcription) && 
								replaceWithCV(difficulty, graph).equals(difficulty)){
							result.add(new StringMatchesInfo(0, graph.length()));
							return result;
						}
						j++;
						if (j>=w.getGraphemesPhonemes().size())
							break;
					}
				}
			} 
			else if(type.equals("ends")){
				String ph = ipa.replace(".", "").replace("ˈ", "");
				if (replaceWithCV(difficulty, w.getWord().substring(w.getWord().length() - difficulty.length())).endsWith(difficulty) && 
						replaceWithCV(transcription, ph.substring(ph.length() - transcription.length())).endsWith(transcription)){
					result.add(new StringMatchesInfo(w.getWord().length()-difficulty.length(), w.getWord().length()));
					return result;
				}
			} 
			else if(type.equals("begins")){
				String ph = ipa.replace(".", "").replace("ˈ", "");
				if (replaceWithCV(difficulty, w.getWord().substring(0, difficulty.length())).startsWith(difficulty) && 
						replaceWithCV(transcription, ph.substring(0, transcription.length())).startsWith(transcription)){
					result.add(new StringMatchesInfo(0, difficulty.length()));
					return result;
				}
			} */
		}
		return null;
	}
	
	private static String replaceWithCV(String difficulty, String string, boolean phoneticCheck){
		string = string.replace(".", "");
		
		int colIndex = -1;
		if(phoneticCheck){
			colIndex = string.indexOf("ː");
		} 
		
		int offset = 1;
		
		if (string.length() <1)
			return "XXX";
		if (difficulty.startsWith("C") && (isConsonant(string.charAt(0)) || !isConsonant(string.charAt(0)) && !isVowel(string.charAt(0))))
			return "C"+string.substring(1);
		
		if (difficulty.startsWith("V") && (isVowel(string.charAt(0)) || !isConsonant(string.charAt(0)) && !isVowel(string.charAt(0)))){
			if(phoneticCheck)
				if(containsDiphthong(string, true))
					offset++;
			
			return "V"+string.substring(offset);
		}
		if (difficulty.endsWith("C") && isConsonant(string.charAt(string.length()-1)) || 
				!isConsonant(string.charAt(string.length()-1)) && !isVowel(string.charAt(string.length()-1)))
			return string.substring(0, string.length()-1)+"C";
		if (difficulty.endsWith("V") && isVowel(string.charAt(string.length()-1)) || 
				!isConsonant(string.charAt(string.length()-1)) && !isVowel(string.charAt(string.length()-1))){
			if(phoneticCheck)
				if(containsDiphthong(string, false))
					offset++;
			
			return string.substring(0, string.length()-offset)+"V";
		}
		return "XXX";
	}
	
	private static boolean containsDiphthong(String string, boolean isStart){
		ArrayList<String> diphthongs = new ArrayList<String>(
				Arrays.asList(
						"əʊ","aɪ","aʊ","ɛə","eə","eɪ","ɪə","ɔɪ","ʊə",
						"ɜː","eː","ɑː","aː","iː","juː","ɔː","uː"));
		for(String d : diphthongs){
			if(isStart){
				if(string.startsWith(d))
					return true;
			} else
				if(string.endsWith(d))
					return true;
		}
		return false;
	}
	
	private static int wordContainsGraphemePhoneme(ArrayList<GraphemePhonemePair> graphsPhons, String difficulty, String ipa){
		ArrayList<GraphemePhonemePair> gpList = graphsPhons;
		
		if(gpList == null)
			return -1;

		String value = "";
		int specialDiff = 0;
		
		// If character contains unknown consonant or vowel character
		if(difficulty.contains("C")){
			specialDiff = 1;
			value = difficulty.replace("C", ":");
		}
		else if(difficulty.contains("V")){
			specialDiff = 2;
			value = difficulty.replace("V", ":");
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
	
	private static boolean isIPAVowel(String c){
		String IPAvowels[] = {"ɔ"};
		for (String t : IPAvowels)
			if (t.equals(c))
				return true;
		return false;
	}
	
	private static boolean isIPAConsonant(String c){
		String IPAconsonants[] = {"ð", "dʒ"};
		for (String t : IPAconsonants)
			if (t.equals(c))
				return true;
		return false;
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

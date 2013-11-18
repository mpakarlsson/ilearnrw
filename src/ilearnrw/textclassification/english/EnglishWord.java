package ilearnrw.textclassification.english;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;


public class EnglishWord extends Word {

    //We put inside only lower case words
    public EnglishWord(String word) {
        super.word = word;
        checkType();
        syllabism();
        super.word = super.word.toLowerCase();
        createCVForm();
        
        if(Program.getDictionary().containsKey(word)){
                frequency = Double.parseDouble(Program.getDictionary().get(word).get(2));
        } else
                frequency = 5001;
    }

    
    protected void syllabism(){
            if(Program.getDictionary().containsKey(word)){
                    ArrayList<String> data = Program.getDictionary().get(word);
                    numSyllables = Integer.parseInt(data.get(1));
                    
                    String ipa = data.get(3);
                    if(ipa.contains("<"))
                    	syllables = new String[] {"<"};
                    else
                    	syllables = syllabify(word.toLowerCase(), ipa);

                    //System.out.println(Arrays.asList(syllables).toString());
            } else {
                    numSyllables = countVowels();
            }
    }
    
    protected void checkType(){
            //if (isVerb(word))...
            //...
            type = WordType.Unknown;
    }
    
    protected char upperCharToCV(char x){
		// y = consonant at beginning of syllable, vowel everywhere else
        switch (x){
                case 'A':
                case 'E':
                case 'I':
                case 'O':
                case 'U':
                case 'Y':
                    return 'v';
                case '-':
                	return '-';
                default :                        	
                    return 'c';
        }
    }
    
    protected void createCVForm(){
            cvForm = "";
            String temp = word.toUpperCase();
            for(int i=0; i<temp.length(); i++){
                    char c = temp.charAt(i);
                    
                    if(i == 0 && c == 'Y'){
                    	c = 'B';
                    }
                    char cv = upperCharToCV(c); 
                    cvForm += cv;
            }
            System.out.println("");
    }
    
    private int countVowels(){
            String temp = word.toUpperCase();
            char prevC = 0;
            int numVowels = 0, size = temp.length();
            for(char c : temp.toCharArray()){
                    if(--size==0 && c == 'E'){
                            if(numVowels == 0)
                                    numVowels++;
                            break;
                    }
                    
                    char currC = upperCharToCV(c);
                    if(currC == 'v' && prevC != 'v'){
                            numVowels++;
                    }
                    prevC = currC;
            }
            
            return numVowels;
    }
    
	private String[] syllabify(String word, String phonetics){
    	Map<String, ArrayList<String>> ssl = Program.getSoundToSpellingList();   
    	String tempPhon = phonetics;
    	
    	/**
		 * Remove stress symbols
		 * primary stress -  http://www.fileformat.info/info/unicode/char/02c8/index.htm
		 * secondary stress - http://www.fileformat.info/info/unicode/char/02cc/index.htm
		 * */
    	tempPhon = tempPhon.replace("\u02C8", "");
		tempPhon = tempPhon.replace("\u02CC", "");
    	
    	System.out.println(word);
    	
    	//contribution ˌkɒn.trɪ.ˈbjuːʃ.ən
    	//mission ˈmɪʃ.ən 
    	// oversee
    	
    	if(word.equals("contribution")){
    		int a =0;
    		a++;
    	}
    	
    	// Translation phase
    	ArrayList<Map<String, ArrayList<Integer>>> possibleMatches = new ArrayList<Map<String,ArrayList<Integer>>>();
    	
    	for(int i = 0, len = tempPhon.length(); i < len; i++){
    		String c = Character.toString(tempPhon.charAt(i));
    		if(c.trim().isEmpty()) continue;
    		
    		int inc = c.length();
    		c = getCharacterCombination(c, tempPhon, i);
    		inc = c.length() - inc;
    		i += inc;
    		
    		if(ssl.containsKey(c)){
    			
    			Map<String, ArrayList<Integer>> charPositions = new HashMap<String, ArrayList<Integer>>();
    			ArrayList<String> symbolItems = ssl.get(c);
    			for(String item : symbolItems){    				
    				int j = 0;
    				while((j=(word.indexOf(item, j)+1)) > 0){
    					if(charPositions.get(item) == null)
    						charPositions.put(item, new ArrayList<Integer>());
    					
    					if(c.equals("k") && item.equals("x"))
    					{
    						if(i+1<len)
    						{ 
								String nextChar = Character.toString(tempPhon.charAt(i+1));
	    				    	
    							if(nextChar.equals("s")) i++;
    							
    							if(nextChar.equals(".")){
	    							if(i+2<len){
	    								nextChar = Character.toString(tempPhon.charAt(i+2));
	    								if(nextChar.equals("s")){
	    									String start = tempPhon.substring(0, i+1);
	    									String end = tempPhon.substring(i+3, tempPhon.length());
	    									tempPhon = start.concat("s.").concat(end);
	    									i++;
	    								}
	    							}
    							}
    							
    						}
    					}    					
						charPositions.get(item).add(j);
    				}	
    			}
    			possibleMatches.add(charPositions);    			
    			
    		} else {
    			Map<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
    			
    			if(c.equals("."))
    				map.put(".", new ArrayList<Integer>());
    			
    			possibleMatches.add(map);    			
    		}
    	}
    	
    	
    	// Removing phase
    	for(int i = 0; i < possibleMatches.size(); i++)
    	{
    		Map<String,ArrayList<Integer>> map = possibleMatches.get(i);
    		
    		if(map.isEmpty() || map.containsKey(".")) continue;
    		if(i+1>=possibleMatches.size()) 
    			continue;
    		
    		Map<String,ArrayList<Integer>> nextMap = possibleMatches.get(i+1);
    		
    		Iterator<Entry<String, ArrayList<Integer>>> it = map.entrySet().iterator();
    		while(it.hasNext())
    		{
    			Entry<String, ArrayList<Integer>> pairs = (Map.Entry<String, ArrayList<Integer>>)it.next();
    			
    			Iterator<Entry<String, ArrayList<Integer>>> it2 = nextMap.entrySet().iterator();
    			Map<String, Integer> removeMap = new HashMap<String, Integer>();
    			while(it2.hasNext())
    			{
    				Entry<String, ArrayList<Integer>> nextPairs = (Map.Entry<String, ArrayList<Integer>>)it2.next();
    				
    				String key1 = pairs.getKey().toString();
					String key2 = nextPairs.getKey().toString();
					String str = key1.concat(key2);
    				for(Integer value : pairs.getValue())
    					for(Integer value2 : nextPairs.getValue())
    						if(value+1 == value2)
    							if(!word.contains(str))
    							{
    								str = key1.concat(key2.substring(1));
    								if(!word.contains(str) || key2.length() > 0)
    									removeMap.put(key2, i+1);
    							}
    			}
    			if(!removeMap.isEmpty())
    			{
    				Iterator itr = removeMap.entrySet().iterator();
    				while(itr.hasNext()){
    					Entry<String, Integer> p = (Map.Entry<String, Integer>)itr.next();
    					possibleMatches.get(i+1).remove(p.getKey());
    				}
    				
					possibleMatches.remove(i+1);
    			}
    		}
    	}
    	
    	

    	// Connecting phase
    	String buildWord = "";
    	int minimum = -1;
    	int keyLength = -1;

    	for(int i = 0; i < possibleMatches.size(); i++){
    		Map<String, ArrayList<Integer>> map = possibleMatches.get(i);
    		
    		if(map.isEmpty()){
    			buildWord = buildWord.concat("*");
    			continue;
    		}
    		
    		Iterator<Entry<String, ArrayList<Integer>>> it = map.entrySet().iterator();
			String correctKey = "";
			int earliestPosition = Integer.MAX_VALUE;
			boolean setValue = false;
			
    		while(it.hasNext()){
    			@SuppressWarnings("unchecked")
				Entry<String, ArrayList<Integer>> pairs = (Map.Entry<String, ArrayList<Integer>>)it.next();
    			
				if(pairs.getKey().equals(".")){
					buildWord = buildWord.concat(("-"));
					continue;
				}
				
				ArrayList<Integer> list = (ArrayList<Integer>)pairs.getValue();
				Object key = pairs.getKey();
				for(Integer pos : list){					
					if((pos < earliestPosition) && (pos > minimum)){
						earliestPosition = pos;
						correctKey = key.toString();
						keyLength = correctKey.length();
						setValue = true;
					}
					if((pos==earliestPosition) && (key.toString().length() > correctKey.length()))
						correctKey = key.toString();
				}
    		}
    		buildWord = buildWord.concat(correctKey);
    		if(setValue)
    			minimum = earliestPosition + keyLength - 1;
    	}

    	if(word.equals("oversee")){
    		int asd =1; asd++;
    		}
    	
    	// Building phase
    	int lookup = 0;
    	String finalWord = "";
    	for(int i = 0; i < word.length(); i++){
    		boolean taken = false;
    		char cw = word.charAt(i), cb;
    		cb = lookup < buildWord.length() ? buildWord.charAt(lookup) : '_';

    		while(cb == '-'){
    			char next;
    			if(lookup+1 < buildWord.length()){
    				next = buildWord.charAt(lookup+1);
    				if(next != cw){
    					finalWord += cw;
    					taken = true;
    					break;
    				}
    			}
    			if(!finalWord.isEmpty())
    				finalWord += cb;
    			
    			cb = lookup+1 < buildWord.length() ? buildWord.charAt(++lookup) : '_';
    		}
    		
    		if(cb == '*'){
    			finalWord += cw;
    			taken = true;
    			++lookup;
    		}
    		
    		if(cw == cb)
    		{
    			finalWord += cb;
    			++lookup;
    		} else if(!taken)
    			finalWord += cw;
    	}
    	System.out.println(finalWord);
    	return finalWord.split("-");
    }
    
    private String getCharacterCombination(String s, String word, int position){
    	if(position+1 >= word.length())
    		return s;
    	
    	boolean isConsonant = (s.equals("p") ||
    			s.equals("b") || s.equals("t") || s.equals("d") ||
    			s.equals("ɡ") || s.equals("k") || s.equals("m") || 
    			s.equals("n") || s.equals("ŋ") || s.equals("r") ||
    			s.equals("f") || s.equals("v") || s.equals("θ") ||
    			s.equals("ð") || s.equals("s") || s.equals("z") || 
    			s.equals("ʃ") || s.equals("ʒ") || s.equals("h") ||
    			s.equals("j") || s.equals("l") || s.equals("w"));
	
    	String nextChar = Character.toString(word.charAt(position+1));
    	
    	// tʃ
    	if(s.equals("t") && nextChar.equals("\u0283")){ return s.concat(nextChar); }
    	// dʒ
    	else if(s.equals("d") && nextChar.equals("\u0292")){ return s.concat(nextChar); }
    	// iː
    	else if(s.equals("i") && nextChar.equals("\u02D0")){ return s.concat(nextChar); }
    	// uː
    	else if(s.equals("u") && nextChar.equals("\u02D0")){ return s.concat(nextChar); }
    	// eɪ
    	else if(s.equals("e") && nextChar.equals("\u026A")){ return s.concat(nextChar); }
    	// oʊ
    	else if(s.equals("o") && nextChar.equals("\u028A")){ return s.concat(nextChar); }
    	// ɔː
    	else if(s.equals("\u0254") && nextChar.equals("\u02D0")){ return s.concat(nextChar); }
    	// ɑː
    	else if(s.equals("\u0251") && nextChar.equals("\u02D0")){ return s.concat(nextChar); }
    	// aɪ
    	else if(s.equals("a") && nextChar.equals("\u026A")){ return s.concat(nextChar); }
    	// ɔɪ
    	else if(s.equals("\u0254") && nextChar.equals("\u026A")){ return s.concat(nextChar); }
    	// aʊ
    	else if(s.equals("a") && nextChar.equals("\u028A")){ return s.concat(nextChar); }
    	// ɑr
    	else if(s.equals("\u0251") && nextChar.equals("r")){ return s.concat(nextChar); }
    	// ɜr
    	else if(s.equals("\u025C") && nextChar.equals("r")){ return s.concat(nextChar); }
    	// ɛər
    	else if(s.equals("\u025B") && nextChar.equals("\u0259")){
    		if(position+2 >= word.length())
    			return s;
    		
    		String n2Char = Character.toString(word.charAt(position + 2));
    		if(n2Char.equals("r")){ return s.concat(nextChar).concat(n2Char); }
    	} 
    	// ɪər
    	else if(s.equals("\u026A") && nextChar.equals("\u0259")){
    		if(position+2 >= word.length())
    			return s;
    		
    		String n2Char = Character.toString(word.charAt(position + 2));
    		if(n2Char.equals("r")){ return s.concat(nextChar).concat(n2Char); }
    	}
    	// juː
    	else if(s.equals("j") && nextChar.equals("u")){
    		if(position+2 >= word.length())
    			return s;
    		
    		String n2Char = Character.toString(word.charAt(position + 2));
    		if(n2Char.equals("\u02D0")){ return s.concat(nextChar).concat(n2Char); }
    	}
    	// combine all letters with ː
    	else if(nextChar.equals("\u02D0")){ return s.concat(nextChar); }
    	// combine letters with ʊ
    	else if(!isConsonant && nextChar.equals("\u028A")){ return s.concat(nextChar); }
    	return s;
    }
}

package ilearnrw.textclassification.english;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

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
                    	syllables = syllabify(word, ipa);

                    System.out.println(Arrays.asList(syllables).toString());
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
    	String tempWord = "";
    	int positionWord = 0, numHyphens = 0;
    	if(word.contains("-")){
    		return word.split("-");
    	}
    	
    	tempPhon = tempPhon.replace(".", "-");
    	/**
		 * Remove stress symbols
		 * primary stress -  http://www.fileformat.info/info/unicode/char/02c8/index.htm
		 * secondary stress - http://www.fileformat.info/info/unicode/char/02cc/index.htm
		 * */
		tempPhon = tempPhon.replace("\u02C8", "");
		tempPhon = tempPhon.replace("\u02CC", "");
    
    	for(int i=0, len=tempPhon.length(); i<len; i++){
    		String c = Character.toString(tempPhon.charAt(i));
    		
    		if(c.trim().isEmpty()) continue;
    		
    		int inc = c.length();
    		c = checkNextCharacter(c, tempPhon, i);
    		inc = c.length() - inc;
    		i += inc;
    		
    		
    		if(ssl.containsKey(c)){
    			ArrayList<String> symbolItems = ssl.get(c);
    			ArrayList<String> items = new ArrayList<String>();
    			
    			ArrayList<String> words = new ArrayList<String>();
    			
    			int longestItem = 0;
    			String largestItem = "";
    			for(String item : symbolItems){
    				int length = item.length();
    				if(positionWord + length - 1 < word.length()){
	    				String part = (word.replace("-", "")).substring(positionWord, positionWord + length).toLowerCase();
	    				String previousPart = part;
	    				if(positionWord!=0)
	    					previousPart = (word.replace("-", "")).substring(positionWord - 1, positionWord + length - 1).toLowerCase();
        				
	    				if(item.equals(part)){
	    					words.add(tempWord.concat(item));
        					items.add(item);
        					if(length > longestItem){
        						longestItem = length;
        						largestItem = item;
    						}
        				} else if(item.equals(previousPart)){        					
        					int pos = tempWord.length();
        					for(int k = tempWord.length() - 1; k>0; k--){
        						char ch = tempWord.charAt(k);
        						if(ch != '-'){
        							pos = k;
        							break;
        						}
        					}
        					
        					String start = tempWord.substring(0, pos);
        					String end = "";
        					try{
        						end = tempWord.substring(pos+item.length()-1, tempWord.length());
        					}catch(Exception e){
        						end = tempWord.substring(pos, tempWord.length());
        					}
        					words.add(start.concat(end).concat(item));
        					items.add(item);
        					if(length > longestItem){
        						longestItem = length;
        						largestItem = item;
    						}
        				}
    				}
    			}
    			
    			String longestWord = "";
    			int currWordLen = 0;
    			
    			for(String w : words){
    				String currWord = w.replace("-", "");
    				String correctWord = (word.replace("-", "")).substring(0, currWord.length()).toLowerCase();
    				
    				if(currWord.equals(correctWord)){
    					if(currWord.length() > currWordLen){
    						currWordLen = currWord.length();
    						longestWord = w;
    					}
    				}
    			}
    			if(!longestWord.isEmpty()){
	    			tempWord = longestWord;
	    			positionWord += (currWordLen - positionWord);
    			}
    			/*while(j<items.size()){
    				String part = items.get(j);
    				if(part.equals(largestItem)){
    					tempWord = tempWord.concat(part);
    					
    					if(c.length()>1)
    						i+=c.length()-1;
    					
    					positionWord += longestItem;
    					applied = true;
    				}
    				
    				//String a = word.substring(0, buildPosition - numHyphens);
    				String diff = tempWord.replace("-", "");
//        				if(!diff.equals(a.toLowerCase()) && applied){
//        					String ws = workingSets.get(j);
//        					if(ws.length() == longestItem){
//        						tempWord = tempWord.substring(0, tempWord.length() - ws.length());
//        						workingSets.remove(j);
//        						applied = false;
//        					}
//        					longestItem = getMax(workingSets);
//        					//buildPosition = longestItem + position;
//        					j = -1;
//        				}
    				j++;
    			}*/
    			
    		} else {
    			tempWord = tempWord.concat(c);
    			positionWord++;
    			// Hyphen '-'
    			if(c.equals("\u002D")){
    				numHyphens++;
    				positionWord--;
    			}
    		}
    	}
    	
    	return tempWord.split("-");
    }
    
    
    private int getMax(ArrayList<String> list){
    	int max = 0;
    	for(int i=0; i<list.size(); i++){
    		if(list.get(i).length() > max)
    			max = list.get(i).length();
    	}
    	return max;
    }
    
    private String checkNextCharacter(String s, String word, int position){
    	if(position+1 >= word.length())
    		return s;
    	
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
    	return s;
    }
}

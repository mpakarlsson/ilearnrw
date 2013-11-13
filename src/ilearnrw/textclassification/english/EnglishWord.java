package ilearnrw.textclassification.english;

import java.util.ArrayList;
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
                    
                    
                    System.out.println(syllables);
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
    	int positionPhonetics = 0;
    	int positionWord = 0;
    	int numHyphens = 0;

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
    		
    		if(c.equals("d") && i+1<len){
    			String nextChar = Character.toString(tempPhon.charAt(i+1));
    			
    			// ʒ - http://www.fileformat.info/info/unicode/char/0292/index.htm
    			if(nextChar.equals("\u0292"))
    				c = c.concat(nextChar);
    		}
    		
    		if(ssl.containsKey(c)){
    			ArrayList<String> availableItems = ssl.get(c);
    			ArrayList<String> workingSets = new ArrayList<String>();
    			int longestItem = 0;
    			
    			for(String s : availableItems){
    				String diff = tempWord.replace("-", "");
    				//String subWord = word.substring(position - numHyphens, position + s.length() - numHyphens);
//        				if(s.equals(subWord.toLowerCase())){
//        					workingSets.add(s);
//        					if(s.length() > longestItem)
//        						longestItem = s.length();
//        					
//        				}
    			}
    			
    			//int buildPosition = position + longestItem;
    			int j = 0;
    			boolean applied = false;
    			
    			while(j<workingSets.size()){
    				String part = workingSets.get(j);
    				if(part.length() == longestItem){
    					tempWord = tempWord.concat(part);
    					if(c.length()>1)
    						i+=c.length()-1;
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
    			}
    			
    		} else {
    			tempWord = tempWord.concat(c);
    			
    			// Hyphen '-'
    			if(c.equals("\u002D"))
    				numHyphens++;
    		}
    		//position++;
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
}

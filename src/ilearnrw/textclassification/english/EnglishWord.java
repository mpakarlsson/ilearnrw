package ilearnrw.textclassification.english;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.util.ArrayList;
import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.GraphemePhonemePair;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;
import ilearnrw.utils.LanguageCode;


public class EnglishWord extends Word {
	private static final long serialVersionUID = 1L;
	private String suffix;
	private String suffixType;
	private String prefix;
	private String prefixType;
	private CharacterInfo chInfo;
	
	private boolean isListGameWord;
	
    //We put inside only lower case words
    public EnglishWord(String word) {
    	super(word);
    	
        wordUnmodified = word;
		super.languageCode = LanguageCode.EN;
        checkType();
        syllabism();
        super.word = word.toLowerCase();
        createCVForm();
        
        isListGameWord = true;
        frequency = 5001;
        setSuffix("");
        setSuffixType("SUFFIX_NONE");
        setPrefix("");
        setPrefixType("PREFIX_NONE");
        super.setStem("");
    }

    //We put inside only lower case words
    public EnglishWord(String word, String phonetic, String stem, ArrayList<GraphemePhonemePair> phoneticList, String[] graphemeSyllables, String suffix, String suffixType, String prefix, String prefixType, int numSyllables, int frequency, WordType type) {
    	super(word, type);
    	super.wordUnmodified = word;
		super.languageCode = LanguageCode.EN;
        checkType();
        //syllabism();
        syllables = graphemeSyllables;
        super.word = word;
        
        phonetics = phonetic;
        this.numSyllables = numSyllables;
		graphemesPhonemes = phoneticList;
        
		isListGameWord = true;
        this.frequency = frequency;
        
        this.setSuffix(suffix);
        this.setSuffixType(suffixType);
        
        this.setPrefix(prefix);
        this.setPrefixType(prefixType);
        
        super.setStem(stem);
        createCVForm();
    }

    
    protected void syllabism(){
        if(Program.getDictionary().containsKey(word)){
                ArrayList<String> data = Program.getDictionary().get(word);
                numSyllables = Integer.parseInt(data.get(1));
                
                String ipa = data.get(3);
                if(ipa.contains("<"))
                	syllables = new String[] {""};
                else
                	syllables = syllabify(word);

        } else {
                numSyllables = countVowels();
                syllables = syllabify(word);
        }
    }
    
    protected void checkType(){
            //if (isVerb(word))...
            //...
            type = WordType.Unknown;
    }
    
    protected char upperCharToCV(char x){
		// y = consonant at beginning of syllable, vowel everywhere else
        switch (Character.toUpperCase(x)){
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
        cvForm = "-";
        
        for(String part : syllables){
        	for(int i=0; i<part.length(); i++){
        		char c = part.charAt(i);
        		char cv = upperCharToCV(c);
        		if(c=='y' && i==0)
        			cv = 'c';
        		
                cvForm += cv;
        	}
        	cvForm += "-";
        }            
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
	
    public boolean getListAsGameWord(){
    	return isListGameWord;
    }
    
    public void setListAsGameWord(boolean value){
    	isListGameWord = value;
    }
    
	public String getSuffix() {
		return suffix;
	}

	private void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getSuffixType() {
		return suffixType;
	}

	public void setSuffixType(String suffixType) {
		this.suffixType = suffixType;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefixType() {
		return prefixType;
	}

	public void setPrefixType(String prefixType) {
		this.prefixType = prefixType;
	}

	private String[] syllabify(String word){    	
    	String fword = word;
    	
    	int numVowels = 0;
    	int tempSyllables = 1;
    	if(numSyllables==1)
    		numVowels=countVowels();

    	if(numVowels > 1 || numSyllables > 1){	    	
	    	fword = splitAtDoubleLetters(fword, tempSyllables, numVowels);
	    	tempSyllables = countSyllables(fword);
	    	fword = splitAtTwoConsonatsBetweenTwoVowels(fword, tempSyllables, numVowels);
	    	tempSyllables = countSyllables(fword);
	    	fword = splitWordBeforeSingleConsonant(fword, tempSyllables, numVowels);
	    	tempSyllables = countSyllables(fword);
    	}
    	
    	return fword.split("-");
    }

	/**
	 * Method that looks for 'cc' pattern and that the characters are identical
	 * 	e.g. excommunication -> mm -> excom-munication
	 */
	private String splitAtDoubleLetters(String word, int nSyllables, int nVowels){
		for(int i=0; i<word.length(); i++){
			int tempSyllables = nVowels==0 ? numSyllables : nVowels;
			
			if(i+1 >= word.length() || nSyllables>=tempSyllables)
				break;
			
			char curr = word.charAt(i);
			char next = word.charAt(i+1);
			
			char currType = upperCharToCV(curr);
			char nextType = upperCharToCV(next);
				
			if(curr == next && (currType=='c' && nextType=='c')){
				String part = word.substring(i+1);
				boolean valid = false;
				
				for(char c : part.toCharArray())
					if(upperCharToCV(c)=='v')
						valid = true;
				if(valid){
					word = new StringBuffer(word).insert(++i,"-").toString();
					nSyllables++;
					continue;
				}
			}		
		
		}		
		return word;
	}
	
	/**
	 * Method that iterates over the characters in a word and looks for 'vccv' pattern
	 * 	- v = vowel
	 *  - c = consonant
	 *  
	 *  Special consideration is taken with the digraphs. 
	 *  A digraph is inseparable, so splitting in one is not allowed. e.g. chicken -> digraph = ck 
	 * 
	 */
	private String splitAtTwoConsonatsBetweenTwoVowels(String word, int nSyllables, int nVowels){
		for(int i=0; i<word.length(); i++){
			char curr 		= word.charAt(i);
			char currType = upperCharToCV(curr);
			
			if(i-1<0 || currType=='v')
				continue;
			
			int tempSyllables = nVowels==0 ? numSyllables : nVowels;
			
			if(i+2>=word.length() || nSyllables>=tempSyllables)
				break;
			
			char prev 		= word.charAt(i-1);
			char n1 		= word.charAt(i+1);
			char n2 		= word.charAt(i+2);
			
			char prevType	= upperCharToCV(prev);
			char n1Type 	= upperCharToCV(n1);
			char n2Type 	= upperCharToCV(n2);
			
			if(i+3<word.length()){
				char n3 = word.charAt(i+3);
				char n3Type = upperCharToCV(n3);
				
				
				// Handle trigraphs
				if(i-2>0){
					char prev2 		= word.charAt(i-2);
					char prev2Type 	= upperCharToCV(prev2);
				
					if(prev2Type=='v' && prevType=='c' && currType=='c' && n1Type=='c' && n2Type=='c' && n3Type=='v'){
						boolean isFirstTrigraph 	= checkTrigraph(prev, curr, n1);
						boolean isSecondTrigraph 	= checkTrigraph(curr, n1, n2);
					
						if((isFirstTrigraph || isSecondTrigraph) && !(isFirstTrigraph && isSecondTrigraph)){
							if(isFirstTrigraph){
								word = new StringBuffer(word).insert(i+2,"-").toString();
								nSyllables++;
							} else if(isSecondTrigraph){
								word = new StringBuffer(word).insert(i,"-").toString();
								nSyllables++;
							}
							continue;
						}
						
						boolean isFirstDigraph = checkDigraph(prev, curr);
						boolean isSecondDigraph = checkDigraph(n1, n2);
						
						if(isFirstDigraph || isSecondDigraph)
							if(!(isFirstDigraph && isSecondDigraph)){
								if(isFirstDigraph){
									word = new StringBuffer(word).insert(i+2,"-").toString();
									nSyllables++;
								}
								else if(isSecondDigraph){
									word = new StringBuffer(word).insert(i+1,"-").toString();
									nSyllables++;
								}
								continue;
							}
						
					}
				}
				
				// Handle digraphs
				if(prevType=='v' && currType=='c' && n1Type=='c' && n2Type=='c' && n3Type=='v'){
					boolean isFirstDigraph = checkDigraph(currType, n1);
					boolean isSecondDigraph = checkDigraph(n1, n2);
					
					if(isFirstDigraph || isSecondDigraph){
						if(!(isFirstDigraph && isSecondDigraph)){
							if(isFirstDigraph){
								word = new StringBuffer(word).insert(i+2,"-").toString();
								nSyllables++;
							}
							else if(isSecondDigraph){
								word = new StringBuffer(word).insert(i+1,"-").toString();
								nSyllables++;
							}
							continue;
						}
					} else {
						word = new StringBuffer(word).insert(i+2, "-").toString();
						nSyllables++;
						continue;
					}
				}
			}
			
			if(prevType=='v' && currType=='c' && n1Type=='c' && n2Type=='v'){
				String consonants = new StringBuilder().append(curr).append(n1).toString();
				
				if(consonants.equals("ck") || consonants.equals("st"))
					word = checkDigraph(curr, n1) ? new StringBuffer(word).insert(i+2,"-").toString() : new StringBuffer(word).insert(i+1,"-").toString();
				else
					word = checkDigraph(curr, n1) ? new StringBuffer(word).insert(i,"-").toString() : new StringBuffer(word).insert(i+1,"-").toString();
				nSyllables++;
			}
		}
		return word;
	}
	
	/**
	 *  Method that checks for 'vcv' pattern.
	 *  Special consideration must be taken for digraphs.
	 * 
	 */
	private String splitWordBeforeSingleConsonant(String word, int nSyllables, int nVowels){
		for(int i=0; i<word.length(); i++){
			char curr = word.charAt(i);
			char cType = upperCharToCV(curr);
			
			if(i-1<0 || cType=='v')
				continue;
			
			int tempSyllables = nVowels==0 ? numSyllables : nVowels;
			
			if(i+1>=word.length() || (nSyllables>=tempSyllables && tempSyllables>1))
				break;
			
			char prev 	= word.charAt(i-1);
			char n1 	= word.charAt(i+1);
			
			char prevType = upperCharToCV(prev);
			char n1Type = upperCharToCV(n1);
			
			if(prevType=='v' && cType=='c' && n1Type=='v'){
				word = new StringBuffer(word).insert(i,"-").toString();
				nSyllables++;
			}
		}
		
		return word;
	}
	
    private boolean checkDigraph(char c1, char c2){
    	String value = new StringBuilder().append(c1).append(c2).toString();
    	for(String digraph : chInfo.getInstance().getDigraphs()){
    		if(digraph.equals(value))
    			return true;
    	}
    	return false;
    }
    
    private boolean checkTrigraph(char c1, char c2, char c3){
    	String value = new StringBuilder().append(c1).append(c2).append(c3).toString();
    	
    	for(String trigraph : chInfo.getInstance().getTrigraphs()){
    		if(trigraph.equals(value))
    			return true;
    	}
    	return false;
    }
    
    private int countSyllables(String word){
    	int count = 1;
    	int j = 0;
		while((j=(word.indexOf("-", j)+1)) > 0){
			count++;
		}
    	return count;
    }
}

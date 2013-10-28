package ilearnrw.textclassification.speller;
/*
 * GreekSpeller.java
 *
 * Created on 4 June 2008, 11:14 pm
 * 
 * Updated on 2 November 2012, 1:40 pm
 * 
 */


/**
 * @author Fouli Argyriou
 */

import java.util.*;

public class GreekSpeller implements Speller{
	private String stringToSpell;
    private ArrayList<String> vowels;
    private ArrayList<String> consonants;
    private ArrayList<String> undues;
    private ArrayList<String> twoDigitsVowels;
    private ArrayList<String> twoDigitsConsonants;
    private ArrayList<String> diphthongs;
    private ArrayList<String> unduesVowels;
    private ArrayList<String> undueDiphthongs;
    private ArrayList<String> combinations;
    private ArrayList<String> greekPrefixes;

    private ArrayList<String> firstTraversal;
    private ArrayList<String> secondTraversal;
    private ArrayList<String> reversedResult;
    private ArrayList<String> result;
    private String dataHolder;

    private char[] lowerCase = {'\u03B1', '\u03B2', '\u03B3', 
    		'\u03B4', '\u03B5', '\u03B6', '\u03B7', '\u03B8', 
    		'\u03B9', '\u03BA', '\u03BB', '\u03BC', '\u03BD', 
    		'\u03BE', '\u03BF', '\u03C0', '\u03C1', '\u03C3', 
    		'\u03C4', '\u03C5', '\u03C6', '\u03C7', '\u03C8', 
    		'\u03C9', '\u03AC', '\u03AD', '\u03AE', '\u03AF', 
    		'\u03CC', '\u03CD', '\u03CE', '\u03CA', '\u03CB', 
    		'\u0390','\u03B0', '\u03C2'};
    private char[] upperCase = {'\u0391', '\u0392', '\u0393', 
    		'\u0394', '\u0395', '\u0396', '\u0397', '\u0398', 
    		'\u0399', '\u039A', '\u039B', '\u039C', '\u039D', 
    		'\u039E', '\u039F', '\u03A0', '\u03A1', '\u03A3', 
    		'\u03A4', '\u03A5', '\u03A6', '\u03A7', '\u03A8', 
    		'\u03A9', '\u0386', '\u0388', '\u0389', '\u038A', 
    		'\u038C', '\u038E', '\u038F', '\u03AA', '\u03AB' };
    private int[] converter;

    /** Creates a new instance of GreekSpeller */
    public GreekSpeller() {
    	this("");
    }


    /** Creates a new instance of GreekSpeller */
    public GreekSpeller(String stringToSpell) {
		this.stringToSpell = stringToSpell;
		this.vowels = new ArrayList<String>();
		this.consonants = new ArrayList<String>();
		this.undues = new ArrayList<String>();
		this.twoDigitsVowels = new ArrayList<String>();
		this.twoDigitsConsonants = new ArrayList<String>();
		this.diphthongs = new ArrayList<String>();
		this.unduesVowels = new ArrayList<String>();
		this.combinations = new ArrayList<String>();
		this.greekPrefixes = new ArrayList<String>();
		this.undueDiphthongs = new ArrayList<String>();
		this.initLists();
		this.firstTraversal = new ArrayList<String>();
		this.secondTraversal = new ArrayList<String>();
		this.reversedResult = new ArrayList<String>();
		this.result = new ArrayList<String>();
		this.dataHolder = "";        
    }

    /**
     * Sets the string to spell.
     */
    public void setStringToSpell(String stringToSpell) {
    	this.stringToSpell = stringToSpell;
    }

    /**
     * Performs a spelling operation.
     */
    public void performSpelling() {
		/** Reset **/
		this.reset();
		String newStringToSpell = this.preprocess(this.stringToSpell);
	
		/** Create tokens **/
		ArrayList<String> tokens = new ArrayList<String>();
		for (int i=0; i<newStringToSpell.length(); i++){
		    tokens.add(newStringToSpell.substring(i, i+1));    
		}
	
		/** Apply first traversal **/
		this.firstTraverse(tokens); 
	
		/** reverse to second traversal **/
		for (int i=this.firstTraversal.size()-1; i>=0; i--){
		    this.secondTraversal.add(this.firstTraversal.get(i));
		} 
	
		/** Apply final rule **/
		this.applyTrivialRule();
	
		this.secondTraverse();
	
	
		/** reverse to result **/
		for (int i=this.reversedResult.size()-1; i>=0; i--){
		    this.result.add(this.reversedResult.get(i));
		}  
	
		this.postprocess(result);
    }
    /**
     * Returns the tokens of spelling.
     */
    public ArrayList<String> getTokens(){
    	return this.result;
    }

    /**
     * Returns spelling tokens as String.
     */
    public String toString(){
		StringBuffer buffer = new StringBuffer();
		for (Iterator it = this.result.iterator(); it.hasNext(); ){
		    buffer.append(it.next()+ (it.hasNext()?"-":""));
		}
		System.out.println(buffer.toString());
		return buffer.toString();
    }


    /**
     * Applies trivial rule
     */
    private void applyTrivialRule(){
		if (this.secondTraversal.size()>0){
		    if (this.secondTraversal.get(0).equals("\u03C2")|| 
		    		this.secondTraversal.get(0).equals("\u03C3") || 
		    		this.secondTraversal.get(0).equals("\u03BD")){
		    	this.dataHolder = this.secondTraversal.get(0);
		    	this.secondTraversal.remove(0);
		    }
		}
    }

    /**
     * Recursive method: First Traverse.
     */
    private void firstTraverse(ArrayList<String> tokens){
		if (tokens.size()==0) return;
	
		if (tokens.size()>=4) {
		    String prefix = tokens.get(0) + tokens.get(1) + tokens.get(2) + tokens.get(3);
	
		    if (this.isUndueDiphthong(prefix)){
				tokens.remove(3);
				tokens.remove(2);
				tokens.remove(1);
				tokens.remove(0);
				this.firstTraversal.add(prefix);
				this.firstTraverse(tokens);
				return;
		    }      
		}
	
		if(tokens.size()>=3){
		    String prefix = tokens.get(0) + tokens.get(1) + tokens.get(2);
		    if (this.isUndueDiphthong(prefix)){
				tokens.remove(2);
				tokens.remove(1);
				tokens.remove(0);
				this.firstTraversal.add(prefix);
				this.firstTraverse(tokens);
				return;
		    }                
		}
	
		if(tokens.size()>=2){
		    String prefix = tokens.get(0) + tokens.get(1);
		    if (this.isUndueDiphthong(prefix) || 
		    		this.isDiphthong(prefix) || 
		    		this.isTwoDigitVowel(prefix) || 
		    		this.isTwoDigitConsonant(prefix) || 
		    		this.isCombination(prefix)) {
				tokens.remove(1);
				tokens.remove(0);
				this.firstTraversal.add(prefix);
				this.firstTraverse(tokens);
				return;
		    }
		}
		this.firstTraversal.add(tokens.remove(0));
		this.firstTraverse(tokens);
    }

    /**
     * Recursive method: Second Traverse.
     */
    private void secondTraverse(){         
    	/** Base **/
		if (this.secondTraversal.size()==0){
		    return;
		}

		/** Rule 1 **/
		if(this.secondTraversal.size()>=3){ 
		    if (this.isVowel(this.secondTraversal.get(0)) && 
		    		this.isConsonant(this.secondTraversal.get(1)) && 
		    		this.isVowel(this.secondTraversal.get(2))) {
				this.reversedResult.add(this.secondTraversal.get(1)+this.secondTraversal.get(0)+this.dataHolder);
				this.secondTraversal.remove(1);
				this.secondTraversal.remove(0);
				this.dataHolder = "";
				this.secondTraverse();
				return;
		    }
		}

		/** Rule 2a **/
		if (this.secondTraversal.size()>=4){
		   
		    if (this.isVowel(this.secondTraversal.get(0)) && 
		    		this.isConsonant(this.secondTraversal.get(1)) && 
		    		this.isConsonant(this.secondTraversal.get(2))&& 
		    		this.isVowel(this.secondTraversal.get(3)) && 
		    		this.isGreekPrefix(this.secondTraversal.get(2)+this.secondTraversal.get(1))){
				this.reversedResult.add(this.secondTraversal.get(2)+this.secondTraversal.get(1)+this.secondTraversal.get(0)+this.dataHolder);
				this.secondTraversal.remove(2);
				this.secondTraversal.remove(1);
				this.secondTraversal.remove(0);
				this.dataHolder = "";
				this.secondTraverse();
				return;   
		    }
		}

		/** Rule 2b **/
		if (this.secondTraversal.size()>=4){
		    if (this.isVowel(this.secondTraversal.get(0)) && 
		    		this.isConsonant(this.secondTraversal.get(1)) && 
		    		this.isConsonant(this.secondTraversal.get(2)) && 
		    		this.isVowel(this.secondTraversal.get(3))) {
				this.reversedResult.add(this.secondTraversal.get(1)+this.secondTraversal.get(0)+this.dataHolder);
				this.dataHolder = this.secondTraversal.get(2);
				this.secondTraversal.remove(2);
				this.secondTraversal.remove(1);
				this.secondTraversal.remove(0);
				this.secondTraverse();
				return;   
		    }
		}

		/** Rule 3a **/
		if (this.secondTraversal.size()>=5){
		    if (this.isVowel(this.secondTraversal.get(0)) && 
		    		this.isConsonant(this.secondTraversal.get(1)) && 
		    		this.isConsonant(this.secondTraversal.get(2)) && 
		    		this.isConsonant(this.secondTraversal.get(3)) && 
		    		this.isVowel(this.secondTraversal.get(4)) && 
		    		this.isGreekPrefix(this.secondTraversal.get(3)+this.secondTraversal.get(2))){
				this.reversedResult.add(this.secondTraversal.get(3)+this.secondTraversal.get(2)+this.secondTraversal.get(1)+this.secondTraversal.get(0)+this.dataHolder);
				this.dataHolder = "";
				this.secondTraversal.remove(3);
				this.secondTraversal.remove(2);
				this.secondTraversal.remove(1);
				this.secondTraversal.remove(0);
				this.secondTraverse();
				return;   
		    }
		}

		/** Rule 3b (?????) **/
		if (this.secondTraversal.size()>=5){
		    if (this.isVowel(this.secondTraversal.get(0)) && 
		    		this.isConsonant(this.secondTraversal.get(1)) && 
		    		this.isConsonant(this.secondTraversal.get(2)) && 
		    		this.isConsonant(this.secondTraversal.get(3)) && 
		    		this.isVowel(this.secondTraversal.get(4))){
				this.reversedResult.add(this.secondTraversal.get(2)+this.secondTraversal.get(1)+this.secondTraversal.get(0)+this.dataHolder);
				this.dataHolder = this.secondTraversal.get(3);
				this.secondTraversal.remove(3);
				this.secondTraversal.remove(2);
				this.secondTraversal.remove(1);
				this.secondTraversal.remove(0);
				this.secondTraverse();
				return;   
		    }
		}

		/** Rule 3c **/
		if (this.secondTraversal.size()>=6){
		    if (this.isVowel(this.secondTraversal.get(0)) && 
		    		this.isConsonant(this.secondTraversal.get(1)) && 
		    		this.isConsonant(this.secondTraversal.get(2)) && 
		    		this.isConsonant(this.secondTraversal.get(3)) && 
		    		this.isConsonant(this.secondTraversal.get(4)) && 
		    		this.isVowel(this.secondTraversal.get(5)) && 
		    		this.isGreekPrefix(this.secondTraversal.get(4)+this.secondTraversal.get(3))){
				this.reversedResult.add(this.secondTraversal.get(4)+this.secondTraversal.get(3)+this.secondTraversal.get(2)+this.secondTraversal.get(1)+this.secondTraversal.get(0)+this.dataHolder);
				this.dataHolder = "";
				this.secondTraversal.remove(4);
				this.secondTraversal.remove(3);
				this.secondTraversal.remove(2);
				this.secondTraversal.remove(1);
				this.secondTraversal.remove(0);
				this.secondTraverse();
				return;   
		    }
		}

		/** Rule 3d **/
		if (this.secondTraversal.size()>=6){
		    if (this.isVowel(this.secondTraversal.get(0)) &&
		    		this.isConsonant(this.secondTraversal.get(1)) &&
				    this.isConsonant(this.secondTraversal.get(2)) &&
				    this.isConsonant(this.secondTraversal.get(3)) &&
				    this.isConsonant(this.secondTraversal.get(4)) &&
				    this.isVowel(this.secondTraversal.get(5))){
				this.reversedResult.add(this.secondTraversal.get(3)+this.secondTraversal.get(2)+this.secondTraversal.get(1)+this.secondTraversal.get(0)+this.dataHolder);
				this.dataHolder = this.secondTraversal.get(4);
				this.secondTraversal.remove(4);
				this.secondTraversal.remove(3);
				this.secondTraversal.remove(2);
				this.secondTraversal.remove(1);
				this.secondTraversal.remove(0);
				this.secondTraverse();
				return;   
		    }
		}        

		/** Rule 4 **/
		if (this.secondTraversal.size()>=2){
		    if (this.isVowel(this.secondTraversal.get(0)) && 
		    		this.isVowel(this.secondTraversal.get(1))){
				this.reversedResult.add(this.secondTraversal.get(0)+this.dataHolder);
				this.dataHolder = "";
				this.secondTraversal.remove(0);
				this.secondTraverse();
				return;   
		    }
		}

		/** Default **/
		String finalString = "";
		for (Iterator it = this.secondTraversal.iterator(); it.hasNext(); ){
		    finalString = it.next() + finalString;
		}
		this.secondTraversal.clear();
		this.reversedResult.add(finalString+this.dataHolder);
		return;
	    }

    /**
     * Resets the required fields to perform a new spelling operation.
     */
    private void reset() {
		this.dataHolder = "";
		this.result.clear();
		this.firstTraversal.clear();
		this.secondTraversal.clear();
		this.reversedResult.clear(); 
    }



    /**
     * To lowerCase
     */
    private String preprocess(String str) {
		String lowerCaseString = str;
		boolean containsTwoSpecialCharacterInFront = false;
		
		this.converter = new int[str.length()];
		
		if (lowerCaseString.startsWith("<<")) {	   
		    lowerCaseString = lowerCaseString.substring(2, lowerCaseString.length());
		    containsTwoSpecialCharacterInFront = true;
		}
		
		if (lowerCaseString.endsWith(".") || lowerCaseString.endsWith(",") || lowerCaseString.endsWith(";") || lowerCaseString.endsWith("!") || lowerCaseString.endsWith("?") || lowerCaseString.endsWith(";") || lowerCaseString.endsWith("-")){	   
		    lowerCaseString = lowerCaseString.substring(0, lowerCaseString.length()-1);
		}
		if (lowerCaseString.endsWith(">>")){	   
		    lowerCaseString = lowerCaseString.substring(0, lowerCaseString.length()-2); 
		}
		
		else if (lowerCaseString.endsWith(">")){	   
		    lowerCaseString = lowerCaseString.substring(0, lowerCaseString.length()-1);
		}
		
		for (int i=0; i<str.length(); i++){	    
		    if (!containsTwoSpecialCharacterInFront){
		    	int position = this.positionOf(this.upperCase, str.charAt(i));
			
		    	if (position != -1) {
		    		if(i==str.length()-1 && str.charAt(i)=='\u03C2'){
		    			lowerCaseString = lowerCaseString.replace(str.charAt(i),'\u03A3');
		    		}
		    		else {
		    			lowerCaseString = lowerCaseString.replace(str.charAt(i),this.lowerCase[position]);		   
		    		}
		    		this.converter[i] = 1;
		    	}
			}
		}
		return lowerCaseString;
    }

    /**
     * To upperCase
     */
    private void postprocess(ArrayList<String> result) {
		int counter = 0;
		for (int i=0; i<this.result.size(); i++){
		    String token = this.result.get(i);
	
		    for (int j=0; j<token.length(); j++) {
				if (this.converter[counter]==1){
				    char c = token.charAt(j);
				    int position = this.positionOf(this.lowerCase, c);
				    char replaceChar = this.upperCase[position];
		
				    token = token.substring(0, j) + replaceChar + token.substring(j+1, token.length());
				    this.result.set(i, token);
				}   
				counter++;
		    }
		}
    }


    private int positionOf(char[] array, char checkChar) {
		for (int i = 0; i < array.length; i++){
		    if (array[i] == checkChar) return i;
		}
		return -1;
    }

    /**
     * Determine whether the given str is vowel...
     */
    private boolean isVowel(String str){
		return this.vowels.contains(str) || 
		this.twoDigitsVowels.contains(str) ||
		this.isDiphthong(str) ||
		this.isUndueDiphthong(str) || 
		this.combinations.contains(str);
    } 

    /**
     * Determine whether the given str is consonant...
     */
    private boolean isConsonant(String str) {
		return this.consonants.contains(str) || this.isTwoDigitConsonant(str);
    }

    /**
     * Determine whether the given str is undue diphthong...
     */
    private boolean isUndueDiphthong(String str) {
    	return this.undueDiphthongs.contains(str);
    }

    /**
     * Determine whether the given str is undue vowel...
     */
    private boolean isUndueVowel(String str){
    	return this.unduesVowels.contains(str);
    }

    /**
     * Determine whether the given str is two-digit consonant...
     */
    private boolean isTwoDigitConsonant(String str){
    	return this.twoDigitsConsonants.contains(str);
    }

    /**
     * Determine whether the given str is two-digit vowel...
     */
    private boolean isTwoDigitVowel(String str) {
    	return this.twoDigitsVowels.contains(str);
    }

    /**
     * Determine whether the given str is dipthong...
     */
    private boolean isDiphthong(String str){
    	return this.diphthongs.contains(str);
    }

    /**
     * Determine whether the given str is combination...
     */
    private boolean isCombination(String str){
    	return this.combinations.contains(str);
    }

    /**
     * Determine whether the given str is undue...
     */
    private boolean isUndue(String str) {
    	return this.undues.contains(str);
    }

    /**
     * Determine whether the given str is greek prefix...
     */
    private boolean isGreekPrefix(String str) {       
    	return this.greekPrefixes.contains(str);
    }   


    /**
     * Appropriately initiates the field lists...
     */
    private void initLists(){
		/* Vowels... */
		this.vowels.add("\u03B1");
		this.vowels.add("\u03B5");
		this.vowels.add("\u03B7");	
		this.vowels.add("\u03B9");
		this.vowels.add("\u03BF");
		this.vowels.add("\u03C5");
		this.vowels.add("\u03C9");
		this.vowels.add("\u03AC");
		this.vowels.add("\u03AD");
		this.vowels.add("\u03AE");
		this.vowels.add("\u03AF");
		this.vowels.add("\u03CC");
		this.vowels.add("\u03CD");
		this.vowels.add("\u03CE");		

		/* Vowels for checking undue diphthogs */
		this.unduesVowels.add("\u03AC");
		this.unduesVowels.add("\u03AD");
		this.unduesVowels.add("\u03AE");
		this.unduesVowels.add("\u03AF");
		this.unduesVowels.add("\u03CC");
		this.unduesVowels.add("\u03CD");
		this.unduesVowels.add("\u03CE");	

		/* Consonants... */
		this.consonants.add("\u03B2");
		this.consonants.add("\u03B3");
		this.consonants.add("\u03B4");
		this.consonants.add("\u03B6");
		this.consonants.add("\u03B8");
		this.consonants.add("\u03BA");
		this.consonants.add("\u03BB");
		this.consonants.add("\u03BC");
		this.consonants.add("\u00B5");
		this.consonants.add("\u03BD");
		this.consonants.add("\u03BE");
		this.consonants.add("\u03C0");
		this.consonants.add("\u03C1");
		this.consonants.add("\u03C3");
		this.consonants.add("\u03C4");
		this.consonants.add("\u03C6");
		this.consonants.add("\u03C7");
		this.consonants.add("\u03C8");
		this.consonants.add("\u03C2");

		/*Two Digits Vowels...*/
		this.twoDigitsVowels.add("\u03BF\u03C5");
		this.twoDigitsVowels.add("\u03BF\u03CD");
		this.twoDigitsVowels.add("\u03B1\u03B9");
		this.twoDigitsVowels.add("\u03B1\u03AF");
		this.twoDigitsVowels.add("\u03B5\u03B9");
		this.twoDigitsVowels.add("\u03B5\u03AF");
		this.twoDigitsVowels.add("\u03BF\u03B9");
		this.twoDigitsVowels.add("\u03BF\u03AF");
		this.twoDigitsVowels.add("\u03C5\u03B9");

		/*Two Digits Consonants...*/
		this.twoDigitsConsonants.add("\u03BC\u03C0");
		this.twoDigitsConsonants.add("\u00B5\u03C0");
		this.twoDigitsConsonants.add("\u03BD\u03C4");
		this.twoDigitsConsonants.add("\u03B3\u03BA");
		this.twoDigitsConsonants.add("\u03C4\u03C3");
		this.twoDigitsConsonants.add("\u03C4\u03B6");
	
		/* Diphthongs... */
		this.diphthongs.add("\u03B1\u03CA");
		this.diphthongs.add("\u03AC\u03B9");
		this.diphthongs.add("\u03B1\u03B7");
		this.diphthongs.add("\u03BF\u03CA");
		this.diphthongs.add("\u03CC\u03B9");
		this.diphthongs.add("\u03BF\u03B7");
	
		/* Combinations... */
		this.combinations.add("\u03B1\u03C5");
		this.combinations.add("\u03B5\u03C5");
		this.combinations.add("\u03B1\u03CD");
		this.combinations.add("\u03B5\u03CD");

	
		/* Undues... */
		this.undues.add("\u03B9");
		this.undues.add("\u03C5");
		this.undues.add("\u03BF\u03B9");
		this.undues.add("\u03B5\u03B9");
	
		/* Two Consonants that a greek word can start */
		this.greekPrefixes.add("\u03B2\u03B4");
		this.greekPrefixes.add("\u03B2\u03BB");
		this.greekPrefixes.add("\u03B2\u03C1");
		this.greekPrefixes.add("\u03B3\u03B4");
		this.greekPrefixes.add("\u03B3\u03BA");
		this.greekPrefixes.add("\u03B3\u03BB");
		this.greekPrefixes.add("\u03B3\u03BD");
		this.greekPrefixes.add("\u03B3\u03C1");
		this.greekPrefixes.add("\u03B4\u03C1");
		this.greekPrefixes.add("\u03B8\u03BB");
		this.greekPrefixes.add("\u03B8\u03BD");
		this.greekPrefixes.add("\u03B8\u03C1");
		this.greekPrefixes.add("\u03BA\u03B2");
		this.greekPrefixes.add("\u03BA\u03BB");
		this.greekPrefixes.add("\u03BA\u03BD");
		this.greekPrefixes.add("\u03BA\u03C1");
		this.greekPrefixes.add("\u03BA\u03C4");
		this.greekPrefixes.add("\u03C0\u03BB");
		this.greekPrefixes.add("\u03C0\u03BD");
		this.greekPrefixes.add("\u03C0\u03C1");
		this.greekPrefixes.add("\u03C3\u03B2");
		this.greekPrefixes.add("\u03C3\u03B3");	
		this.greekPrefixes.add("\u03C3\u03B8");
		this.greekPrefixes.add("\u03C3\u03BA");
		this.greekPrefixes.add("\u03C3\u03BB");
		this.greekPrefixes.add("\u03C3\u03BC");
		this.greekPrefixes.add("\u03C3\u00B5");
		this.greekPrefixes.add("\u03C3\u03BD");
		this.greekPrefixes.add("\u03C3\u03C0");
		this.greekPrefixes.add("\u03C3\u03C4");
		this.greekPrefixes.add("\u03C3\u03C6");
		this.greekPrefixes.add("\u03C3\u03C7");
		this.greekPrefixes.add("\u03C4\u03B6");
		this.greekPrefixes.add("\u03C4\u03BC");
		this.greekPrefixes.add("\u03C4\u00B5");
		this.greekPrefixes.add("\u03C4\u03C1");
		this.greekPrefixes.add("\u03C4\u03C3");
		this.greekPrefixes.add("\u03C6\u03B8");
		this.greekPrefixes.add("\u03C6\u03BB");
		this.greekPrefixes.add("\u03C6\u03C1");
		this.greekPrefixes.add("\u03C6\u03C4");
		this.greekPrefixes.add("\u03C7\u03BB");
		this.greekPrefixes.add("\u03C7\u03BD");
		this.greekPrefixes.add("\u03C7\u03C1");	
		this.greekPrefixes.add("\u03C7\u03C4");	
		this.greekPrefixes.add("\u03B8\u03C1");	


		for (Iterator<String> it = this.undues.iterator(); it.hasNext(); ){
		    String undue = it.next();
		    for (Iterator<String> innerIt = this.unduesVowels.iterator(); innerIt.hasNext(); ) {
		    	String undueVowel = innerIt.next();
		    	this.undueDiphthongs.add(undue+undueVowel);
		    }
		    for (Iterator<String> innerIt = this.twoDigitsVowels.iterator(); innerIt.hasNext(); ){
		    	String twoDigitVowel = innerIt.next();                
		    	this.undueDiphthongs.add(undue+twoDigitVowel);
		    }
		}
    }

    /**
     * Returns the hyphen syllable
     * TODO: Description
     * @param tokens
     * @return
     */
    public String getHyphenSyllable(ArrayList<String> tokens){
		String hyphenSyllable = "";
		for (Iterator<String> it = tokens.iterator(); it.hasNext();){
		    hyphenSyllable = it.next();
		    if (hyphenSyllable.contains("\u03AC") || 
		    		hyphenSyllable.contains("\u03AD") || 
				    hyphenSyllable.contains("\u03AE") || 
				    hyphenSyllable.contains("\u03AF") || 
				    hyphenSyllable.contains("\u03CC") ||
				    hyphenSyllable.contains("\u03CD") || 
				    hyphenSyllable.contains("\u03CE") ||
				    hyphenSyllable.contains("\u0390") ||
				    hyphenSyllable.contains("\u03B0") ||
				    hyphenSyllable.contains("\u0386") || 
				    hyphenSyllable.contains("\u0388") || 
				    hyphenSyllable.contains("\u0389") || 
				    hyphenSyllable.contains("\u038A") || 
				    hyphenSyllable.contains("\u038C") ||
				    hyphenSyllable.contains("\u038E") || 
				    hyphenSyllable.contains("\u038F")) {			
		    	return hyphenSyllable;
		    }	    
		}
		return "";
    }


	@Override
	public int getTokensNumber() {
		return result.size();
	}


	@Override
	public String getToken(int index) {
		if (index<0 || index>this.getTokensNumber()) return null;
		else return result.get(index);
	}


	@Override
	public String[] getTokensArray() {
		String res[] = new String[getTokensNumber()];
		for (int i=0;i<res.length;i++){
			res[i] = getToken(i);
		}
		return res;
	}
	
	

}

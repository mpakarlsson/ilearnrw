package ilearnrw.languagetools.greek;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
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

    private char[] lowerCase = {'α', 'β', 'γ', 
    		'δ', 'ε', 'ζ', 'η', 'θ', 
    		'ι', 'κ', 'λ', 'μ', 'ν', 
    		'ξ', 'ο', 'π', 'ρ', 'σ', 
    		'τ', 'υ', 'φ', 'χ', 'ψ', 
    		'ω', 'ά', 'έ', 'ή', 'ί', 
    		'ό', 'ύ', 'ώ', 'ϊ', 'ϋ', 
    		'ΐ','ΰ', 'ς'};
    private char[] upperCase = {'Α', 'Β', 'Γ', 
    		'Δ', 'Ε', 'Ζ', 'Η', 'Θ', 
    		'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 
    		'Ξ', 'Ο', 'Π', 'Ρ', 'Σ', 
    		'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 
    		'Ω', 'Ά', 'Έ', 'Ή', 'Ί', 
    		'Ό', 'Ύ', 'Ώ', 'Ϊ', 'Ϋ' };
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
		if (result.size() == 0)
			this.result.add(this.stringToSpell);
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
		/*if (this.secondTraversal.size()>0){
		    if (this.secondTraversal.get(0).equals("ς")|| 
		    		this.secondTraversal.get(0).equals("σ") || 
		    		this.secondTraversal.get(0).equals("ν")){
		    	this.dataHolder = this.secondTraversal.get(0);
		    	this.secondTraversal.remove(0);
		    }
		}*/
    	// TODO check if it works
		if (this.secondTraversal.size()>0){
		    if (this.consonants.contains(this.secondTraversal.get(0))){
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
		    		if(i==str.length()-1 && str.charAt(i)=='ς'){
		    			lowerCaseString = lowerCaseString.replace(str.charAt(i),'Σ');
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
		this.vowels.add("α");
		this.vowels.add("ε");
		this.vowels.add("η");	
		this.vowels.add("ι");
		this.vowels.add("ο");
		this.vowels.add("υ");
		this.vowels.add("ω");
		this.vowels.add("ά");
		this.vowels.add("έ");
		this.vowels.add("ή");
		this.vowels.add("ί");
		this.vowels.add("ό");
		this.vowels.add("ύ");
		this.vowels.add("ώ");		

		/* Vowels for checking undue diphthogs */
		// TODO check if it works!
		this.unduesVowels.add("α");
		this.unduesVowels.add("ε");
		this.unduesVowels.add("η");	
		this.unduesVowels.add("ι");
		this.unduesVowels.add("ο");
		this.unduesVowels.add("υ");
		this.unduesVowels.add("ω");
		//chris' input ends
		
		this.unduesVowels.add("ά");
		this.unduesVowels.add("έ");
		this.unduesVowels.add("ή");
		this.unduesVowels.add("ί");
		this.unduesVowels.add("ό");
		this.unduesVowels.add("ύ");
		this.unduesVowels.add("ώ");	

		/* Consonants... */
		this.consonants.add("β");
		this.consonants.add("γ");
		this.consonants.add("δ");
		this.consonants.add("ζ");
		this.consonants.add("θ");
		this.consonants.add("κ");
		this.consonants.add("λ");
		this.consonants.add("μ");
		this.consonants.add("μ");
		this.consonants.add("ν");
		this.consonants.add("ξ");
		this.consonants.add("π");
		this.consonants.add("ρ");
		this.consonants.add("σ");
		this.consonants.add("τ");
		this.consonants.add("φ");
		this.consonants.add("χ");
		this.consonants.add("ψ");
		this.consonants.add("ς");

		/*Two Digits Vowels...*/
		this.twoDigitsVowels.add("ου");
		this.twoDigitsVowels.add("ού");
		this.twoDigitsVowels.add("αι");
		this.twoDigitsVowels.add("αί");
		this.twoDigitsVowels.add("ει");
		this.twoDigitsVowels.add("εί");
		this.twoDigitsVowels.add("οι");
		this.twoDigitsVowels.add("οί");
		this.twoDigitsVowels.add("υι");

		/*Two Digits Consonants...*/
		this.twoDigitsConsonants.add("μπ");
		this.twoDigitsConsonants.add("μπ");
		this.twoDigitsConsonants.add("ντ");
		this.twoDigitsConsonants.add("γκ");
		this.twoDigitsConsonants.add("τσ");
		this.twoDigitsConsonants.add("τζ");
	
		/* Diphthongs... */
		this.diphthongs.add("αϊ");
		this.diphthongs.add("άι");
		this.diphthongs.add("αη");
		this.diphthongs.add("οϊ");
		this.diphthongs.add("όι");
		this.diphthongs.add("οη");
	
		/* Combinations... */
		this.combinations.add("αυ");
		this.combinations.add("ευ");
		this.combinations.add("αύ");
		this.combinations.add("εύ");

	
		/* Undues... */
		this.undues.add("ι");
		this.undues.add("υ");
		this.undues.add("οι");
		this.undues.add("ει");
	
		/* Two Consonants that a greek word can start */
		this.greekPrefixes.add("βδ");
		this.greekPrefixes.add("βλ");
		this.greekPrefixes.add("βρ");
		this.greekPrefixes.add("γδ");
		this.greekPrefixes.add("γκ");
		this.greekPrefixes.add("γλ");
		this.greekPrefixes.add("γν");
		this.greekPrefixes.add("γρ");
		this.greekPrefixes.add("δρ");
		this.greekPrefixes.add("θλ");
		this.greekPrefixes.add("θν");
		this.greekPrefixes.add("θρ");
		this.greekPrefixes.add("κβ");
		this.greekPrefixes.add("κλ");
		this.greekPrefixes.add("κν");
		this.greekPrefixes.add("κρ");
		this.greekPrefixes.add("κτ");
		this.greekPrefixes.add("πλ");
		this.greekPrefixes.add("πν");
		this.greekPrefixes.add("πρ");
		this.greekPrefixes.add("σβ");
		this.greekPrefixes.add("σγ");	
		this.greekPrefixes.add("σθ");
		this.greekPrefixes.add("σκ");
		this.greekPrefixes.add("σλ");
		this.greekPrefixes.add("σμ");
		this.greekPrefixes.add("σμ");
		this.greekPrefixes.add("σν");
		this.greekPrefixes.add("σπ");
		this.greekPrefixes.add("στ");
		this.greekPrefixes.add("σφ");
		this.greekPrefixes.add("σχ");
		this.greekPrefixes.add("τζ");
		this.greekPrefixes.add("τμ");
		this.greekPrefixes.add("τμ");
		this.greekPrefixes.add("τρ");
		this.greekPrefixes.add("τσ");
		this.greekPrefixes.add("φθ");
		this.greekPrefixes.add("φλ");
		this.greekPrefixes.add("φρ");
		this.greekPrefixes.add("φτ");
		this.greekPrefixes.add("χλ");
		this.greekPrefixes.add("χν");
		this.greekPrefixes.add("χρ");	
		this.greekPrefixes.add("χτ");	
		this.greekPrefixes.add("θρ");	


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
     * @param tokens
     * @return
     */
    public String getHyphenSyllable(ArrayList<String> tokens){
		String hyphenSyllable = "";
		for (Iterator<String> it = tokens.iterator(); it.hasNext();){
		    hyphenSyllable = it.next();
		    if (hyphenSyllable.contains("ά") || 
		    		hyphenSyllable.contains("έ") || 
				    hyphenSyllable.contains("ή") || 
				    hyphenSyllable.contains("ί") || 
				    hyphenSyllable.contains("ό") ||
				    hyphenSyllable.contains("ύ") || 
				    hyphenSyllable.contains("ώ") ||
				    hyphenSyllable.contains("ΐ") ||
				    hyphenSyllable.contains("ΰ") ||
				    hyphenSyllable.contains("Ά") || 
				    hyphenSyllable.contains("Έ") || 
				    hyphenSyllable.contains("Ή") || 
				    hyphenSyllable.contains("Ί") || 
				    hyphenSyllable.contains("Ό") ||
				    hyphenSyllable.contains("Ύ") || 
				    hyphenSyllable.contains("Ώ")) {			
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

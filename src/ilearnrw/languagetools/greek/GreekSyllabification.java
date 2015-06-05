package ilearnrw.languagetools.greek;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.util.ArrayList;
import java.util.Iterator;


public class GreekSyllabification implements Speller{
	private String stringToSpell;
    private ArrayList<String> vowels;
    private ArrayList<String> consonants;
    private ArrayList<String> nonSeperable;
    private ArrayList<String> strongNonSeperable;
    private ArrayList<String> greekPrefixes;

    private ArrayList<String> result;

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
    public GreekSyllabification() {
    	this("");
    }


    /** Creates a new instance of GreekSpeller */
    public GreekSyllabification(String stringToSpell) {
    	this.stringToSpell = preprocess(stringToSpell);
		this.vowels = new ArrayList<String>();
		this.consonants = new ArrayList<String>();
		this.nonSeperable = new ArrayList<String>();
		this.strongNonSeperable = new ArrayList<String>();
		this.greekPrefixes = new ArrayList<String>();
		this.result = new ArrayList<String>();
		this.initLists();
    }

    /**
     * Sets the string to spell.
     */
    public void setStringToSpell(String stringToSpell) {
    	this.stringToSpell = preprocess(stringToSpell);
    }

    /**
     * Performs a spelling operation.
     */
    public void performSpelling() {
		/** Reset **/
		this.reset();
		this.result.add(this.stringToSpell);
		this.splitSyllables(0);
		this.postprocess(result);

		//System.err.println(this.stringToSpell + " == " +result.toString());
    }
    
    private void splitSyllables(int i){
    	if (this.result.size() <= i)
    		return;
    	ArrayList<String> res;
    	res = vowelsRule(this.result.get(i));//lastFirstRule(this.result.get(i));
    	check(res, i);

    	res = firstRule(this.result.get(i));
    	check(res, i);

    	res = secondRule(this.result.get(i));
    	check(res, i);
    	
    	res = thirdRule(this.result.get(i));
    	check(res, i);
    	
    	splitSyllables(i+1);
    }
    
    private void check(ArrayList<String> res, int i){
    	if (res.size() >1){
    		result.remove(i);
    		result.add(i, res.remove(0));
    		result.add(i+1, res.remove(0));
    	}
    }
    
    private ArrayList<String> firstRule(String str){
    	for (int i=0; i<str.length()-2; i++){    		
	    	if (this.vowels.contains(""+str.charAt(i)) 
	    			&& this.consonants.contains(""+str.charAt(i+1))
	    			&& this.vowels.contains(""+str.charAt(i+2))){
	    		String str1 = str.substring(0, i+1);
	    		String str2 = str.substring(i+1);
	    		ArrayList<String> l1 = new ArrayList<String>();
	    		l1.add(str1);
	    		l1.add(str2);
	    		return l1;
	    	}
    	}
		ArrayList<String> l = new ArrayList<String>();
		l.add(str);
		return l;
    }
    
    private ArrayList<String> secondRule(String str){
    	for (int i=0; i<str.length()-3; i++){
	    	if (this.vowels.contains(""+str.charAt(i)) 
	    			&& this.vowels.contains(""+str.charAt(i+3))
	    			&& this.consonants.contains(""+str.charAt(i+1))
	    			&& this.consonants.contains(""+str.charAt(i+2))){
		    	if (greekPrefixes.contains(str.substring(i+1, i+3))){
		    		String str1 = str.substring(0, i+1);
		    		String str2 = str.substring(i+1);
		    		ArrayList<String> l1 = new ArrayList<String>();
		    		l1.add(str1);
		    		l1.add(str2);
		    		return l1;
		    	}
		    	else {
		    		String str1 = str.substring(0, i+2);
		    		String str2 = str.substring(i+2);
		    		ArrayList<String> l1 = new ArrayList<String>();
		    		l1.add(str1);
		    		l1.add(str2);
		    		return l1;
		    	}
	    	}
    	}
		ArrayList<String> l = new ArrayList<String>();
		l.add(str);
		return l;
    }
    
    private ArrayList<String> thirdRule(String str){
    	for (int i=0; i<str.length()-2; i++){
	    	if (this.vowels.contains(""+str.charAt(i))) {
	    		int j = i+1;
	    		while (j<str.length() && this.consonants.contains(""+str.charAt(j)))
	    				j++;
	    		if (j<str.length() && j-i>3
	    				&& this.vowels.contains(""+str.charAt(j))){
	    			if (greekPrefixes.contains(str.substring(i+1, i+3))){
			    		String str1 = str.substring(0, i+1);
			    		String str2 = str.substring(i+1);
			    		ArrayList<String> l1 = new ArrayList<String>();
			    		l1.add(str1);
			    		l1.add(str2);
			    		return l1;
	    			}
	    			else {
			    		String str1 = str.substring(0, i+2);
			    		String str2 = str.substring(i+2);
			    		ArrayList<String> l1 = new ArrayList<String>();
			    		l1.add(str1);
			    		l1.add(str2);
			    		return l1;
	    			}
	    		}
	    	}
    	}
		ArrayList<String> l = new ArrayList<String>();
		l.add(str);
		return l;
    }
    
    private ArrayList<String> vowelsRule(String str){
    	for (int i=0; i<str.length()-1; i++){
    		int j = i;
	    	while (j<str.length() && this.vowels.contains(""+str.charAt(j)))
	    		j++;
	    	if (j>i){
		    	int idx = multipleVowels(str.substring(i, j));
		    	if (idx > 0){
		    		String str1 = str.substring(0, i+idx);
		    		String str2 = str.substring(i+idx);
		    		ArrayList<String> l1 = new ArrayList<String>();
		    		l1.add(str1);
		    		l1.add(str2);
		    		return l1;
		    	}
	    	} 
    	}
		ArrayList<String> l = new ArrayList<String>();
		l.add(str);
		return l;
    }
    
    private ArrayList<String> lastFirstRule(String str){
    	for (int i=0; i<str.length()-1; i++){
    		// vvvv
    		if (i<str.length()-3 && this.vowels.contains(""+str.charAt(i)) 
	    			&& this.vowels.contains(""+str.charAt(i+1))
	    			&& this.vowels.contains(""+str.charAt(i+2))
	    			&& this.vowels.contains(""+str.charAt(i+3))
	    			&& this.nonSeperable.contains(str.substring(i, i+2))
	    			&& this.nonSeperable.contains(str.substring(i+2, i+4))
	    			&& !this.nonSeperable.contains(str.substring(i, i+4))
	    			&& !this.nonSeperable.contains(str.substring(i, i+3))
	    			&& !this.nonSeperable.contains(str.substring(i+1, i+4))){
	    		String str1 = str.substring(0, i+2);
	    		String str2 = str.substring(i+2);
	    		ArrayList<String> l1 = new ArrayList<String>();
	    		l1.add(str1);
	    		l1.add(str2);
	    		return l1;
	    	}
    		// vvv
	    	if (i<str.length()-2 && this.vowels.contains(""+str.charAt(i)) 
	    			&& this.vowels.contains(""+str.charAt(i+1))
	    			&& this.vowels.contains(""+str.charAt(i+2))
	    			&& this.nonSeperable.contains(str.substring(i, i+2))
	    			&& !this.nonSeperable.contains(str.substring(i, i+3))){
	    		String str1 = str.substring(0, i+2);
	    		String str2 = str.substring(i+2);
	    		ArrayList<String> l1 = new ArrayList<String>();
	    		l1.add(str1);
	    		l1.add(str2);
	    		return l1;
	    	} 
    		// vvv
	    	if (i<str.length()-2 && this.vowels.contains(""+str.charAt(i)) 
	    			&& this.vowels.contains(""+str.charAt(i+1))
	    			&& this.vowels.contains(""+str.charAt(i+2))
	    			&& !this.nonSeperable.contains(str.substring(i, i+2))
	    			&& !this.nonSeperable.contains(str.substring(i, i+3))){
	    		String str1 = str.substring(0, i+1);
	    		String str2 = str.substring(i+1);
	    		ArrayList<String> l1 = new ArrayList<String>();
	    		l1.add(str1);
	    		l1.add(str2);
	    		return l1;
	    	} 
	    	// vv
	    	if (this.vowels.contains(""+str.charAt(i)) 
	    			&& this.vowels.contains(""+str.charAt(i+1))
	    			&& !this.nonSeperable.contains(str.substring(i, i+2))){
	    		String str1 = str.substring(0, i+1);
	    		String str2 = str.substring(i+1);
	    		ArrayList<String> l1 = new ArrayList<String>();
	    		l1.add(str1);
	    		l1.add(str2);
	    		return l1;
	    	}
    	}
		ArrayList<String> l = new ArrayList<String>();
		l.add(str);
		return l;
    }
    
    /*
     * @param a greek string contains only vowels
     * @return the first index that the string needs to be split (-1 if not exists)
     */
    private int multipleVowels(String vv){
    	if (vv.length() <= 1)
    		return -1;
    	if (this.nonSeperable.contains(vv))
    		return -1;
    	if (!this.nonSeperable.contains(vv) && vv.length() == 2)
    		return 1;
    	if (!this.nonSeperable.contains(vv.substring(0, vv.length()-1)) && this.nonSeperable.contains(1) 
    			&& !strongNonSeperable.contains(vv.substring(0, 2)))
    		return 1;
    	else if (strongNonSeperable.contains(vv.substring(0, 2)))
    		return 2;
    	if (this.nonSeperable.contains(vv.substring(0, vv.length()-1)) && !this.nonSeperable.contains(1) 
    			&& !strongNonSeperable.contains(vv.substring(vv.length()-2)))
    		return multipleVowels(vv.substring(0, vv.length()-1));
    	else if (strongNonSeperable.contains(vv.substring(vv.length()-2)))
    		return multipleVowels(vv.substring(0, vv.length()-2)) == -1?vv.length()-2:multipleVowels(vv.substring(0, vv.length()-2));
        	if (vv.substring(vv.length()-1).length()>1)
        		return multipleVowels(vv.substring(0, vv.length()-1));
    	return -1;
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
		for (Iterator<String> it = this.result.iterator(); it.hasNext(); ){
		    buffer.append(it.next()+ (it.hasNext()?"-":""));
		}
		return buffer.toString();
    }

    /**
     * Resets the required fields to perform a new spelling operation.
     */
    private void reset() {
		this.result.clear(); 
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
		this.vowels.add("ϊ");
		this.vowels.add("ΐ");
		this.vowels.add("ό");
		this.vowels.add("ύ");
		this.vowels.add("ϋ");
		this.vowels.add("ΰ");
		this.vowels.add("ώ");		
		
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
		this.strongNonSeperable.add("ου");
		this.strongNonSeperable.add("ού");
		this.strongNonSeperable.add("αι");
		this.strongNonSeperable.add("αί");
		this.strongNonSeperable.add("ει");
		this.strongNonSeperable.add("εί");
		this.strongNonSeperable.add("εϊ");
		this.strongNonSeperable.add("οι");
		this.strongNonSeperable.add("οί");
		this.strongNonSeperable.add("υι");
		this.strongNonSeperable.add("αϊ");
		this.strongNonSeperable.add("άι");
		this.strongNonSeperable.add("αη");
		this.strongNonSeperable.add("οϊ");
		this.strongNonSeperable.add("όι");
		this.strongNonSeperable.add("οη");
		this.strongNonSeperable.add("αυ");
		this.strongNonSeperable.add("ευ");
		this.strongNonSeperable.add("αύ");
		this.strongNonSeperable.add("εύ");

		/*Two Digits Vowels...*/
		this.nonSeperable.add("ου");
		this.nonSeperable.add("ού");
		this.nonSeperable.add("αι");
		this.nonSeperable.add("αί");
		this.nonSeperable.add("ει");
		this.nonSeperable.add("εί");
		this.nonSeperable.add("εϊ");
		this.nonSeperable.add("οι");
		this.nonSeperable.add("οί");
		this.nonSeperable.add("υι");

		/*Two Digits Consonants...*/
		this.nonSeperable.add("μπ");
		this.nonSeperable.add("μπ");
		this.nonSeperable.add("ντ");
		this.nonSeperable.add("γκ");
		this.nonSeperable.add("τσ");
		this.nonSeperable.add("τζ");
	
		/* Diphthongs... */
		this.nonSeperable.add("αϊ");
		this.nonSeperable.add("άι");
		this.nonSeperable.add("αη");
		this.nonSeperable.add("οϊ");
		this.nonSeperable.add("όι");
		this.nonSeperable.add("οη");
	
		/* Combinations... */
		this.nonSeperable.add("αυ");
		this.nonSeperable.add("ευ");
		this.nonSeperable.add("αύ");
		this.nonSeperable.add("εύ");
		
		/* Combinations... */
		this.nonSeperable.add("ια");
		this.nonSeperable.add("ιά");
		this.nonSeperable.add("ειά");
		this.nonSeperable.add("ιο");
		//this.nonSeperable.add("ιό");
		this.nonSeperable.add("υος");
		this.nonSeperable.add("ιου");

		// ι,υ,ει,οι+φωνήεν ή δίψηφο (ου, αι, ει, οι)
		this.nonSeperable.add("ια");
		this.nonSeperable.add("ιά");
		this.nonSeperable.add("ιε");
		this.nonSeperable.add("ιέ");
		this.nonSeperable.add("ιο");
		this.nonSeperable.add("ιό");
		this.nonSeperable.add("ιυ");
		this.nonSeperable.add("ιύ");
		this.nonSeperable.add("ιω");
		this.nonSeperable.add("ιώ");
		this.nonSeperable.add("υα");
		this.nonSeperable.add("υά");
		this.nonSeperable.add("υε");
		this.nonSeperable.add("υέ");
		this.nonSeperable.add("υη");
		this.nonSeperable.add("υή");
		this.nonSeperable.add("υι");
		this.nonSeperable.add("υί");
		this.nonSeperable.add("υο");
		this.nonSeperable.add("υό");
		this.nonSeperable.add("υω");
		this.nonSeperable.add("υώ");
		this.nonSeperable.add("εια");
		this.nonSeperable.add("ειά");
		this.nonSeperable.add("ειέ");
		this.nonSeperable.add("ειή");
		this.nonSeperable.add("ειό");
		this.nonSeperable.add("ειύ");
		this.nonSeperable.add("ειώ");
		this.nonSeperable.add("οια");
		this.nonSeperable.add("οιέ");
		this.nonSeperable.add("οιή");
		this.nonSeperable.add("οιό");
		this.nonSeperable.add("οιο");
		this.nonSeperable.add("οιυ");
		this.nonSeperable.add("οιύ");

		this.nonSeperable.add("ιου");
		this.nonSeperable.add("ιού");
		this.nonSeperable.add("ιαι");
		this.nonSeperable.add("ιαί");
		this.nonSeperable.add("ιει");
		this.nonSeperable.add("ιεί");
		this.nonSeperable.add("ιοι");
		this.nonSeperable.add("ιοί");

		this.nonSeperable.add("υου");
		this.nonSeperable.add("υού");
		this.nonSeperable.add("υαι");
		this.nonSeperable.add("υαί");
		this.nonSeperable.add("υει");
		this.nonSeperable.add("υεί");
		this.nonSeperable.add("υοι");
		this.nonSeperable.add("υοί");

		this.nonSeperable.add("ειου");
		this.nonSeperable.add("ειού");
		this.nonSeperable.add("ειαι");
		this.nonSeperable.add("ειαί");
		this.nonSeperable.add("ειει");
		this.nonSeperable.add("ειεί");
		this.nonSeperable.add("ειοι");
		this.nonSeperable.add("ειοί");

		this.nonSeperable.add("οιου");
		this.nonSeperable.add("οιού");
		this.nonSeperable.add("οιαι");
		this.nonSeperable.add("οιαί");
		this.nonSeperable.add("οιει");
		this.nonSeperable.add("οιεί");
		this.nonSeperable.add("οιοι");
		this.nonSeperable.add("οιοί");

		this.nonSeperable.add("ι");
		this.nonSeperable.add("υ");
		this.nonSeperable.add("ει");
		this.nonSeperable.add("οι");
	
		/* Undues... */
		//this.nonSeperable.add("ι");
		//this.nonSeperable.add("υ");
		//this.nonSeperable.add("οι");
		//this.nonSeperable.add("ει");
	
		/* Two Consonants that a greek word can start */
		this.greekPrefixes.add("βγ");
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
		this.greekPrefixes.add("μν");
		this.greekPrefixes.add("μπ");
		this.greekPrefixes.add("ντ");
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
		this.greekPrefixes.add("χθ");
		this.greekPrefixes.add("χλ");
		this.greekPrefixes.add("χν");
		this.greekPrefixes.add("χρ");	
		this.greekPrefixes.add("χτ");	
		this.greekPrefixes.add("θρ");	
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
	

    /**
     * To lowerCase
     */
    private String preprocess(String str) {
		String lowerCaseString = str;
		
		this.converter = new int[str.length()];
		
		for (int i=0; i<str.length(); i++){	    
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
		return lowerCaseString;
    }
	
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
}

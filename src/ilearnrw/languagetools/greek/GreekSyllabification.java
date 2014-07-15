package ilearnrw.languagetools.greek;
import java.util.ArrayList;
import java.util.Iterator;


public class GreekSyllabification implements Speller{
	private String stringToSpell;
    private ArrayList<String> vowels;
    private ArrayList<String> consonants;
    private ArrayList<String> nonSeperable;
    private ArrayList<String> greekPrefixes;

    private ArrayList<String> result;

    /** Creates a new instance of GreekSpeller */
    public GreekSyllabification() {
    	this("");
    }


    /** Creates a new instance of GreekSpeller */
    public GreekSyllabification(String stringToSpell) {
		this.stringToSpell = stringToSpell;
		this.vowels = new ArrayList<String>();
		this.consonants = new ArrayList<String>();
		this.nonSeperable = new ArrayList<String>();
		this.greekPrefixes = new ArrayList<String>();
		this.result = new ArrayList<String>();
		this.initLists();
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
		this.result.add(this.stringToSpell);
		this.splitSyllables(0);
	
    }
    
    private void splitSyllables(int i){
    	if (this.result.size() <= i)
    		return;
    	ArrayList<String> res;
    	res = lastFirstRule(this.result.get(i));
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
	    			&& this.vowels.contains(""+str.charAt(i+2))
	    			&& this.consonants.contains(""+str.charAt(i+1))){
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
    
    private ArrayList<String> lastFirstRule(String str){
    	for (int i=0; i<str.length()-1; i++){    
	    	if (i<str.length()-2 && this.vowels.contains(""+str.charAt(i)) 
	    			&& this.vowels.contains(""+str.charAt(i+1))
	    			&& this.vowels.contains(""+str.charAt(i+2))
	    			&& !this.nonSeperable.contains(str.substring(i, i+3))){
	    		String str1 = str.substring(0, i+1);
	    		String str2 = str.substring(i+1);
	    		ArrayList<String> l1 = new ArrayList<String>();
	    		l1.add(str1);
	    		l1.add(str2);
	    		return l1;
	    	}  
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
		this.nonSeperable.add("ου");
		this.nonSeperable.add("ού");
		this.nonSeperable.add("αι");
		this.nonSeperable.add("αί");
		this.nonSeperable.add("ει");
		this.nonSeperable.add("εί");
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
		this.nonSeperable.add("ιο");
		this.nonSeperable.add("ιό");
		this.nonSeperable.add("οιο");
		this.nonSeperable.add("ειο");
		this.nonSeperable.add("υος");
		this.nonSeperable.add("ιου");

	
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
		this.greekPrefixes.add("μπ");
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

package ilearnrw.languagetools.greek;

import java.util.ArrayList;


public class GreekPhonetics {

    private String word;
    private String tempResult[];
    private char CVphonetics[];
	private String result;
    private ArrayList<String> resultInCells;
   
    private final String makez[] = {"γ", "β", "δ", "ζ", "λ", "μ", "ν", "ρ", "τζ", "μπ", "ντ", "γκ"};
    private final String voicedConsonants[] = {"γ", "β", "δ", "ζ", "λ", "μ", "ν", "ρ", "τζ", "μπ", "ντ", "γκ"};
    private final String voicelessConsonants[] = {"κ", "π", "τ", "χ", "φ", "θ", "σ", "τσ"};
    private final String vowels[] = {"α", "ε", "η", "ι", "ο", "υ", "ω", "ά", "έ", "ή", "ί", "ό", "ύ", "ώ"};
	private final char[] chars = {'α', 'β', 'γ', 
    		'δ', 'ε', 'ζ', 'η', 'θ', 
    		'ι', 'κ', 'λ', 'μ', 'ν', 
    		'ξ', 'ο', 'π', 'ρ', 'σ', 
    		'τ', 'υ', 'φ', 'χ', 'ψ', 
    		'ω', 'ά', 'έ', 'ή', 'ί', 
    		'ό', 'ύ', 'ώ', 'ϊ', 'ϋ', 
    		'ΐ','ΰ', 'ς'};
	private final String[] phonetics = {"a", "v", "γ", 
    		"ð", "e", "z", "i", "θ", 
    		"i", "k", "l", "m", "n", 
    		"ks", "o", "p", "r", "s", 
    		"t", "i", "f", "χ", "ps", 
    		"o", "á", "é", "í", "í", 
    		"ό", "í", "ό", "i", "i", 
    		"í", "í", "s"};
	
	private final String[] vowelPhonetics = 
		{"a", "e", "i", "o", "á", "é", "j", "í", "ό", "i", "ε", "u", "ú"};
   
    /** Creates a new instance of GreekPhonetics */
    public GreekPhonetics() {
        this("");
    }


    /** Creates a new instance of GreekPhonetics */
    public GreekPhonetics(String word) {
        this.word = word;
        resultInCells = new ArrayList<String>();
        this.result = "";
        convert();
        cvPhonetics();
    }
   
   
    public String getWord() {
        return word;
    }


    public void setWord(String word) {
        this.word = word;
    }


    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }


    public ArrayList<String> getResultInCells() {
        return resultInCells;
    }


    public void setResultInCells(ArrayList<String> resultInCells) {
        this.resultInCells = resultInCells;
    }

    /**
	 * @return the cVphonetics
	 */
	public char[] getCVphonetics() {
		return CVphonetics;
	}

	/**
	 * @param cVphonetics the cVphonetics to set
	 */
	public void setCVphonetics(char[] cVphonetics) {
		CVphonetics = cVphonetics;
	}

    private void convert(){
        int n = word.trim().length();
        tempResult = new String[n];
    	if (word.length()<1){
    		result = "";
    		return;
    	}
    	else if (word.length()==1){
    		singleChar();
    		tempResult[0] = result;
    		return;
    	}
        for (int i=0;i<tempResult.length; i++)
            tempResult[i] = "#";
        convertTwoOrThreeConsonants();
        convertTwoVowels();
        convertTwoVowelsFirstRule();
        convertOneVowel();
        convertOneConsonant();
        convertGGorGK();
        convertG();
        convertK();
        convertX();
        convertS();
        convertLiNi();
        this.result = "";
        for (String c : tempResult){
            if (!c.equals("*")){
                if (c.equals("#"))
                    c = " ";
                this.result = this.result+c;
                this.resultInCells.add(c);
            }
        }

    }
    
    public void cvPhonetics(){
    	if (tempResult == null){
    		CVphonetics = null;
    		return;
    	}
        this.CVphonetics = new char[tempResult.length];
        for (int i=0; i<CVphonetics.length; i++)
        	CVphonetics[i] = ' ';
        for (int i=0; i<tempResult.length; i++){
        	if (CVphonetics[i] == 'v' || CVphonetics[i] == 'c')
        		continue;
        	if (hasTwoVowelsTogether(i)){
        		CVphonetics[i] = 'v';
        		CVphonetics[i+1] = 'v';
        		continue;
        	}
        	if (tempResult[i].equals("ç") && word.charAt(i) == 'ι'){
        		CVphonetics[i] = 'v';
        		continue;
        	}
        	if (tempResult[i].equals("#") || tempResult[i].equals("*")){
        		CVphonetics[i] = '*';
        		continue;
        	}
        	CVphonetics[i] = 'c';
        	for (String x : vowelPhonetics){
        		if (tempResult[i].equals(x)){
        			CVphonetics[i] = 'v';
        			break;
        		}
            }
        }
    }
    
    public void singleChar(){
        this.result = "";
    	for (int i=0; i<chars.length; i++){
        	if (word.charAt(0) == chars[i]){
                this.result = this.result+phonetics[i];
                this.resultInCells.add(""+phonetics[i]);
        	}
    	}
    }
       
        private void convertTwoOrThreeConsonants(){
        int n = word.trim().length(), tmpStart;
        tmpStart = 0;
        while (tmpStart < n && word.substring(tmpStart).contains("ντζ")){
            int k = word.indexOf("ντζ", tmpStart);
            tempResult[k] = "ndz";
            tempResult[k+1] = "*";
            tempResult[k+2] = "*";
            tmpStart = k+3;
        }
        replaceTwoConsonants("σσ", "s");
        replaceTwoConsonants("γχ", "ŋχ");
        replaceTwoConsonants("κζ", "gz");
        replaceTwoConsonants("τσ", "ts");
        
        tmpStart = 0;
        while (tmpStart < n && word.substring(tmpStart).contains("ντ")){
            int k = word.indexOf("ντ", tmpStart);
            if (word.indexOf("ντζ", tmpStart)==k){
                tmpStart++;
                continue;
            }
            if (k>0)
                tempResult[k] = "nd";
            else
                tempResult[k] = "d";
            tempResult[k+1] = "*";
            tmpStart = k+2;
        }
        tmpStart = 0;
        while (tmpStart < n && word.substring(tmpStart).contains("τζ")){
            int k = word.indexOf("τζ", tmpStart);
            if (k>0 && word.indexOf("ντζ", tmpStart-1)==k-1){
                tmpStart++;
                continue;
            }
            tempResult[k] = "dz";
            tempResult[k+1] = "*";
            tmpStart = k+2;
        }
        tmpStart = 0;
        while (tmpStart < n && word.substring(tmpStart).contains("μπ")){
            int k = word.indexOf("μπ", tmpStart);
            if (k>0)
                tempResult[k] = "mb";
            else
                tempResult[k] = "b";
            tempResult[k+1] = "*";
            tmpStart = k+2;
        }
    }
   
    private void replaceTwoConsonants(String a, String t){
        int n = word.trim().length(), tmpStart;
        tmpStart = 0;
        while (tmpStart < n && word.substring(tmpStart).contains(a)){
            int k = word.indexOf(a, tmpStart);
            tempResult[k] = t;
            tempResult[k+1] = "*";
            tmpStart = k+2;
        }
    }
   
    private void convertTwoVowels(){
        replaceTwoChars("αι", "ε");
        replaceTwoChars("αί", "é");
        replaceTwoChars("ει", "i");
        replaceTwoChars("εί", "í");
        replaceTwoChars("ηι", "i");
        replaceTwoChars("ηί", "í");
        replaceTwoChars("οι", "i");
        replaceTwoChars("οί", "í");
        replaceTwoChars("υι", "i");
        replaceTwoChars("υί", "í");
        replaceTwoChars("ου", "u");
        replaceTwoChars("ού", "ú");
    }

    private void convertOneVowel(){
        replaceChar("α", "a");
        replaceChar("ά", "á");
        replaceChar("ε", "e");
        replaceChar("έ", "é");
        replaceChar("η", "i");
        replaceChar("ή", "í");
        replaceChar("ι", "i");
        replaceChar("ί", "í");
        replaceChar("ο", "o");
        replaceChar("ό", "ó");
        replaceChar("υ", "i");
        replaceChar("ύ", "í");
        replaceChar("ω", "o");
        replaceChar("ώ", "ó");
    }

    private void convertOneConsonant(){
        replaceChar("β", "v");
        replaceChar("δ", "ð");
        replaceChar("ζ", "z");
        replaceChar("θ", "θ");
        replaceChar("μ", "m");
        replaceChar("ξ", "ks");
        replaceChar("π", "p");
        replaceChar("ρ", "r");
        //replaceChar("σ", "s");
        replaceChar("ς", "s");
        replaceChar("τ", "t");
        replaceChar("φ", "f");
        replaceChar("ψ", "ps");
    }

    private void convertTwoVowelsFirstRule(){
        replaceTwoVowelsFirstRule("αυ", "av", "af");
        replaceTwoVowelsFirstRule("αύ", "av", "áf");
        replaceTwoVowelsFirstRule("ευ", "ev", "ef");
        replaceTwoVowelsFirstRule("εύ", "ev", "éf");
        replaceTwoVowelsFirstRule("ηυ", "iv", "if");
        replaceTwoVowelsFirstRule("ηύ", "ív", "íf");
    }
   
    private void convertGGorGK(){
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains("γγ"))){
            int k = word.indexOf("γγ", tmpStart);
            if (k>n-3){
                tempResult[k] = "ɟ";
                tmpStart = k+2;
                continue;
            }
            else if (tempResult[k+2].startsWith("e") || tempResult[k+2].startsWith("i") ||
                    tempResult[k+2].startsWith("é") || tempResult[k+2].startsWith("í")){
                if (k>0)
                    tempResult[k] = "ŋɟ";
                else
                    tempResult[k] = "ɟ";
            }
            else {
                if (k>0)
                    tempResult[k] = "ŋg";
                else
                    tempResult[k] = "g";
            }
            tempResult[k+1] = "*";
            tmpStart = k+2;
        }
        tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains("γκ"))){
            int k = word.indexOf("γκ", tmpStart);
            if (k>n-3){
                tempResult[k] = "ɟ";
                tmpStart = k+2;
                continue;
            }
            else if (tempResult[k+2].startsWith("e") || tempResult[k+2].startsWith("i") ||
                    tempResult[k+2].startsWith("é") || tempResult[k+2].startsWith("í")){
                if (k>0)
                    tempResult[k] = "ŋɟ";
                else
                    tempResult[k] = "ɟ";
            }
            else {
                if (k>0)
                    tempResult[k] = "ŋg";
                else
                    tempResult[k] = "g";
            }
            tempResult[k+1] = "*";
            tmpStart = k+2;
        }
    }
   
    private void convertG(){
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains("γ"))){
            int k = word.indexOf("γ", tmpStart);
            if (word.indexOf("γγ", tmpStart)==k || word.indexOf("γκ", tmpStart)==k){
                tmpStart = k+1;
                continue;
            }
            if (k>n-2){
                tempResult[k] = "γ";
                tmpStart = k+1;
                continue;
            }
            else if (tempResult[k+1].startsWith("e") || tempResult[k+1].startsWith("i") ||
                    tempResult[k+1].startsWith("é") || tempResult[k+1].startsWith("í")){
                tempResult[k] = "j";
            }
            else {
                tempResult[k] = "γ";
            }
            tmpStart = k+1;
        }
    }
   
    private void convertK(){
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains("κ"))){
            int k = word.indexOf("κ", tmpStart);
            if (k>0 && word.indexOf("γκ", tmpStart)==k-1){
                tmpStart = k+1;
                continue;
            }
            if (k>n-2){
                tempResult[k] = "k";
                tmpStart = k+1;
                continue;
            }
            else if (tempResult[k+1].startsWith("e") || tempResult[k+1].startsWith("i") ||
                    tempResult[k+1].startsWith("é") || tempResult[k+1].startsWith("í")){
                tempResult[k] = "c";
            }
            else {
                tempResult[k] = "k";
            }
            tmpStart = k+1;
        }
    }
   
    private void convertX(){
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains("χ"))){
            int k = word.indexOf("χ", tmpStart);
            if (k>0 && word.indexOf("γχ", tmpStart)==k-1){
                tmpStart = k+1;
                continue;
            }
            if (k>n-2){
                tempResult[k] = "χ";
                tmpStart = k+1;
                continue;
            }
            else if (tempResult[k+1].startsWith("e") || tempResult[k+1].startsWith("i") ||
                    tempResult[k+1].startsWith("é") || tempResult[k+1].startsWith("í")){
                tempResult[k] = "ç";
            }
            else {
                tempResult[k] = "χ";
            }
            tmpStart = k+1;
        }
    }
   
    private void convertS(){
        //it is 'z' before γ, β, δ, ζ, λ, μ, ν, ρ, τζ, μπ, ντ, γκ
        //'s' otherwise
        replaceTwoConsonants("σλ", "zl");
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains("σ"))){
            int k = word.indexOf("σ", tmpStart);
            String next = word.substring(k+1);
            if (!tempResult[k].equals("#")){
                tmpStart = k+1;
                continue;
            }
            tempResult[k] = "s";
            for (String x: makez){
                if (next.startsWith(x)){
                    tempResult[k] = "z";
                    tmpStart = k+1;
                    continue;
                }
            }
            tmpStart = k+1;
        }
    }
   
    private void convertLiNi(){
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains("λ"))){
            int k = word.indexOf("λ", tmpStart);
            if (!tempResult[k].equals("#")){
                tmpStart = k+1;
                continue;
            }
            int pos = hasIandVowelAfter(k);
            if (pos>0){
                tempResult[k] = "ʎ";
                tempResult[pos] = "*";
            }
            else
                tempResult[k] = "l";
            if (k>0 && tempResult[k-1].equals("l"))
                tempResult[k-1]="*";
            tmpStart = k+1;
        }

        tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains("ν"))){
            int k = word.indexOf("ν", tmpStart);
            if (!tempResult[k].equals("#")){
                tmpStart = k+1;
                continue;
            }
            int pos = hasIandVowelAfter(k);
            if (pos>0){
                tempResult[k] = "ɲ";
                tempResult[pos] = "*";
            }
            else
                tempResult[k] = "n";
            if (k>0 && tempResult[k-1].equals("n")){
                tempResult[k-1]="*";
            }
            tmpStart = k+1;
        }
    }
   
    private void replaceChar(String a, String t){
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && (word.substring(tmpStart).contains(a))){
            int k = word.indexOf(a, tmpStart);
            if (!tempResult[k].equals("#")){
                tmpStart = k+1;
                continue;
            }
            if (t.equals("i")){
                tempResult[k] = replaceI(k);
            }
            else{
                tempResult[k] = t;
                if (k>0 && tempResult[k-1].equals(t))
                	tempResult[k-1] = "*";
            }
            tmpStart = k+1;
        }
    }
   
    private void replaceTwoChars(String a, String t){
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && word.substring(tmpStart).contains(a)){
            int k = word.indexOf(a, tmpStart);
            tempResult[k] = t;
            tempResult[k+1] = "*";
            tmpStart = k+2;
        }
    }
   
    private void replaceTwoVowelsFirstRule(String a, String s, String t){
        //replace a by: s before vowels and voiced consonants / t elsewhere
        int n = word.trim().length();
        int tmpStart = 0;
        while (tmpStart < n && word.substring(tmpStart).contains(a)){
            int k = word.indexOf(a, tmpStart);
            if (hasVowelOrVoicedAfter(k+1)){
                tempResult[k] = s;
                tempResult[k+1] = "*";                
            }
            else{
                tempResult[k] = t;
                tempResult[k+1] = "*";
            }
            tmpStart = k+2;
        }
    }
   
    private String replaceI(int pos){
        if (hasVoicedBefore(pos) && hasVowelAfter(pos))
            return "j";
        else if (hasVoicelessBefore(pos) && hasVowelAfter(pos))
            return "ç";
        return "i";
    }
   
    private boolean hasVowelAfter(int k){
        int n = word.trim().length();
        if (k>=n-1 || k<0)
            return false;
        String next = word.substring(k+1, k+2);
        for (String vv : vowels){
            if (next.equals(vv))
                return true;
        }
        return false;
    }
   
    private boolean hasVoicedBefore(int k){
        int n = word.trim().length();
        if (k>n-1 || k<1)
            return false;
        String before1 = word.substring(k-1, k);
        String before2 = "";
        if (k-2>=0)
            before2 = word.substring(k-2, k);
        for (String vv : voicedConsonants){
            if (before1.equals(vv) || before2.endsWith(vv))
                return true;
        }
        return false;
    }
   
    private boolean hasVoicelessBefore(int k){
        int n = word.trim().length();
        if (k>n-1 || k<1)
            return false;
        String before1 = word.substring(k-1, k);
        String before2 = "";
        if (k-2>=0)
            before2 = word.substring(k-2, k);
        for (String vv : voicelessConsonants){
            if (before1.equals(vv) || before2.endsWith(vv))
                return true;
        }
        return false;
    }
   
    private boolean hasVowelOrVoicedAfter(int k){
        int n = word.trim().length();
        if (k==n-1)
            return false;
        String next = word.substring(k+1);
        for (String vv : vowels){
            if (next.startsWith(vv))
                return true;
        }
        for (String vv : voicedConsonants){
            if (next.startsWith(vv))
                return true;
        }
        return false;
    }
   
    //returns the position of i
    private int hasIandVowelAfter(int pos){
        int l = tempResult.length, res = -1;
        if (pos>=l-2)
            return -1;
        int p = pos+1;
        while (p<l-1 && tempResult[p].equals("*"))
            p++;
        if (p==l-1 || !(tempResult[p].equals("i") || (tempResult[p].equals("j") && word.charAt(p)=='ι')))
            return -1;
        res = p;
        p++;
        while (p<l-1 && tempResult[p].equals("*"))
            p++;
        if (p>l-1)
            return -1;
        if (hasVowelAt(p))
            return res;
        else return -1;
    }

    private boolean hasVowelAt(int p){
        if (p<0 || p>word.length())
            return false;
        for (String vv : vowels){
            if (word.subSequence(p,  p+1).equals(vv))
                return true;
        }
        return false;
    }

    private boolean hasTwoVowelsTogether(int i){
        //("αυ", "av", "af");
        //("αύ", "av", "áf");
        //("ευ", "ev", "ef");
        //("εύ", "ev", "éf");
        //("ηυ", "iv", "if");
        //("ηύ", "ív", "íf");
    	if (tempResult == null || word == null || word.length()<=i+1 || i<0)
    		return false;
    	if ((tempResult[i].equals("av") || tempResult[i].equals("af")) 
      			 && word.charAt(i) == 'α' && word.charAt(i+1) == 'υ')
      		return true;
       	if ((tempResult[i].equals("av") || tempResult[i].equals("áf")) 
      			 && word.charAt(i) == 'α' && word.charAt(i+1) == 'ύ')
       		return true;
    	if ((tempResult[i].equals("ev") || tempResult[i].equals("ef")) 
     			 && word.charAt(i) == 'ε' && word.charAt(i+1) == 'υ')
     		return true;
      	if ((tempResult[i].equals("ev") || tempResult[i].equals("éf")) 
     			 && word.charAt(i) == 'ε' && word.charAt(i+1) == 'ύ')
     		return true;
    	if ((tempResult[i].equals("iv") || tempResult[i].equals("if")) 
     			 && word.charAt(i) == 'η' && word.charAt(i+1) == 'υ')
     		return true;
      	if ((tempResult[i].equals("iv") || tempResult[i].equals("íf")) 
     			 && word.charAt(i) == 'η' && word.charAt(i+1) == 'ύ')
     		return true;
      	return false;
    }
    
}
package ilearnrw.textclassification.english;

import java.util.ArrayList;
import java.util.Map;

import org.junit.rules.TemporaryFolder;

import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordType;


public class EnglishWord extends Word {

	ArrayList<String> prefix;
	ArrayList<String> suffix;
	ArrayList<String> cDigraphs;
	ArrayList<String> cTrigraphs;
	
    //We put inside only lower case words
    public EnglishWord(String word) {   	
    	setupLists();
    	
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
                	syllables = new String[] {""};
                else
                	syllables = syllabify(word.toLowerCase());

        } else {
                numSyllables = countVowels();
                syllables = syllabify(word.toLowerCase());
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
    
	private String[] syllabify(String word){    	
    	String fword = word;

    	ArrayList<String> possiblePrefixes = new ArrayList<String>();
    	ArrayList<String> possibleSuffixes = new ArrayList<String>();
    	
    	int numVowels = 0;
    	int tempSyllables = 1;
    	if(numSyllables==1)
    		numVowels=countVowels();

    	if(numVowels > 1 || numSyllables > 1){
	    	possiblePrefixes = gatherPossible(fword, prefix, true);
	    	possibleSuffixes = gatherPossible(fword, suffix, false);
	    	
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
	
	private ArrayList<String> gatherPossible(String word, ArrayList<String> list, boolean prefix){	
		ArrayList<String> options = new ArrayList<String>();
		
		for(String item : list){
			if(item.length() < word.length()){
				if(prefix){
					if(!word.startsWith(item))
						continue;
				} else 
					if(!word.endsWith(item))
						continue;
				
				String part = prefix ? word.substring(item.length()) : word.substring(0, word.length() - item.length());
				boolean valid = false;
				
				for(char c : part.toCharArray())
					if(upperCharToCV(c) == 'v'){
						valid = true;
						break;
					}
				
				if(valid)
					options.add(item);
			}
		}
		
		return options;
	}
	
    private boolean checkDigraph(char c1, char c2){
    	String value = new StringBuilder().append(c1).append(c2).toString();
    	for(String digraph : cDigraphs){
    		if(digraph.equals(value))
    			return true;
    	}
    	return false;
    }
    
    private boolean checkTrigraph(char c1, char c2, char c3){
    	String value = new StringBuilder().append(c1).append(c2).append(c3).toString();
    	
    	for(String trigraph : cTrigraphs){
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
    
    private void setupLists(){
    	prefix 		= new ArrayList<String>();
    	suffix 		= new ArrayList<String>();
    	cDigraphs 	= new ArrayList<String>();
    	cTrigraphs	= new ArrayList<String>();
    	
    	/**
    	 * 
    	 * Prefixes
    	 * 	- http://en.wikipedia.org/wiki/English_prefixes
    	 * 
    	 */
    	prefix.add("a"); prefix.add("afro"); prefix.add("an"); prefix.add("ana"); prefix.add("ante");
    	prefix.add("anti"); prefix.add("anglo"); prefix.add("ambi"); prefix.add("amphi"); prefix.add("apo");
    	prefix.add("arch"); prefix.add("astro"); prefix.add("auto"); 
    	
    	prefix.add("be"); prefix.add("bi"); prefix.add("bio"); 
    	
    	prefix.add("circum"); prefix.add("cis"); prefix.add("co"); prefix.add("con"); prefix.add("com");
    	prefix.add("col"); prefix.add("cor"); prefix.add("contra"); prefix.add("counter"); prefix.add("cryo");
    	prefix.add("crypto");
    	
    	prefix.add("de"); prefix.add("demi"); prefix.add("demo"); prefix.add("deutero"); prefix.add("deuter");
    	prefix.add("di"); prefix.add("dia"); prefix.add("dis"); prefix.add("dif"); prefix.add("du");
    	prefix.add("duo");
    	
    	prefix.add("eco"); prefix.add("electro"); prefix.add("en"); prefix.add("el"); prefix.add("em"); 
    	prefix.add("epi"); prefix.add("euro"); prefix.add("ex"); prefix.add("extra");
    	
    	prefix.add("fin"); prefix.add("franco"); prefix.add("fore");
    	
    	prefix.add("geo"); prefix.add("gyro");
    	
    	prefix.add("hetero"); prefix.add("hemi"); prefix.add("hind"); prefix.add("homo"); prefix.add("hydro");
    	prefix.add("hyper"); prefix.add("hypo");
    	
    	prefix.add("in"); prefix.add("indo"); prefix.add("il"); prefix.add("im"); prefix.add("ir");
    	prefix.add("infra"); prefix.add("inter"); prefix.add("intra"); prefix.add("iso");
    	
    	prefix.add("mal"); prefix.add("macr"); /* macr(o)*/ prefix.add("maxi"); prefix.add("mega"); prefix.add("megalo");
    	prefix.add("meta"); prefix.add("micro"); prefix.add("mid"); prefix.add("midi"); prefix.add("mini");
    	prefix.add("mis"); prefix.add("mono"); prefix.add("mon"); prefix.add("multi"); prefix.add("mult");
    	
    	prefix.add("neo"); prefix.add("non"); prefix.add("omni"); prefix.add("ortho"); prefix.add("out"); prefix.add("over");
    	
    	prefix.add("paleo"); prefix.add("pan"); prefix.add("para"); prefix.add("ped"); prefix.add("peri");
    	prefix.add("photo"); prefix.add("pod"); prefix.add("poly"); prefix.add("post"); prefix.add("pre");
    	prefix.add("preter"); prefix.add("pro"); prefix.add("pros"); prefix.add("proto"); prefix.add("pseudo");
    	prefix.add("pyro");
    	
    	prefix.add("quasi");
    	
    	prefix.add("re"); prefix.add("retro"); 
    	
    	prefix.add("self"); prefix.add("semi"); prefix.add("socio"); prefix.add("step"); prefix.add("sub");
    	prefix.add("sup"); prefix.add("super"); prefix.add("supra"); prefix.add("sur"); prefix.add("syn");
    	prefix.add("sy"); prefix.add("syl"); prefix.add("sym");
    	
    	prefix.add("tele"); prefix.add("trans"); prefix.add("tri"); prefix.add("twi");
    	
    	prefix.add("ultra"); prefix.add("un"); prefix.add("uni"); prefix.add("under"); prefix.add("up");
    	
    	prefix.add("vice");
    	
    	prefix.add("with");
    	
    	/**
    	 * 
    	 * Suffixes
    	 * 	- http://en.wiktionary.org/wiki/Appendix%3AEnglish_suffixes
    	 * 
    	 */
    	suffix.add("a"); suffix.add("ability"); suffix.add("able"); suffix.add("ably"); suffix.add("ac");
    	suffix.add("acean"); suffix.add("aceous"); suffix.add("ad"); suffix.add("ade"); suffix.add("aemia");
    	suffix.add("age"); suffix.add("agog"); suffix.add("agogue"); suffix.add("aholic"); suffix.add("al");
    	suffix.add("algia"); suffix.add("amine"); suffix.add("an"); suffix.add("ana"); suffix.add("ance");
    	suffix.add("ancy"); suffix.add("androus"); suffix.add("andry"); suffix.add("ane"); suffix.add("ant");
    	suffix.add("ar"); suffix.add("arch"); suffix.add("archy"); suffix.add("ard"); suffix.add("arian");
    	suffix.add("arium"); suffix.add("art"); suffix.add("ary"); suffix.add("ase"); suffix.add("ate");
    	suffix.add("athon"); suffix.add("ation"); suffix.add("ative"); suffix.add("ator"); suffix.add("atory");

    	suffix.add("biont"); suffix.add("biosis"); suffix.add("blast"); suffix.add("bot");

    	suffix.add("cade"); suffix.add("caine"); suffix.add("carp"); suffix.add("carpic"); suffix.add("carpous");
    	suffix.add("cele"); suffix.add("cene"); suffix.add("centric"); suffix.add("cephalic"); suffix.add("cephalous");
    	suffix.add("cephaly"); suffix.add("chore"); suffix.add("chory"); suffix.add("chrome"); suffix.add("cide");
    	suffix.add("clast"); suffix.add("clinal"); suffix.add("cline"); suffix.add("clinic"); suffix.add("coccus");
    	suffix.add("coel"); suffix.add("coele"); suffix.add("colous"); suffix.add("cracy"); suffix.add("crat");
    	suffix.add("cratic"); suffix.add("cy"); suffix.add("cyte");
    	
    	suffix.add("dale"); suffix.add("derm"); suffix.add("derma"); suffix.add("dermatous"); suffix.add("dom");
    	suffix.add("drome"); suffix.add("dromous");
    	
    	suffix.add("ean"); suffix.add("eaux"); suffix.add("ectomy"); suffix.add("ed"); suffix.add("ee");
    	suffix.add("eer"); suffix.add("ein"); suffix.add("eme"); suffix.add("emia"); suffix.add("en");
    	suffix.add("ence"); suffix.add("enchyma"); suffix.add("ency"); suffix.add("ene"); suffix.add("ent");
    	suffix.add("eous"); suffix.add("er"); suffix.add("ergic"); suffix.add("ergy"); suffix.add("es");
    	suffix.add("escence"); suffix.add("escent"); suffix.add("ese"); suffix.add("esque"); suffix.add("ess");
    	suffix.add("est"); suffix.add("et"); suffix.add("eth");suffix.add("etic"); suffix.add("ette");
    	suffix.add("ey");

    	suffix.add("facient"); suffix.add("faction"); suffix.add("fer"); suffix.add("ferous"); suffix.add("fic");
    	suffix.add("fication"); suffix.add("fid"); suffix.add("florous"); suffix.add("fold"); suffix.add("foliate");
    	suffix.add("foliolate"); suffix.add("form"); suffix.add("fuge"); suffix.add("ful"); suffix.add("fy");
    	    	
    	suffix.add("gamous"); suffix.add("gamy"); suffix.add("gate"); suffix.add("gen"); suffix.add("gene");
    	suffix.add("genesis"); suffix.add("genetic"); suffix.add("genic"); suffix.add("genous"); suffix.add("geny");
    	suffix.add("gnathous"); suffix.add("gon"); suffix.add("gony"); suffix.add("gram"); suffix.add("graph");
    	suffix.add("grapher"); suffix.add("graphy"); suffix.add("gyne"); suffix.add("gynous"); suffix.add("gyny");
    	
    	suffix.add("hood");
    	
    	suffix.add("ia"); suffix.add("ial"); suffix.add("ian"); suffix.add("iana"); suffix.add("iasis");
    	suffix.add("iatric"); suffix.add("iatrics"); suffix.add("iatry"); suffix.add("ibility"); suffix.add("ible");
    	suffix.add("ic"); suffix.add("icide"); suffix.add("ician"); suffix.add("ics"); suffix.add("id");
    	suffix.add("ide"); suffix.add("ie"); suffix.add("ify"); suffix.add("ile"); suffix.add("in");
    	suffix.add("ine"); suffix.add("ing"); suffix.add("ion"); suffix.add("ious"); suffix.add("isation");
    	suffix.add("ise"); suffix.add("ish"); suffix.add("ism"); suffix.add("ist"); suffix.add("istic");
    	suffix.add("istical"); suffix.add("istically"); suffix.add("ite"); suffix.add("itious"); suffix.add("itis");
    	suffix.add("ity"); suffix.add("ium"); suffix.add("ive"); suffix.add("ix"); suffix.add("ization");
    	suffix.add("ize");
    	
    	suffix.add("kin"); suffix.add("kinesis"); suffix.add("kins");
    	
    	suffix.add("latry"); suffix.add("le"); suffix.add("lepry"); suffix.add("less"); suffix.add("let");
    	suffix.add("like"); suffix.add("ling"); suffix.add("lite"); suffix.add("lith"); suffix.add("lithic");
    	suffix.add("log"); suffix.add("logue"); suffix.add("logic"); suffix.add("logical"); suffix.add("logist");
    	suffix.add("logy"); suffix.add("ly"); suffix.add("lyse"); suffix.add("lysis"); suffix.add("lyte");
    	suffix.add("lytic"); suffix.add("lyze");
    	
    	suffix.add("mancy"); suffix.add("mania"); suffix.add("meister"); suffix.add("ment"); suffix.add("mer");
    	suffix.add("mere"); suffix.add("merous"); suffix.add("meter"); suffix.add("metric"); suffix.add("metrics");
    	suffix.add("metry"); suffix.add("mire"); suffix.add("mo"); suffix.add("morph"); suffix.add("morphic");
    	suffix.add("morphism"); suffix.add("morphous"); suffix.add("most"); suffix.add("mycete"); suffix.add("mycin");

    	suffix.add("n't"); suffix.add("nasty"); suffix.add("ness"); suffix.add("nik"); suffix.add("nomy");
    	suffix.add("nomics");
    	
    	suffix.add("o"); suffix.add("ode"); suffix.add("odon"); suffix.add("odont"); suffix.add("odontia");
    	suffix.add("oholic"); suffix.add("oic"); suffix.add("oid"); suffix.add("ol"); suffix.add("ole");
    	suffix.add("oma"); suffix.add("ome"); suffix.add("omics"); suffix.add("on"); suffix.add("one");
    	suffix.add("ont"); suffix.add("onym"); suffix.add("onymy"); suffix.add("opia"); suffix.add("opsis");
    	suffix.add("opsy"); suffix.add("or"); suffix.add("orama"); suffix.add("ory"); suffix.add("ose");
    	suffix.add("osis"); suffix.add("otic"); suffix.add("otomy"); suffix.add("ous");

    	suffix.add("para"); suffix.add("parous"); suffix.add("path"); suffix.add("pathy"); suffix.add("ped");
    	suffix.add("pede"); suffix.add("penia"); suffix.add("petal"); suffix.add("phage"); suffix.add("phagia");
    	suffix.add("phagous"); suffix.add("phagy"); suffix.add("phane"); suffix.add("phasia"); suffix.add("phil");
    	suffix.add("phile"); suffix.add("philia"); suffix.add("philiac"); suffix.add("philic"); suffix.add("philous");
    	suffix.add("phobe"); suffix.add("phobia"); suffix.add("phobic"); suffix.add("phone"); suffix.add("phony");
    	suffix.add("phore"); suffix.add("phoresis"); suffix.add("phorous"); suffix.add("phrenia"); suffix.add("phyll");
    	suffix.add("phyllous"); suffix.add("plasia"); suffix.add("plasm"); suffix.add("plast"); suffix.add("plastic");
    	suffix.add("plasty"); suffix.add("plegia"); suffix.add("plex"); suffix.add("ploid"); suffix.add("pod");
    	suffix.add("pode"); suffix.add("podous"); suffix.add("poieses"); suffix.add("poietic"); suffix.add("pter");
    	suffix.add("punk");
    	
    	suffix.add("rrhagia"); suffix.add("rrhea"); suffix.add("ric"); suffix.add("ry");

    	suffix.add("'s"); suffix.add("s"); suffix.add("scape"); suffix.add("scope"); suffix.add("scopy");
    	suffix.add("scribe"); suffix.add("script"); suffix.add("sect"); suffix.add("sepalous"); suffix.add("ship");
    	suffix.add("some"); suffix.add("speak"); suffix.add("sperm"); suffix.add("sphere"); suffix.add("sporous");
    	suffix.add("st"); suffix.add("stasis"); suffix.add("stat"); suffix.add("ster"); suffix.add("stome");
    	suffix.add("stomy");
    	
    	suffix.add("taxis"); suffix.add("taxy"); suffix.add("tend"); suffix.add("th"); suffix.add("therm");
    	suffix.add("thermal"); suffix.add("thermic"); suffix.add("thon"); suffix.add("thymia"); suffix.add("tion");
    	suffix.add("tome"); suffix.add("tomy"); suffix.add("tonia"); suffix.add("trichous"); suffix.add("trix");    	
    	suffix.add("tron"); suffix.add("trophic"); suffix.add("trophy"); suffix.add("tropic"); suffix.add("tropism");    	
    	suffix.add("tropous"); suffix.add("tropy"); suffix.add("tude"); suffix.add("ture"); suffix.add("ty");   	
    	
    	suffix.add("ular"); suffix.add("ule"); suffix.add("ure"); suffix.add("urgy"); suffix.add("uria");
    	suffix.add("uronic"); suffix.add("urous");

    	suffix.add("valent"); suffix.add("virile"); suffix.add("vorous");
    	
    	suffix.add("ward"); suffix.add("wards"); suffix.add("ware"); suffix.add("ways"); suffix.add("wear");
    	suffix.add("wide"); suffix.add("wise"); suffix.add("worthy");
    	
    	suffix.add("xor");
    	
    	suffix.add("y"); suffix.add("yl"); suffix.add("yne");
    	
    	suffix.add("zilla"); suffix.add("zoic"); suffix.add("zoon"); suffix.add("zygous"); suffix.add("zyme");
    	
    	/**
    	 * Consonant digraphs
    	 * 	- http://www.enchantedlearning.com/consonantblends/
    	 */
    	cDigraphs.add("bl"); cDigraphs.add("br"); cDigraphs.add("ch"); cDigraphs.add("ck"); cDigraphs.add("cl");
    	cDigraphs.add("cr"); cDigraphs.add("dr"); cDigraphs.add("fl"); cDigraphs.add("fr"); cDigraphs.add("gh");
    	cDigraphs.add("gl"); cDigraphs.add("gr"); cDigraphs.add("ng"); cDigraphs.add("ph"); cDigraphs.add("pl");
    	cDigraphs.add("pr"); cDigraphs.add("qu"); cDigraphs.add("sc"); cDigraphs.add("sh"); cDigraphs.add("sk");
    	cDigraphs.add("sl"); cDigraphs.add("sm"); cDigraphs.add("sn"); cDigraphs.add("sp"); cDigraphs.add("st");
    	cDigraphs.add("sw"); cDigraphs.add("th"); cDigraphs.add("tr"); cDigraphs.add("tw"); cDigraphs.add("wh");
    	cDigraphs.add("wr");
    	
    	/**
    	 * Consonant trigraphs
    	 * 	- http://www.enchantedlearning.com/consonantblends/
    	 */
    	
    	cTrigraphs.add("nth"); cTrigraphs.add("sch"); cTrigraphs.add("scr"); cTrigraphs.add("shr"); cTrigraphs.add("spl");
    	cTrigraphs.add("spr"); cTrigraphs.add("squ"); cTrigraphs.add("str"); cTrigraphs.add("tch"); cTrigraphs.add("thr");
    }
}

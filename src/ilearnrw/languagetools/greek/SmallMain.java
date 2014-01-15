/**
 * @author Chris Litsas 
 * 15 Ιαν 2014 1:14:25 μ.μ.
**/
package ilearnrw.languagetools.greek;

import java.util.ArrayList;

/**
 * 
 **/
public class SmallMain {
	public static void main(String[] args) {
		String word = "άσπρος";
		String pattern = "-";
		GreekPhonetics gp = new GreekPhonetics(word);
		GreekSpeller gs = new GreekSpeller(word);
		gs.performSpelling();
		for (String x : gs.getTokens()){
			System.out.print(x+" ");
		}
		System.out.println();
		for (char x : gp.getCVphonetics()){
			System.out.print(x+" ");
		}
		System.out.println();
		int k = 0;
		String syllables[] = gs.getTokensArray();
		char ph[] = gp.getCVphonetics();
		for (String x : syllables){
			int len = x.trim().length();
			for (int i=k; i<len+k; i++){
				if (ph[i] != '*')
					pattern += ph[i];
			}
			pattern += "-";
			k = len+k;
		}
		System.out.println(pattern);
	}

}

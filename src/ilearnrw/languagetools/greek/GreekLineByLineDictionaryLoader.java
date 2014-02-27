package ilearnrw.languagetools.greek;

import java.util.HashMap;
import java.util.Scanner;

import ilearnrw.languagetools.DictionaryLoader;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.utils.LanguageCode;

public class GreekLineByLineDictionaryLoader extends DictionaryLoader{
	public GreekLineByLineDictionaryLoader(String filename) {
		super(filename);
	}
	
	public void readDic(){
		String text = "";
		HashMap<String, Integer> theWords = new HashMap<String, Integer>();
		text = new Scanner(dictionary, "UTF-8").useDelimiter("\\A").next();
		Text txt = new Text(text, LanguageCode.GR);
		HashMap<Word, Integer> tmp = txt.getWordsFreq();
		Object obj[] = tmp.keySet().toArray();
		for (Object x : obj){
			String w = ((Word) x).toString();
			theWords.put(w,0);
		}
		Object obj2[] = theWords.keySet().toArray();
		for (Object x2 : obj2){
			GreekWord gw = new GreekWord((String)x2);
			//System.out.println("sound "+gw.toString());
			entries.add(gw);
		}
	}

}

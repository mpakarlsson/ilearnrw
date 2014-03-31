package ilearnrw.languagetools.greek;

import ilearnrw.languagetools.DictionaryLoader;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.greek.GreekWord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GreekDictionaryLoader extends DictionaryLoader{

	public GreekDictionaryLoader(String filename) {
		super(filename);
	}

	public void readDic(){
		try {
			InputStreamReader in = new InputStreamReader(dictionary, "UTF-8");
			BufferedReader buf = new BufferedReader(in);
			String line = null;
			while((line=buf.readLine())!=null) {
				String[] row = line.split(" ");
				if (row.length<3)
					continue;
				DictionaryEntry entry = new DictionaryEntry(row[2], row[1], null);
				if (entry.isActive() && row[0]!=null && !row[0].isEmpty())
					entries.add(new GreekWord(row[0], partOfSpeech(row[1])));
			}
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private WordType partOfSpeech(String pos){
		if (pos.trim().equals("ουσιαστικό"))
			return WordType.Noun;
		if (pos.trim().equals("επίθετο"))
			return WordType.Adjective;
		if (pos.trim().equals("ρήμα"))
			return WordType.Verb;
		if (pos.trim().equals("μετοχή"))
			return WordType.Participle;
		return WordType.Unknown;
	}
}

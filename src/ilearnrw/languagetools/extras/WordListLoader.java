package ilearnrw.languagetools.extras;

import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WordListLoader {
	private InputStream dictionary;
	private String filename;
	private WordList words;

	public WordListLoader() {
		this.filename = null;
		this.words = new WordList();
	}
	
	public void load(String filename) throws Exception{
		
		this.filename = filename;
		this.words = new WordList();
		loadFile();
		readWords();
	}
	
	private void loadFile() throws Exception{
		dictionary = ResourceLoader.getInstance().getInputStream(Type.DATA, this.filename);
	}

	public void readWords(){
		try {
			InputStreamReader in = new InputStreamReader(dictionary, "UTF-8");
			BufferedReader buf = new BufferedReader(in);
			String line = null;
			while((line=buf.readLine())!=null) {
				String[] row = line.split(" ");
				if (row[0] != null)
					this.words.add(row[0]);
			}
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public WordList getWordList() {
		return words;
	}
	
}

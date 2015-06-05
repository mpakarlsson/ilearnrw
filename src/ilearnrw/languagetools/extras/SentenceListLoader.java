package ilearnrw.languagetools.extras;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SentenceListLoader {
		private InputStream dictionary;
		private String filename;
		private ArrayList<String> sentences;

		public SentenceListLoader() {
			this.filename = null;
			this.sentences = new ArrayList<String>();
		}
		
		public void load(String filename) throws Exception{
			
			this.filename = filename;
			this.sentences = new ArrayList<String>();
			loadFile();
			readSentences();
		}
		
		private void loadFile() throws Exception{
			dictionary = ResourceLoader.getInstance().getInputStream(Type.DATA, this.filename);
		}

		public void readSentences(){
			try {
				InputStreamReader in = new InputStreamReader(dictionary, "UTF-8");
				BufferedReader buf = new BufferedReader(in);
				String line = null;
				while((line=buf.readLine())!=null) {
					this.sentences.add(line);
				}
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public ArrayList<String> getSentenceList() {
			return sentences;
		}
		
	}

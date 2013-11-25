package ilearnrw.textclassification.tests;

import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.utils.LanguageCode;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

public class GreekWordGenerator {

	public static void main(String a[]) {
		String path = "texts/";
		String[] files = { "deyteraDim2.txt", "deyteraGym2.txt",
				"ekthDhm2.txt", "pempthDhm.txt", "prwthDhm.txt", "prwthDhm3.txt",
				"prwthGym.txt", "tetarthDhm2.txt", "trithDhm.txt", "trithDhm3.txt",
				"deyteraDim.txt", "deyteraGym.txt", "deyteraGym3.txt", "ekthDhm.txt",
				"pempthDhm2.txt", "pempthDhm3.txt", "prwthDhm2.txt", "prwthGym2.txt",
				"tetarthDhm.txt","prwthGym3.txt", "trithDhm2.txt" };
		String text = "";
		try {
			HashMap<String, Integer> words = new HashMap<String, Integer>();
			for (String fName : files){
				text = new Scanner(new File(path + fName), "UTF-8").useDelimiter("\\A").next();
				Text txt = new Text(text, LanguageCode.GR);
				HashMap<Word, Integer> tmp = txt.getWordsFreq();
				Object obj[] = tmp.keySet().toArray();
				for (Object x : obj){
					String w = ((Word) x).toString();
					words.put(w,0);
				}
			}
			Object obj[] = words.keySet().toArray();
			int i = 0;
			for (Object x : obj){
				String result = "";
				String w = (String)x;
				if (!w.matches(".*\\d.*"))
					result = result+(++i)+" = "+w;//+"\n";

				System.out.println(result);
				try {
					setContents(new File(path+"GreekWords.txt"), result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	static public void setContents(File aFile, String aContents)
			throws FileNotFoundException, IOException {
		if (aFile == null) {
			throw new IllegalArgumentException("File should not be null.");
		}
		if (!aFile.exists()) {
			throw new FileNotFoundException("File does not exist: " + aFile);
		}
		if (!aFile.isFile()) {
			throw new IllegalArgumentException("Should not be a directory: "
					+ aFile);
		}
		if (!aFile.canWrite()) {
			throw new IllegalArgumentException("File cannot be written: "
					+ aFile);
		}

		// use buffering
		Writer output = new BufferedWriter(new FileWriter(aFile));
		try {
			// FileWriter always assumes default encoding is OK!
			output.write(aContents);
		} finally {
			output.close();
		}
	}

}

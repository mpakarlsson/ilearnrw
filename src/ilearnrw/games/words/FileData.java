package ilearnrw.games.words;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class FileData {
	String filename;
	ArrayList<String> data;
	public FileData(){}
	public FileData(String n){
		filename = "data/"+n;
		data = new ArrayList<String>();
	}
	
	public void store(){
		try {
			System.out.println("start writing to files!");
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(this.filename), "UTF-8"));
			try {
				for (String ss : this.data)
					out.write(ss + "\n");
			} finally {
				out.close();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

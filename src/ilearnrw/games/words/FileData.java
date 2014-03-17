package ilearnrw.games.words;

import java.util.ArrayList;

public class FileData {
	String filename;
	ArrayList<String> data;
	public FileData(){}
	public FileData(String n){
		filename = n;
		data = new ArrayList<String>();
	}

}

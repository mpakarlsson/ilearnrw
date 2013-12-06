package ilearnrw.textclassification;

import ilearnrw.user.profile.UserProblems;

public class ProblematicWords {
	private WordsList table[][];

	public ProblematicWords(UserProblems userSeveritiesToProblems) {
		int n = userSeveritiesToProblems.getNumerOfRows();
		table = new WordsList[n][];
		
		for (int i=0; i<n; i++){
			table[i] = new WordsList[userSeveritiesToProblems.getRowLength(i)];
			for (int j=0; j<table[i].length; j++){
				table[i][j] = new WordsList();
			}
		}
	}

	public WordsList[][] getTable() {
		return table;
	}

	public void setTable(WordsList[][] table) {
		this.table = table;
	}

	public WordsList getWordList(int i, int j) {
		return table[i][j];
	}

	public void addWord(int i, int j, Word w) {
			table[i][j].add(w);
	}
	

}

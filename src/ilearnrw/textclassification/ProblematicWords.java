package ilearnrw.textclassification;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */

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

	
	/*public void save() throws IOException {
		int n = table.length;
		for (int i=0; i<n; i++){
			String path = "game_words_EN/cat"+i;
			for (int j=0; j<table[i].length; j++){
				ArrayList<Word> w = table[i][j].getWordList();
				ArrayList<String> base = new ArrayList<String>();
				int cnt = 0;
				for (Word x:w){
					if (x.getFrequency()>=15000)	
						base.add(x.toString());
					if (cnt++ >=300)
						break;
				}	
				base.add("###");
				for (Word x:w){
					if (x.getFrequency()<15000)	
						base.add(x.toString());
					if (cnt++ >=500)
						break;
				}
				String fileName = "words_"+i+"_"+j+"_EN.txt";
				FileWriter writer = new FileWriter("data/"+path+"/"+fileName);
				for(int ii=0;ii<base.size()-1;ii++) {
				  writer.write(base.get(ii)+"\n");
				}
				if (base.size()>0)
					writer.write(base.get(base.size()-1));
				writer.close();
			}
		}
	}
*/
}

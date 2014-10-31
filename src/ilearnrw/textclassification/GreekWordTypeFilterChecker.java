package ilearnrw.textclassification;

import ilearnrw.textclassification.greek.GreekWord;

public class GreekWordTypeFilterChecker {
	static boolean check(GreekWord w, String filter){
		//filters should be in the following format:
		//filters property:value property:value ...
		String filters[] = filter.toLowerCase().split(" ");
		String hackWord = "gender:"+(w.getWordTypeInfo().getGender() == null?"":w.getWordTypeInfo().getGender().toLowerCase())+" " +
				"mood:"+(w.getWordTypeInfo().getMood() == null?"":w.getWordTypeInfo().getMood().toLowerCase())+" " + 
				"number:"+(w.getWordTypeInfo().getNumber() == null?"":w.getWordTypeInfo().getNumber().toLowerCase())+ " " +
				"person:"+(w.getWordTypeInfo().getPerson() == null?"":w.getWordTypeInfo().getPerson().toLowerCase())+ " " +
				"tense:"+(w.getWordTypeInfo().getTense() == null?"":w.getWordTypeInfo().getTense().toLowerCase())+ " " +
				"thecase:"+(w.getWordTypeInfo().getTheCase() == null?"":w.getWordTypeInfo().getTheCase().toLowerCase())+ " " +
				"voice:"+(w.getWordTypeInfo().getVoice() == null?"":w.getWordTypeInfo().getVoice().toLowerCase());
		for (int i=1;i<filters.length; i++){
			if (!hackWord.contains(filters[i]))
					return false;
		}
		return true;
	}
}
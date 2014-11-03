package ilearnrw.textclassification;

import ilearnrw.textclassification.greek.GreekWord;

public class GreekWordTypeFilterChecker {
	static boolean check(GreekWord w, String filter){
		//filters should be in the following format:
		//(filter1, filter2, ...)
		String filters[] = filter.substring(1, filter.length()-1).toLowerCase().split(", ");
		String hackWord = "["+(w.getWordTypeInfo().getGender() == null?"":w.getWordTypeInfo().getGender().toLowerCase())+"]" +
				(w.getWordTypeInfo().getMood() == null?"":"["+w.getWordTypeInfo().getMood().toLowerCase() +"]") + 
				(w.getWordTypeInfo().getNumber() == null?"":"["+w.getWordTypeInfo().getNumber().toLowerCase() +"]") +
				(w.getWordTypeInfo().getPerson() == null?"":"["+w.getWordTypeInfo().getPerson().toLowerCase() + "]") +
				(w.getWordTypeInfo().getTense() == null?"":"["+w.getWordTypeInfo().getTense().toLowerCase() + "]") +
				(w.getWordTypeInfo().getTheCase() == null?"":"["+w.getWordTypeInfo().getTheCase().toLowerCase() + "]") +
				(w.getWordTypeInfo().getVoice() == null?"":"["+w.getWordTypeInfo().getVoice().toLowerCase() + "]");
		for (int i=0;i<filters.length; i++){
			if (!hackWord.contains("["+filters[i]+"]"))
				return false;
		}
		return true;
	}
}
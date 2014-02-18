package ilearnrw.games.mapping;
import ilearnrw.utils.LanguageCode;

import java.util.ArrayList;

public class OneToOneMapping {
	private String englishProbs[] = {"Syllable Division",
				"Vowel Sounds", "Suffixing", "Prefixing", 
				"Phon-Graph", "Letter Patterns", "Letter Names", 
				"Sight Words", "Confusing Letter Shapes"};
	private String greekProbs[] = {"Syllable Division", 
			"Phonemes:Consonants", "Phonemes:Vowels", 
			"Suffixing:Derivational", "Suffixing:Inflectional/Grammatical",
			"Prefixing", "Grapheme/Phoneme Correspondence", 
			"Grammar/Function Words", "Word Recognition", 
			"Letter Visual Similarity"};
	private String apps[] = {"Mail Sorter", "WhackaMole", 
			"Endless Runner", "Harvest", "Serenade Hero", 
			"Moving Pathways", "Eye Exam", "Typing Train Dispatcher", 
			"Drop Chop"};
	private boolean appsProbsCorrespondanceEN[][] = 
		{
			//syl div
			{false, false, true, true, false, true, true, true, true},
			//vow soun
			{true, true, false, true, false, true, true, false, false},
			//suf
			{true, true, true, true, true, false, true, true, true},
			//pref
			{true, true, true, true, true, false, true, true, true},
			//gp
			{true, true, false, true, false, true, false, true, false},
			//lett/wor
			{true, true, true, true, false, true, false, false, false},
			//lett names
			{true, true, false, true, false, false, false, false, false},
			//sight
			{true, true, false, false, false, true, false, true, false},
			//conf lett
			{true, true, false, true, true, true, true, true, false}			
		};
	
	private boolean appsProbsCorrespondanceGR[][] = 
		{
			//syl div
			{false, false, true, true, false, true, true, true, true},
			//phon:cons
			{true, false, false, false, false, true, true, false, false},
			//phon:vow
			{false, false, false, true, false, true, true, false, false},
			//der suf
			{true, true, true, true, true, false, true, true, true},
			//inf suf
			{true, true, true, true, true, false, true, true, false},
			//pref
			{true, false, true, true, true, false, true, true, true},
			//gp
			{true, true, false, true, false, true, false, false, false},
			//gram fun words
			{false, false, false, true, true, true, false, true, false},
			//word rec
			{true, true, false, false, false, true, true, true, false},
			//lett vis sim
			{true, true, false, false, false, true, false, false, false}			
		};
	public OneToOneMapping(){
		
	}
	public int getAppID(String appName){
		for (int i=0; i<apps.length; i++)
			if (apps[i].equals(appName))
				return i;
		return -1;
	}
	public int getProblemID(String probName, LanguageCode lan){
		if (lan == LanguageCode.EN){
			for (int i=0; i<englishProbs.length; i++)
				if (englishProbs[i].equals(probName))
					return i;
		}
		else{
		for (int i=0; i<greekProbs.length; i++)
			if (greekProbs[i].equals(probName))
				return i;
		}
		return -1;
	}
	public ArrayList<Integer> getProblemRelatedApps(int probId, LanguageCode lan){
		ArrayList<Integer> res = new ArrayList<Integer>();
		if (lan == LanguageCode.EN){
			if (probId>appsProbsCorrespondanceEN.length)
				return null;
			for (int i=0; i<appsProbsCorrespondanceEN[i].length; i++)
				if (appsProbsCorrespondanceEN[probId][i])
					res.add(i);
		}
		else{
			if (probId>appsProbsCorrespondanceGR.length)
				return null;
			for (int i=0; i<appsProbsCorrespondanceGR[i].length; i++)
				if (appsProbsCorrespondanceGR[probId][i])
					res.add(i);
		}
		return null;
	}
	public ArrayList<Integer> getProblemRelatedApps(String prob, LanguageCode lan){
		int id = getProblemID(prob, lan);
		return getProblemRelatedApps(id, lan);
	}
	public ArrayList<String> getProblemRelatedAppsString(int probId, LanguageCode lan){
		ArrayList<String> res = new ArrayList<String>();
		if (lan == LanguageCode.EN){
			if (probId>appsProbsCorrespondanceEN.length)
				return null;
			for (int i=0; i<appsProbsCorrespondanceEN[i].length; i++)
				if (appsProbsCorrespondanceEN[probId][i])
					res.add(englishProbs[i]);
		}
		else{
			if (probId>appsProbsCorrespondanceGR.length)
				return null;
			for (int i=0; i<appsProbsCorrespondanceGR[i].length; i++)
				if (appsProbsCorrespondanceGR[probId][i])
					res.add(greekProbs[i]);
		}
		return null;
	}
	public ArrayList<String> getProblemRelatedAppsString(String prob, LanguageCode lan){
		int id = getProblemID(prob, lan);
		return getProblemRelatedAppsString(id, lan);
	}
}

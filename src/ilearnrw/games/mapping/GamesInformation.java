package ilearnrw.games.mapping;

import ilearnrw.utils.LanguageCode;

import java.util.ArrayList;

public class GamesInformation {
	public static GamesInformation mapping = new GamesInformation();
	private static String englishProbs[] = { "Syllable Division",
			"Vowel Sounds", "Suffixing", "Prefixing", "Phon-Graph",
			"Letter Patterns", "Letter Names", "Sight Words",
			"Confusing Letter Shapes" };
	private static String greekProbs[] = { "Syllable Division",
			"Phonemes:Consonants", "Phonemes:Vowels", "Suffixing:Derivational",
			"Suffixing:Inflectional/Grammatical", "Prefixing",
			"Grapheme/Phoneme Correspondence", "Grammar/Function Words",
			"Word Recognition", "Visual Similarity" };
	private static String apps[] = { "Mail Sorter", "WhackaMole",
			"Endless Runner", "Harvest", "Serenade Hero", "Moving Pathways",
			"Eye Exam", "Typing Train Dispatcher", "Drop Chop" };
	private static boolean appsProbsCorrespondanceEN[][] = {
			// syl div
			{ false, false, true, true, false, true, true, true, true },
			// vow soun
			{ true, true, false, true, false, true, true, false, false },
			// suf
			{ true, true, true, true, true, false, true, true, true },
			// pref
			{ true, true, true, true, true, false, true, true, true },
			// gp
			{ true, true, false, true, false, true, false, true, false },
			// lett/wor
			{ true, true, true, true, false, true, false, false, false },
			// lett names
			{ true, true, false, true, false, false, false, false, false },
			// sight
			{ true, true, false, false, false, true, false, true, false },
			// conf lett
			{ true, true, false, true, true, true, true, true, false } };

	private static boolean appsProbsCorrespondanceGR[][] = {
			// syl div
			{ false, false, true, true, false, true, true, true, true },
			// phon:cons
			{ true, false, false, false, false, true, true, false, false },
			// phon:vow
			{ false, false, false, true, false, true, true, false, false },
			// der suf
			{ true, true, true, true, true, false, true, true, true },
			// inf suf
			{ true, true, true, true, true, false, true, true, false },
			// pref
			{ true, false, true, true, true, false, true, true, true },
			// gp
			{ true, true, false, true, false, true, false, false, false },
			// gram fun words
			{ false, false, false, true, true, true, false, true, false },
			// word rec
			{ true, true, false, false, false, true, true, true, false },
			// lett vis sim
			{ true, true, false, false, false, true, false, false, false } };

	// letter, word, sentence
	private static boolean appsInputsCorrespondance[][] = {
			// "Mail Sorter"
			{ true, true, false },
			// "Endless Runner"
			{ false, true, false },
			// "Harvest"
			{ false, true, false },
			// "Serenade Hero"
			{ false, false, true },
			// "Moving Pathways"
			{ true, true, false },
			// "Drop Chop"
			{ false, true, false },
			// "WhackaMole"
			{ false, true, false },
			// "Eye Exam"
			{ false, true, false },
			// "Typing Train Dispatcher"
			{ false, true, false } };

	private GamesInformation() {
	}

	public static GamesInformation getInstance() {
		return mapping;
	}

	public static int getAppID(String appName) {
		System.err.println(appName);
		for (int i = 0; i < apps.length; i++)
			if (apps[i].equalsIgnoreCase(appName))
				return i;
		return -1;
	}

	public static int getProblemID(String probName, LanguageCode lan) {
		if (lan == LanguageCode.EN) {
			for (int i = 0; i < englishProbs.length; i++)
				if (englishProbs[i].equalsIgnoreCase(probName))
					return i;
		} else {
			for (int i = 0; i < greekProbs.length; i++)
				if (greekProbs[i].equalsIgnoreCase(probName))
					return i;
		}
		return -1;
	}

	public static ArrayList<Integer> getAppRelatedProblems(int appId,
			LanguageCode lan) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		if (lan == LanguageCode.EN) {
			if (appId > appsProbsCorrespondanceEN[0].length)
				return null;
			for (int i = 0; i < appsProbsCorrespondanceEN[i].length; i++)
				if (appsProbsCorrespondanceEN[i][appId])
					res.add(i);
		} else {
			if (appId > appsProbsCorrespondanceGR.length)
				return null;
			for (int i = 0; i < appsProbsCorrespondanceGR[i].length; i++)
				if (appsProbsCorrespondanceGR[i][appId])
					res.add(i);
		}
		return null;
	}

	public static ArrayList<Integer> getAppRelatedProblems(String appName,
			LanguageCode lan) {
		int id = getAppID(appName);
		if (id==-1)
			return null;
		return getProblemRelatedApps(id, lan);
	}

	public static ArrayList<Integer> getProblemRelatedApps(int probId,
			LanguageCode lan) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		if (lan == LanguageCode.EN) {
			if (probId > appsProbsCorrespondanceEN.length)
				return null;
			for (int i = 0; i < appsProbsCorrespondanceEN[i].length; i++)
				if (appsProbsCorrespondanceEN[probId][i])
					res.add(i);
		} else {
			if (probId > appsProbsCorrespondanceGR.length)
				return null;
			for (int i = 0; i < appsProbsCorrespondanceGR[i].length; i++)
				if (appsProbsCorrespondanceGR[probId][i])
					res.add(i);
		}
		return null;
	}

	public static ArrayList<Integer> getProblemRelatedApps(String prob,
			LanguageCode lan) {
		int id = getProblemID(prob, lan);
		return getProblemRelatedApps(id, lan);
	}

	public static ArrayList<String> getProblemRelatedAppsString(int probId,
			LanguageCode lan) {
		ArrayList<String> res = new ArrayList<String>();
		if (lan == LanguageCode.EN) {
			if (probId > appsProbsCorrespondanceEN.length)
				return null;
			for (int i = 0; i < appsProbsCorrespondanceEN[i].length; i++)
				if (appsProbsCorrespondanceEN[probId][i])
					res.add(englishProbs[i]);
		} else {
			if (probId > appsProbsCorrespondanceGR.length)
				return null;
			for (int i = 0; i < appsProbsCorrespondanceGR[i].length; i++)
				if (appsProbsCorrespondanceGR[probId][i])
					res.add(greekProbs[i]);
		}
		return null;
	}

	public static ArrayList<String> getProblemRelatedAppsString(String prob,
			LanguageCode lan) {
		int id = getProblemID(prob, lan);
		return getProblemRelatedAppsString(id, lan);
	}
	
	public static boolean allowsLetters(int appId){
		return appsInputsCorrespondance[appId][0];
	}
	public static boolean allowsLetters(String appName){
		int id = getAppID(appName);
		if (id==-1)
			return false;
		return allowsLetters(id);
	}
	
	public static boolean allowsWors(int appId){
		return appsInputsCorrespondance[appId][1];
	}
	public static boolean allowsWors(String appName){
		int id = getAppID(appName);
		if (id==-1)
			return false;
		return allowsWors(id);
	}
	
	public static boolean allowsSentences(int appId){
		return appsInputsCorrespondance[appId][2];
	}
	public static boolean allowsSentences(String appName){
		int id = getAppID(appName);
		if (id==-1)
			return false;
		return allowsSentences(id);
	}
}

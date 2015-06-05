package ilearnrw.utils;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.util.ArrayList;

import com.google.gson.Gson;

import ilearnrw.languagetools.extras.EasyHardList;
import ilearnrw.languagetools.extras.WordListLoader;
import ilearnrw.prototype.application.JsonHandler;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemType;

public class ProfileTestsAndUpdates {
	public static void main(String[] args) {
		ProblemDefinitionIndex tmp;
		LanguageCode language = LanguageCode.EN;
		if (language == LanguageCode.EN){
			JsonHandler handler = new JsonHandler(Type.DATA, "problem_definitions_en.json", true);
			tmp = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
		}
		else{
			JsonHandler handler = new JsonHandler(Type.DATA, "problem_definitions_greece.json", true);
			tmp = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
		}

		WordListLoader wordListLoader = new WordListLoader();
		String path = "game_words_EN";
		
		for (int i=0; i<tmp.getProblems().length; i++){
			for (int j=0; j<tmp.getRowLength(i); j++){
				String eg = "";
				try {
					wordListLoader.load(path+"/cat"+i+"/words_"+i+"_"+j+"_EN.txt");
					EasyHardList dw = new EasyHardList(wordListLoader.getWordList());
					ArrayList<String> w = dw.getEasy();
					if (!w.isEmpty())
						eg = "For example: " + w.remove(0);
					if (!w.isEmpty())
						eg = eg+", "+w.remove(0);
					if (!w.isEmpty())
						eg = eg+", "+w.remove(0);
					w = dw.getHard();
					if (eg.isEmpty()){
						if (!w.isEmpty())
							eg = "For example: " + w.remove(0);
						if (!w.isEmpty())
							eg = eg+", "+w.remove(0);
						if (!w.isEmpty())
							eg = eg+", "+w.remove(0);
					}
				} catch (Exception e) {
					wordListLoader = null;
					e.printStackTrace();
				}
				if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.SUFFIX_ADD){
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Suffix '"+tmp.getProblemDescription(i, j).getDescriptions()[0]+
									"' adds to the word with no changes. "+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.SUFFIX_DROP){
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Suffix '"+tmp.getProblemDescription(i, j).getDescriptions()[0]+
									"' drops the last letter of the stem. "+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.SUFFIX_CHANGE){
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Suffix '"+tmp.getProblemDescription(i, j).getDescriptions()[0]+
									"' changes the last letter of the word. "+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.SUFFIX_DOUBLE){
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Suffix '"+tmp.getProblemDescription(i, j).getDescriptions()[0]+
									"' double the last letter of the word. "+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.SUFFIX_PATTERN){
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.SUFFIX_STRESS_PATTERN){
					if (j==46)
						tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Suffix -ing on words longer than one syllable " +
									"doubles the last consonant if the the last syllable is stressed " +
									"(as in submit + ing = submitting) but simply adds if first syllable " +
									"stressed (as in 'target + ing = 'targeting).");
					else if (j==47)
						tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Suffix -ed on words longer than one syllable " +
									"doubles the last consonant if the the last syllable is stressed (as " +
									"in submit + ed = submitted) but simply adds if first syllable stressed " +
									"(as in 'target + ed = 'targeted).");
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.PREFIX){
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Add prefix '"+tmp.getProblemDescription(i, j).getDescriptions()[0]+
									"' to the beginning of the word. "+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.PREFIX_GROUP){
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Prefix '"+tmp.getProblemDescription(i, j).getDescriptions()[0]+
									"' is a common pattern in some words. When you chop off this prefix, " +
									"you don't get a new word."+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.LETTER_EQUALS_PHONEME){
					String s[] = tmp.getProblemDescription(i, j).getDescriptions()[0].split("-");
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Pronounce '"+s[0]+ "' as '"+s[1]+"'."+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.DIGRAPH_EQUALS_PHONEME){
					String s[] = tmp.getProblemDescription(i, j).getDescriptions()[0].split("-");
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Pronounce '"+s[0]+ "' as '"+s[1]+"'."+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.TRIGRAPH_EQUALS_PHONEME){
					String s[] = tmp.getProblemDescription(i, j).getDescriptions()[0].split("-");
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Pronounce '"+s[0]+ "' as '"+s[1]+"'."+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.PATTERN_EQUALS_PRONUNCIATION_CONTAINS){
					String s[] = tmp.getProblemDescription(i, j).getDescriptions()[0].split("-");
					if (s.length<2)
						s = tmp.getProblemDescription(i, j).getDescriptions()[0].split("_");
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Pronounce '"+s[0]+ "' as '"+s[1]+"'."+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.PATTERN_EQUALS_PRONUNCIATION_BEGINS){
					String s[] = tmp.getProblemDescription(i, j).getDescriptions()[0].split("-");
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Pronounce '"+s[0]+ "' as '"+s[1]+"'."+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.PATTERN_EQUALS_PRONUNCIATION_ENDS){
					String s[] = tmp.getProblemDescription(i, j).getDescriptions()[0].split("-");
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Pronounce '"+s[0]+ "' as '"+s[1]+"'."+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.SYLLABLE_PATTERN){
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Syllables are divided according to the '"+
									tmp.getProblemDescription(i, j).getDescriptions()[0]+
									"' pattern."+(eg.isEmpty()?"":eg));
				}
				else if (tmp.getProblemDescription(i, j).getProblemType() == ProblemType.SYLLABLE_COUNT){
					tmp.getProblemDescription(i, j).
							setHumanReadableDescription("Has "+tmp.getProblemDescription(i, j).getDescriptions()[0]+
									"."+(eg.isEmpty()?"":eg));
				}

			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(tmp);
		System.out.println(json);
	}

}

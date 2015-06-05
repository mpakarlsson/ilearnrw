package ilearnrw.games.words;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.languagetools.extras.EasyHardList;
import ilearnrw.languagetools.extras.WordListLoader;
import ilearnrw.languagetools.greek.GreekDictionary;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.structs.sets.SortedTreeSet;
import ilearnrw.textclassification.GraphemePhonemePair;
import ilearnrw.textclassification.Sentence;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordProblemInfo;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.utils.LanguageCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
public class CollectDataForProblems {
	static GreekDictionary gd;
	static LanguageAnalyzerAPI languageAnalyser;
	static WordVsProblems wp;
	static Text txt;
	static WordListLoader wordListLoader;

	public static void main(String args[]) {
		/*
		 * System.out.println("loading dictionary..."); //gd = new
		 * GreekDictionary();
		 * System.out.println("loading language analyser..."); languageAnalyser
		 * = new GreekLanguageAnalyzer(gd, null); wp = new
		 * WordVsProblems(languageAnalyser); SortedTreeSet sts = new
		 * SortedTreeSet(); for (int i = 0; i<12;i++){ gd = new
		 * GreekDictionary(); getProblematicWords(1, i, true); }
		 */
		// unionOfSoundSimilarWords();
		
		gd = GreekDictionary.getInstance();
		LanguageCode lc = LanguageCode.GR;
		
		String lan = "EN";
		if (lc == LanguageCode.GR)
			lan = "GR";
		System.out.println("loading text...");

		String namesEN[] = {"englishCollection.txt"};
		String namesGR[] = {"deyteraDim2.txt", "papadiamantis.txt", 
				"pempthDhm2.txt", "prwthDhm2.txt", "prwthDhm3.txt", 
				"prwthDhm.txt", "prwthGym3.txt", "greekCollection.txt"};
		String names[] = namesEN;
		if (lc == LanguageCode.GR)
			names = namesGR;
		txt = loadFiles(names, lc);
		System.out.println("loading language analyser...");
		
		languageAnalyser = EnglishLanguageAnalyzer.getInstance();
		if (lc == LanguageCode.GR)
			languageAnalyser = GreekLanguageAnalyzer.getInstance();
		wp = new WordVsProblems(languageAnalyser);
		
		int row = 3, column = 3;
		wordListLoader = new WordListLoader();
		try {
			wordListLoader.load("game_words_"+lan+"/cat"+row+"/words_"+row+"_"+column+"_"+lan+".txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		EasyHardList dw = new EasyHardList(wordListLoader.getWordList());
		// for (int i = 0; i<13;i++){
		// gd = new GreekDictionary();
		getProblematicSentences(row, column, true, dw);
		// }
	}

	private static GreekDictionary getWordsWithPhonemeAtPosition(
			SortedTreeSet sts, int idx, String ph[]) {
		SortedTreeSet ws = new SortedTreeSet();
		for (Word w : sts) {
			if (idx >= w.getGraphemesPhonemes().size())
				continue;
			GraphemePhonemePair tmp = (w.getGraphemesPhonemes()).get(idx);
			GraphemePhonemePair tmp1 = null;
			if (ph[0].length() > 1 && idx + 1 < w.getGraphemesPhonemes().size()
					&& tmp.getPhoneme().length() < 2)
				tmp1 = (w.getGraphemesPhonemes()).get(idx + 1);
			String phoneme = tmp.getPhoneme();
			if (tmp1 != null)
				phoneme = tmp.getPhoneme() + tmp1.getPhoneme();
			if (phoneme.equals(ph[0]) || phoneme.equals(ph[1])) {
				ws.add(w);
			}
		}
		return null;//GreekDictionary.getInstance(ws);
	}

	private static void unionOfSoundSimilarWords() {
		GreekDictionary gr = GreekDictionary.getInstance();
		SortedTreeSet sts = new SortedTreeSet();
		String path = "greek_collection_for_problems/cat1/";
		for (int i = 0; i < 12; i++) {
			String name = path + "words_for_problem_1_" + i + "_" + "GR.txt";
			gr = null;//GreekDictionary.getInstance(name);
			SortedTreeSet s = null;// gr.getWords();
			sts = sts.union(s);
			System.out.println(sts.size());
		}
		FileData fd = new FileData(path + "similarSoundWords.txt");
		for (Word w : sts) {
			fd.data.add(w.toString() + " " + partOfSpeech(w.getType()) + " "
					+ w.getStem());
		}
		fd.store();
	}

	public static void getProblematicWords(int category, int index,
			boolean bruteForce) {
		ProblemDefinitionIndex probs = wp.getTheProblems();
		String path = "data/greek_collection_for_problems/cat" + category + "/";
		FileData fd = new FileData(path + "words_for_problem_" + category + "_"
				+ index + "_" + "GR.txt");
		SortedTreeSet sts1 = null;//gd.getWords();
		System.out.println("filling a list with words...");
		Word allWords[];
		int reps = 1;
		int next = 0;
		if (category == 1)
			reps = 20;
		String str[] = probs.getProblemDescription(category, index)
				.getDescriptions();
		for (String prb : str) {
			System.out.println("problem:" + prb);
			for (int again = 0; again < reps; again++) {
				if (category == 1) {
					String[] parts = prb.split("-");
					gd = getWordsWithPhonemeAtPosition(sts1, again, parts);
					System.out.println("loading again language analyser...");
					languageAnalyser = GreekLanguageAnalyzer.getInstance();
				}
				SortedTreeSet sts = null;//gd.getWords();
				System.out.println("filling an array with " + sts.size()
						+ " words...");
				allWords = new Word[sts.size()];
				int ii = 0;
				for (Word w : sts) {
					allWords[ii++] = w;
				}

				Random rand = new Random();
				ArrayList<Integer> tested = new ArrayList<Integer>();
				ArrayList<Integer> passed = new ArrayList<Integer>();
				int limit = 1000;
				next = 0;
				while (passed.size() < limit && tested.size() < sts.size()) {
					boolean found = false;
					tested.add(next);
					Word w = allWords[next];
					wp.insertWord(w);
					ArrayList<WordProblemInfo> wpi = wp.getMatchedProbs();
					int lastProbs[] = new int[probs.returnIndexLength()];
					if (bruteForce) {
						for (WordProblemInfo pr : wpi) {
							int x = pr.getCategory();
							int y = pr.getIndex();
							if (x == category && y == index) {
								fd.data.add(w.toString() + " "
										+ partOfSpeech(w.getType()) + " "
										+ w.getStem());
								System.out.println(w.toString() + " "
										+ partOfSpeech(w.getType()) + " "
										+ w.getStem());
								found = true;
								break;
							}
						}
					} else {
						for (int j = 0; j < lastProbs.length; j++)
							lastProbs[j] = -1;
						for (WordProblemInfo pr : wpi) {
							if (pr != null) {
								int x = pr.getCategory();
								int y = pr.getIndex();
								if (lastProbs[x] < y && y <= index)
									lastProbs[x] = y;
							}
						}
						if (lastProbs[category] == index) {
							fd.data.add(w.toString() + " "
									+ partOfSpeech(w.getType()) + " "
									+ w.getStem());
							// System.out.println(w.toString());
							found = true;
						}
					}
					if (found && !passed.contains(next)) {
						passed.add(next);
						System.out.println(passed.size());
					}
					next++;
				}
			}
		}
		try {
			System.out.println("start writing to files!");
			String results = "";
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fd.filename), "UTF-8"));
			try {
				for (String ss : fd.data)
					out.write(ss + "\n");
			} finally {
				out.close();
			}
			results = results + "Problem (" + category + ", " + index + ") : "
					+ fd.data.size() + "\n";
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path + "results.txt"), "UTF-8"));
			try {
				out.write(results);
				System.err.println(results);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getProblematicSentences(int category, int index,
			boolean bruteForce, EasyHardList easyHardList) {
		ProblemDefinitionIndex probs = wp.getTheProblems();
		String path = "";
		FileData fd = new FileData(path + "sentencesTest.txt");
		System.out.println("filling a list with sentences...");
		Sentence allSentences[] = txt.getSentences();
		for (Sentence sen : allSentences) {
			if (sen.getNumberOfWords()<3 || sen.getNumberOfWords()>12 || 
					sen.getSentence().contains(",") || sen.getSentence().contains(":"))
				continue;
			boolean found = false;
			for (Word w : sen.getWords()) {
				wp.insertWord(w);
				ArrayList<WordProblemInfo> wpi = wp.getMatchedProbs();
				int lastProbs[] = new int[probs.returnIndexLength()];
				if (bruteForce) {
					for (WordProblemInfo pr : wpi) {
						int x = pr.getCategory();
						int y = pr.getIndex();
						if (x == category && y == index) {
							ArrayList<String> destructors = easyHardList.getRandom(5, 1);
							found = true;
							String newSen = sen.getSentence().replace(w.getWordUnmodified(), 
									"{"+w.getWordUnmodified()+"}")+" "+destructors.toString();
							fd.data.add("----------------------------\n"+newSen);
							System.out.println(w.toString() + " "
									+ partOfSpeech(w.getType()) + " "
									+ w.getStem());
							break;
						}
					}
				} else {
					for (int j = 0; j < lastProbs.length; j++)
						lastProbs[j] = -1;
					for (WordProblemInfo pr : wpi) {
						if (pr != null) {
							int x = pr.getCategory();
							int y = pr.getIndex();
							if (lastProbs[x] < y && y <= index)
								lastProbs[x] = y;
						}
					}
				}
				if (found)
					break;
			}
		}
		try {
			System.out.println("start writing to files!");
			String results = "";
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fd.filename), "UTF-8"));
			try {
				for (String ss : fd.data)
					out.write(ss + "\n");
			} finally {
				out.close();
			}
			results = results + "Problem (" + category + ", " + index + ") : "
					+ fd.data.size() + "\n";
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path + "results.txt"), "UTF-8"));
			try {
				out.write(results);
				System.err.println(results);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Text loadFiles(String names[], LanguageCode lc) {

		String files;
		String path = "texts/";

		String text = "";
		try {
			for (String name : names)
				text = text+"\n\n"+new Scanner(new File(path + name), "UTF-8").useDelimiter(
					"\\A").next();
			Text t = new Text(text, lc);
			return t;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	private static WordType partOfSpeech(String pos, ArrayList<String> ext) {
		if (pos.trim().equals("ουσιαστικό"))
			return WordType.Noun;
		if (pos.trim().equals("επίθετο"))
			return WordType.Adjective;
		if (pos.trim().equals("ρήμα"))
			return WordType.Verb;
		if (ext.contains("μετοχή"))
			return WordType.Participle;
		return WordType.Unknown;
	}

	private static String partOfSpeech(WordType t) {
		if (t == WordType.Noun)
			return "ουσιαστικό";
		if (t == WordType.Adjective)
			return "επίθετο";
		if (t == WordType.Verb)
			return "ρήμα";
		if (t == WordType.Participle)
			return "μετοχή";
		return "άγνωστο";
	}
}

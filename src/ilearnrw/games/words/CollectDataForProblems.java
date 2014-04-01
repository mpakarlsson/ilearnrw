package ilearnrw.games.words;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.greek.DictionaryEntry;
import ilearnrw.languagetools.greek.GreekDictionary;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.structs.sets.SortedTreeSet;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordProblemInfo;
import ilearnrw.textclassification.WordType;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.user.problems.ProblemDefinitionIndex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

public class CollectDataForProblems {
	static GreekDictionary gd;
	static LanguageAnalyzerAPI languageAnalyser;
	static WordVsProblems wp;

	public static void main(String args[]) {
		System.out.println("loading dictionary...");
		gd = new GreekDictionary();
		System.out.println("loading language analyser...");
		languageAnalyser = new GreekLanguageAnalyzer(gd, null);
		wp = new WordVsProblems(languageAnalyser);
		for (int i = 8; i<20;i++)
			getProblematicWords(0, i);
	}

	public static void getProblematicWords(int category, int index) {
		ProblemDefinitionIndex probs = wp.getTheProblems();
		String path = "data/greek_collection_for_problems/";
		FileData fd = new FileData(path + "words_for_problem_" + category + "_"
				+ index + "_" + "GR.txt");
		boolean bruteForce = false;
		SortedTreeSet sts = gd.getWords();
		System.out.println("filling a list with words...");
		System.out.println("filling an array with " + sts.size() + " words...");
		Word allWords[] = new Word[sts.size()];
		int ii = 0;
		for (Word w : sts) {
			allWords[ii++] = w;
		}

		Random rand = new Random();
		ArrayList<Integer> tested = new ArrayList<Integer>();
		ArrayList<Integer> passed = new ArrayList<Integer>();
		int limit = 1000;
		while (passed.size() < limit && tested.size()<110000) {
			boolean found = false;
			int next = rand.nextInt(allWords.length);
			while (tested.contains(next)) {
				next = rand.nextInt(allWords.length);
				//System.out.println(next);
			}
			tested.add(next);
			Word w = allWords[next];
			wp.insertWord(w);
			ArrayList<WordProblemInfo> wpi = wp.getMatchedProbs();
			int lastProbs[] = new int[probs.getIndexLength()];
			if (bruteForce) {
				for (WordProblemInfo pr : wpi) {
					int x = pr.getCategory();
					int y = pr.getIndex();
					if (x == category && y == index) {
						fd.data.add(w.toString() + " "
								+ partOfSpeech(w.getType()) + " " + w.getStem());
						// System.out.println(w.toString()+" "+partOfSpeech(w.getType())+" "+w.getStem());
						found = true;
					}
				}
			} else {
				for (int j = 0; j < lastProbs.length; j++)
					lastProbs[j] = -1;
				for (WordProblemInfo pr : wpi) {
					if (pr != null) {
						int x = pr.getCategory();
						int y = pr.getIndex();
						if (lastProbs[x] < y)
							lastProbs[x] = y;
					}
				}
				if (lastProbs[category] == index) {
					fd.data.add(w.toString() + " " + partOfSpeech(w.getType())
							+ " " + w.getStem());
					// System.out.println(""+i+") "+w.toString());
					found = true;
				}
			}
			if (found) {
				passed.add(next);
				System.out.println(passed.size());
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

	public static void test() {
		InputStream greekDictionary;
		LanguageAnalyzerAPI languageAnalyser = new GreekLanguageAnalyzer();
		WordVsProblems wp = new WordVsProblems(languageAnalyser);
		ProblemDefinitionIndex probs = wp.getTheProblems();
		FileData fd[][] = new FileData[probs.getIndexLength()][];
		String path = "data/greek_problems/";
		boolean bruteForce = false;
		for (int i = 0; i < fd.length; i++) {
			fd[i] = new FileData[probs.getRowLength(i)];
			for (int j = 0; j < fd[i].length; j++) {
				fd[i][j] = new FileData(path + "words_for_problem_" + i + "_"
						+ j + "_" + "GR.txt");
			}
		}
		greekDictionary = ResourceLoader.getInstance().getInputStream(
				Type.DATA, "greek_dictionary.txt");
		// Open the file
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					greekDictionary, "UTF-8"));

			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String test = strLine;
				if ((test.contains("#"))) {
					continue;
				}

				String[] result = test.split("\\\t");
				if (result.length < 3)
					continue;

				String word, lemma, partOfSpeech;
				word = result[0].toLowerCase().trim();
				lemma = result[1].toLowerCase().trim();
				partOfSpeech = result[2].toLowerCase().trim();
				ArrayList<String> extras = new ArrayList<String>();
				if (result.length >= 4) {
					result = result[3].split("\\,");
					for (String s : result) {
						extras.add(s.toLowerCase().trim());
					}
				}
				DictionaryEntry entry = new DictionaryEntry(lemma,
						partOfSpeech, extras);
				if (entry.isActive() && word != null && !word.isEmpty()) {
					Word w = new GreekWord(word, partOfSpeech(partOfSpeech,
							extras));
					wp.insertWord(w);
					ArrayList<WordProblemInfo> wpi = wp.getMatchedProbs();
					int lastProbs[] = new int[probs.getIndexLength()];
					if (bruteForce) {
						for (WordProblemInfo pr : wpi) {
							int x = pr.getCategory();
							int y = pr.getIndex();
							fd[x][y].data.add(test);
						}
					} else {
						for (int i = 0; i < lastProbs.length; i++)
							lastProbs[i] = -1;
						for (WordProblemInfo pr : wpi) {
							int x = pr.getCategory();
							int y = pr.getIndex();
							if (lastProbs[x] < y)
								lastProbs[x] = y;
						}
						for (int i = 0; i < fd.length; i++) {
							if (lastProbs[i] > -1) {
								fd[i][lastProbs[i]].data.add(test);
							}
						}
					}
				}
				// Print the content on the console
				// System.out.println (strLine);
			}
			System.out.println("start writing to files!");
			String results = "";
			for (int i = 0; i < fd.length; i++) {
				for (int j = 0; j < fd[i].length; j++) {
					Writer out = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(fd[i][j].filename), "UTF-8"));
					try {
						for (String ss : fd[i][j].data)
							out.write(ss + "\n");
					} finally {
						out.close();
					}
					results = results + "Problem (" + i + ", " + j + ") : "
							+ fd[i][j].data.size() + "\n";
				}
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path + "results.txt"), "UTF-8"));
			try {
				out.write(results);
			} finally {
				out.close();
			}

			// Close the input stream
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end of program!");
		// int i=0;
		// for (Word w:greekWords)
		// System.out.println((++i)+" "+w.toString()+" "+w.getPhonetics()+" "+w.getType());

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

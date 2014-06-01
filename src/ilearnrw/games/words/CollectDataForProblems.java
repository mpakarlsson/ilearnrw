package ilearnrw.games.words;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.greek.GreekDictionary;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.resource.ResourceLoader;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.structs.sets.SortedTreeSet;
import ilearnrw.textclassification.GraphemePhonemePair;
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
		/*System.out.println("loading dictionary...");
		//gd = new GreekDictionary();
		System.out.println("loading language analyser...");
		languageAnalyser = new GreekLanguageAnalyzer(gd, null);
		wp = new WordVsProblems(languageAnalyser);
		SortedTreeSet sts = new SortedTreeSet();
		for (int i = 0; i<12;i++){
			gd = new GreekDictionary();
			getProblematicWords(1, i, true);
		}*/
		unionOfSoundSimilarWords();
	}
	
	private static GreekDictionary getWordsWithPhonemeAtPosition(SortedTreeSet sts, int idx, String ph[]){
		SortedTreeSet ws = new SortedTreeSet();
		for (Word w : sts){
			if (idx>=w.getGraphemesPhonemes().size())
				continue;
			GraphemePhonemePair tmp = (w.getGraphemesPhonemes()).get(idx);
			GraphemePhonemePair tmp1 = null;
			if (ph[0].length()>1 && idx+1<w.getGraphemesPhonemes().size() && tmp.getPhoneme().length()<2)
				tmp1 = (w.getGraphemesPhonemes()).get(idx+1);
			String phoneme = tmp.getPhoneme();
			if (tmp1 != null)
				phoneme = tmp.getPhoneme() + tmp1.getPhoneme();
			if (phoneme.equals(ph[0]) || phoneme.equals(ph[1])){
				ws.add(w);
			}
		}
		return new GreekDictionary(ws);
	}

	private static void unionOfSoundSimilarWords(){
		GreekDictionary gr = new GreekDictionary();
		SortedTreeSet sts = new SortedTreeSet();
		String path = "greek_collection_for_problems/cat1/";
		for (int i = 0; i<12;i++){
			String name = path + "words_for_problem_1_"+ i + "_" + "GR.txt";
			gr = new GreekDictionary(name);
			SortedTreeSet s = gr.getWords();
			sts = sts.union(s);
			System.out.println(sts.size());
		}
		FileData fd = new FileData(path + "similarSoundWords.txt");
		for (Word w : sts){
			fd.data.add(w.toString() + " "
					+ partOfSpeech(w.getType()) + " " + w.getStem());
		}
		fd.store();
	}
	
	public static void getProblematicWords(int category, int index, boolean bruteForce) {
		ProblemDefinitionIndex probs = wp.getTheProblems();
		String path = "data/greek_collection_for_problems/cat"+category+"/";
		FileData fd = new FileData(path + "words_for_problem_" + category + "_"
				+ index + "_" + "GR.txt");
		SortedTreeSet sts1 = gd.getWords();
		System.out.println("filling a list with words...");
		Word allWords[];
		int reps = 1;
		int next = 0;
		if (category == 1)
			reps = 20;
		String str[] = probs.getProblemDescription(category, index).getDescriptions();
		for (String prb : str){
			System.out.println("problem:"+prb);
			for (int again = 0; again<reps; again++){
				if (category == 1){
					String[] parts = prb.split("-");
					gd = getWordsWithPhonemeAtPosition(sts1, again, parts);
					System.out.println("loading again language analyser...");
					languageAnalyser = new GreekLanguageAnalyzer(gd, null);
				}
				SortedTreeSet sts = gd.getWords();
				System.out.println("filling an array with " + sts.size() + " words...");
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
				while (passed.size() < limit && tested.size()<sts.size()) {
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
										+ partOfSpeech(w.getType()) + " " + w.getStem());
								System.out.println(w.toString()+" "+partOfSpeech(w.getType())+" "+w.getStem());
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
								if (lastProbs[x] < y && y<=index)
									lastProbs[x] = y;
							}
						}
						if (lastProbs[category] == index) {
							fd.data.add(w.toString() + " " + partOfSpeech(w.getType())
									+ " " + w.getStem());
							//System.out.println(w.toString());
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

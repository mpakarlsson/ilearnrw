package ilearnrw.structs.sets;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.textclassification.Word;
import java.util.*;

public class SortedTreeSet extends TreeSet<Word>{
	private static final long serialVersionUID = 1L;

	public SortedTreeSet intersection(SortedTreeSet b){
		SortedTreeSet result = new SortedTreeSet();
		Iterator<Word> iterator = this.iterator();
		if (b == null)
			return result;
		while (iterator.hasNext()) {
			Word tmp = iterator.next();
			if(b.contains(tmp))
				result.add(tmp);
		}
		return result;
	}
	
	public SortedTreeSet union(SortedTreeSet b){
		SortedTreeSet result = new SortedTreeSet();
		Iterator<Word> iterator = this.iterator();
		while (iterator.hasNext()) {
			Word tmp = iterator.next();
			result.add(tmp);
		}
		if (b != null){
			iterator = b.iterator();
			while (iterator.hasNext()) {
				Word tmp = iterator.next();
				result.add(tmp);
			}
		}
		return result;
	}
	public SortedTreeSet diff(SortedTreeSet b){
		SortedTreeSet result = new SortedTreeSet();
		Iterator<Word> iterator = this.iterator();
		while (iterator.hasNext()) {
			Word tmp = iterator.next();
			if(b == null || !b.contains(tmp))
				result.add(tmp);
		}
		return result;
	}
	public ArrayList<Word> diffToList(SortedTreeSet b){
		ArrayList<Word> result = new ArrayList<Word>();
		Iterator<Word> iterator = this.iterator();
		while (iterator.hasNext()) {
			Word tmp = iterator.next();
			if(b == null || !b.contains(tmp))
				result.add(tmp);
		}
		return result;
	}
	
	public ArrayList<Word> getRandomElementsNotIn(int n, SortedTreeSet ex){
		ArrayList<Word> availableWords = diffToList(ex);
		ArrayList<Word> res = new ArrayList<Word>();
		Random rand = new Random();
		while (!availableWords.isEmpty() && res.size()<n) {
			res.add(availableWords.remove(rand.nextInt(availableWords.size())));
		}
		return res;
	}
	@Override
	public String toString() {
		Iterator<Word> iterator = this.iterator();	 
		String str = "";
		while (iterator.hasNext()) {
			str = str + " ["+iterator.next() + "]";
		}
		System.out.println();
		return "SortedTreeSet ["+str+"]";
	}
	
}

package ilearnrw.structs.sets;
import ilearnrw.textclassification.Word;

import java.util.*;
public class SortedTreeSet extends TreeSet<Word>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SortedTreeSet intersection(SortedTreeSet b){
		SortedTreeSet result = new SortedTreeSet();
		Iterator<Word> iterator = this.iterator();
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
		iterator = b.iterator();
		while (iterator.hasNext()) {
			Word tmp = iterator.next();
			result.add(tmp);
		}
		return result;
	}
	public SortedTreeSet diff(SortedTreeSet b){
		SortedTreeSet result = new SortedTreeSet();
		Iterator<Word> iterator = this.iterator();
		while (iterator.hasNext()) {
			Word tmp = iterator.next();
			if(!b.contains(tmp))
				result.add(tmp);
		}
		return result;
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

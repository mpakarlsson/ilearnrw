package ilearnrw.user;


import java.io.Serializable;

public class UserTextCounters implements Serializable {

	private static final long serialVersionUID = 1L;
	private int counters[][];

	public UserTextCounters() {
		counters = null;

	}

	public UserTextCounters(int length) {
		counters = new int[length][];
	}

	public UserTextCounters(UserSeveritiesToProblems usp){
		counters = new int[usp.getNumerOfRows()][];
		for (int i=0;i<usp.getNumerOfRows(); i++){
			counters[i] = new int[usp.getRowLength(i)];
			for (int j=0; j<counters[i].length; j++){
				counters[i][j] = 0;
			}
		}
	}

	public boolean constructRow(int i, int elements) {
		counters[i] = new int[elements];
		for (int j=0; j<counters[i].length; j++)
			counters[i][j] = 0;
		return true;
	}

	public void setValue(int i, int j, int value) {
		counters[i][j] = value;
	}

	public void increaseValue(int i, int j) {
		counters[i][j]++;
	}

	public int getValue(int i, int j) {
		return counters[i][j];
	}

	public int getLength() {
		return counters.length;
	}

	public int getRowLength(int i) {
		return counters[i].length;
	}

	public int[][] getCounters() {
		return counters;
	}

	@Override
	public String toString() {
		if (counters == null)
			return "null counters matrix";
		String res = "User Problems Against Text Matrix\n";
		for (int i = 0; i < counters.length; i++) {
			res = res + "|";
			for (int j = 0; j < counters[i].length; j++) {
				res = res + counters[i][j] + " | ";
			}
			res = res + "\n";
		}
		return res;
	}
}

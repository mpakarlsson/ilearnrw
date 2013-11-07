package ilearnrw.user;

import java.io.Serializable;

public class UserSeverities implements Serializable {

	private static final long serialVersionUID = 1L;
	private int indices[];
	private int severities[][];

	public UserSeverities() {
		indices = null;
		severities = null;

	}

	public UserSeverities(int length) {
		indices = new int[length];
		severities = new int[length][];
	}

	public boolean constructRow(int i, int elements) {
		if (severities == null || i < 0 || i > severities.length)
			return false;
		severities[i] = new int[elements];
		return true;
	}

	public void setWorkingIndex(int i, int value) {
		indices[i] = value;
	}

	public int getWorkingIndex(int i) {
		return indices[i];
	}

	public void setSeverity(int i, int j, int value) {
		severities[i][j] = value;
	}

	public int getNumberOfRows() {
		return indices.length;
	}

	public int getSeverity(int i, int j) {
		return severities[i][j];
	}

	public int getLength() {
		return indices.length;
	}

	public void setLength(int length) {
	//	indices = new int[length];
		severities = new int[length][];
	}
	
	public int getSeverityLength(int i) {
		return severities[i].length;
	}

	public int getIthIndex(int i) {
		return indices[i];
	}

	public int[] getIndices() {
		return indices;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

	public int[][] getSeverities() {
		return severities;
	}

	public void setSeverities(int[][] severities) {
		this.severities = severities;
	}

	@Override
	public String toString() {
		if (indices == null || severities == null)
			return "null indices matrix";
		String res = "";
		for (int i = 0; i < indices.length; i++) {
			res = res + indices[i] + " ] : |";
			for (int j = 0; j < severities[i].length; j++) {
				res = res + severities[i][j] + " | ";
			}
			res = res + "\n";
		}
		return res;
	}
}

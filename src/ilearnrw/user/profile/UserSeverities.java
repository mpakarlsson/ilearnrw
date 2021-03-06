package ilearnrw.user.profile;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.problems.ProblemDefinitionIndex;

import java.io.Serializable;

public class UserSeverities implements Serializable {

	private static final long serialVersionUID = 1L;
	// the array systemIndices contains the system's index about the user
	private int systemIndices[];
	// the array teacherIndices contains the teacher's index about the user
	private int teacherIndices[];
	// the matrix severities contains the severities to the problems of the user
	private int severities[][];
	
	private int systemCluster = -1;

	public UserSeverities() {
		systemIndices = null;
		teacherIndices = null;
		severities = null;
	}

	public UserSeverities(int length) {
		systemIndices = new int[length];
		teacherIndices = new int[length];
		severities = new int[length][];
	}

	public boolean constructRow(int i, int elements) {
		if (severities == null || i < 0 || i > severities.length)
			return false;
		severities[i] = new int[elements];
		return true;
	}

	public void setSystemIndex(int i, int value) {
		systemIndices[i] = value;
	}

	public int getSystemIndex(int i) {
		return systemIndices[i];
	}

	public void setTeacherIndex(int i, int value) {
		teacherIndices[i] = value;
	}

	public int getTeacherIndex(int i) {
		return teacherIndices[i];
	}
	
	public void setIndicesFromCount(ProblemDefinitionIndex theProblems)
	{
		for (int i = 0; i < theProblems.getProblemsIndex().length; i++) {
			teacherIndices[i] = systemIndices[i] = countAcomplished(i, theProblems);  
		}
	}
	
	private int countAcomplished(int i, ProblemDefinitionIndex theProblems) {
		int count = 0;
		int threshold;
		if (theProblems.getProblemDefinition(i).getSeverityType().equals("binary"))
			threshold = 0;
		else {
			threshold = 1;
		}
		for (int j = 0; j < severities[i].length; j++) {
			if (severities[i][j] <= threshold)
				count++;
		}
		return count;
	}

	public void setSeverity(int i, int j, int value) {
		severities[i][j] = value;
	}

	public int getSeverity(int i, int j) {
		return severities[i][j];
	}

	public int getNumberOfRows() {
		return systemIndices.length;
	}

	public int getLength() {
		return systemIndices.length;
	}

	public void setLength(int length) {
	//	indices = new int[length];
		severities = new int[length][];
	}
	
	public int getSeverityLength(int i) {
		return severities[i].length;
	}

	public int getIthIndex(int i) {
		return systemIndices[i];
	}

	public int[] getSystemIndices() {
		return systemIndices;
	}

	public void setSystemIndices(int[] indices) {
		this.systemIndices = indices;
	}

	public int[] getTeacherIndices() {
		return teacherIndices;
	}

	public void setTeacherIndices(int[] indices) {
		this.teacherIndices = indices;
	}
	
	public int getSystemCluster(){
		
		return this.systemCluster;
	}
	
	public void setSystemCluster(int cluster){
		this.systemCluster = cluster;
	}

	public int[][] getSeverities() {
		return severities;
	}

	public void setSeverities(int[][] severities) {
		this.severities = severities;
	}

	@Override
	public String toString() {
		if (systemIndices == null || severities == null)
			return "null indices matrix";
		String res = "";
		for (int i = 0; i < systemIndices.length; i++) {
			res = res + systemIndices[i] + ", "+teacherIndices[i]+" ] : |";
			for (int j = 0; j < severities[i].length; j++) {
				res = res + severities[i][j] + " | ";
			}
			res = res + "\n";
		}
		return res;
	}
}

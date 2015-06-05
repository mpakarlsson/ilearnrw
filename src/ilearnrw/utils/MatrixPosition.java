package ilearnrw.utils;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
public class MatrixPosition {
	private int i, j;

	
	public MatrixPosition() {
		this.i = -1;
		this.j = -1;
	}
	
	public MatrixPosition(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

}

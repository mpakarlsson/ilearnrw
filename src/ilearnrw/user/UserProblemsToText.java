package ilearnrw.user;


import java.io.Serializable;

public class UserProblemsToText implements Serializable {
	private static final long serialVersionUID = 1L;

	private UserSeveritiesToProblems userS2P;
	private UserTextCounters userCnts;
	int threshold = 1;//the threshold for a severity so that the corresponding problem will be counted in the matrix
	
	public UserProblemsToText(){
		this.userS2P = null;
		this.userCnts = null;
	}
	
	public UserProblemsToText(UserSeveritiesToProblems userS2P){
		this.userS2P = userS2P;
		int n = userS2P.getNumerOfRows();
		
		userCnts = new UserTextCounters(n);
		
		for (int i=0; i<n; i++){
			userCnts.constructRow(i, userS2P.getRowLength(i));
		}
	}

	public void setValue(int i, int j, int value) {
		userCnts.setValue(i, j, value);
	}

	public void increaseValue(int i, int j) {
		if (userS2P.getSeverity(i, j)>threshold)
			userCnts.increaseValue(i, j);
	}

	public int getValue(int i, int j) {
		return userCnts.getValue(i, j);
	}

	public UserSeveritiesToProblems getUserSeveritiesToProblems(){
		return userS2P;
	}

	public void setUserSeveritiesToProblems(UserSeveritiesToProblems userS2P) {
		this.userS2P = userS2P;
	}
	
	@Override
	public String toString(){
		if (userCnts == null)
			return "null counters or problems matrix";
		String res = "";
		res = res + userCnts.toString();
		return res;
	}
	
}

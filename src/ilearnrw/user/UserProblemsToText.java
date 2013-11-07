package ilearnrw.user;


import java.io.Serializable;

public class UserProblemsToText implements Serializable {
	private static final long serialVersionUID = 1L;

	private UserSeveritiesToProblems userSeveritiesToProblems;
	private UserTextCounters userCounters;
	// TODO fix the threshold to a valua that the experts want!
	int threshold = -1;//the threshold for a severity so that the corresponding problem will be counted in the matrix
	
	public UserProblemsToText(){
		this.userSeveritiesToProblems = null;
		this.userCounters = null;
	}
	
	public UserProblemsToText(UserSeveritiesToProblems userS2P){
		this.userSeveritiesToProblems = userS2P;
		int n = userS2P.getNumerOfRows();
		
		userCounters = new UserTextCounters(n);
		
		for (int i=0; i<n; i++){
			userCounters.constructRow(i, userS2P.getRowLength(i));
		}
	}

	public void setValue(int i, int j, int value) {
		userCounters.setValue(i, j, value);
	}

	public void increaseValue(int i, int j) {
		if (userSeveritiesToProblems.getSeverity(i, j)>threshold)
			userCounters.increaseValue(i, j);
	}

	public int getValue(int i, int j) {
		return userCounters.getValue(i, j);
	}

	public UserSeveritiesToProblems getUserSeveritiesToProblems(){
		return userSeveritiesToProblems;
	}

	public void setUserSeveritiesToProblems(UserSeveritiesToProblems userS2P) {
		this.userSeveritiesToProblems = userS2P;
	}
	
	
	public UserTextCounters getUserCounters() {
		return userCounters;
	}

	public void setUserCounters(UserTextCounters userCounters) {
		this.userCounters = userCounters;
	}

	@Override
	public String toString(){
		if (userCounters == null)
			return "null counters or problems matrix";
		String res = "";
		res = res + userCounters.toString();
		return res;
	}
	
}

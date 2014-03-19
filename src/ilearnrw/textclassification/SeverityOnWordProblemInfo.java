package ilearnrw.textclassification;

import ilearnrw.user.profile.UserProfile;

import java.util.ArrayList;

public class SeverityOnWordProblemInfo extends WordProblemInfo{
	private int userSeverity;
	public SeverityOnWordProblemInfo() {
		super();
		this.userSeverity = -1;
	}
	
	public void setProblemInfo(int posI, int posJ, ArrayList<StringMatchesInfo> smi, 
			UserProfile userProfile) {
		if (smi!=null) {
			this.found = true;
			this.category = posI;
			this.index = posJ;
			this.matched = smi;
			if (userProfile != null){
				userSeverity = userProfile.getUserProblems().getUserSeverity(posI, posJ);
			}
		}
		else 
			this.found = false;
	}
	
	public void setProblemInfo(WordProblemInfo wpi, UserProfile userProfile) {
		if (wpi!=null) {
			this.found = true;
			this.category = wpi.getCategory();
			this.index = wpi.getIndex();
			this.matched = wpi.getMatched();
			if (userProfile != null){
				userSeverity = userProfile.getUserProblems().getUserSeverity(category, index);
			}
		}
		else 
			this.found = false;
	}

	public int getUserSeverity() {
		return userSeverity;
	}

	public void setUserSeverity(int userSeverity) {
		this.userSeverity = userSeverity;
	}

	@Override
	public String toString() {
		return "SeverityOnWordProblemInfo [problem:(" + category + ", " + index + "), matched=" + matched + 
				" severity="+userSeverity+"]";
	}

}

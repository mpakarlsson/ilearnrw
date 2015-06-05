package ilearnrw.textadaptation;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.profile.UserProfile;
import ilearnrw.user.profile.UserProblems;
import java.io.Serializable;



public class PresentationRulesModule implements PresentationRules, Serializable{
	
	private static final long serialVersionUID = 1L;
	private UserProfile profile;
	private Rule[][] rulesTable; 
	
	
	public PresentationRulesModule(UserProfile profile)
	{
		this.profile = profile;
		initializePresentationRules();
	}
	
	
	/**
	 * Initializes a Presentation Rules object based on a user's profile.
	 */
	public void initializePresentationRules(){
		UserProblems userProblems = this.profile.getUserProblems();
		
		int[][] severities = userProblems.getUserSeverities().getSeverities();
		rulesTable = new Rule[severities.length][];
		
		for (int i = 0; i < severities.length; i++)
		{
			rulesTable[i] = new Rule[severities[i].length];
			for(int j=0; j<rulesTable[i].length; j++)
				rulesTable[i][j] = new Rule();
		}
	}
	
	public Rule[][] getRulesTable()
	{
		return this.rulesTable;
	}
	
	public void setRulesTable(Rule rulesTable[][])
	{
		this.rulesTable = rulesTable;
	}

	/**
	 * Sets a new user profile to the Presentation Rules object
	 */
	public void setUserProfile(UserProfile profile)
	{
		this.profile = profile;
	}

	/**
	 * Returns the current user profile
	 */
	public UserProfile getUserProfile()
	{
		return this.profile;
	}

	/**
	 * Sets presentation rule r to the profile entry that corresponds to indices i,j.
	 * 
	 */
	public void setPresentationRule(int i, int j, int r)
	{
		rulesTable[i][j].setPresentationStyle(r);
	}

	/**
	 * Returns an integer indicating the presentation rule for the profile entry that corresponds to indices i,j.
	 */
	public int getPresentationRule(int i, int j)
	{
		return rulesTable[i][j].getPresentationStyle();
	}

	/**
	 * Sets the desired text colour for the problem that corresponds to indices i,j of the user’s profile table.
	 */
	public void setTextColor(int i, int j, int textColor)
	{
		rulesTable[i][j].setTextColor(textColor);
	}
	
	/**
	 * Returns the text colour for the problem that corresponds to indices i,j of the user’s profile table.
	 */
	public int getTextColor(int i, int j)
	{
		return rulesTable[i][j].getTextColor();
	}	

	/**
	 * Sets the highlighting colour for the problem that corresponds to indices i,j of the user’s profile table.
	 */
	public void setHighlightingColor(int i, int j, int highlightingColor)
	{
		rulesTable[i][j].setHighlightingColor(highlightingColor);
	}

	/**
	 * Returns the highlighting colour for the problem that corresponds to indices i,j of the user’s profile table.
	 */
	public int getHighlightingColor(int i, int j)
	{
		return rulesTable[i][j].getHighlightingColor();
	}

	/**
	 * Sets whether or not the problem that corresponds to indices i,j of the user’s profile table is activated or not.
	 */
	public void setActivated(int i, int j, boolean activated)
	{
		rulesTable[i][j].setActivated(activated);
	}

	/**
	 * Returns true if the problem that corresponds to indices i,j of the user’s profile is activated, false otherwise.
	 */
	public boolean getActivated(int i, int j)
	{
		return rulesTable[i][j].getActivated();
	}
	
}

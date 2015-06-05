package ilearnrw.textadaptation;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */

import ilearnrw.user.profile.UserProfile;
import java.awt.Color;

public interface PresentationRules {

	void initializePresentationRules();

	void setUserProfile(UserProfile profile);

	UserProfile getUserProfile();

	void setPresentationRule(int i, int j, int r);

	int getPresentationRule(int i, int j);

	void setTextColor(int i, int j, int color);
	
	int getTextColor(int i, int j);	

	void setHighlightingColor(int i, int j, int color);

	int getHighlightingColor(int i, int j);

	void setActivated(int i, int j, boolean flag);

	boolean getActivated(int i, int j);

}

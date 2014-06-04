package ilearnrw.annotation.tests;

import ilearnrw.textadaptation.TextAnnotationModule;
import ilearnrw.textadaptation.PresentationRulesModule;
import ilearnrw.user.profile.UserProfile;

public class Mediator 
{
	private TextAnnotationModule tx;
	private PresentationRulesModule pm;
	private int xCellCoord;
	private int yCellCoord;
	private UserProfile userProfile;
	
	public Mediator()
	{
		xCellCoord = -1;
		yCellCoord = -1;
	}
	
	public void setPresentationRulesModule(PresentationRulesModule pm)
	{
		this.pm = pm;
	}
	
	public void setTextAnnotationModule(TextAnnotationModule tx)
	{
		this.tx = tx;
	}
	
	public PresentationRulesModule getPresentationRulesModule()
	{
		return this.pm;
	}
	
	public void setProfile(UserProfile userProfile)
	{
		this.userProfile = userProfile;
	}
	
	public UserProfile getUserProfile()
	{
		return userProfile;
	}
	
	public TextAnnotationModule getTextAnnotationModule()
	{
		return this.tx;
	}
	
	public void setXCellCoord(int xCellCoord)
	{
		this.xCellCoord = xCellCoord;
	}
	
	public void setYCellCoord(int yCellCoord)
	{
		this.yCellCoord = yCellCoord;
	}
	
	public int getXCellCoord()
	{
		return this.xCellCoord;
	}
	
	public int getYCellCoord()
	{
		return this.yCellCoord;
	}
}

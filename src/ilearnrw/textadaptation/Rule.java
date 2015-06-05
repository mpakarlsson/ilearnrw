package ilearnrw.textadaptation;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
public class Rule {

	private int presentationStyle;
	private int textColor;
	private int highlightingColor;
	private boolean activated;

	public static final int DO_NOTHING = 0;
	public static final int PAINT_PROBLEMATIC_PARTS = 1;
	public static final int PAINT_WHOLE_WORD = 2;
	public static final int HIGHLIGHT_PROBLEMATIC_PARTS = 3;
	public static final int HIGHLIGHT_WHOLE_WORD = 4;
	public static final int HIDE_PROBLEMATIC_PARTS = 5;
	
	public Rule()
	{
		this.presentationStyle = DO_NOTHING;
		this.textColor =  0xffff0000; 
		this.highlightingColor = 0xffff00; //color yellow
		this.activated = false;
	}
	
	public Rule(int presentationStyle, boolean activated)
	{
		this.presentationStyle = presentationStyle;
		this.activated = activated;
	}
	
	public void setPresentationStyle(int presentationStyle)
	{
		this.presentationStyle = presentationStyle;
	}
	
	public int getPresentationStyle()
	{
		return this.presentationStyle;
	}
	
	public void setTextColor(int textColor)
	{
		this.textColor = textColor;
	}
	
	public int getTextColor()
	{
		return this.textColor;
	}

	public void setHighlightingColor(int highlightingColor)
	{
		this.highlightingColor = highlightingColor;
	}

	public int getHighlightingColor()
	{
		return this.highlightingColor;
	}

	public void setActivated(boolean activated)
	{
		this.activated = activated;
	}

	public boolean getActivated()
	{
		return this.activated;
	}
	
	public String toString()
	{
		return this.presentationStyle + " " + this.textColor +" " + this.highlightingColor +" " + this.activated;
	}
	
}

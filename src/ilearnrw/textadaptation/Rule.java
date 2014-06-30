package ilearnrw.textadaptation;

import java.awt.Color;

public class Rule {

	private int presentationStyle;
	private int textColor;
	private int highlightingColor;
	private boolean activated;
	
	public static final int PAINT_PROBLEMATIC_PARTS = 0;
	public static final int PAINT_WHOLE_WORD = 1;
	public static final int HIGHLIGHT_PROBLEMATIC_PARTS = 2;
	public static final int HIGHLIGHT_WHOLE_WORD = 3;
	public static final int DO_NOTHING = 4;
	
	public Rule()
	{
		this.presentationStyle = HIGHLIGHT_WHOLE_WORD;
		this.textColor =  0xffff0000; 
		this.highlightingColor = 0xffff00; //color yellow
		this.activated = true;
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

package ilearnrw.textadaptation.utils;

import ilearnrw.textadaptation.Rule;

public class Utils {

	
	public static double calculateWordDifficulty(String word)
	{
		return 0;
	}
	
	public static String translateRulesToHTMLTags(Rule rule)
	{
		if (rule.getPresentationStyle() == Rule.HIGHLIGHT_PROBLEMATIC_PARTS || rule.getPresentationStyle() == Rule.HIGHLIGHT_WHOLE_WORD)
		{
			return "background-color";
		}
		else if (rule.getPresentationStyle() == Rule.PAINT_PROBLEMATIC_PARTS || rule.getPresentationStyle() == Rule.PAINT_WHOLE_WORD)
		{
			return "color";
		}
		else
			return ""; 
	}
	
	public static String rgbToHEX(java.awt.Color color)
	{
		String hex = Integer.toHexString(color.getRGB() & 0xffffff);
		if (hex.length() < 6) 
		{
		    hex = "0" + hex;
		}
		hex = "#" + hex;
		return hex;
	}
	
}

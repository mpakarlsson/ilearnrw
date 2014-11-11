package ilearnrw.annotation;


import ilearnrw.textclassification.*;
import ilearnrw.textadaptation.Rule;
/**
 * An object indicating which part of the word will be finally annotated.
 * @author Fouli
 *
 */
public class FinalAnnotation 
{
	private WordProblemInfo wordProblemInfo;
	private StringMatchesInfo stringMatchesInfo;
	private Rule rule;
	
	
	public FinalAnnotation(WordProblemInfo wordProblemInfo, StringMatchesInfo stringMatchesInfo, Rule rule)
	{
		this.wordProblemInfo = wordProblemInfo;
		this.stringMatchesInfo = stringMatchesInfo;
		this.rule = rule;
	}
	
	public void setRule(Rule rule)
	{
		this.rule = rule;
	}
	
	public Rule getRule()
	{
		return this.rule;
	}
	
	public void StringMatchesInfo(StringMatchesInfo stringMatchesInfo)
	{
		this.stringMatchesInfo = stringMatchesInfo;
	}
	
	public StringMatchesInfo getStringMatchesInfo()
	{
		return this.stringMatchesInfo;
	}
	
	public void setWordProblemInfo(WordProblemInfo wordProblemInfo)
	{
		this.wordProblemInfo = wordProblemInfo;
	}
	
	public WordProblemInfo getWordProblemInfo()
	{
		return this.wordProblemInfo;
	}
	
	public String toString()
	{
		return this.rule+"";
	}
	
	
}

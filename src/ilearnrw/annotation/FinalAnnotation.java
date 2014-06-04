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
	private SeverityOnWordProblemInfo severityOnWordProblemInfo;
	private StringMatchesInfo stringMatchesInfo;
	private Rule rule;
	
	
	public FinalAnnotation(SeverityOnWordProblemInfo severityOnWordProblemInfo, StringMatchesInfo stringMatchesInfo, Rule rule)
	{
		this.severityOnWordProblemInfo = severityOnWordProblemInfo;
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
	
	public void setSeverityOnWordProblemInfo(SeverityOnWordProblemInfo severityOnWordProblemInfo)
	{
		this.severityOnWordProblemInfo = severityOnWordProblemInfo;
	}
	
	public SeverityOnWordProblemInfo getSeverityOnWordProblemInfo()
	{
		return this.severityOnWordProblemInfo;
	}
	
	
}

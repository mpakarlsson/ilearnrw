/**
 * @author Chris Litsas 
 * 10 Ιουλ 2013 2:21:21 μ.μ.
**/
package ilearnrw.games;

import java.util.ArrayList;

import ilearnrw.user.LanguageCode;
import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemNode;


public class NumberProblems {
	ProblemDefinitionIndex allProbs;

	public NumberProblems() {
		this.allProbs = new ProblemDefinitionIndex();
	}

	public void initialize(){
		ArrayList<LanguageCode> lang = new ArrayList<LanguageCode>();
		lang.add(LanguageCode.EN);
		lang.add(LanguageCode.GR);
		
		//1) Problem on adding numbers (one digit, two digits, three digits, four digits)
		Category cat = new Category("Numbers.Addition");
		ProblemDefinition prob = new ProblemDefinition("1.1", cat, 4, lang);
		prob.addProblemNode(new ProblemNode("One Digit", null));
		prob.addProblemNode(new ProblemNode("Two Digits", null));
		prob.addProblemNode(new ProblemNode("Three Digits", null));
		prob.addProblemNode(new ProblemNode("Four Digits", null));
		allProbs.addProblemDefinition(prob);
		

		//2) Problem on recognizing numbers containing the digit {3} or {4} or {5 and ending in 2 or 3 or 7} 
		cat = new Category("Numbers.Recognition");
		prob = new ProblemDefinition("1.2", cat, 3, lang);
		prob.addProblemNode(new ProblemNode("3", null));
		prob.addProblemNode(new ProblemNode("4", null));
		
		ArrayList<ProblemNode> subnodes = new ArrayList<ProblemNode>();
		subnodes.add(new ProblemNode("Ends With Number 2", null));
		subnodes.add(new ProblemNode("Ends With Number 3", null));
		subnodes.add(new ProblemNode("Ends With Number 7", null));
		
		prob.addProblemNode(new ProblemNode("5", subnodes));

		
		//3) Problem on recognizing numbers containing pattern xy, where y = x+1
		prob = new ProblemDefinition("1.3", cat, 1, lang);
		prob.addProblemNode(new ProblemNode("Containing xy, y = x+1", null));
		
	}
	

}

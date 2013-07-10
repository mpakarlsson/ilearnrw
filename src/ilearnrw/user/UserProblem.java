package ilearnrw.user;

import java.io.Serializable;

import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemNode;


public class UserProblem implements Serializable{
	private static final long serialVersionUID = 1L;
	private ProblemDefinition problem;
	private int index;
	
	public UserProblem(ProblemDefinition problem, int index){
		this.problem = problem;
		this.index = index;
	}
	
	public UserProblem(ProblemDefinition problem){
		this(problem, problem.getScoreUpperBound()/2);
	}
	
	/**
	 * @return the ProblemDefintion of the user's problem
	 */
	public ProblemDefinition getProblem() {
		return problem;
	}

	public void setProblem(ProblemDefinition problem) {
		this.problem = problem;
	}
	
	public ProblemNode getProblemNode() throws IlearnException{
		if (this.problem == null) throw new IlearnException("Null Problem");
		return this.problem.getProblemNode(index);
	}


	@Override
	public String toString() {
		return "URI: "+problem.getURI() + ", index="+index;
	}

	
	
}

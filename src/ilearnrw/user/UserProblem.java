package ilearnrw.user;

import ilearn.user.problems.ProblemDefinition;


public class UserProblem {
	private ProblemDefinition problem;
	private int score;
	
	public UserProblem(ProblemDefinition problem, int score){
		this.problem = problem;
		this.score = score;
	}
	
	public UserProblem(ProblemDefinition problem){
		this(problem, problem.getScoreUpperBound()/2);
	}
	
	/**
	 * 
	 * @return the ProblemDefintion of the user's problem
	 */
	public ProblemDefinition getProblem() {
		return problem;
	}

	public void setProblem(ProblemDefinition problem) {
		this.problem = problem;
	}

	/**
	 * 
	 * @return the frequency that the user does errors related to this problem
	 */
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}

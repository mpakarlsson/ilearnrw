package ilearnrw.user.problems;

import java.io.Serializable;
import java.util.ArrayList;


public class ProblemNode implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nodeTitle;
	private ArrayList<ProblemNode> children;
	/**
	 * @param nodeTitle the title of the problem
	 * @param children a list of subproblems
	 */
	public ProblemNode(String nodeTitle, ArrayList<ProblemNode> children) {
		this.nodeTitle = nodeTitle;
		this.children = children;
	}
	/**
	 * @return the nodeTitle
	 */
	public String getNodeTitle() {
		return nodeTitle;
	}
	/**
	 * @param nodeTitle the nodeTitle to set
	 */
	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}
	/**
	 * @return the arraylist with the subproblems
	 */
	public ArrayList<ProblemNode> getChildren() {
		return children;
	}
	/**
	 * @param the children to set
	 */
	public void setChildren(ArrayList<ProblemNode> children) {
		this.children = children;
	}
	
}
/**
 * @author Chris Litsas 
 * 4 Jul 2013 3:41:53 ì.ì.
**/
package ilearnrw.user.problems;

import java.util.ArrayList;


public class ProblemNode {

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

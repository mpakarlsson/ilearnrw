package ilearnrw.user.problems;

import java.io.Serializable;

public class ProblemDescription implements Serializable{
	private static final long serialVersionUID = 1L;
	private String descriptions[];
	private ProblemType problemType;
	private String humanReadableDescription;
	private int cluster;
	
	public ProblemDescription()
	{
		//for JSON deserialization
	}
	
	public ProblemDescription(ProblemType problemType, String[] descriptions, 
			String humanReadableDescription, int cluster) {
		this.descriptions = descriptions;
		this.problemType= problemType;
		this.humanReadableDescription = humanReadableDescription;
		this.cluster = cluster;
	}

	public String[] getDescriptions() {
		return descriptions;
	}

	public ProblemType getProblemType() {
		return problemType;
	}
	
	public void setDescriptions(String[] descriptions) {
		this.descriptions = descriptions;
	}

	public void setProblemType(ProblemType problemType) {
		this.problemType = problemType;
	}
	
	public String getHumanReadableDescription() {
		return humanReadableDescription;
	}

	public void setHumanReadableDescription(String humanReadableDescription) {
		this.humanReadableDescription = humanReadableDescription;
	}

	public int getCluster() {
		return cluster;
	}

	public void setCluster(int cluster) {
		this.cluster = cluster;
	}

	public boolean isDescription(String x){
		for (int i=0; i<descriptions.length; i++){
			if (descriptions[i].equalsIgnoreCase(x))
				return true;
		}
		return false;
	}
	
	public String returnDescriptionsAsString(){
		String res =getHumanReadableDescription()+ "{";
		for (int i=0; i<descriptions.length-1; i++){
			res = res + descriptions[i]+" ,";
		}
		res = res + descriptions[descriptions.length-1]+"}";
		return res;
	}
	
	@Override
	public String toString(){
		String res = "Problem Description: "+humanReadableDescription+
			" \n{Problem Type:"+problemType+", Problem Contains:";
		for (int i=0; i<descriptions.length-1; i++){
			res = res + descriptions[i]+" ,";
		}
		res = res + descriptions[descriptions.length-1]+", Cluster:"+cluster+"}";
		return res;
	}

}

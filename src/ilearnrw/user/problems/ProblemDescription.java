package ilearnrw.user.problems;

import java.io.Serializable;

public class ProblemDescription implements Serializable{
	private static final long serialVersionUID = 1L;
	private String descriptions[];
	private ProblemType problemType;
	
	public ProblemDescription(ProblemType problemType, String[] descriptions) {
		this.descriptions = descriptions;
		this.problemType= problemType;
	}

	public String[] getDescriptions() {
		return descriptions;
	}

	public ProblemType getProblemType() {
		return problemType;
	}
	
	public boolean isDescription(String x){
		for (int i=0; i<descriptions.length; i++){
			if (descriptions[i].equalsIgnoreCase(x))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		String res = "|--Problem Description: \n  |--Problem Type:"+problemType+"\n  |--Problem Contains:";
		for (int i=0; i<descriptions.length-1; i++){
			res = res + descriptions[i]+" ,";
		}
		res = res + descriptions[descriptions.length-1]+"\n";
		return res;
	}

}

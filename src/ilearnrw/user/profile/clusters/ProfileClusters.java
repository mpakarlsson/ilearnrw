package ilearnrw.user.profile.clusters;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDescription;
import ilearnrw.utils.LanguageCode;

import java.util.ArrayList;

public class ProfileClusters {
	private ArrayList<ClusterInfo> clusters;
	private LanguageCode languageCode;

	public ProfileClusters() {
		this.clusters = new ArrayList<ClusterInfo>();
	}

	public ProfileClusters(ProblemDefinitionIndex pdi) {
		this();
		languageCode = pdi.getLanguage();
		ProblemDescription pd[][] = pdi.getProblems();
		for (int i=0;i<pd.length;i++){
			for (int j=0;j<pd[i].length;j++){
				this.addProblemDefinition(new ProblemDescriptionCoordinates(i, j, pd[i][j]));
			}
		}
	}

	public ArrayList<ClusterInfo> getClusters() {
		return clusters;
	}

	public void setClusters(ArrayList<ClusterInfo> clusters) {
		this.clusters = clusters;
	}
	
	public ArrayList<ProblemDescriptionCoordinates> getClusterProblems(int clusterNumber){
		for (int i=0;i<clusters.size();i++){
			if (clusters.get(i).getClusterNumber() == clusterNumber)
				return clusters.get(i).getRelatedProblems();
		}
		return new ArrayList<ProblemDescriptionCoordinates>();
	}
	
	public String getClusterHTMLDescription(int clusterNumber){
		String specialSymbol = "<br>";
		if (languageCode == LanguageCode.EN){
			specialSymbol = ", ";
			String res = "";
			boolean firstTime = true;
			for (ProblemDescriptionCoordinates pdc : getClusterProblems(clusterNumber)){
				int idx = pdc.getProblemDescription().getHumanReadableDescription().indexOf("<>");
				if (idx<0)
					idx = pdc.getProblemDescription().getHumanReadableDescription().length();
				String description = pdc.getProblemDescription().getHumanReadableDescription().substring(0, idx).trim();
				if (!firstTime)
					res = res+specialSymbol+description;
				else if (getClusterProblems(clusterNumber).size() == 1)
					res = description;
				else{
					res = description;
					firstTime = false;
				}
			}
			return res;
		}
		else {
			String res = "";
			boolean firstTime = true;
			for (ProblemDescriptionCoordinates pdc : getClusterProblems(clusterNumber)){
				if (!firstTime)
					res = res+specialSymbol+pdc.getProblemDescription().getHumanReadableDescription();
				else if (getClusterProblems(clusterNumber).size() == 1)
					res = pdc.getProblemDescription().getHumanReadableDescription();
				else{
					res = pdc.getProblemDescription().getHumanReadableDescription();
					firstTime = false;
				}
			}
			return res;
		}
	}
	
	public void addProblemDefinition(ProblemDescriptionCoordinates problem){
		for (int i=0;i<clusters.size();i++){
			if (clusters.get(i).getClusterNumber() == problem.getProblemDescription().getCluster()){
				clusters.get(i).addRelatedProblem(problem);
				return;
			}
		}
		int i=0;
		while (i<clusters.size() && 
				clusters.get(i).getClusterNumber() < problem.getProblemDescription().getCluster()){
			i++;
		}
		ClusterInfo ci = new ClusterInfo(problem.getProblemDescription().getCluster());
		ci.addRelatedProblem(problem);
		clusters.add(i, ci);
	}
	
	public ArrayList<Integer> getClustersNumbers(){
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (int i=0;i<clusters.size();i++){
			res.add(clusters.get(i).getClusterNumber());
		}
		return res;
	}
}

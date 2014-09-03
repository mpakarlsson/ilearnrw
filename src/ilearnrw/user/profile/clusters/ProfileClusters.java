package ilearnrw.user.profile.clusters;

import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemDescription;

import java.util.ArrayList;

public class ProfileClusters {
	private ArrayList<ClusterInfo> clusters;

	public ProfileClusters(ArrayList<ClusterInfo> clusters) {
		this.clusters = clusters;
	}

	public ProfileClusters() {
		this.clusters = new ArrayList<ClusterInfo>();
	}

	public ProfileClusters(ProblemDefinitionIndex pdi) {
		this();
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
		return null;
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

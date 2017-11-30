package org.crossminer.similaritycalculator.CrossSim;



import java.util.*;


public class CrossSim {
	
	private double damping;	
	private Graph graph;	
	private int iter;
	private int numNodes;	
	
	//private Map<Integer, Map<Integer,Float>> scores;
	
	SparseMatrix scores;

	public CrossSim(Graph graph) {
		this.graph = graph;
		this.damping = 0.85f;		
		numNodes = graph.numNodes();		
		this.scores =  new SparseMatrix(numNodes);	
	}

	public void setDamping(double damp) {
		this.damping = damp;
	}

	public double getDamping() {
		return this.damping;
	}

	public void computeSimRank() {
		this.iter = 4;		
		System.out.println("Iteration: " + this.iter);
		computeSimRank(this.iter);
	}

	public void computeSimRank(int iter) {
		float score = 0;
			
		SparseMatrix newScores = new SparseMatrix(numNodes);
		
		while ((iter--) > 0) {			
			for (int id1 = 0; id1 < numNodes; id1++) {								
				for (int id2 = 0; id2 < id1; id2++) {			
					score = simRank(id1,id2);
					if(score!=0)newScores.put(id1, id2, score);						
				}
			}			
			scores =  new SparseMatrix(numNodes);			
			for (int i = 0; i < numNodes; i++) {
				for (int j = 0; j < i; j++){
					score = newScores.get(i, j);
					if(score!=0)scores.put(i, j, score);					
				}						
			}			
			newScores = new SparseMatrix(numNodes);
		}		
	}
	
	
	public float getSimRank(Integer id1, Integer id2){		
		if(id1.equals(id2))return new Float(1.0);
		if(id2>id1){
			Integer id3=id1;
			id1=id2;
			id2=id3;
		}		
		return scores.get(id1,id2);			
	}
	
	
	
	private float simRank(Integer id1, Integer id2) {
		float score = 0;					
		int numInLinks1=0,numInLinks2=0;
		
		if(id1.equals(id2))return new Float(1.0);
				
		Set<Integer> inlinks1 = graph.inLinks(new Integer(id1));		
		Set<Integer> inlinks2 = graph.inLinks(new Integer(id2));
		
		if(inlinks1==null || inlinks2==null) {			
			return new Float(0);
		}
		
		numInLinks1 = inlinks1.size();
		numInLinks2 = inlinks2.size();
	
		for(int i:inlinks1){												
			for(int j:inlinks2){
				score += getSimRank(new Integer (i),new Integer (j));				
			}	
		}
		
		score = new Float(( damping / ( numInLinks1 * numInLinks2 ) ) * score );
		//score = new Float(( damping / ( Math.sqrt(numInLinks1 * numInLinks2) ) ) * score );

		return score;
	}
}

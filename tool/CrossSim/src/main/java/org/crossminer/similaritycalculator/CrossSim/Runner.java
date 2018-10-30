package org.crossminer.similaritycalculator.CrossSim;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;


class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}


public class Runner {	
	
	private String srcDir;
	private String resultDir;
	
	public Runner(){
		
	}
	
	public void loadConfigurations(){		
		Properties prop = new Properties();				
		try {
			prop.load(new FileInputStream("evaluation.properties"));		
			this.srcDir=prop.getProperty("sourceDirectory");
			this.resultDir=prop.getProperty("resultDirectory");
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return;
	}
		
	
	public void run(){		
		System.out.println("Similarity Calculation using SimRank!");
		loadConfigurations();
//		convert();
		simRankSimilarity();
	}
	
	
	public void simRankSimilarity(){
		
		DataReader reader = new DataReader();	
	
		Map<String, Integer> dictionary = new HashMap<String, Integer>();	
		Map<String, Double> sim = new HashMap<String, Double>();	
		String graphFilename = "", dictFilename = "", content="";		
				
		BufferedWriter writer = null;						
		Graph graph = null;		
		CrossSim crosssim = null;
				
		int id1=0,id2=0;
		double val=0;		
		
		Map<Integer,String> repoList = new HashMap<Integer,String>();		
								
		graphFilename = this.srcDir + "Graph.txt";
		dictFilename = this.srcDir + "Dictionary.txt";
		
		System.out.println(graphFilename);		
		graph = new Graph(graphFilename);
		
				
		dictionary = reader.readDictionary(dictFilename);
		
		
		crosssim = new CrossSim(graph);
		crosssim.computeSimRank();
		String repo1="",repo2="";
								
		repoList = reader.readRepositoryList3(this.srcDir + "RepositoryList.txt");				
		Set<Integer> keySet = repoList.keySet();		
		
		
		for(Integer k1:keySet){					
			repo1 = repoList.get(k1);			
			try {					
								
				sim = new HashMap<String, Double>();
				
				for(Integer k2:keySet){					
					repo2 = repoList.get(k2);		
					System.out.println(repo1);
					id1 = dictionary.get(repo1);
					id2 = dictionary.get(repo2);					
					val = crosssim.getSimRank(new Integer(id1), new Integer(id2));					
					sim.put(k2.toString(),val);					
					System.out.println("The similarity between " + repo1 + " and " + repo2 + " is: " + val);									
				}				
				
				ValueComparator bvc =  new ValueComparator(sim);        
				TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
				sorted_map.putAll(sim);				
				Set<String> keySet2 = sorted_map.keySet();				
				String str1 = repo1.replace("git://github.com/", "").replace(".git", "").replace("/", "_");	
				
				
				writer = new BufferedWriter(new FileWriter(this.resultDir + str1 + ".txt"));				
				for(String key:keySet2){				
					content = repoList.get(k1) + "\t" + repoList.get(Integer.parseInt(key)) + "\t" + sim.get(key);					
					writer.append(content);							
					writer.newLine();
					writer.flush();					
				}
				
				writer.close();
				
			}  catch (IOException e) {				
				e.printStackTrace();
			}			
		}				
				
		
		return;
	}
	
	
	
	
	public Graph copyGraph(Graph graph){
		
		Graph temp = new Graph();
		Map<Integer,Set<Integer>> InLinks = new HashMap<Integer,Set<Integer>>();
		Map<Integer,Set<Integer>> InLinks2 = new HashMap<Integer,Set<Integer>>();
		InLinks = graph.getInLinks();		
		Set<Integer> nodes = new HashSet<Integer>();
		Set<Integer> nodes2 = new HashSet<Integer>();
		Set<Integer> keySet = InLinks.keySet();
		
		for(Integer key:keySet){		
			nodes = InLinks.get(key);
			nodes2 = new HashSet<Integer>();
			for(Integer key2:nodes)nodes2.add(key2);
			InLinks2.put(key, nodes2);		
		}
		
		temp.setInLinks(InLinks2);
		temp.setNumNodes(graph.numNodes());
			
		return temp;
	}
	
	
	

	public Map<String, Integer> copyDictionary(Map<String, Integer> dict){		
		Map<String, Integer> tempDict = new HashMap<String, Integer>();		
		Set<String> keySet = dict.keySet();		
		int val=0;		
		for(String key:keySet){
			val = dict.get(key);
			tempDict.put(key, val);			
		}				
		return tempDict;
	}
	
	
	
	
	
	public void convert() {
		
		Map<String, String> dictionary2 = new HashMap<String, String>();		
		Map<String, Integer> tmpDict = new HashMap<String, Integer>();		
		Map<Integer, String> newDict = new HashMap<Integer, String>();		
		String dictFilename = this.srcDir + "Dictionary.txt";				
		DataReader reader = new DataReader();				
		dictionary2 = reader.readDictionary3(dictFilename);		
		int id = 0;		
		Set<String> keySet = dictionary2.keySet();				
		for(String k1:keySet){			
			newDict.put(id, dictionary2.get(k1));
//			System.out.println(k1);
			tmpDict.put(k1,id);
			id++;		
		}
		
		
		/*Read the graph file, line by line and save to a new file*/
		
		BufferedReader buf = null;
		String line = "";
		
		File file = new File(this.srcDir + "Graph.txt");
		
		String[] pair = null;
		String content = "";	
		BufferedWriter writer = null;
				
		try {
			buf = new BufferedReader(new FileReader(file));
			writer = new BufferedWriter(new FileWriter(this.srcDir + "Graph2.txt"));
			
			while((line=buf.readLine())!=null) {													
				pair = line.trim().split("#");			
				String startNode = pair[0].trim();
				String endNode = pair[1].trim();
				System.out.println(startNode + " " + endNode);
				content = tmpDict.get(startNode) + "#" + tmpDict.get(endNode) ;
				writer.append(content);							
				writer.newLine();
				writer.flush();						
			}	
			
			buf.close();
			writer.close();			
			writer = new BufferedWriter(new FileWriter(this.srcDir + "Dictionary2.txt"));
						
			Set<Integer> keySet2 = newDict.keySet();
			
			for(Integer k1:keySet2){				
				content = k1 + "\t" + newDict.get(k1);
				writer.append(content);							
				writer.newLine();
				writer.flush();
			}					
			writer.close();	
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return;
	}
	
	
	
	
	
	public double SpearmanRankCorrelation(int[] vector1, int[] vector2) {
				
		int len = vector1.length;
		int sum = 0;
		for(int i=0;i<len;i++) {
			int tmp = vector1[i]-vector2[i];
			sum+=tmp*tmp;
		}
			
		double ret = (double)1-6*sum/(len*(len*len-1));
								
		return ret;
	}
	
	
	
	
	
	
	public static void main(String[] args) {	
		Runner runner = new Runner();			
		runner.run();				    		    
		return;
	}	
	
}

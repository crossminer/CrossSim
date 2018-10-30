package org.crossminer.similaritycalculator.CrossSim;

import java.io.*;
import java.util.*;


public class Graph {

	/**
	 *  A Map storing InLinks. For each identifier (the key), another Map is stored,
	 *  containing for each inlink an associated "connection weight"
	 */
	private Map<Integer,Set<Integer>> InLinks;
	
	
	/** The number of nodes in the graph */
	private int nodeCount;
	
	BufferedWriter writer = null;	

	/**
	 * Constructor for Graph
     *
	 */
	
	Map<String, Integer> dictionary = new HashMap<String, Integer>();
		
	private static SynchronizedCounter counter = new SynchronizedCounter();
	
	public Graph () {		
		InLinks = new HashMap<Integer, Set<Integer>>();
		nodeCount = 0;
	}
	
	public Map<Integer,Set<Integer>> getInLinks(){
		return InLinks;
	}
	
	public void setInLinks(Map<Integer,Set<Integer>> InLinks){
		this.InLinks = InLinks;
	}
	
	public void setNumNodes(int n){
		this.nodeCount = n;
	}
		
	public Graph (File file) {
		this();
		
		InLinks = new HashMap<Integer, Set<Integer>>();
		nodeCount = 0;
		
		BufferedReader reader = null;
		String line;
		String[] vals = null;
		
		Set<Integer> inlinks = new HashSet<Integer>();
		Set<Integer> nodes = new HashSet<Integer>();
				
		try {
			reader = new BufferedReader(new FileReader(file));			
			while((line=reader.readLine())!=null) {				
				vals = line.split("\t");	
				int startNode = Integer.parseInt(vals[0].trim());
				int endNode = Integer.parseInt(vals[1].trim());
				nodes.add(startNode);
				nodes.add(endNode);
										
				if(InLinks.containsKey(endNode)){				
					inlinks = InLinks.get(endNode);
					inlinks.add(startNode);
					InLinks.put(endNode, inlinks);					
				} else {
					inlinks = new HashSet<Integer>();
					inlinks.add(startNode);
					InLinks.put(endNode, inlinks);
				}							
			}
			reader.close();
			nodeCount=nodes.size();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	
	
	
	
	
	public Graph (String fileName) {
		
		InLinks = new HashMap<Integer, Set<Integer>>();
		nodeCount = 0;
		
		BufferedReader reader = null;
		String line;		
		String[] pair = null;
		
		Set<Integer> inlinks = new HashSet<Integer>();
		Set<Integer> nodes = new HashSet<Integer>();
		
		File file = new File(fileName);
				
		try {
			reader = new BufferedReader(new FileReader(file));
			
			while((line=reader.readLine())!=null) {			
												
//				System.out.println(line);
				pair = line.trim().split("#");
			
				int startNode = Integer.parseInt(pair[0].trim());
				int endNode = Integer.parseInt(pair[1].trim());
				
				nodes.add(startNode);
				nodes.add(endNode);
																	
				if(InLinks.containsKey(endNode)){				
					inlinks = InLinks.get(endNode);
					inlinks.add(startNode);
					InLinks.put(endNode, inlinks);					
				} else {
					inlinks = new HashSet<Integer>();
					inlinks.add(startNode);
					InLinks.put(endNode, inlinks);
				}				
				
										
			}
			reader.close();
			nodeCount=nodes.size();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public Map<String, Integer> combine(Graph graph, Map<String, Integer> dictionary1, Map<Integer, String> dictionary2){
	
		Map<Integer,Set<Integer>> tmpInLinks = graph.getInLinks();
		
		dictionary = new HashMap<String, Integer>();		
		dictionary = dictionary1;
					
		Set<Integer> inlinks1 = new HashSet<Integer>();		
		Set<Integer> inlinks2 = new HashSet<Integer>();
		
		Set<Integer> key = tmpInLinks.keySet();		
				
		String address = "";
		int idEndNode = 0, idStartNode = 0;		
		
		counter = new SynchronizedCounter(nodeCount-1);
	
		for(Integer endNode:key){				
			inlinks1 = tmpInLinks.get(endNode);
			address = dictionary2.get(endNode);				
			idEndNode = extractKey(address);
															
			for(Integer startNode:inlinks1){							
				address = dictionary2.get(startNode);
				idStartNode = extractKey(address);				
																						
				if(this.InLinks.containsKey(idEndNode)){					
					inlinks2 = this.InLinks.get(idEndNode);
					inlinks2.add(idStartNode);
					this.InLinks.put(idEndNode, inlinks2);					
				} else {				
					inlinks2 = new HashSet<Integer>();
					inlinks2.add(idStartNode);
					this.InLinks.put(idEndNode, inlinks2);
				}								
			}				
		}	
		
				
		Set<Integer> nodes = new HashSet<Integer>();	
		
		key = this.InLinks.keySet();	
		
		for(Integer endNode:key){	
			nodes.add(endNode);
			inlinks1 = this.InLinks.get(endNode);
			
			for(Integer startNode:inlinks1){	
				nodes.add(startNode);
			}		
		}
								
		nodeCount = nodes.size();	
			
		return dictionary;
	}
	
	
	
		
	private int extractKey(String s) {		
		//String content = "";			

		if (dictionary.containsKey(s))
			return dictionary.get(s);
		else {
			int c = counter.value();							
			dictionary.put(s,c);			
			
			/*
			try {									
				content = c + "\t" + s ;						
				writer.append(content);						
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			
			return c;
		}		
	}
	
	
	
	
	public Set<Integer> inLinks(int id){		
		return InLinks.get(id);		
	}
	
	public void printGraph(){
		
		Set<Integer> keySet = InLinks.keySet();
		
		for(int key:keySet){
			Set<Integer> list = InLinks.get(key);
			System.out.print(key + ": ");
			if(list!=null){
				for(int j:list)System.out.print(j + " ");
			}
			System.out.println();
		}
		
		return;
	}
	
	
	public int numNodes() {
		return nodeCount;
	}

}

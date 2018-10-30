package org.crossminer.similaritycalculator.CrossSim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DataReader {

	
	
	public DataReader() {		
		
	}
	
	
	public Map<Integer, String> readRepositoryList(String filename){		
		Map<Integer,String> ret = new HashMap<Integer,String>();
		String[] vals = null;		
		String line="",uri="";
		int id=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {				
				line = line.trim();
				vals = line.split("\t");
				id=Integer.parseInt(vals[0].trim());
				uri=vals[1].trim();//.replace("http://dbpedia.org/resource/", "");
				ret.put(id,uri);							
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;			
	}
	
	
	public Map<Integer, String> readRepositoryList2(String filename){		
		Map<Integer,String> ret = new HashMap<Integer,String>();
		String[] vals = null;		
		String line="",uri="";
		int id=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {				
				line = line.trim();
				vals = line.split(",");					
				uri="git://github.com/"+vals[1].trim() + ".git";				
				ret.put(id,uri);	
				id+=1;
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return ret;			
	}

	
	public Map<Integer, String> readRepositoryList3(String filename){		
		Map<Integer,String> ret = new HashMap<Integer,String>();
		String[] vals = null;		
		String line="",uri="";
		int id=0;
		
		try {					
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {				
				line = line.trim();
				vals = line.split("\t");					
				uri=vals[1].trim();				
				ret.put(id,uri);	
				id+=1;
			}
				
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return ret;				
	}
	
	
	
	public Map<Integer, String> readMovieLenList(String filename){
		
		Map<Integer,String> ret = new HashMap<Integer,String>();
		String[] vals = null;		
		String line="",uri="";
		int id=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {				
				line = line.trim();
				vals = line.split("\t");
				id=Integer.parseInt(vals[0].trim());
				uri=vals[2].trim().replace("http://dbpedia.org/resource/", "");
				ret.put(id,uri);							
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;			
	}
	
	public Map<Integer,String> readLastFMMostPopularArtistList(String filename) {	
		
		Map<Integer,String> ret = new HashMap<Integer,String>();
		String[] vals = null;		
		String line="",uri="";
		int id=0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {				
				line = line.trim();
				vals = line.split("\t");
				id=Integer.parseInt(vals[0].trim());
				uri=vals[1].trim();
				ret.put(id,uri);							
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;		
	}
	
	
	
	public List<String> readSimilarityArtist(String filename){
		
		List<String> ret = new ArrayList<String>();		
		String line="",URI="";			
		String[] vals = null;
		BufferedReader reader = null;
	
		try {
			reader = new BufferedReader(new FileReader(filename));		
			try {
				while ((line = reader.readLine()) != null) {				
					vals = line.split("\t");
					URI = vals[0].trim();
					//if(!URI.contains("<"))
					ret.add(URI);									
				}
			} finally {
				reader.close();
			}			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	public ArrayList<List<String>> readResultMatrix(String filename) {
		
		ArrayList<List<String>> ArtistMatrix = new ArrayList<List<String>>();
		
		String line="";
		
		String[] vals = null;
		
		List<String> list;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
						
			while ((line = reader.readLine()) != null) {
				
				vals = line.split(",");			
				
				list = new ArrayList<String>();				
				
				for (String sim : vals) {			
					
					list.add(sim.trim());				
				}
				
				ArtistMatrix.add(list);							
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return ArtistMatrix;
	}
	
	
	
	/* Load the list of artists that is used for the similarity calculation */
		
	public Map<String, List<String>> readArtistList(String filename) {	
		
		Map<String, List<String>> list = new HashMap<String, List<String>>();
		String line="",node1="",node2="";
		List<String> nodes = new ArrayList<String>();
		String[] vals = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));		
			while ((line = reader.readLine()) != null) {				
				line = line.trim();
				vals = line.split("\t");
				node1= vals[0].trim();
				node2= vals[1].trim();		
				
				if(!list.containsKey(node1)){
					nodes = new ArrayList<String>();									
				} else {
					nodes = list.get(node1);															
				}						
				nodes.add(node2);
				list.put(node1, nodes);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return list;		
	}
	
		
	public List<String> readArtistList2(String filename) {		
		List<String> ret = new ArrayList<String>();				
		String line="";				
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {				
				line = line.trim();				
				ret.add(line);							
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;		
	}
	
	
	public List<String> readMovieList(String filename) {		
		List<String> ret = new ArrayList<String>();				
		String line="";				
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {				
				line = line.trim();				
				ret.add(line);							
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;		
	}
	
	
	
	public List<String> readResultPyramid(String filename) {
		
		List<String> ret = new ArrayList<String>();
				
		String line="";
		
		String[] vals = null;
				
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(filename));
						
			while ((line = reader.readLine()) != null) {
				
				line = line.trim();
				
				vals = line.split(",");
				
				for (String sim : vals) {			
					ret.add(sim.trim());						
				}
															
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;		
	}
	
	
	
	public Map<String, Integer> readAset400List() {
		
		Map<String, Integer> ret = new HashMap<String, Integer>();
		
		int index = 0;
				
		String line="";
				
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/home/nguyen/Public/Evaluation/aset400.txt"));
						
			while ((line = reader.readLine()) != null) {
				
				line = line.trim();
				
				ret.put(line, index);							
				
				index += 1;				
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
		return ret;
		
	}
	
	
	
	
	
		
		
	
	public Map<String, Integer> readDictionary(String filename) {							
		Map<String, Integer> vector = new HashMap<String, Integer>();		
		String line = null;		
		String[] vals = null;		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
						
			while ((line = reader.readLine()) != null) {
										
				vals = line.split("\t");
			
				int ID = Integer.parseInt(vals[0].trim());
				
				String URI = vals[1].trim();
		
				vector.put(URI, ID);							
			}
			
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
						
		return vector;		
	}
	
	
	public Map<Integer, String> readDictionary2(String filename) {							
		Map<Integer, String> vector = new HashMap<Integer, String>();		
		String line = null;		
		String[] vals = null;		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
						
			while ((line = reader.readLine()) != null) {										
				vals = line.split("\t");			
				int ID = Integer.parseInt(vals[0].trim());				
				String URI = vals[1].trim();		
				vector.put(ID, URI);							
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
						
		return vector;		
	}
	
	
	public Map<String, String> readDictionary3(String filename) {							
		Map<String, String> vector = new HashMap<String, String>();		
		String line = null;		
		String[] vals = null;		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
						
			while ((line = reader.readLine()) != null) {
										
				vals = line.split("\t");			
				String ID = vals[0].trim();				
				String URI = vals[1].trim();		
				vector.put(ID, URI);							
			}
			
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
						
		return vector;		
	}
		
	
	
}
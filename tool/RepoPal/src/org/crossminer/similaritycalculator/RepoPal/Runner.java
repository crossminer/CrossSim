package org.crossminer.similaritycalculator.RepoPal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import com.google.common.collect.Sets;
import com.computergodzilla.cosinesimilarity.DocSim;


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
	
	private String sourceDirectory;
	private String indexDirectory;
	
	public Map<String, String> Readmefiles = new HashMap<String, String>(); //Format: <repoID, readme_filename>
	public Map<String, Set<String>> Repositories = new HashMap<String, Set<String>>(); //Format: <repoID, List of userID who starred the repoID>	
	public Map<String, Map<String,Integer>> Stars = new HashMap<String, Map<String,Integer>>(); //Format: <userID, <repoID, timestamp>>
	
	public void loadConfigurations(){		
		Properties prop = new Properties();				
		try {
			prop.load(new FileInputStream("evaluation.properties"));		
			this.sourceDirectory=prop.getProperty("sourceDirectory");
			this.indexDirectory=prop.getProperty("indexDirectory");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return;
	}
	
		
	public Runner(){
		
	}
	
	
	/*read RepositoriesReadmeFiles file*/
	
	private Map<String, String> readReadmeFileList(String filename) {							
		Map<String, String> vector = new HashMap<String, String>();		
		String line = null;		
		String[] vals = null;		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {										
				vals = line.split("\t");			
				String repo = vals[0].trim();				
				String fname = vals[1].trim();		
				vector.put(repo, fname);							
			}			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}						
		return vector;		
	}
	
			
	/*read RepositoriesUsers file*/
	
	private Map<String, Set<String>> readRepositoriesUsers(String filename) {							
		Map<String, Set<String>> repositories = new HashMap<String, Set<String>>();		
		String line = null;
		String[] vals = null;
		String[] users = null;
		Set<String> userList = null;		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {										
				vals = line.split("\t");			
				String repo = vals[0].trim();			
				users = vals[1].trim().split(",");
				userList = new HashSet<String>();
				for(int i=0;i<users.length;i++)userList.add(users[i].trim());		
				repositories.put(repo, userList);							
			}			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}						
		return repositories;		
	}
			
	
	
	/*read UsersStars file*/
		
	private Map<String, Map<String,Integer>> readUsersStars(String filename) {
		
		Map<String, Map<String,Integer>> stars = new HashMap<String, Map<String,Integer>>();									
		String line = null;
		String[] vals = null;
		String[] repos = null;
		String[] stamps = null;				
		Map<String,Integer> temp = null;		
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));						
			while ((line = reader.readLine()) != null) {										
				vals = line.split("\t");			
				String userID = vals[0].trim();			
				repos = vals[1].trim().split(",");		
				temp = new HashMap<String,Integer>();
				for(int i=0;i<repos.length;i++) {
					stamps = repos[i].trim().split("#");					
					temp.put(stamps[0], Integer.parseInt(stamps[1]));
				}				
				stars.put(userID, temp);
			}			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
		return stars;		
	}
		
	
	/* This funtion loads metadata about stars, users, repositories from files*/	
		
	public void loadMetadata() {		
		this.Readmefiles =  readReadmeFileList(this.sourceDirectory + "RepositoriesReadmeFiles.txt");
		this.Repositories = readRepositoriesUsers(this.sourceDirectory + "RepositoriesUsers.txt");
		this.Stars = readUsersStars(this.sourceDirectory + "UsersStars.txt");		
	}
	
	
	public void run(){		
		loadConfigurations();
		System.out.println("Similarity Calculation using RepoPal!");			
		loadMetadata();		
		RepoPal();			

//		String repo1 = "git://github.com/spring-projects/spring-data-mongodb.git";
//		String repo2 = "git://github.com/abinj/DataNucleus-MongoDB-Dropwizard.git";	
//		ReadmeSimilarity(repo1,repo2);
//		String filename1 = "C:\\Temp\\TFIDF\\doc3.txt";
//		String filename2 = "C:\\Temp\\TFIDF\\doc4.txt";
//		DocSim doc = new DocSim(this.indexDirectory);	
//		double ret = doc.calculateSimilarity(filename1, filename2);
//		System.out.println("The similarity between documents " + filename1 + " and " + filename2 + " is: " + ret);		
	}
	
		
		
	/*Computes the similarity between two text files */
	
	/*@Juri: Please help me to integrate this function from the code you already have*/
		
	public double ReadmeSimilarity(String repo1, String repo2) {		
		String filename1 = Readmefiles.get(repo1);
		String filename2 = Readmefiles.get(repo2);		
		DocSim doc = new DocSim(this.indexDirectory);				
		return doc.calculateSimilarity(filename1, filename2);	
	}
		
		
	/*Jaccard index for the two sets of repos of two users*/
	
	public double UserSimilarity(String userID1, String userID2) {
		double tmp = 0;
		
		Map<String,Integer> StarsOfUser1 = new HashMap<String,Integer>();
		Map<String,Integer> StarsOfUser2 = new HashMap<String,Integer>();
		
		StarsOfUser1 = Stars.get(userID1);
		StarsOfUser2 = Stars.get(userID2);
		
		Set<String> reposOfUser1 = StarsOfUser1.keySet();		
		Set<String> reposOfUser2 = StarsOfUser2.keySet();
						
		Set<String> common = Sets.intersection(reposOfUser1, reposOfUser2);										
		double size1 = common.size();		
		double size2 = reposOfUser1.size() + reposOfUser2.size() - size1;
		
		tmp = (double)size1/size2;			
				
		return tmp;
	}
		
	
	public double StarBasedSimilarity(String repo1, String repo2) {
		double tmp = 0, val1 = 0, val2 = 0;
			
		Set<String> usersWhoStarRepo1 = new HashSet<String>();
		Set<String> usersWhoStarRepo2 = new HashSet<String>();
		
		usersWhoStarRepo1 = Repositories.get(repo1);
		usersWhoStarRepo2 = Repositories.get(repo2);
						
		for (String user1: usersWhoStarRepo1) {			
			for (String user2: usersWhoStarRepo2) {
				if(user1.equals(user2))tmp+=1;
				else tmp+=UserSimilarity(user1,user2);				
			}					
		}
		
		int size1= usersWhoStarRepo1.size();
		int size2= usersWhoStarRepo2.size();
		
		val1 = (double)1/(size1*size2);
		val2 = (double)val1*tmp;	
				
		return val2;
	}
	
		
	
	
	/*calculate similarity using the time difference between the time that two repositories are starred by the same user*/
	
	/*I suppose that the time has been converted to an integer number*/
	
	public double TimeBasedSimilarity(String repo1, String repo2) {
		double ret = 0, val1=0, val2 =0;
		
		Set<String> usersWhoStarRepo1 = new HashSet<String>();
		Set<String> usersWhoStarRepo2 = new HashSet<String>();
		
		usersWhoStarRepo1 = Repositories.get(repo1);
		usersWhoStarRepo2 = Repositories.get(repo2);
				
		Set<String> common = Sets.intersection(usersWhoStarRepo1, usersWhoStarRepo2);
				
		Map<String,Integer> StarsOfUser = new HashMap<String,Integer>();
						
		int timeStamp1, timeStamp2;
		
		for (String userID: common) {
			
			StarsOfUser = Stars.get(userID);
			timeStamp1 = StarsOfUser.get(repo1);
			timeStamp2 = StarsOfUser.get(repo2);			
			val1+=(double)1/Math.abs(timeStamp1-timeStamp2);
			
		}
		
		val2 = (double)1/common.size();	
		ret = val2*val1;				
		return ret;
	}
		
	
		
	public double RepoPalSimilarity(String repo1, String repo2) {			
		double readmeSim = 0, starSim = 0, timeSim = 0;		
		readmeSim = ReadmeSimilarity(repo1, repo2);
		starSim = StarBasedSimilarity(repo1, repo2);
		timeSim = TimeBasedSimilarity(repo1, repo2);		
		double ret =  readmeSim*starSim*timeSim;				
		System.out.println("The similarity between " + repo1 + " and " + repo2 +" is: " + ret);								
		return ret;
	}
		
	
	public void RepoPal() {				
		Set<String> repoList = new HashSet<String>();		
		repoList = Repositories.keySet();
		Map<String, Double> sim = new HashMap<String, Double>();
		double val = 0;		
		BufferedWriter writer = null;
				
		for(String repo1:repoList) {			
			try {			
				sim = new HashMap<String, Double>();				
				for(String repo2:repoList) {
					val = 0;
					if(repo1.equals(repo2))val=1.0;
					else val = RepoPalSimilarity(repo1,repo2);									
					sim.put(repo2,val);	
				}	
								
				ValueComparator bvc =  new ValueComparator(sim);        
				TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
				sorted_map.putAll(sim);				
				Set<String> keySet = sorted_map.keySet();
				String content = null;
				
				writer = new BufferedWriter(new FileWriter("C:\\CROSSMINER\\RepoPal\\" + repo1 + ".txt"));
				
				for(String repo2:keySet){				
					content = repo1 + "\t" + repo2 + "\t" + sim.get(repo2);					
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
	
	
	
	public static void main(String[] args) {	
		Runner runner = new Runner();			
		runner.run();				    		    
		return;
	}	
	
}

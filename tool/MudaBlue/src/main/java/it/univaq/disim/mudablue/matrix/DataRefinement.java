package it.univaq.disim.mudablue.matrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class DataRefinement {
	
	public void refine (RealMatrix m, File folder_path) throws IOException
	{	
		HashMap<String, Double> results = new HashMap<String, Double>();
		
		/*
		 * recupero il nome delle repository
		 */
		ArrayList<String> path_list = new ArrayList<String>();
		
		File[] listOfRepos = folder_path.listFiles();
		
		for(File elem:listOfRepos)
		{
			String repo = elem.toString();
			repo = repo.replace("=", "\\");
			repo = repo.replace(".txt","");
			int index = repo.indexOf("\\");
			index = repo.indexOf("\\",index);
			repo = repo.substring(index+1, repo.length());
			
			path_list.add(repo);
			
			
		}
		
		for(int i=0; i<m.getRowDimension(); i++)
		{	
			RealVector vector = m.getRowVector(i);
			for(int j=0; j<m.getRowDimension(); j++)
			{
				results.put(path_list.get(i)+" - "+path_list.get(j)+" : ", vector.getEntry(j));
				/*if(vector.getEntry(j)>0.75 && i!=j)
				{
					System.out.println(path_list.get(i)+" - "+path_list.get(j)+" : "+vector.getEntry(j));
				}*/	
			}
		}

		results.entrySet().stream()
        .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) 
        .limit(m.getRowDimension()*m.getRowDimension()) 
        .forEach(System.out::println); // or any other terminal method
		
	}

}

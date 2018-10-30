package it.univaq.disim.crossminer.clan.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import it.univaq.disim.crossminer.clan.models.Repositories;
import it.univaq.disim.crossminer.clan.scan.FolderNavigator;
import it.univaq.disim.crossminer.matrix.Row;

public class MatrixManager {

	public RealMatrix createMatrix (ArrayList<ArrayList<Double>> occurrencies_list)
	{
		int max = 0;
		for(ArrayList<Double> elem : occurrencies_list)
		{
			if(max <= elem.size())
			{
				max = elem.size();
			}
			
		}
		
		/*
		 * conversione nei formati real matrix
		 */
		
		//RealMatrix m = MatrixUtils.createRealMatrix(occurrencies_list.get(occurrencies_list.size()).size(),occurrencies_list.size());
		RealMatrix m = MatrixUtils.createRealMatrix(max,occurrencies_list.size());

		int rowCounter=0;
		for(ArrayList<Double> elem : occurrencies_list)
		{
			//RealVector vector = new ArrayRealVector(elem.size());
			RealVector vector = new ArrayRealVector(max);
			for(int i=0; i<elem.size();i++)
			{
				vector.setEntry(i,  (Double) elem.get(i));
			}
			m.setColumnVector(rowCounter, vector);
			rowCounter++;
		}
		
		return m;
	}
	
	public ArrayList<ArrayList<Double>> createFiles(ArrayList<String> path_list,String operation, ArrayList jdk) throws IOException
	{
		Row row = new Row();
		Repositories repository_object = new Repositories(operation);
		repository_object.setMainList(repository_object.resumeMainList(operation));

		for(String repo : path_list)
		{		
			int endindex;
			/*
			 * creazione del nome della repo
			 */
			int index1 = 0;
			for(int i=0; i<org.apache.commons.lang3.StringUtils.countMatches(repo.toString(),"\\")-1; i++)
			{
				index1 = repo.toString().indexOf("\\",index1+1);
			}
			String repoName = repo.toString().substring(index1+1, repo.toString().length());
			repoName = repoName.replace("\\", "=");
			
			File folder_path = new File("results"+operation+"/");
			File[] listOfFiles = folder_path.listFiles();
			
			ArrayList<String> files = new ArrayList<String>();
			
			for(File elem:listOfFiles)
			{
				int indexx = elem.toString().indexOf("\\");
				endindex = elem.toString().indexOf(".txt");
				String string = elem.toString().substring(indexx+1,endindex+4);
				files.add(string);
			}
			
			/*
			 * controllo per verificare se ho already analizzato quella repository
			 */
			if(files.contains(repoName+".txt")) {
				//System.out.println("already analysed");
				continue;
			}
			
			else {
			
			File file = new File(repo);
			 
	        FolderNavigator navigator = new FolderNavigator();
			ArrayList<String> terms= new ArrayList<String>();
	        
	        repository_object = navigator.filesList(file, repository_object.getMainList(), terms, repository_object,operation,jdk);
			repository_object.saveMainList(repository_object.getMainList(),operation);
	        
			System.out.println("saving: "+repoName);
	        PrintStream ps = new PrintStream(new File("results"+operation+"/"+repoName+".txt"));
	        row.createRow(repository_object,ps);
			
			ps.close();
			}
		}
		
	    Scanner scan;
	    
		File folder_path = new File("results"+operation+"/");
		File[] listOfFiles = folder_path.listFiles();
		ArrayList<ArrayList<Double>> occurrencies_list = new ArrayList<ArrayList<Double>>();
		
		for(File elem:listOfFiles)
		{
			if(elem.toString().indexOf("mainList.txt")==-1)
			{
				ArrayList<Double> ol = new ArrayList<Double>();
			    try {
				        scan = new Scanner(elem);
				        while(scan.hasNext())
				        {
				        	double var = Double.parseDouble(scan.next());
				        	
				            ol.add(var);
				        }
		
			    	} 
			    catch (FileNotFoundException e1) 
			    {
			            e1.printStackTrace();
			    }
			    
			    System.out.println("repo loaded: "+elem.toString());
		    	occurrencies_list.add(ol);
			}
			else
			{
				continue;
			}
		}
		
		return occurrencies_list;

	}

	public RealMatrix cleanMatrix(RealMatrix m){
		
		/*
		 * prendiamo la matrice realmatrix termini-documenti ed eliminiamo alcune colonne secondo questo principio:
		 * se un termine appare in una sola repository lo eliminiamo oppure se appare more della than half† delle repository
		 */
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		for(int i=0; i<m.getRowDimension(); i++)
		{
			RealVector row = m.getRowVector(i);
			
			int counter = 0;
			boolean remove = false;
			
			for(int j=0; j<row.getDimension(); j++)
			{
				if(row.getEntry(j)!=0.0)
				{
					counter += 1;
				}
			}
			
			if(counter==1)
			{
				remove=true;
				//System.out.println("removeLB");
			}
			
			if(counter>row.getDimension()/2) //occhio qui, fallir√† sempre se gli passi meno di 4 repository
			{
				remove=true;
				//System.out.println("removeUB");
			}
			
			if(remove==false)
			{
				results.add(i);
			}
			
		}
		
		RealMatrix mFinal = MatrixUtils.createRealMatrix(results.size(),m.getColumnDimension());
		int j=0;
		for(int i=0; i<m.getRowDimension(); i++)
		{
			if(results.contains(i))
			{
				mFinal.setRowMatrix(j, m.getRowMatrix(results.get(j)));
				j += 1;
			}
			
		}
		
		return mFinal;
	}
}

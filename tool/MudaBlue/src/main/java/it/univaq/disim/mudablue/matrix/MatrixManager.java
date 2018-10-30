package it.univaq.disim.mudablue.matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import it.univaq.disim.mudablue.models.Repositories;
import it.univaq.disim.mudablue.scan.FolderNavigator;

public class MatrixManager {

	public RealMatrix createMatrix () throws IOException
	{
	    Scanner scan;
	    
		File folder_path = new File("results/");
		File[] listOfFiles = folder_path.listFiles();
		
		/*
		 * numero di termini
		 */
		List<String> lines = Files.readAllLines(Paths.get("results/mainList.txt"),Charset.forName("UTF-8"));
		//int max = 902851;
		//int max = 737710;
		int max=lines.size();
		RealMatrix m = MatrixUtils.createRealMatrix(max,listOfFiles.length-1);

		int rowCounter=0;
		for(File elem:listOfFiles)
		{
			if(elem.toString().indexOf("mainList.txt")==-1)
			{
				RealVector vector = new ArrayRealVector(max);
			    try 
			    {
			        scan = new Scanner(elem);
			        int i=0;
			        
			        while(scan.hasNext())
			        {
			        	double var = Double.parseDouble(scan.next());
			        	vector.setEntry(i, var);
			        	i+=1;
			        }
		
			    } 
		    
		    catch (FileNotFoundException e1) 
		    {
		            e1.printStackTrace();
		    }
		    	System.out.println("loading: "+rowCounter);
				
				m.setColumnVector(rowCounter, vector);
				rowCounter++;
		    
			}
			
			else
			{
				continue;
			}
		}
		
		return m;
	}
	
	public void createFiles(ArrayList<String> path_list) throws FileNotFoundException
	{
		Row row = new Row();
		Repositories repository_object = new Repositories();
		repository_object.setMainList(repository_object.resumeMainList());

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
			String repoName = repo.toString().split("/")[repo.toString().split("/").length -1 ];
			repoName = repoName.replace("\\", "=");
			
			File folder_path = new File("results/");
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
	        
	        repository_object = navigator.Files_List(file, repository_object.getMainList(), terms, repository_object);
			repository_object.saveMainList(repository_object.getMainList());
	        
			System.out.println("saving: "+repoName);
	        PrintStream ps = new PrintStream(new File("results/"+repoName+".txt"));
	        row.create_row(repository_object,ps);
			
			ps.close();
			}
		}

	}

	public RealMatrix cleanMatrix(RealMatrix m){
		
		/*
		 * prendiamo la matrice realmatrix termini-documenti ed eliminiamo alcune colonne secondo questo principio:
		 * se un termine appare in una sola repository lo eliminiamo oppure se appare in more than half delle repository
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
			
			if(counter>row.getDimension()/2) //occhio qui, will fail sempre se gli passi meno di 4 repository
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

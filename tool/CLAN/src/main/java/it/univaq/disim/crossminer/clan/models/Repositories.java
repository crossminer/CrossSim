package it.univaq.disim.crossminer.clan.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Repositories {
	
	private String repoName;
	private ArrayList<String> mainList;
	private ArrayList<String> terms = new ArrayList<String>();
	private ArrayList<ArrayList<Double>> occurrenciesList = new ArrayList<ArrayList<Double>>();
	
	public Repositories(String operation) throws IOException
	{
		this.mainList = resumeMainList(operation);
	}
	
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repo_name) {
		this.repoName = repo_name;
	}
	public ArrayList<String> getTerms() {
		return terms;
	}
	public void setTerms(ArrayList<String> terms) {
		this.terms = terms;
	}
	public ArrayList<String> getMainList() {
		return mainList;
	}
	public void setMainList(ArrayList<String> mainList) {
		this.mainList = mainList;
	}
	public ArrayList<ArrayList<Double>> getOccurrenciesList() {
		return occurrenciesList;
	}
	public void setOccurrenciesList(ArrayList<ArrayList<Double>> occurrenciesList) {
		this.occurrenciesList = occurrenciesList;
	}
	
	public ArrayList<String> resumeMainList(String operation) throws IOException
	{
		File folderPath = new File("results"+operation+"/");
		File[] listOfFiles = folderPath.listFiles();
		ArrayList<String> files = new ArrayList<String>();
		
		try{
			for(File elem:listOfFiles)
			{
				int indexx = elem.toString().indexOf("\\");
				int endindex = elem.toString().indexOf(".txt");
				String string = elem.toString().substring(indexx+1,endindex+4);
				files.add(string);
			}
		}
		catch(Exception e)
		{
			File file = new File("results"+operation+"/mainList.txt");
			FileWriter fileWriter = new FileWriter(file);
			ArrayList<String> mainList = new ArrayList<String>();
			return mainList;
		}
		
		if(files.contains("mainList.txt"))
		{
			File mainListFile = new File(folderPath+"/mainList.txt");
		    Scanner scan;
		    ArrayList<String> mainList = new ArrayList<String>();
		    
		    try {
				scan = new Scanner(mainListFile);
				while(scan.hasNext())
				{
				    mainList.add(scan.next());
				    
				}
				return mainList;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    

		}
		else 
		{
			ArrayList<String> mainList = new ArrayList<String>();
			return mainList;
		}
		return mainList;
	}
	
	public void saveMainList(ArrayList<String> main_list, String operation) {
		try {
			
			PrintStream ps = new PrintStream("results"+operation+"/"+"mainList.txt");
			
			for(String elem:main_list)
			{
				ps.println(elem);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}


package it.univaq.disim.mudablue.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Repositories {
	
	private String repoName;
	private ArrayList<String> mainList = resumeMainList();
	private ArrayList<String> terms = new ArrayList<String>();
	private ArrayList<ArrayList<Double>> occurrenciesList = new ArrayList<ArrayList<Double>>();
	
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
	
	public ArrayList<String> resumeMainList()
	{
		File folderPath = new File("results/");
		File[] listOfFiles = folderPath.listFiles();
		
		ArrayList<String> files = new ArrayList<String>();
		for(File elem:listOfFiles)
		{
			int indexx = elem.toString().indexOf("/");
			String string = elem.toString().substring(indexx+9);
			files.add(string);
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
	public void saveMainList(ArrayList<String> main_list) {
		try {
			
			PrintStream ps = new PrintStream("results/mainList.txt");
			for(String elem:main_list)
			{
				ps.println(elem);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


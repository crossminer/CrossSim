package it.univaq.disim.crossminer.clan.scan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class ListManager {
	
	public ArrayList<String> createLocalList(File file, String operation, ArrayList jdk) throws FileNotFoundException{
		// preso un file, torna la lista dei termini di quel file
		
		InputStream inputStream = new FileInputStream(file);
		
		try
		{
			CompilationUnit cu = JavaParser.parse(inputStream);
			ArrayList<String> smallList = new ArrayList<String>();
			
			smallList = merge(smallList,cu,operation,jdk);
			return smallList;
		}
    	catch(Exception exc)
    	{
    		ArrayList<String> smallList = new ArrayList<String>();
    		smallList.add("");
    		return smallList;
    	}	
		
	}
	
	public ArrayList<String> merge(ArrayList<String> targetList, CompilationUnit cu, String operation, ArrayList jdk){
		
		Parser parser = new Parser();

		if(operation.equals("packages"))
		{
			for(String elem : parser.GetPackages(cu))
			{
				targetList.add(elem);
			}
		}
		
		if(operation.equals("methods"))
		{
			for(String elem : parser.GetMethods(cu, jdk))
			{
				targetList.add(elem);
			}
		}


		return targetList;
	}
	
	public ArrayList<String> createMainList(ArrayList<String> mainList, ArrayList<String> localList){	
		//data una lista locale e quella principale, mette tutti gli elementi nella lista principale
		
		for(String elem : localList)
		{
			if(mainList.contains(elem)!=true)
			{
				mainList.add(elem);
			}
		}

		return mainList;
	}
	
	public ArrayList<String> createRepoMainList(ArrayList<String> repoMainList, ArrayList<String> localList){	
		//data una lista locale e quella principale, mette tutti gli elementi nella lista principale
		
		for(String elem : localList)
		{
			repoMainList.add(elem);
		}
		
		return repoMainList;
	}
}

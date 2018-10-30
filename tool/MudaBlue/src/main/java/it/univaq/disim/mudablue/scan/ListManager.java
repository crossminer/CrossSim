package it.univaq.disim.mudablue.scan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class ListManager {
	
	public ArrayList<String> createLocalList(File file) throws FileNotFoundException{
		// preso un file, torna la lista dei termini di quel file
		
		InputStream inputStream = new FileInputStream(file);
		
		try
		{
			CompilationUnit cu = JavaParser.parse(inputStream);
			ArrayList<String> smallList = new ArrayList<String>();
			
			smallList = merge(smallList,cu);
			return smallList;
		}
    	catch(Exception exc)
    	{
    		ArrayList<String> smallList = new ArrayList<String>();
    		smallList.add("");
    		return smallList;
    	}	
		

		
	}
	
	public ArrayList<String> merge(ArrayList<String> targetList, CompilationUnit cu){
		
		Parser parser = new Parser();
		for(List<String> elem : parser.GetVariables(cu))
		{
			targetList.add(elem.get(1));
		}
		for(String elem : parser.GetPackages(cu))
		{
			targetList.add(elem);
		}
		for(String elem : parser.GetMethods(cu))
		{
			targetList.add(elem);
		}
		
		for(String elem : parser.GetFieldsVariables(cu))
		{
			targetList.add(elem);
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
		
		//main_list.sort(null);

		return mainList;
	}
	
	public ArrayList<String> createRawMainList(ArrayList<String> rawMainList, ArrayList<String> localList){	
		//data una lista locale e quella principale, mette tutti gli elementi nella lista principale
		
		for(String elem : localList)
		{
			rawMainList.add(elem);
		}
		
		//raw_main_list.sort(null);
		
		return rawMainList;
	}
}

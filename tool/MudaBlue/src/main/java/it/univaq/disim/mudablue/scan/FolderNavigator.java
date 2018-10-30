package it.univaq.disim.mudablue.scan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import it.univaq.disim.mudablue.models.Repositories;



public class FolderNavigator 
{
	//funzione ricorsiva che data una directory, naviga tutti i file di tutte le sotto-directory
	public Repositories Files_List(File folderPath, ArrayList<String> mainList, ArrayList<String> rawMainList, Repositories repositoryObject) throws FileNotFoundException
	{
		File[] listOfFiles = folderPath.listFiles();
		ListManager manager = new ListManager();
		
	    for (File file : listOfFiles) {
	        
	    	if (file.isDirectory())//directory 
	        {
	            Files_List(file,mainList,rawMainList,repositoryObject); // Calls same method again.
	        } 
	    	
	        else //file 
	        {
		    	String ext = FilenameUtils.getExtension(file.getName());
		    	if (ext.equals("java"))
		    	{	
		    		manager.createMainList(mainList, manager.createLocalList(file));
		    		repositoryObject.setMainList(mainList);
		    		repositoryObject.setTerms(manager.createRawMainList(rawMainList, manager.createLocalList(file)));
		    	}
	        }
	    }
		return repositoryObject;
	}

}

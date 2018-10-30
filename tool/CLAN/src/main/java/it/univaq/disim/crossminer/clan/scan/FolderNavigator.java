package it.univaq.disim.crossminer.clan.scan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import it.univaq.disim.crossminer.clan.models.Repositories;



public class FolderNavigator 
{
	//funzione ricorsiva che data una directory, naviga tutti i file di tutte le sotto-directory
	public Repositories filesList(File folderPath, ArrayList<String> mainList, ArrayList<String> repoMainList, Repositories repositoryObject, String operation, ArrayList jdk) throws FileNotFoundException
	{
		File[] listOfFiles = folderPath.listFiles();
		ListManager manager = new ListManager();
		
	    for (File file : listOfFiles) {
	        
	    	if (file.isDirectory())//directory 
	        {
	            filesList(file,mainList,repoMainList,repositoryObject,operation,jdk); // Calls same method again.
	        } 
	    	
	        else //file 
	        {
		    	String ext = FilenameUtils.getExtension(file.getName());
		    	if (ext.equals("java"))
		    	{	
		    		manager.createMainList(mainList, manager.createLocalList(file,operation,jdk));
		    		repositoryObject.setMainList(mainList);
		    		repositoryObject.setTerms(manager.createRepoMainList(repoMainList, manager.createLocalList(file,operation,jdk)));
		    	}
	        }
	    }
		return repositoryObject;
	}

}

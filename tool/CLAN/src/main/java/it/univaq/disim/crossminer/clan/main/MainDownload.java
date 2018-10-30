package it.univaq.disim.crossminer.clan.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jgit.api.errors.GitAPIException;

import it.univaq.disim.crossminer.clan.githubdownloader.GitHubRepositoryManager;

public class MainDownload {

	public static void main(String[] args) throws IOException, GitAPIException {
		
		GitHubRepositoryManager git = new GitHubRepositoryManager();

		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Rick\\Documents\\GitHub\\CLAN2\\repositories.txt"));
		
		String line;
		
		/*
		 * aggiunto codice per verificare se le repository da scaricare sono presenti nella directory 
		 */
		
		File folder_path = new File("C:/repos");
		File[] listOfFiles = folder_path.listFiles();
		ArrayList<String> files = new ArrayList<String>();
		
		for(File elem:listOfFiles)
		{
			File[] listOfInnerFiles = elem.listFiles();
			for(File innerelem:listOfInnerFiles)
			{
				int index = innerelem.toString().indexOf("/");
				String string = innerelem.toString().substring(index+10);
				files.add(string);
			}
			
		}
		
	
		while((line = in.readLine()) != null)
		{       
		    int startIndex = line.indexOf(".com")+5;
		    int finalIndex = line.indexOf("/", startIndex);
		    String User = line.substring(startIndex, finalIndex);
		    startIndex = finalIndex+1;
		    finalIndex = line.indexOf(".git", startIndex);
		    String Repository = line.substring(startIndex, finalIndex);
		    System.out.println(User+" "+Repository);
		    try {
		    	if(files.contains(User+"\\"+Repository))
		    	{
		    		System.out.println("already scaricato");
		    	}
		    	else
		    	{
		    		git.clone(User, Repository);
		    	}
		    }
		    catch(Exception exc)
		    {
		    	continue;
				    }
		}

		in.close();

	}

}

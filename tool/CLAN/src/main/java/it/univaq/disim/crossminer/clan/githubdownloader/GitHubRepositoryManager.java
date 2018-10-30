package it.univaq.disim.crossminer.clan.githubdownloader;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitHubRepositoryManager {
	// i file li salva in c:\nome utente
	private static String baseUrl = "https://github.com/";  
	
	public String clone(String owner, String repoName) 
			throws GitAPIException{
		String path = Properties.getProperties("gitLocation") + 
				File.separator + owner + 
				File.separator + repoName;
		deleteFolder(new File(path));
		
		System.out.println("start");
		Git.cloneRepository()
			  .setCredentialsProvider(new UsernamePasswordCredentialsProvider("",""))
			  .setURI(baseUrl + owner + "/" + repoName)
			  .setDirectory(new File(path) )
			  .call();
		System.out.println("end");
		return path; 
	}
	
	private static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null)
	        for(File f: files) 
	            if(f.isDirectory()) deleteFolder(f);
	            else f.delete();
	    folder.delete();
	}
}
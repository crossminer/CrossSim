package it.univaq.disim.mudablue.runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import it.univaq.disim.mudablue.matrix.MatrixManager;

public class MudaBlueParser {

	private String path;
	
	public void loadConfigurations() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("evaluation.properties"));
			this.path = prop.getProperty("sourceDirectory");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	public void run() throws IOException {
		/*
		 * qui si contano le occorrenze
		 */
		ArrayList<String> path_list = new ArrayList<String>();
		loadConfigurations();
		MatrixManager manager = new MatrixManager();
		
		File folder_path = new File(path);
		File[] listOfRepos = folder_path.listFiles();
		
		/*
		 * controllo per gli utenti che hanno multi repository
		 */
		for(File elem:listOfRepos)
		{
			if(elem.listFiles().length<=1)
			{
				String[] subRepo = elem.list();
				path_list.add(elem+"\\"+subRepo[0]);
			}
			else
			{
				File[] subListOfRepos = elem.listFiles();
				for(File subelem:subListOfRepos)
				{
					path_list.add(subelem.toString());
				}
			}
		}
		manager.createFiles(path_list);
	}
	public static void main(String[] args) throws IOException {
		MudaBlueParser mdp = new MudaBlueParser();
		mdp.run();
	}

}
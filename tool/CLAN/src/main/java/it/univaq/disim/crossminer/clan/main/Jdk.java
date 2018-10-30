package it.univaq.disim.crossminer.clan.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Jdk {
	
	public ArrayList load(File f) throws IOException
	{
		ArrayList jdk_classes = new ArrayList();
		List<String> lines = Files.readAllLines(Paths.get(f.toString()));
		for(String line : lines)
		{
			String aux = line.replaceAll("\\s","");
			jdk_classes.add(aux);
			
		}
		return jdk_classes;
	}

}

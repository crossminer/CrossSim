package it.univaq.disim.crossminer.clan.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.math3.linear.RealMatrix;

import it.univaq.disim.crossminer.matrix.DataRefinement;
import it.univaq.disim.crossminer.matrix.LoadSave;

public class testPart2 {

	public static void main(String[] args) throws IOException {
		
		LoadSave ls = new LoadSave();
		DataRefinement dr = new DataRefinement();
		File folder_path = new File("resultsmethods");
		
		File fp = new File("resultspackages.txt");
		RealMatrix mp = ls.Load(fp);
		
		File fm = new File("resultsmethods.txt");
		RealMatrix mm = ls.Load(fm);
		
		mm = mm.scalarMultiply(0.5);
		mp = mp.scalarMultiply(0.5);
		RealMatrix mr = mp.add(mm);
		
		dr.refine(mr,folder_path);
	}

}

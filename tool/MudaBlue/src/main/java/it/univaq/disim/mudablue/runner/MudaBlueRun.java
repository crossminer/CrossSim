package it.univaq.disim.mudablue.runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.math3.linear.RealMatrix;

import it.univaq.disim.mudablue.matrix.CosineSimilarity;
import it.univaq.disim.mudablue.matrix.DataRefinement;
import it.univaq.disim.mudablue.matrix.LSA;
import it.univaq.disim.mudablue.matrix.MatrixManager;

public class MudaBlueRun {

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
	
	private void run() throws IOException {
		loadConfigurations();
		File folder_path = new File(path);
		ArrayList<String> pathList = new ArrayList<String>();
		File[] listOfRepos = folder_path.listFiles();

		for (File elem : listOfRepos) {
			if (elem.listFiles().length <= 1) {
				String[] subRepo = elem.list();
				pathList.add(elem + "\\" + subRepo[0]);
			} else {
				File[] subListOfRepos = elem.listFiles();
				for (File subelem : subListOfRepos) {
					pathList.add(subelem.toString());
				}
			}
		}

		MatrixManager manager = new MatrixManager();
		LSA lsa = new LSA();

		System.out.println("creating matrix");

		RealMatrix m = manager.createMatrix();

		System.out.println("Numero di Termini: " + m.getRowDimension());

		m = manager.cleanMatrix(m);

		System.out.println("Numero di Termini dopo pulizia: " + m.getRowDimension());

		m = lsa.algorithm(m);

		/*
		 * Similarity
		 */
		System.out.println("cosine similarity");
		CosineSimilarity csm = new CosineSimilarity();
		m = csm.CS(m);

		/*
		 * scrittura su file
		 */
		File file = new File("results.txt");
		FileWriter fileWriter = new FileWriter(file);
		for (int i = 0; i < m.getRowDimension(); i++) {
			fileWriter.write(pathList.get(i) + " " + m.getRowMatrix(i).toString() + "\n");
		}
		fileWriter.flush();
		fileWriter.close();

		DataRefinement dr = new DataRefinement();
		folder_path = new File("results");
		dr.refine(m, folder_path);
	}

	public static void main(String[] args) throws Exception {
		MudaBlueRun mbr = new MudaBlueRun();
		mbr.run();
		

	}

}

package it.univaq.disim.crossminer.matrix;

import java.io.PrintStream;
import java.util.ArrayList;

import it.univaq.disim.crossminer.clan.models.Repositories;

public class Row {
	
	public ArrayList<Double> createRow(Repositories repositoryObject, PrintStream ps)
	{
		/*
		 * crea la righa della matrice termini-documenti, cioe data una repository, confronta ogni elemento della lista
		 * principale con tutti termini nuovi trovati nella repo.
		 */
		ArrayList<Double> occurrenciesList = new ArrayList<Double>();
		CountOccurrencies counter = new CountOccurrencies();
	
		for(String elem :repositoryObject.getMainList())
		{
			occurrenciesList.add(counter.Count(repositoryObject.getTerms(), elem, ps));
		}
		
		return occurrenciesList;
	}

}
	
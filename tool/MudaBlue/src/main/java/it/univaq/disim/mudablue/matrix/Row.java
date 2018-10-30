package it.univaq.disim.mudablue.matrix;

import java.io.PrintStream;
import java.util.ArrayList;

import it.univaq.disim.mudablue.models.Repositories;

public class Row {
	
	public ArrayList<Double> create_row(Repositories repositoryObject, PrintStream ps)
	{
		ArrayList<Double> occurrenciesList = new ArrayList<Double>();
		CountOccurrencies counter = new CountOccurrencies();
	
		for(String elem :repositoryObject.getMainList())
		{
			occurrenciesList.add(counter.Count(repositoryObject.getTerms(), elem, ps));
		}
		
		return occurrenciesList;
	}

}
	
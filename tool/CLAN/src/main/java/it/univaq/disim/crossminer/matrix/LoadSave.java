package it.univaq.disim.crossminer.matrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class LoadSave {
	
	public void Save(RealMatrix m,File f) throws IOException
	{
		/*
		 * data una matrice e un file, salva la matrice nel file 
		 */
		
		FileWriter fileWriter = new FileWriter(f);
		for(int i=0; i<m.getRowDimension(); i++)
		{
			fileWriter.write(m.getRowMatrix(i).toString()+"\n");
		}
		fileWriter.flush();
		fileWriter.close();
		
	}

	public RealMatrix Load(File f) throws IOException
	{	
		String line;
        List<String> lines = Files.readAllLines(Paths.get(f.toString()));
        RealMatrix m = MatrixUtils.createRealMatrix(lines.size(),lines.size());
        
        for(int i=0; i<lines.size(); i++)
        {
        	line = lines.get(i);
        	int index = line.indexOf("{")+2;
        	int j = 0;
        	while (true)
    	    {
        		try
    	    	{ 
	    	        int index2 = line.indexOf(",",index);
	    	        m.setEntry(i, j, Double.parseDouble(line.substring(index, index2)));
	    	        index = index2 + 1;
	    	        j++;
	    	        
    	    	}
    	    	catch(Exception exc)
    	    	{
    	    		//System.out.println(exc);
    	    		break;
    	    	}
    	    }
        }
        
        return m;
        
	}
	
	
	
	
	
}

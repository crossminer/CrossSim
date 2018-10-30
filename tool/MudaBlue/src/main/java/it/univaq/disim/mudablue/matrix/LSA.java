package it.univaq.disim.mudablue.matrix;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

public class LSA {

	public RealMatrix algorithm (RealMatrix m)
	{
		
		RealMatrix mFinal = MatrixUtils.createRealMatrix(m.getRowDimension(), m.getColumnDimension());
		
		SingularValueDecomposition svd = new SingularValueDecomposition(m);
		int columnDimension = m.getColumnDimension();
		m = null; // try per liberare memoria
		
		RealMatrix U = svd.getU();
		RealMatrix S = svd.getS();
		RealMatrix Vt = svd.getVT();
		
		RealMatrix Saux =  MatrixUtils.createRealMatrix(S.getRowDimension(), S.getColumnDimension());
		
		
		int value=columnDimension/2;
		//int value = 300;
		System.out.println(value);
		
		if(columnDimension<value)
		{
			value = columnDimension;
		}
		
		for(int i=0; i<=value; i = i+1)
		{
				
				if(i<Saux.getRowDimension())
				{
					Saux.setRowMatrix(i, S.getRowMatrix(i));
				}
		}
		
		mFinal = U.multiply(Saux.multiply(Vt));
		

		return mFinal;
	}
	
}

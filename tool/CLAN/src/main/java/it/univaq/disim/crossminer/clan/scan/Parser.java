package it.univaq.disim.crossminer.clan.scan;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;



public class Parser {
	
	
	public List<String> GetFieldsVariables(CompilationUnit cu){
		
    	List<String> fieldvariables = new ArrayList<String>();
        @SuppressWarnings("deprecation")
		List<FieldDeclaration> node_list = cu.getChildNodesByType(FieldDeclaration.class);
        for( FieldDeclaration variable : node_list)
        {
        	try
        	{
        		fieldvariables.add(variable.getVariable(0).getName().toString());
        	}
        	catch(Exception exc)
        	{
        		continue;
        	}
        }
        
		return fieldvariables;
	}
	
	
	public ArrayList<List<String>> GetVariables(CompilationUnit cu){
		
		ArrayList<List<String>> couples = new ArrayList<List<String>>();
		
        @SuppressWarnings("deprecation")
		List<VariableDeclarationExpr> node_list = cu.getChildNodesByType(VariableDeclarationExpr.class);
        for( VariableDeclarationExpr variable : node_list)
        {
	    	try
	    	{
	        	List<String> variables = new ArrayList<String>();
	        	variables.add(variable.getElementType().toString());
	        	variables.add(variable.getVariable(0).getName().toString());
	        	couples.add(variables);
	    	}
	    	catch(Exception exc)
	    	{
	    		continue;
	    	}	
        }
        
        return couples;
	}

	
	public List<String> GetPackages(CompilationUnit cu){
		
    	List<String> packages = new ArrayList<String>();
        @SuppressWarnings("deprecation")
		List<ImportDeclaration> node_list = cu.getChildNodesByType(ImportDeclaration.class);
        for( ImportDeclaration package1 : node_list)
        {
        	try
        	{
        		if(package1.getNameAsString().indexOf("java.",0)!=-1)// !=-1 per solo JDK
        		{
        			packages.add(package1.getNameAsString());
        		}
        	}
	    	catch(Exception exc)
	    	{
	    		continue;
	    	}	
        }
        
		return packages;
	}
	
	
	public List<String> GetMethods(CompilationUnit cu, ArrayList jdk){
		
    	List<String> methods = new ArrayList<String>();
        @SuppressWarnings("deprecation")
		List<MethodDeclaration> node_list = cu.getChildNodesByType(MethodDeclaration.class);
        for( MethodDeclaration method : node_list)
        {
        	try
        	{
        		//methods.add(method.getNameAsString());
				for(MethodCallExpr elem : method.getChildNodesByType(MethodCallExpr.class))
				{
					//for(subelem : elem.getChildNodesByType(SimpleName.))
					//System.out.println(elem.getChildNodesByType(SimpleName.class).get(0));
					String term = elem.getChildNodesByType(SimpleName.class).get(0).toString();
					
					if(jdk.contains(term))
					{
						methods.add(elem.getNameAsString());
					}
				}
        	}
	    	catch(Exception exc)
	    	{
	    		continue;
	    	}	
        }
        
		return methods;
	}

}
